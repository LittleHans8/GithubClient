package littlehans.cn.githubclient.feature.repos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import java.util.ArrayList;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.ui.fragment.BaseFragment;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
  @Bind(R.id.tab_layout) TabLayout mTabLayout;
  @Bind(R.id.view_pager) ViewPager mViewPager;
  private int[] mDrawableNormal;
  private int[] mDrawableSelected;
  private String[] mTitles;
  private Adapter mAdapter;

  public static Fragment create() {
    return new ReposFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapter = new Adapter(getChildFragmentManager());
    mAdapter.addFragment(ReposOverviewFragment.create());
    mAdapter.addFragment(ReposCodeFragment.create());
    mAdapter.addFragment(ReposIssueFragment.create());
    // TODO: 2016/11/18
    //mAdapter.addFragment(ReposPullRequestFragment.create());

  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    initRes();
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repos;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mViewPager.setAdapter(mAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
    mTabLayout.addOnTabSelectedListener(this);
    setupTab();
  }

  private void initRes() {
    mTitles = new String[] {
        getString(R.string.overview), getString(R.string.code), getString(R.string.issue),
        getString(R.string.pull_request)
    };
    mDrawableNormal = new int[] {
        R.drawable.ic_home_normal, R.drawable.ic_code_normal, R.drawable.ic_issue_normal,
        R.drawable.ic_pull_request_normal
    };
    mDrawableSelected = new int[] {
        R.drawable.ic_home, R.drawable.ic_code, R.drawable.ic_issue, R.drawable.ic_pull_request
    };
  }

  private void setupTab() {
    for (int i = 0; i < mTabLayout.getTabCount(); i++) {
      TabLayout.Tab tab = mTabLayout.getTabAt(i).setCustomView(R.layout.tab_textview);
      TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_textview);
      textView.setText(mTitles[i]);

      if (i == 0) {
        textView.setCompoundDrawablesWithIntrinsicBounds(mDrawableSelected[i], 0, 0, 0);
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
      } else {
        textView.setCompoundDrawablesWithIntrinsicBounds(mDrawableNormal[i], 0, 0, 0);
      }
      textView.setCompoundDrawablePadding(2);
    }
  }

  @Override public void onTabSelected(TabLayout.Tab tab) {
    int index = tab.getPosition();
    getCustomTextView(tab).setCompoundDrawablesWithIntrinsicBounds(mDrawableSelected[index], 0, 0,
        0);
    getCustomTextView(tab).setTextColor(getResources().getColor(R.color.colorAccent));
  }

  @Override public void onTabUnselected(TabLayout.Tab tab) {
    int index = tab.getPosition();
    getCustomTextView(tab).setTextColor(getResources().getColor(R.color.gray));
    getCustomTextView(tab).setCompoundDrawablesWithIntrinsicBounds(mDrawableNormal[index], 0, 0, 0);
  }

  @Override public void onTabReselected(TabLayout.Tab tab) {

  }

  public TextView getCustomTextView(TabLayout.Tab tab) {
    return (TextView) tab.getCustomView();
  }

  static class Adapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();

    Adapter(FragmentManager fm) {
      super(fm);
    }

    void addFragment(Fragment fragment) {
      mFragments.add(fragment);
    }

    @Override public Fragment getItem(int position) {
      return mFragments.get(position);
    }

    @Override public int getCount() {
      return mFragments.size();
    }
  }
}
