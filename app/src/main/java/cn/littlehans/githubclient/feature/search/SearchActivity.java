package cn.littlehans.githubclient.feature.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.widget.FrameLayout;
import butterknife.Bind;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.ui.activity.BaseActivity;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by littlehans on 2016/9/27.
 */

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {

  private static final String TAG = "SearchActivity";
  public static final String KEY_SAVED_QUERY_STRING = "KEY_SAVED_QUERY_STRING";
  @Bind(R.id.fragment_container) FrameLayout mFragmentContainer;
  @Bind(R.id.search_view) SearchView mSearchView;
  private onSearchListenerA mSearchListenerA;
  private onSearchListenerB mSearchListenerB;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.acticity_search);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, SearchFragment.create())
          .commit();
    } else {
      mSearchView.setQuery(savedInstanceState.getString(KEY_SAVED_QUERY_STRING), false);
    }


    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
    mSearchView.setOnQueryTextListener(this);
    mSearchView.onActionViewExpanded();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(KEY_SAVED_QUERY_STRING, mSearchView.getQuery().toString().trim());
  }

  public void setOnSearchListenerA(onSearchListenerA onSearchListenerA) {
    this.mSearchListenerA = onSearchListenerA;
  }

  public void setOnSearchListenerB(onSearchListenerB onSearchListenerB) {
    this.mSearchListenerB = onSearchListenerB;
  }

  @Override public boolean onQueryTextSubmit(String query) {
    mSearchListenerA.onSearch(query);
    mSearchListenerB.onSearch(query);// SearchUserFragment
    mSearchView.clearFocus();
    return true;
  }

  @Override public boolean onQueryTextChange(String newText) {
    return false;
  }

  public interface onSearchListenerA {
    void onSearch(String query);
  }

  public interface onSearchListenerB {
    void onSearch(String query);
  }
}
