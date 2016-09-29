package littlehans.cn.githubclient.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.model.entity.Search;
import littlehans.cn.githubclient.ui.activity.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

  private static final String TAG = "MainActivity";
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
  @BindView(R.id.nav_view) NavigationView mNavigationView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setSupportActionBar(mToolbar);

    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    mDrawer.setDrawerListener(toggle);
    toggle.syncState();

    mNavigationView.setNavigationItemSelectedListener(this);
    final Call<Search> repos = GithubService.createSearchService().repositories("bootsrap",null,null);
    repos.enqueue(new Callback<Search>() {
      @Override public void onResponse(Call<Search> call, Response<Search> response) {
        for(Search.Items items : response.body().items) {
          Log.v(TAG, "onResponse: " + items.toString());
        }
        Log.v(TAG, "onResponse: " + response.message());
      }

      @Override public void onFailure(Call<Search> call, Throwable t) {
        for(StackTraceElement errorMessage: t.getStackTrace()) {
          Log.v(TAG, "onFailure: " + errorMessage.toString());
        }

        mToolbar.setTitle(t.getMessage());
      }
    });
  }

  @Override public void onBackPressed() {
    if (mDrawer.isDrawerOpen(GravityCompat.START)) {
      mDrawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody") @Override public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    mDrawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
