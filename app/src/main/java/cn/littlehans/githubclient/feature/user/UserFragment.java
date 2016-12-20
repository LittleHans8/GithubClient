package cn.littlehans.githubclient.feature.user;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Bind;
import cn.littlehans.githubclient.Nav;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.feature.owner.AllReposFragment;
import cn.littlehans.githubclient.feature.owner.FollowersFragment;
import cn.littlehans.githubclient.feature.owner.FollowingFragment;
import cn.littlehans.githubclient.feature.owner.StartsFragment;
import cn.littlehans.githubclient.model.entity.User;
import cn.littlehans.githubclient.ui.fragment.BaseFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LittleHans on 2016/12/19.
 */
public class UserFragment extends BaseFragment {

  private UserFragment.Adapter mAdapter;
  @Bind(R.id.tab_layout) TabLayout mTabLayout;
  @Bind(R.id.view_pager) ViewPager mViewPager;

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_user;
  }

  public static Fragment create() {
    return new UserFragment();
  }

  public static Fragment create(Parcelable data) {
    UserFragment fragment = new UserFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Nav.USER, data);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    User user = getArguments().getParcelable(Nav.USER);
    mAdapter = new UserFragment.Adapter(getChildFragmentManager());
    mAdapter.addFragment(UserOverviewFragment.create(user), getString(R.string.overview));
    mAdapter.addFragment(AllReposFragment.create(user), getString(R.string.repositories));
    mAdapter.addFragment(StartsFragment.create(user), getString(R.string.starts));
    mAdapter.addFragment(FollowersFragment.create(user), getString(R.string.followers));
    mAdapter.addFragment(FollowingFragment.create(user), getString(R.string.following));
    mViewPager.setAdapter(mAdapter);
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
