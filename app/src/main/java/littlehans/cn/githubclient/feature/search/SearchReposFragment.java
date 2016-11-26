package littlehans.cn.githubclient.feature.search;

/**
 * Created by LittleHans on 2016/11/15.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import littlehans.cn.githubclient.Nav;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.feature.repos.ReposActivity;
import littlehans.cn.githubclient.model.entity.SearchRepos;
import littlehans.cn.githubclient.ui.adapter.SearchReposAdapter;
import littlehans.cn.githubclient.ui.fragment.PageFragment;
import okhttp3.Headers;

/**
 * Created by littlehans on 2016/10/1.
 */
public class SearchReposFragment extends PageFragment<SearchRepos>
    implements BaseQuickAdapter.RequestLoadMoreListener,
    SearchActivity.onSearchListenerA {

  private static final String TAG = "SearchReposFragment";
  private SearchReposAdapter mQuickSearchAdapter;
  private String mQuery;
  private int mCurrentPage = 1;
  private int mLastPage;
  private OnItemClickListener mOnItemClickListener;
  private boolean onRefresh = false;

  public static Fragment create() {
    return new SearchReposFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    SearchActivity searchActivity = (SearchActivity) context;
    searchActivity.setOnSearchListenerA(this);
  }

  @Override public void respondSuccess(final SearchRepos data) {
    updateRecyclerView(data);
  }

  private void updateRecyclerView(final SearchRepos data) {

    mRecyclerView.post(new Runnable() {
      @Override public void run() {
        if (mCurrentPage == 1 && onRefresh) {
          mQuickSearchAdapter = new SearchReposAdapter(data.items);
          mRecyclerView.setAdapter(mQuickSearchAdapter);
          mQuickSearchAdapter.setOnLoadMoreListener(SearchReposFragment.this);
          mCurrentPage++;
          removeOnItemClickListener();
          addOnItemClickListener();
          return;
        } else if(mCurrentPage == 1 && !onRefresh) {
          onRefresh = false;
          mQuickSearchAdapter.setNewData(data.items);
          mRecyclerView.setAdapter(mQuickSearchAdapter);
          mRecyclerView.invalidate();
          return;
        }

        if (mCurrentPage > mLastPage) {
          mQuickSearchAdapter.loadMoreEnd();
          mQuickSearchAdapter.loadMoreEnd();
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
    super.onRefresh();
    onRefresh = true;
    loadData(mQuery);
  }

  @Override public void respondHeader(Headers headers) {
    mLastPage = getLastPage(headers);
  }

  private void addOnItemClickListener() {
    mOnItemClickListener = new OnItemClickListener() {
      @Override public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        SearchRepos.Items repoItem = (SearchRepos.Items) baseQuickAdapter.getItem(i);
        Intent intent = new Intent(getActivity(), ReposActivity.class);
        intent.putExtra(Nav.REPO_ITEM, repoItem); //reposTests
        startActivity(intent);
      }
    };
    mRecyclerView.addOnItemTouchListener(mOnItemClickListener);
  }

  private void loadData(String query) {
    networkQueue().enqueue(
        GithubService.createSearchService().repositories(query, null, null, mCurrentPage));
  }

  @Override public void onSearch(String query) {
    mQuery = query;
    onRefresh();
    removeOnItemClickListener();
  }

  private void removeOnItemClickListener() {
    if (mOnItemClickListener != null) {
      mRecyclerView.removeOnItemTouchListener(mOnItemClickListener);
    }
  }
}
