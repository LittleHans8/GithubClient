package cn.littlehans.githubclient.ui.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by littlehans on 2016/9/27.
 */

public class BaseActivity extends AppCompatActivity {

  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
  }
}
