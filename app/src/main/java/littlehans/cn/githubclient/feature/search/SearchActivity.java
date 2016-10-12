package littlehans.cn.githubclient.feature.search;

import android.app.SearchManager;
import android.content.Intent;
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
 * Created by LittleHans on 2016/9/27.
 */

public class SearchActivity extends BaseActivity  {


  @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;
  @BindView(R.id.search_view) SearchView mSearchView;

  private static final String TAG = "SearchActivity";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.acticity_search);

    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, SearchFragment.create())
        .commit();

    handleIntent(getIntent());
    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,R.color.colorAccent));
  }

  @Override protected void onNewIntent(Intent intent) {
    setIntent(intent);
    handleIntent(intent);
  }

  private void handleIntent(Intent intent) {
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query;
      query = intent.getStringExtra(SearchManager.QUERY);
      mSearchView.onActionViewExpanded();
      mSearchView.setQuery(query,false);
      mSearchView.setFocusable(false);
    }
  }


}
