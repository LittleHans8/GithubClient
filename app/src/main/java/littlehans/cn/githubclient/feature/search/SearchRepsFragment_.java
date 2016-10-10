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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.Arrays;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.Search.Items;
import littlehans.cn.githubclient.ui.adapter.QuickSearchAdapter;
import littlehans.cn.githubclient.ui.adapter.SearchReposSortAdapter;
import littlehans.cn.githubclient.ui.fragment.BaseFragment;

/**
 * Created by LittleHans on 2016/10/1.
 */
public class SearchRepsFragment_ extends BaseFragment
    implements BaseQuickAdapter.RequestLoadMoreListener {

  List<Items> loadItem;

  private static final String TAG = "SearchReposFragment";

  String sort[] = { "Best Match", "Most Start", "Most Forks" };
  String language[] = { "Java", "JavaScript", "Object-C", "C++/C", "Php" };

  private LinearLayoutManager mLinearLayoutManager;
  private GridLayoutManager mGridLayoutManger;
  private QuickSearchAdapter mQuickSearchAdapter;
  int currentPage;
  PageLink mPageLink;

  @BindView(R.id.recycler_search) RecyclerView mRecycler;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.checkbox_sort) CheckBox mCheckboxSort;
  @BindView(R.id.checkbox_language) CheckBox mCheckboxLanguage;
  @BindView(R.id.recycler_tag_sort) RecyclerView mRecyclerTagSort;
  private int mCurrentPage = 1;
  private int mLastPage = 100;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, rootView);
    return rootView;
  }


  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);

    mSwipeRefreshLayout.setRefreshing(true);
    mGridLayoutManger = new GridLayoutManager(getActivity(), 3);
    mRecyclerTagSort.setLayoutManager(mGridLayoutManger);
    mRecyclerTagSort.setAdapter(
        new SearchReposSortAdapter(R.layout.item_check, Arrays.asList(sort)));
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
    return new SearchRepsFragment_();
  }

  @Override public void onLoadMoreRequested() {

  }

}
