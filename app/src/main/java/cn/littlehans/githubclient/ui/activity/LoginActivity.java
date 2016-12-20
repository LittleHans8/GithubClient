package cn.littlehans.githubclient.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.littlehans.githubclient.GitHubWelcomeActivity;
import cn.littlehans.githubclient.Nav;
import cn.littlehans.githubclient.Profile;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.api.service.UsersService;
import cn.littlehans.githubclient.model.AccountManager;
import cn.littlehans.githubclient.model.ErrorModel;
import cn.littlehans.githubclient.model.entity.User;
import cn.littlehans.githubclient.network.retrofit2.LoginInterceptor;
import cn.littlehans.githubclient.network.retrofit2.RetrofitBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.smartydroid.android.starter.kit.app.StarterKitApp;
import com.smartydroid.android.starter.kit.utilities.HudUtils;
import com.stephentuso.welcome.WelcomeHelper;
import java.io.IOException;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import support.ui.utilities.ToastUtils;
import timber.log.Timber;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends NetworkActivity<User> {

  @Bind(R.id.edit_text_account) EditText mEditTextAccount;
  @Bind(R.id.edit_password) EditText mEditPassword;
  @Bind(R.id.btn_sign_in) TextView mBtnSign;
  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.avatar) SimpleDraweeView mAvatar;
  private UsersService mAuthUsersService;
  private UsersService mUsersService;
  private LoginInterceptor mLoginInterceptor;
  private Retrofit mDefaultRetrofit;// get the user'avatar
  private Retrofit mRetrofit; // auth the user;
  private KProgressHUD mKProgressHUD;
  WelcomeHelper welcomeScreen;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    setSupportActionBar(mToolbar);
    if (StarterKitApp.isFirstEnterApp()) {
      welcomeScreen = new WelcomeHelper(this, GitHubWelcomeActivity.class);
      welcomeScreen.show(savedInstanceState);
      StarterKitApp.enterApp();
    }

    if (AccountManager.getIsLogin()) {
      User user = AccountManager.getAccount();
      mEditTextAccount.setText(user.login);
      new RetrofitBuilder.Builder().baseUrl(Profile.API_ENDPOINT)
          .client(newLoginClient(AccountManager.getBasic()))
          .build();
      Nav.startMainActivity(this);
    }

    mDefaultRetrofit = new Retrofit.Builder().baseUrl(Profile.API_ENDPOINT).
        addConverterFactory(JacksonConverterFactory.create()).
        build();
    mUsersService = mDefaultRetrofit.create(UsersService.class);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    welcomeScreen.onSaveInstanceState(outState);
  }

  @OnClick(R.id.btn_sign_in) void sigIn() {

    if (!checkAccount()) {
      OkHttpClient client = newLoginClient(null);
      mRetrofit = new Retrofit.Builder().baseUrl(Profile.API_ENDPOINT)
          .client(client)
          .addConverterFactory(JacksonConverterFactory.create())
          .build();
      mAuthUsersService = mRetrofit.create(UsersService.class);
      networkQueue().enqueue(mAuthUsersService.getAuthUser());
    } else {
      Toast.makeText(this, getString(R.string.error_field_required), Toast.LENGTH_SHORT).show();
    }
  }

  @NonNull private OkHttpClient newLoginClient(@Nullable String basicCode) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    HttpLoggingInterceptor loggingInterceptor =
        new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
          @Override public void log(String message) {
            Timber.d(message);
          }
        });
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    builder.addInterceptor(loggingInterceptor);
    if (TextUtils.isEmpty(basicCode)) {
      mLoginInterceptor = new LoginInterceptor(getAccount(), getPassword());
    } else {
      mLoginInterceptor = new LoginInterceptor(basicCode);
    }

    builder.addInterceptor(mLoginInterceptor);
    return builder.build();
  }

  @OnFocusChange(R.id.edit_text_account) void editTextAccountFocusChange() {
    if (!TextUtils.isEmpty(getAccount())) {
      new Thread(new Runnable() {
        @Override public void run() {
          try {
            final User user = mUsersService.getUser(getAccount()).execute().body();
            if (!(user == null)) {
              mAvatar.post(new Runnable() {
                @Override public void run() {
                  if (!TextUtils.isEmpty(user.avatar_url)) {
                    mAvatar.setImageURI(user.avatar_url);
                  }
                }
              });
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }).start();
    }
  }

  private boolean checkAccount() {
    return TextUtils.isEmpty(getAccount()) || TextUtils.isEmpty(getPassword());
  }

  @NonNull private String getPassword() {
    return mEditPassword.getText().toString().trim();
  }

  @NonNull private String getAccount() {
    return mEditTextAccount.getText().toString().trim();
  }

  @Override public void startRequest() {
    super.startRequest();
    mKProgressHUD = HudUtils.showHud(this, getString(R.string.signing_in));
  }

  @Override public void respondSuccess(User data) {
    Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
    new RetrofitBuilder.Builder().baseUrl(Profile.API_ENDPOINT)
        .client(newLoginClient(null))
        .build();
    AccountManager.storeAccount(data);
    AccountManager.storeBasic(getAccount(), getPassword());
    Nav.startMainActivity(this);
  }

  @Override public void errorUnauthorized(ErrorModel errorModel) {
    ToastUtils.toast(R.string.error_incorrect_field);
  }

  @Override public void endRequest() {
    super.endRequest();
    mKProgressHUD.dismiss();
  }

  @Override public void respondWithError(Throwable t) {
    super.respondWithError(t);
  }

  @Override public void respondHeader(Headers headers) {
  }
}

