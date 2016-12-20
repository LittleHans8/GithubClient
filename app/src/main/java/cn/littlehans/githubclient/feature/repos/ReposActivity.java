package cn.littlehans.githubclient.feature.repos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import cn.littlehans.githubclient.Nav;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.model.entity.Repository;
import cn.littlehans.githubclient.ui.activity.BaseActivity;
import java.util.ArrayList;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposActivity extends BaseActivity {
  @Bind(R.id.toolbar) Toolbar mToolbar;
  private Intent mIntent;
  private Repository mItems;
  private ArrayList<OnDatePassListener> mOnDatePassListeners;
  private OnDatePassListener mOnDatePassListener;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repos);
    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, ReposFragment.create())
        .commit();
    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
    setSupportActionBar(mToolbar);
    mOnDatePassListeners = new ArrayList<>();
    mIntent = getIntent();
    mItems = mIntent.getExtras().getParcelable(Nav.REPO_ITEM);
    setTitle(mItems.name);
  }

  public void addOnDatePassListener(OnDatePassListener onDatePassListener) {
    mOnDatePassListener = onDatePassListener;
    mOnDatePassListener.onCardTouchListener(mItems);
    // bug: will touch off twice
    //mOnDatePassListeners.add(onDatePassListener);
    //for (OnDatePassListener OnDatePassListenerX : mOnDatePassListeners) {
    //  OnDatePassListenerX.onCardTouchListener(mItems);
    //}
  }

  public void removeOnDatePassListener(OnDatePassListener onDatePassListener) {
    mOnDatePassListeners.remove(mOnDatePassListeners);
  }
}
