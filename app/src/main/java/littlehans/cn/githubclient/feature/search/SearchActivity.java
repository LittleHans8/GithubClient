package littlehans.cn.githubclient.feature.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.widget.FrameLayout;
import butterknife.BindView;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.ui.activity.BaseActivity;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by littlehans on 2016/9/27.
 */

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {


  private static final String TAG = "SearchActivity";
  @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;
  @BindView(R.id.search_view) SearchView mSearchView;
  private onSearchListenerA mSearchListenerA;
  private onSearchListenerB mSearchListenerB;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.acticity_search);
    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, SearchFragment.create())
        .commit();

    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,R.color.colorAccent));
    mSearchView.setOnQueryTextListener(this);
    mSearchView.onActionViewExpanded();
  }

  public void setOnSearchListenerA(onSearchListenerA onSearchListenerA) {
    this.mSearchListenerA = onSearchListenerA;
  }

  public void setOnSearchListenerB(onSearchListenerB onSearchListenerB) {
    this.mSearchListenerB = onSearchListenerB;
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    mSearchListenerA.onSearch(query);
    //mSearchListenerB.onSearch(query);// SearchUserFragment
    mSearchView.clearFocus();
    return true;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    return false;
  }

  public interface onSearchListenerA {
    void onSearch(String query);
  }

  public interface onSearchListenerB {
    void onSearch(String query);
  }



}
