package littlehans.cn.githubclient.ui.fragment;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.feature.search.SearchFragment;
import littlehans.cn.githubclient.feature.search.SearchReposFragment;
import littlehans.cn.githubclient.feature.search.SearchUsersFragment;

/**
 * Created by LittleHans on 2016/11/18.
 */

public class MainFragment extends BaseFragment{

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private int[] mDrawableNormal;
    private int[] mDrawableSelected;
    private String[] mTitles;
    private SearchFragment.Adapter mAdapter;

    public static Fragment create() {
        return new SearchFragment();
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MainFragment.Adapter(getChildFragmentManager());
        mAdapter.addFragment(SearchReposFragment.create());
        mAdapter.addFragment(SearchUsersFragment.create());
    }

    @Override protected int getFragmentLayout() {
        return R.layout.fragment_search;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTab();
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
}
