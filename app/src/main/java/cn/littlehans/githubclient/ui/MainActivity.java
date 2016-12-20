package cn.littlehans.githubclient.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.littlehans.githubclient.Nav;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.feature.owner.MainFragment;
import cn.littlehans.githubclient.model.AccountManager;
import cn.littlehans.githubclient.model.entity.User;
import cn.littlehans.githubclient.ui.activity.BaseActivity;
import cn.littlehans.githubclient.utilities.TextViewUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private static final String TAG = "MainActivity";
  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.drawer_layout) DrawerLayout mDrawer;
  @Bind(R.id.nav_view) NavigationView mNavigationView;
  @Bind(R.id.small_avatar) SimpleDraweeView mSmallAvatar;
  @Bind(R.id.text_small_name) TextView mTextSmallName;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setSupportActionBar(mToolbar);
    setTitle("");
    View headerView = mNavigationView.getHeaderView(0);
    SimpleDraweeView mAvatar = ButterKnife.findById(headerView, R.id.avatar);
    TextView mTextName = ButterKnife.findById(headerView, R.id.text_name);
    TextView mTextEmail = ButterKnife.findById(headerView, R.id.text_email);
    User user = AccountManager.getAccount();
    if (user != null) {
      mSmallAvatar.setImageURI(user.avatar_url);
      mAvatar.setImageURI(user.avatar_url);
      TextViewUtils.setTextNotEmpty(mTextEmail, user.email);
      mTextName.setText(user.name);
      mTextSmallName.setText(user.name);
    }

    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    //mDrawer.setDrawerListener(toggle);
    mDrawer.addDrawerListener(toggle);
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
    if (id == R.id.nav_sing_out) {
      AccountManager.clearAllData();
      Nav.startLoginActivity(this);
    }

    mDrawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
