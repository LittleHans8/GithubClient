/**
 * Created by YuGang Yang on October 29, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package littlehans.cn.githubclient.network.retrofit2;

import android.text.TextUtils;
import android.util.Base64;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoginInterceptor implements HeaderInterceptor {

  //AccountProvider mAccountProvider;
  /**
   * example: application/vnd.xxx.v1+json
   */
  public final static String ACCEPT = "Accept";
  public final static String ACCEPT_TEXT_MATCH = "application/vnd.github.v3.text-match+json";
  public final static String ACCEPT_JSON = "application/json";
  public final static String ACCEPT_FULL_JSON = "application/vnd.github.v3.full+json";

  private String mAccount;
  private String mPassword;
  private String mBasicCode;

  public LoginInterceptor(String basicCode) {
    this.mBasicCode = basicCode;
  }

  public LoginInterceptor(String account,String password){
    this.mAccount = account;
    this.mPassword = password;
  }

  @Override public Response intercept(Interceptor.Chain chain) throws IOException {
    String basic;
    Request.Builder requestBuilder;
    Request request;
    if (TextUtils.isEmpty(mAccount)) {
      basic = "Basic " + mBasicCode;
      Request originalRequest = chain.request();
      requestBuilder = originalRequest.newBuilder()
          .header("Authorization", basic)
          .header("Accept", "application/json")
          .header(ACCEPT, ACCEPT_TEXT_MATCH + "," + ACCEPT_JSON + "," + ACCEPT_FULL_JSON)
          .method(originalRequest.method(), originalRequest.body());
      request = requestBuilder.build();
      return chain.proceed(request);
    } else {
      String credentials = mAccount + ":" + mPassword;

      basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
      Request originalRequest = chain.request();

      requestBuilder = originalRequest.newBuilder()
          .header("Authorization", basic)
          .header("Accept", "application/json")
          .header(ACCEPT, ACCEPT_TEXT_MATCH + "," + ACCEPT_JSON + "," + ACCEPT_FULL_JSON)
          .method(originalRequest.method(), originalRequest.body());
      request = requestBuilder.build();
      return chain.proceed(request);
    }
  }
}
