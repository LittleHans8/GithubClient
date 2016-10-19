package littlehans.cn.githubclient.feature.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.model.entity.SearchUser;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.ui.adapter.SearchUserAdapter;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;
import okhttp3.Headers;

/**
 * Created by littlehans on 2016/10/1.
 */
public class SearchUsersFragment extends NetworkFragment<SearchUser>
    implements SwipeRefreshLayout.OnRefreshListener, SearchActivity.onSearchListenerB,
    BaseQuickAdapter.RequestLoadMoreListener {

  @BindView(R.id.user_recycler_search) RecyclerView mRecycler;
  @BindView(R.id.user_layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  private LinearLayoutManager mLinearLayoutManager;
  private String mQuery;
  private int mCurrentPage = 1;
  private int mLastPage;
  private SearchUserAdapter mSearchUserAdapter;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initUI();
  }

  private void initUI() {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);
    mSwipeRefreshLayout.setOnRefreshListener(this);
  }

  @Override public void respondSuccess(final SearchUser data) {

    final List<User> users = new ArrayList<>();

    Thread thread = new Thread(new Runnable() {
      @Override public void run() {
        try {
          List<SearchUser.Items> items = data.items;

          for (SearchUser.Items i : items) {
            try {
              User user = GithubService.createInfoService().user(i.login).execute().body();
              users.add(user);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }

          mRecycler.post(new Runnable() {
            @Override public void run() {
              if (mCurrentPage == 1) {
                mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mRecycler.setLayoutManager(mLinearLayoutManager);
                mSearchUserAdapter = new SearchUserAdapter(users);
                mSearchUserAdapter.openLoadMore(30);
                mRecycler.setAdapter(mSearchUserAdapter);
                mSearchUserAdapter.setOnLoadMoreListener(SearchUsersFragment.this);
                mCurrentPage++;
                mSwipeRefreshLayout.setRefreshing(false);
                return;
              }

              if (mCurrentPage > mLastPage) {
                mSearchUserAdapter.loadComplete();
                mSwipeRefreshLayout.setRefreshing(false);
              } else {
                mSearchUserAdapter.addData(users);
                mCurrentPage++;
                mSwipeRefreshLayout.setRefreshing(false);
              }
            }
          });
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    thread.start();
  }

  @Override public void respondWithError(Throwable t) {

  }

  @Override public void respondHeader(Headers headers) {
    List<String> links = headers.values("Link");
    if (!links.isEmpty()) {
      String link = links.get(0);
      PageLink pageLink = new PageLink(link);
      if (pageLink.getLastPage() != 0) {
        mLastPage = pageLink.getLastPage();
      }
      Log.d("SearchUsersFragment", "respondHeader: " + mLastPage);
    }
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_search_users;
  }

  public static Fragment create() {
    return new SearchUsersFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    SearchActivity searchActivity = (SearchActivity) context;
    searchActivity.setOnSearchListenerB(this);
  }

  @Override public void onRefresh() {
    mCurrentPage = 1;
    loadData(mQuery);
  }

  private void loadData(String query) {
    networkQueue().enqueue(
        GithubService.createSearchService().users(query, null, null, mCurrentPage));
  }

  @Override public void onSearch(String query) {
    mQuery = query;
    onRefresh();
  }

  @Override public void onLoadMoreRequested() {
    loadData(mQuery);
  }
}
