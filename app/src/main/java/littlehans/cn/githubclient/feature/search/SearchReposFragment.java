package littlehans.cn.githubclient.feature.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.net.UnknownHostException;
import java.util.Arrays;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.model.ErrorModel;
import littlehans.cn.githubclient.model.entity.SearchRepos;
import littlehans.cn.githubclient.ui.adapter.QuickSearchAdapter;
import littlehans.cn.githubclient.ui.adapter.SearchReposSortAdapter;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;
import okhttp3.Headers;

/**
 * Created by LittleHans on 2016/10/1.
 */
public class SearchReposFragment extends NetworkFragment<SearchRepos>
    implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

  private static final String TAG = "SearchReposFragment";

  String sort[] = { "Best Match", "Most Start", "Most Forks" };
  String language[] = { "Java", "JavaScript", "Object-C", "C++/C", "Php" };

  private LinearLayoutManager mLinearLayoutManager;
  private GridLayoutManager mGridLayoutManger;
  private QuickSearchAdapter mQuickSearchAdapter;
  String query;
  @BindView(R.id.recycler_search) RecyclerView mRecycler;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.checkbox_sort) CheckBox mCheckboxSort;
  @BindView(R.id.checkbox_language) CheckBox mCheckboxLanguage;
  @BindView(R.id.recycler_tag_sort) RecyclerView mRecyclerTagSort;

  private int mCurrentPage = 1;
  private int mLastPage;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initUI();
    query = getArguments().getString("query");
    loadData(query);

  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_search_repos;
  }

  @Override public void respondSuccess(final SearchRepos data) {

    mRecycler.post(new Runnable() {
      @Override public void run() {
        if (mCurrentPage == 1) {
          Log.d(TAG, "respondSuccess: " + "mCurrentPage:" + mCurrentPage);
          mLinearLayoutManager = new LinearLayoutManager(getActivity());
          mRecycler.setLayoutManager(mLinearLayoutManager);
          mQuickSearchAdapter = new QuickSearchAdapter(data.items);
          mQuickSearchAdapter.openLoadMore(30);
          mRecycler.setAdapter(mQuickSearchAdapter);
          mQuickSearchAdapter.setOnLoadMoreListener(SearchReposFragment.this);
          mCurrentPage++;
          return;
        }

        if (mCurrentPage > mLastPage) {
          mQuickSearchAdapter.loadComplete();
        } else {
          mQuickSearchAdapter.addData(data.items);
          mCurrentPage++;
        }
      }
    });
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override public void onLoadMoreRequested() {
    loadData(query);
  }

  @Override public void onRefresh() {
    mCurrentPage = 1;
    loadData(query);
  }

  @Override public void respondWithError(Throwable t) {
    Log.d(TAG, "respondWithError: " + t.getMessage());
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override public void respondHeader(Headers headers) {
    PageLink pageLink = new PageLink(headers.values("Link").get(0));
    if (pageLink.getLastPage() != 0) {
      mLastPage = pageLink.getLastPage();
    }
    Log.d(TAG, "respondHeader: " + mLastPage);
  }

  @Override public void errorSocketTimeout(Throwable t, ErrorModel errorModel) {
    super.errorSocketTimeout(t, errorModel);
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override public void errorUnknownHost(UnknownHostException e, ErrorModel errorModel) {
    super.errorUnknownHost(e, errorModel);
    mSwipeRefreshLayout.setRefreshing(false);
  }

  public static Fragment create() {
    return new SearchReposFragment();
  }

  private void initUI() {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);

    mGridLayoutManger = new GridLayoutManager(getActivity(), 3);
    mRecyclerTagSort.setLayoutManager(mGridLayoutManger);
    mRecyclerTagSort.setAdapter(
        new SearchReposSortAdapter(R.layout.item_check, Arrays.asList(sort)));
    mSwipeRefreshLayout.setRefreshing(true);
    mSwipeRefreshLayout.setOnRefreshListener(this);
  }

  private void loadData(String query) {
    networkQueue().enqueue(
        GithubService.createSearchService().repositories(query, null, null, mCurrentPage));
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);

  }

  //@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
  //  inflater.inflate(R.menu.search, menu);
  //  MenuItem searchItem = menu.findItem(R.id.action_search);
  //  SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
  //  searchView.setQueryHint(getString(R.string.query_hint));
  //  super.onCreateOptionsMenu(menu, inflater);
  //}
}
