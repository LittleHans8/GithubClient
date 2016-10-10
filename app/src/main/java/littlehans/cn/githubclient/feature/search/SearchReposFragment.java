package littlehans.cn.githubclient.feature.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.Arrays;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.SearchService;
import littlehans.cn.githubclient.data.SearchReposServer;
import littlehans.cn.githubclient.model.entity.Search;
import littlehans.cn.githubclient.model.entity.Search.Items;
import littlehans.cn.githubclient.network2.PagePaginator;
import littlehans.cn.githubclient.network2.Pagination.PageEmitter;
import littlehans.cn.githubclient.network2.callback.PaginatorCallback;
import littlehans.cn.githubclient.ui.adapter.QuickSearchAdapter;
import littlehans.cn.githubclient.ui.adapter.SearchReposSortAdapter;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;
import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by LittleHans on 2016/10/1.
 */
public class SearchReposFragment extends NetworkFragment<Search>
    implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

  private static final String TAG = "SearchReposFragment";

  String sort[] = { "Best Match", "Most Start", "Most Forks" };
  String language[] = { "Java", "JavaScript", "Object-C", "C++/C", "Php" };

  private SearchService mSearchService;
  private View mLoadCompleteView;

  private LinearLayoutManager mLinearLayoutManager;
  private GridLayoutManager mGridLayoutManger;
  private QuickSearchAdapter mQuickSearchAdapter;
  private Call<Search> mCall;

  @BindView(R.id.recycler_search) RecyclerView mRecycler;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.checkbox_sort) CheckBox mCheckboxSort;
  @BindView(R.id.checkbox_language) CheckBox mCheckboxLanguage;
  @BindView(R.id.recycler_tag_sort) RecyclerView mRecyclerTagSort;
  private int mCurrentPage = 1;
  private int mLastPage = 100;
  private boolean isErr;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mSearchService = GithubService.createSearchService();
    mCall = mSearchService.repositories("bootstrap", null, null, 1);

    mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);

    mGridLayoutManger = new GridLayoutManager(getActivity(), 3);
    mRecyclerTagSort.setLayoutManager(mGridLayoutManger);
    mRecyclerTagSort.setAdapter(
        new SearchReposSortAdapter(R.layout.item_check, Arrays.asList(sort)));
    mSwipeRefreshLayout.setRefreshing(true);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    onRefresh();
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.search, menu);
    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
    searchView.setQueryHint(getString(R.string.query_hint));
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_search_repos;
  }

  public static Fragment create() {
    return new SearchReposFragment();
  }

  @Override public void onLoadMoreRequested() {
    mRecycler.post(new Runnable() {
      @Override
      public void run() {
        if (mCurrentPage >= mLastPage) {
          mQuickSearchAdapter.loadComplete();
          if (mLoadCompleteView == null) {
            mLoadCompleteView = new TextView(getActivity());
          }
          mQuickSearchAdapter.addFooterView(mLoadCompleteView);
        } else {
          if (isErr) {
            mQuickSearchAdapter.addData(SearchReposServer.getDataByPage(mCurrentPage++));
          } else {
            isErr = true;
            mQuickSearchAdapter.showLoadMoreFailedView();
          }
        }
      }
    });

  }

  @Override public void startRequest() {
    Log.d(TAG, "startRequest: ");
  }

  @Override public void respondSuccess(Search data) {
    Log.d(TAG, "respondSuccess: " + data.items.toString());
    mLinearLayoutManager = new LinearLayoutManager(getActivity());
    mRecycler.setLayoutManager(mLinearLayoutManager);
    mRecycler.setAdapter(mQuickSearchAdapter);
    mQuickSearchAdapter.notifyDataSetChanged();
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override public void respondWithError(Throwable t) {
    Log.d(TAG, "respondWithError: " + t.getMessage());
  }

  @Override public void endRequest() {
    Log.d(TAG, "endRequest: ");
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override public void respondHeader(Headers headers) {

  }

  @Override public void onRefresh() {
    mCall = GithubService.createSearchService().repositories("bootstrap", null, null, 1);
    networkQueue().enqueue(mCall);
  }

  public void loadDataByPage(int page) {
    mCall = GithubService.createSearchService().repositories("bootstrap", null, null, page);
    networkQueue().enqueue(mCall);
  }

  private void initAdapter() {
    mQuickSearchAdapter = new QuickSearchAdapter(); // page
    mQuickSearchAdapter.openLoadAnimation();
    mQuickSearchAdapter.openLoadMore(30);
    mRecycler.setAdapter(mQuickSearchAdapter);
    mLastPage =  SearchReposServer.getLastPage();
    mQuickSearchAdapter.setOnLoadMoreListener(this);
  }
}
