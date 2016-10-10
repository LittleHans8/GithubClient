/**
 * Created by YuGang Yang on October 29, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package littlehans.cn.githubclient.network2.retrofit2;

import java.io.IOException;
import okhttp3.Headers;
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


  @Override public Response intercept(Interceptor.Chain chain) throws IOException {
    Request originalRequest = chain.request();
    Headers.Builder builder = new Headers.Builder();
    builder.add(ACCEPT, ACCEPT_TEXT_MATCH);
    Request compressedRequest = originalRequest
        .newBuilder()
        .headers(builder.build())
        .build();


    return chain.proceed(compressedRequest);

  }
}
