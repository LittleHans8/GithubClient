package littlehans.cn.githubclient.ui.activity;

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
import com.facebook.drawee.view.SimpleDraweeView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.smartydroid.android.starter.kit.utilities.HudUtils;
import java.io.IOException;
import littlehans.cn.githubclient.Nav;
import littlehans.cn.githubclient.Profile;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.service.UsersService;
import littlehans.cn.githubclient.model.AccountManager;
import littlehans.cn.githubclient.model.ErrorModel;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.network.retrofit2.LoginInterceptor;
import littlehans.cn.githubclient.network.retrofit2.RetrofitBuilder;
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

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    setSupportActionBar(mToolbar);
    mDefaultRetrofit = new Retrofit.Builder().baseUrl(Profile.API_ENDPOINT).
        addConverterFactory(JacksonConverterFactory.create()).
        build();
    mUsersService = mDefaultRetrofit.create(UsersService.class);
  }

  @OnClick(R.id.btn_sign_in) void sigIn() {

    if (!checkAccount()) {
      OkHttpClient client = newLoginClient();
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

    mLoginInterceptor = new LoginInterceptor(getAccount(), getPassword());
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
    new RetrofitBuilder.Builder().baseUrl(Profile.API_ENDPOINT).client(newLoginClient()).build();
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

