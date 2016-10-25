package littlehans.cn.githubclient.feature.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import java.net.UnknownHostException;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.feature.repos.ReposActivity;
import littlehans.cn.githubclient.model.ErrorModel;
import littlehans.cn.githubclient.model.entity.SearchRepos;
import littlehans.cn.githubclient.ui.adapter.SearchReposAdapter;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;
import okhttp3.Headers;

/**
 * Created by littlehans on 2016/10/1.
 */
public class SearchReposFragment extends NetworkFragment<SearchRepos>
    implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,
    SearchActivity.onSearchListenerA {

  private static final String TAG = "SearchReposFragment";
  @BindView(R.id.user_recycler_search) RecyclerView mRecycler;
  @BindView(R.id.user_layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  private LinearLayoutManager mLinearLayoutManager;
  private SearchReposAdapter mQuickSearchAdapter;
  private String mQuery;
  private int mCurrentPage = 1;
  private int mLastPage;

  public static Fragment create() {
    return new SearchReposFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    SearchActivity searchActivity = (SearchActivity) context;
    searchActivity.setOnSearchListenerA(this);
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_search_repos;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initUI();
  }

  @Override public void respondSuccess(final SearchRepos data) {

    updateRecyclerView(data);
    mSwipeRefreshLayout.setRefreshing(false);
  }

  private void updateRecyclerView(final SearchRepos data) {
    mRecycler.post(new Runnable() {
      @Override public void run() {
        if (mCurrentPage == 1) {
          mLinearLayoutManager = new LinearLayoutManager(getActivity());
          mRecycler.setLayoutManager(mLinearLayoutManager);
          mQuickSearchAdapter = new SearchReposAdapter(data.items);
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
  }

  @Override public void onLoadMoreRequested() {
    loadData(mQuery);
  }

  @Override public void onRefresh() {
    mCurrentPage = 1;
    loadData(mQuery);
  }

  @Override public void respondWithError(Throwable t) {
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override public void respondHeader(Headers headers) {
    List<String> links = headers.values("Link");

    if (!links.isEmpty()) {
      String link = links.get(0);
      PageLink pageLink = new PageLink(link);
      mLastPage = pageLink.getLastPage();
      Log.d(TAG, "mLastPage: " + mLastPage);
    }

    List<String> remains = headers.values("X-RateLimit-Remaining");

    if (!remains.isEmpty()) {
      String remain = remains.get(0);
      Log.d(TAG, "X-RateLimit-Remaining: " + remain);
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

  private void initUI() {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    mRecycler.addOnItemTouchListener(new OnItemClickListener() {
      @Override public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        SearchRepos.Items repos = (SearchRepos.Items) baseQuickAdapter.getItem(i);
        String owner = repos.owner.login;
        String repo = repos.name;
        String defaultBranch = repos.default_branch;
        Intent intent = new Intent(getActivity(), ReposActivity.class);
        intent.putExtra("owner", owner);
        intent.putExtra("repo", repo);
        intent.putExtra("defaultBranch", defaultBranch);
        //Bundle bundle = intent.getExtras();
        //bundle.putString("owner", owner);
        //bundle.putString("repo", repo);
        //bundle.putString("defaultBranch", defaultBranch);
        startActivity(intent);
      }
    });
  }

  private void loadData(String query) {
    networkQueue().enqueue(
        GithubService.createSearchService().repositories(query, null, null, mCurrentPage));
  }

  @Override public void errorNotFound(ErrorModel errorModel) {
    super.errorNotFound(errorModel);
  }

  @Override public void onSearch(String query) {
    mQuery = query;
    onRefresh();
  }
}
