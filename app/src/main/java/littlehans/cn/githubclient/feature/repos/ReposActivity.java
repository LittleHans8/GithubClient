package littlehans.cn.githubclient.feature.repos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import littlehans.cn.githubclient.Nav;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.SearchRepos;
import littlehans.cn.githubclient.ui.activity.BaseActivity;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposActivity extends BaseActivity {
  @BindView(R.id.toolbar) Toolbar mToolbar;
  private OnDatePassListener mOnDatePassListenerA; // OverviewCodeFragment
  private OnDatePassListener mOnDatePassListenerB; // ReposCodeFragment
  private OnDatePassListener mOnDatePassListenerC; // ReposIssueFragment
  private Intent mIntent;
  private SearchRepos.Items mItems;
  private OnDatePassListener mOnDatePassListener;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repos);
    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, ReposFragment.create())
        .commit();
    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
    setSupportActionBar(mToolbar);
    mIntent = getIntent();
    mItems = mIntent.getExtras().getParcelable(Nav.REPO_ITEM);
    setTitle(mItems.name);
  }

  public void setOnDatePassListenerA(OnDatePassListener onDatePassListenerA) {
    mOnDatePassListenerA = onDatePassListenerA;
    mOnDatePassListenerA.onCardTouchListener(mItems);
  }

  public void setOnDatePassListenerB(OnDatePassListener onDatePassListenerB) {
    mOnDatePassListenerB = onDatePassListenerB;
    mOnDatePassListenerB.onCardTouchListener(mItems);
  }

  public void setOnDatePassListenerC(OnDatePassListener onDatePassListenerC) {
    mOnDatePassListenerC = onDatePassListenerC;
    mOnDatePassListenerC.onCardTouchListener(mItems);
  }
}
