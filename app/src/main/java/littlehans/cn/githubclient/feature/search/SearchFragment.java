package littlehans.cn.githubclient.feature.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import java.util.ArrayList;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.ui.fragment.BaseFragment;

/**
 * Created by LittleHans on 2016/10/1.
 */

public class SearchFragment extends BaseFragment {

  @BindView(R.id.tab_layout) TabLayout mTabLayout;
  @BindView(R.id.view_pager) ViewPager mViewPager;

  private Adapter mAdapter;
  private String query;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = new Bundle();
    bundle.putString("query",query);
    mAdapter = new Adapter(getChildFragmentManager());
    Fragment searchReposFragment = SearchReposFragment.create();
    searchReposFragment.setArguments(bundle);
    mAdapter.addFragment(searchReposFragment, getString(R.string.Repositories));
    mAdapter.addFragment(SearchCodeFragment.create(), getString(R.string.Code));
    mAdapter.addFragment(SearchIssuesFragment.create(), getString(R.string.Issues));
    mAdapter.addFragment(SearchUsersFragment.create(), getString(R.string.Users));
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mViewPager.setAdapter(mAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_search;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    SearchActivity searchActivity = (SearchActivity) context;
    query = searchActivity.mSearchView.getQuery().toString();
  }

  static class Adapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    Adapter(FragmentManager fm) {
      super(fm);
    }

    void addFragment(Fragment fragment, String title) {
      mFragments.add(fragment);
      mFragmentTitles.add(title);
    }

    @Override public Fragment getItem(int position) {
      return mFragments.get(position);
    }

    @Override public int getCount() {
      return mFragments.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return mFragmentTitles.get(position);
    }
  }

  public static Fragment create() {
    return new SearchFragment();
  }
}
