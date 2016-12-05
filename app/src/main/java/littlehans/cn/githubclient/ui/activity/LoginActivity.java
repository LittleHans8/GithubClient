package littlehans.cn.githubclient.ui.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.OnClick;
import littlehans.cn.githubclient.Profile;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.UsersService;
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

  @Bind(R.id.auto_text_account) AutoCompleteTextView mAutoTextAccount;
  @Bind(R.id.edit_password) EditText mEditPassword;
  @Bind(R.id.btn_sign_in) Button mBtnSign;
  private UsersService mUsersService;
  private LoginInterceptor mLoginInterceptor;
  private RetrofitBuilder mRetrofitBuilder;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

  }


  @OnClick(R.id.btn_sign_in) void sigIn(){

    if(checkAccount()) {
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
    }
    mUsersService = GithubService.createUserService();
    networkQueue().enqueue(mUsersService.getAuthUser());
  }

  private boolean checkAccount() {
    if(TextUtils.isEmpty(getAccount()) && TextUtils.isEmpty(getPassword())) {
      Toast.makeText(this, "Incorrect username or password.", Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }

  @NonNull
  private String getPassword() {
    return mEditPassword.getText().toString().trim();
  }

  @NonNull
  private String getAccount() {
    return mAutoTextAccount.getText().toString().trim();
  }

  @Override
  public void respondSuccess(User data) {
    Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
    Log.d("TAG ", "respondSuccess: " + data.email);
  }

  @Override
  public void respondHeader(Headers headers) {

  }
}

