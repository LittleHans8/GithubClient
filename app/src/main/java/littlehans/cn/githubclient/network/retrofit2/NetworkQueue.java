/**
 * Created by YuGang Yang on October 30, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package littlehans.cn.githubclient.network.retrofit2;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import littlehans.cn.githubclient.model.ErrorModel;
import littlehans.cn.githubclient.network.callback.GenericCallback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.common.internal.Preconditions.checkNotNull;

/**
 * 对 Retrofit2 的 Callback 进行封装
 */
public class NetworkQueue<T> implements Callback<T> {

  private final GenericCallback<T> callback;
  // GenericCall includes NetWorkCallback<T> and ErrorCallback
  private Call<T> delegate; // delegate design pattern

  public NetworkQueue(GenericCallback<T> callback) {
    checkNotNull(callback, "callback == null");
    this.callback = callback;
  }

  public void enqueue(Call<T> delegate) {
    checkNotNull(delegate, "delegate == null");
    this.delegate = delegate;
    callback.startRequest();
    delegate.enqueue(this); // call the Retrofit's Call function which is enqueue.
  }

  public void cancel() {
    if (delegate != null) {
      delegate.cancel();
    }
  }

  @Override public void onResponse(Call<T> call, Response<T> response) {
    if (response.isSuccessful()) {
      callback.respondSuccess(
          // if have a class implement GenericCallback,then the respondSuccess is  return the respond.body() data
          response.body());
      callback.respondHeader(response.headers());
    } else {
      final int statusCode = response.code();
      final ResponseBody errorBody = response.errorBody(); // default is json data.
      ErrorModel errorModel = null;
      if (errorBody != null) {
        try {
          ObjectMapper mapper = new ObjectMapper();
          String json = errorBody.string();
          errorModel = mapper.readValue(json, ErrorModel.class);// json data is mapping a class
        } catch (IOException e) {
          errorModel = new ErrorModel(statusCode, e.getMessage());
        }
      }
      processError(statusCode, errorModel); // generate error data by generic callback
    }

    callback.endRequest(); // However network is ok or error,call the function
  }

  @Override public void onFailure(Call<T> call, Throwable t) {
    if (t instanceof ConnectException) { // 无网络
      ConnectException e = (ConnectException) t;
      callback.eNetUnReach(t, new ErrorModel(500, "无网络连接", "请连接到无线网络或者蜂窝数据网络"));
    } else if (t instanceof SocketTimeoutException) { // 链接超时
      final SocketTimeoutException e = (SocketTimeoutException) t;
      callback.errorSocketTimeout(t, new ErrorModel(500, "连接超时"));
    } else if (t instanceof UnknownHostException) { // 无法解析 ip 地址~
      final UnknownHostException e = (UnknownHostException) t;
      ErrorModel errorModel = new ErrorModel(500, "无网络连接", "请连接到无线网络或者蜂窝数据网络");
      callback.errorUnknownHost(e, errorModel);
    } else {
      callback.respondWithError(t);
    }
    callback.endRequest();
  }

  private void processError(final int statusCode, ErrorModel errorModel) {
    switch (statusCode) {
      case 401:
        callback.errorUnauthorized(errorModel);
        break;
      case 403:
        callback.errorForbidden(errorModel);
        break;
      case 404:
        callback.errorNotFound(errorModel);
        break;
      case 422:
        callback.errorUnProcessable(errorModel);
        break;
      default:
        callback.error(errorModel);
        break;
    }
  }
}
