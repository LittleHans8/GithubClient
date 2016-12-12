package littlehans.cn.githubclient.ui.activity;

import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import littlehans.cn.githubclient.Nav;
import littlehans.cn.githubclient.Profile;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.UsersService;
import littlehans.cn.githubclient.model.AccountManager;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.network.retrofit2.LoginInterceptor;
import littlehans.cn.githubclient.network.retrofit2.RetrofitBuilder;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
  private UsersService mUsersService;
  private LoginInterceptor mLoginInterceptor;
  private RetrofitBuilder mRetrofitBuilder;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    setSupportActionBar(mToolbar);

  }


  @OnClick(R.id.btn_sign_in) void sigIn(){

    if(!checkAccount()) {
      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
          Timber.d(message);
        }
      });
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
      builder.addInterceptor(loggingInterceptor);

      mLoginInterceptor = new LoginInterceptor(getAccount(),getPassword());
      builder.addInterceptor(mLoginInterceptor);

      OkHttpClient client = builder.build();

      mRetrofitBuilder = null;

      mRetrofitBuilder = new RetrofitBuilder
                .Builder()
                .baseUrl(Profile.API_ENDPOINT)
                .client(client)
                .build();
      mUsersService = GithubService.createUserService();
      networkQueue().enqueue(mUsersService.getAuthUser());
    }else {
      Toast.makeText(this, getString(R.string.error_invalid_password), Toast.LENGTH_SHORT).show();
    }

  }

  @OnFocusChange(R.id.edit_text_account) void editTextAccountFocusChange(){
    Toast.makeText(this, "changed", Toast.LENGTH_SHORT).show();
    if(!TextUtils.isEmpty(getAccount())){
      new RetrofitBuilder
              .Builder()
              .baseUrl(Profile.API_ENDPOINT)
              .build();
      mUsersService = GithubService.createUserService();
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            final User user = mUsersService.getUser(getAccount()).execute().body();
            Timber.d(user.avatar_url);
            mAvatar.post(new Runnable() {
              @Override
              public void run() {
               mAvatar.setImageURI(user.avatar_url);
              }
            });

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

  @NonNull
  private String getPassword() {
    return mEditPassword.getText().toString().trim();
  }

  @NonNull
  private String getAccount() {
    return mEditTextAccount.getText().toString().trim();
  }

  @Override
  public void respondSuccess(User data) {
    Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
    AccountManager.storeAccount(data);
    AccountManager.storeBasic(getAccount(),getPassword());
    Nav.startMainActivity(this);
    Log.d("TAG ", "respondSuccess: " + data.email);
  }

  @Override
  public void respondHeader(Headers headers) {

  }
}

