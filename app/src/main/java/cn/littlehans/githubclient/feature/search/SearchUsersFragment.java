package cn.littlehans.githubclient.feature.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Bind;
import cn.littlehans.githubclient.Nav;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.feature.user.UserActivity;
import cn.littlehans.githubclient.model.entity.SearchUser;
import cn.littlehans.githubclient.model.entity.User;
import cn.littlehans.githubclient.ui.adapter.SearchUserAdapter;
import cn.littlehans.githubclient.ui.fragment.NetworkFragment;
import cn.littlehans.githubclient.utilities.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Headers;

/**
 * Created by littlehans on 2016/10/1.
 */
public class SearchUsersFragment extends NetworkFragment<SearchUser>
    implements SwipeRefreshLayout.OnRefreshListener, SearchActivity.onSearchListenerB,
    BaseQuickAdapter.RequestLoadMoreListener {

  @Bind(R.id.user_recycler_search) RecyclerView mRecycler;
  @Bind(R.id.user_layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  private LinearLayoutManager mLinearLayoutManager;
  private String mQuery;
  private int mCurrentPage = 1;
  private int mLastPage;
  private SearchUserAdapter mSearchUserAdapter;

  public static Fragment create() {
    return new SearchUsersFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    SearchActivity searchActivity = (SearchActivity) context;
    searchActivity.setOnSearchListenerB(this);
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_search_users;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initUI();
    mRecycler.addOnItemTouchListener(new OnItemClickListener() {
      @Override public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        User user = (User) baseQuickAdapter.getItem(i);
        Intent intent = new Intent(getActivity(), UserActivity.class);
        intent.putExtra(Nav.USER, user);
        startActivity(intent);
      }
    });
  }

  @Override public void respondSuccess(final SearchUser data) {

    final List<User> users = new ArrayList<>();

    Thread thread = new Thread(new Runnable() {
      @Override public void run() {
        try {
          List<User> items = data.items;

          for (User i : items) {
            try {
              User user = GitHubService.createInfoService().user(i.login).execute().body();
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
                mRecycler.addItemDecoration(
                    new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                mSearchUserAdapter = new SearchUserAdapter(users);
                mRecycler.setAdapter(mSearchUserAdapter);
                mSearchUserAdapter.setOnLoadMoreListener(SearchUsersFragment.this);
                mCurrentPage++;
                mSwipeRefreshLayout.setRefreshing(false);
                return;
              }

              if (mCurrentPage > mLastPage) {
                mSearchUserAdapter.loadMoreEnd();
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
    }
  }

  @Override public void onRefresh() {
    mCurrentPage = 1;
    loadData(mQuery);
  }

  @Override public void onSearch(String query) {
    mQuery = query;
    onRefresh();
  }

  @Override public void onLoadMoreRequested() {
    loadData(mQuery);
  }

  private void initUI() {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);
    mSwipeRefreshLayout.setOnRefreshListener(this);
  }

  private void loadData(String query) {
    networkQueue().enqueue(
        GitHubService.createSearchService().users(query, null, null, mCurrentPage));
  }

  @Override public void startRequest() {
    super.startRequest();
    mSwipeRefreshLayout.setRefreshing(true);
  }

  @Override public void endRequest() {
    super.endRequest();
    mSwipeRefreshLayout.setRefreshing(false);
  }
}
