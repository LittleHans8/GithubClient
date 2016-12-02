package littlehans.cn.githubclient;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import littlehans.cn.githubclient.network.retrofit2.RetrofitBuilder;
import timber.log.Timber;

/**
 * Created by littlehans on 2016/9/27.
 */

public class GithubApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    new RetrofitBuilder
        .Builder()
        .baseUrl(Profile.API_ENDPOINT)
        .build();
    Fresco.initialize(this);
  }
}
