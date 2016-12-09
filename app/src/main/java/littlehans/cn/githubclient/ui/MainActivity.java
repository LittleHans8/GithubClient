package littlehans.cn.githubclient.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import littlehans.cn.githubclient.Nav;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.feature.owner.MainFragment;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.ui.activity.BaseActivity;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private static final String TAG = "MainActivity";
  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.drawer_layout) DrawerLayout mDrawer;
  @Bind(R.id.nav_view) NavigationView mNavigationView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setSupportActionBar(mToolbar);

    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    mDrawer.setDrawerListener(toggle);
    toggle.syncState();

    mNavigationView.setNavigationItemSelectedListener(this);

    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
    getSupportFragmentManager().beginTransaction().add(R.id.content_main, MainFragment.create())
        .commit();
  }

  @Override public void onBackPressed() {
    if (mDrawer.isDrawerOpen(GravityCompat.START)) {
      mDrawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {

    getMenuInflater().inflate(R.menu.main, menu);

    //Associate searchable configuration with the SearchView

    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.m_search:
        Nav.startSearchActivity(this);
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody") @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();
    //if (id == R.id.nav_manage) {
    //
    //} else if (id == R.id.nav_share) {
    //
    //} else if (id == R.id.nav_send) {
    //
    //}

    mDrawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
