/**
 * Created by YuGang Yang on October 29, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package littlehans.cn.githubclient.network.retrofit2;

import android.util.Base64;
import android.util.Log;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultHeaderInterceptor implements HeaderInterceptor {

  //AccountProvider mAccountProvider;
  /**
   * example: application/vnd.xxx.v1+json
   */
  public final static String ACCEPT = "Accept";
  public final static String ACCEPT_TEXT_MATCH = "application/vnd.github.v3.text-match+json";
  public final static String ACCEPT_JSON = "application/json";
  public final static String ACCEPT_FULL_JSON = "application/vnd.github.v3.full+json";

  @Override public Response intercept(Interceptor.Chain chain) throws IOException {

    String credentials = "LittleHans8" + ":" + "asd6629236";
    String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    Log.v("Basic", basic);
    Request originalRequest = chain.request();

    Request.Builder requestBuilder = originalRequest.newBuilder()
        .header("Authorization", basic)
        .header("Accept", "application/json")
        .header(ACCEPT, ACCEPT_TEXT_MATCH + "," + ACCEPT_JSON + "," + ACCEPT_FULL_JSON)
        .method(originalRequest.method(), originalRequest.body());
    Request request = requestBuilder.build();
    return chain.proceed(request);
  }
}
