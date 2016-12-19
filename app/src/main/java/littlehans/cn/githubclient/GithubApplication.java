package littlehans.cn.githubclient;

import android.os.Handler;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartydroid.android.starter.kit.account.Account;
import com.smartydroid.android.starter.kit.app.StarterKitApp;
import java.io.IOException;
import littlehans.cn.githubclient.model.entity.User;

/**
 * Created by littlehans on 2016/9/27.
 */

public class GitHubApplication extends StarterKitApp {

  private Handler mHandler;

  @Override public Account accountFromJson(String json) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(json, User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
  }

  public static GitHubApplication getInstance() {
    return (GitHubApplication) appContext().getApplicationContext();
  }

  public Handler getHandler() {
    return mHandler == null ? new Handler() : mHandler;
  }
}
