package littlehans.cn.githubclient.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.Bind;
import butterknife.OnClick;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.network.retrofit2.LoginInterceptor;
import littlehans.cn.githubclient.network.retrofit2.RetrofitBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

  @Bind(R.id.auto_text_account) AutoCompleteTextView mAutoTextAccount;
  @Bind(R.id.edit_password) EditText mEditPassword;
  @Bind(R.id.btn_sign_in) Button mBtnSign;

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
      builder.addInterceptor(new LoginInterceptor(getAccount(),getPassword()));
      OkHttpClient client = builder.build();
    }


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
}

