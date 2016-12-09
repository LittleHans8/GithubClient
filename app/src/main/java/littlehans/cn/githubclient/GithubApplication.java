package littlehans.cn.githubclient;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartydroid.android.starter.kit.account.Account;
import com.smartydroid.android.starter.kit.app.StarterKitApp;

import java.io.IOException;

import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.network.retrofit2.RetrofitBuilder;
import timber.log.Timber;

/**
 * Created by littlehans on 2016/9/27.
 */

public class GithubApplication extends StarterKitApp {
  @Override
  public Account accountFromJson(String json) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(json,User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override public void onCreate() {
    super.onCreate();
//    new RetrofitBuilder
//        .Builder()
//        .baseUrl(Profile.API_ENDPOINT)
//        .build();
    Fresco.initialize(this);
  }
}
