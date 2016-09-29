package littlehans.cn.githubclient.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.model.entity.Search;
import littlehans.cn.githubclient.ui.activity.BaseActivity;
import littlehans.cn.githubclient.ui.adapter.SearchAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LittleHans on 2016/9/27.
 */

public class SearchActivity extends BaseActivity {

  private static final String TAG = "SearchActivity";

  private RecyclerView.Adapter mAdapter;
  private LinearLayoutManager mLinearLayoutManager;

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.recycler_search) RecyclerView mRecycler;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    setSupportActionBar(mToolbar);
    mLinearLayoutManager = new LinearLayoutManager(this);
    mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mRecycler.setLayoutManager(mLinearLayoutManager);


    final Call<Search> repos =
        GithubService.createSearchService().repositories("bootstrap", null, null);
    repos.enqueue(new Callback<Search>() {
      @Override public void onResponse(Call<Search> call, Response<Search> response) {
        for (Search.Items items : response.body().items) {
          Log.v(TAG, "onResponse: " + items.toString());
        }
        Log.v(TAG, "onResponse: " + response.message());
        mAdapter = new SearchAdapter(response.body());
        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
      }

      @Override public void onFailure(Call<Search> call, Throwable t) {
        for (StackTraceElement errorMessage : t.getStackTrace()) {
          Log.v(TAG, "onFailure: " + errorMessage.toString());
        }

        mToolbar.setTitle(t.getMessage());
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
    //mRecycler.setAdapter(mAdapter);
    //mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    //  @Override public void onRefresh() {
    //    loadDate();
    //  }
    //});
  }

  public void loadDate() {

    final Call<Search> repos =
        GithubService.createSearchService().repositories("bootstrap", null, null);
    repos.enqueue(new Callback<Search>() {
      @Override public void onResponse(Call<Search> call, Response<Search> response) {
        for (Search.Items items : response.body().items) {
          Log.v(TAG, "onResponse: " + items.toString());
        }
        Log.v(TAG, "onResponse: " + response.message());
        mAdapter = new SearchAdapter(response.body());
        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
      }

      @Override public void onFailure(Call<Search> call, Throwable t) {
        for (StackTraceElement errorMessage : t.getStackTrace()) {
          Log.v(TAG, "onFailure: " + errorMessage.toString());
        }

        mToolbar.setTitle(t.getMessage());
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
    searchView.setQueryHint(getString(R.string.query_hint));
    return super.onCreateOptionsMenu(menu);
  }
}
