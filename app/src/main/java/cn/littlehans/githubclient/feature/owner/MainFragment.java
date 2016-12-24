package cn.littlehans.githubclient.feature.owner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Bind;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.ui.fragment.BaseFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LittleHans on 2016/11/18.
 */

public class MainFragment extends BaseFragment {

  @Bind(R.id.tab_layout) TabLayout mTabLayout;
  @Bind(R.id.view_pager) ViewPager mViewPager;
  private MainFragment.Adapter mAdapter;

  public static Fragment create() {
    return new MainFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapter = new MainFragment.Adapter(getChildFragmentManager());
    //TODO EventFragment
//    mAdapter.addFragment(ReceivedEventsFragment.create(), getString(R.string.events));
    mAdapter.addFragment(AllReposFragment.create(), getString(R.string.repositories));
    mAdapter.addFragment(StartsFragment.create(), getString(R.string.stars));
    mAdapter.addFragment(FollowersFragment.create(), getString(R.string.followers));
    mAdapter.addFragment(FollowingFragment.create(), getString(R.string.following));
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_main;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(5);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  static class Adapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles;

    Adapter(FragmentManager fm) {
      super(fm);
      mTitles = new ArrayList<>();
    }

    void addFragment(Fragment fragment, String title) {
      mFragments.add(fragment);
      mTitles.add(title);
    }

    @Override public Fragment getItem(int position) {
      return mFragments.get(position);
    }

    @Override public int getCount() {
      return mFragments.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return mTitles.get(position);
    }
  }

}
