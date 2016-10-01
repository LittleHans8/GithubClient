package littlehans.cn.githubclient.feature.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.model.entity.Search;
import littlehans.cn.githubclient.ui.adapter.SearchAdapter;
import littlehans.cn.githubclient.ui.fragment.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LittleHans on 2016/10/1.
 */
public class SearchRepsFragment extends BaseFragment {

  private static final String TAG = "SearchRepsFragment";

  private RecyclerView.Adapter mAdapter;
  private LinearLayoutManager mLinearLayoutManager;
  @BindView(R.id.recycler_search) RecyclerView mRecycler;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_search_repos;
  }

  public static Fragment create() {
    return new SearchRepsFragment();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    loadDate();
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        loadDate();
      }
    });
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
        mSwipeRefreshLayout.setRefreshing(false);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mAdapter = new SearchAdapter(response.body());
        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
      }

      @Override public void onFailure(Call<Search> call, Throwable t) {
        for (StackTraceElement errorMessage : t.getStackTrace()) {
          Log.v(TAG, "onFailure: " + errorMessage.toString());
        }
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.search, menu);
    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
    searchView.setQueryHint(getString(R.string.query_hint));
    super.onCreateOptionsMenu(menu, inflater);
  }
}
