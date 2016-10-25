package littlehans.cn.githubclient.feature.repos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.ui.activity.BaseActivity;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposActivity extends BaseActivity {
  @BindView(R.id.toolbar) Toolbar mToolbar;
  private OnCardTouchListener mOnCardTouchListener;
  private Bundle mBundle;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repos);
    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, ReposFragment.create())
        .commit();
    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,R.color.colorAccent));
    setSupportActionBar(mToolbar);

  }

  public void setOnCardTouchListener(OnCardTouchListener onCardTouchListener) {
    Intent intent = getIntent();
    mOnCardTouchListener = onCardTouchListener;
    mOnCardTouchListener.onCardTouchListener(intent
    );
  }

}
