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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,SearchActivity.onSearchListener {

  private static final String TAG = "SearchReposFragment";
  private LinearLayoutManager mLinearLayoutManager;
  private QuickSearchAdapter mQuickSearchAdapter;
  private String mQuery;
  @BindView(R.id.recycler_search) RecyclerView mRecycler;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

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
    loadData(mQuery);
  }

  @Override public void onRefresh() {
    mCurrentPage = 1;
    loadData(mQuery);
  }

  @Override public void respondWithError(Throwable t) {
    Log.d(TAG, "respondWithError: " + t.getMessage());
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override public void respondHeader(Headers headers) {
    List<String> links = headers.values("Link");


    if (!links.isEmpty()) {
      String link = links.get(0);
      PageLink pageLink = new PageLink();
      if (pageLink.getLastPage() != 0) {
        mLastPage = pageLink.getLastPage();
      }
      Log.d(TAG, "respondHeader: " + mLastPage);
    }
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
    mSwipeRefreshLayout.setOnRefreshListener(this);
  }

  private void loadData(String query) {
    mSwipeRefreshLayout.setRefreshing(true);
    networkQueue().enqueue(
        GithubService.createSearchService().repositories(query, null, null, mCurrentPage));
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    SearchActivity searchActivity = (SearchActivity)context;
    searchActivity.setOnSearchListener(this);
  }

  @Override
  public void errorNotFound(ErrorModel errorModel) {
    super.errorNotFound(errorModel);
  }

  @Override
  public void onSearch(String query) {
    mQuery = query;
    onRefresh();
  }
}
