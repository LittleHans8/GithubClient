package cn.littlehans.githubclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import butterknife.Bind;
import cn.littlehans.githubclient.model.AccountManager;
import cn.littlehans.githubclient.network.retrofit2.LoginInterceptor;
import cn.littlehans.githubclient.network.retrofit2.RetrofitBuilder;
import cn.littlehans.githubclient.ui.activity.BaseActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import qiu.niorgai.StatusBarCompat;
import timber.log.Timber;

/**
 * Created by LittleHans on 2016/12/19.
 */

public class SplashActivity extends BaseActivity {
  @Bind(R.id.text_version_code) TextView mTextVersionCode;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.blue_300));
    mTextVersionCode.setText(
        String.format(getString(R.string.version), GitHubApplication.appInfo().version));

    GitHubApplication.getInstance().getHandler().postDelayed(new Runnable() {
      @Override public void run() {
        isLogin();
      }
    }, 1500);
  }

  private void isLogin() {
    if (AccountManager.getIsLogin()) {
      new RetrofitBuilder.Builder().baseUrl(Profile.API_ENDPOINT).client(newLoginClient()).build();
      Nav.startMainActivity(this);
    } else {
      Nav.startLoginActivity(this);
    }
  }

  @NonNull private OkHttpClient newLoginClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    HttpLoggingInterceptor loggingInterceptor =
        new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
          @Override public void log(String message) {
            Timber.d(message);
          }
        });
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    builder.addInterceptor(loggingInterceptor);
    String basic = AccountManager.getBasic();
    builder.addInterceptor(new LoginInterceptor(basic));
    return builder.build();
  }
}
