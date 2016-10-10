/**
 * Created by YuGang Yang on October 30, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package littlehans.cn.githubclient.network2.callback;

import okhttp3.Headers;

public interface NetworkCallback<T> { // In fact ,it is a Callback(Retrofit)

  /**
   * 请求开始
   */
  void startRequest();

  /**
   * 请求返回数据
   */
  void respondSuccess(T data);

  /**
   * 请求返回错误
   */
  void respondWithError(Throwable t);

  /**
   * 请求结束，成功或者失败都会调用 endRequest()
   */
  void endRequest();

  void respondHeader(Headers headers);
}

