package littlehans.cn.githubclient.utilities;

import littlehans.cn.githubclient.network.callback.MessageCallback;
import littlehans.cn.githubclient.network.retrofit2.NetworkQueue;
import retrofit2.Call;

/**
 * Created by YuGang Yang on February 27, 2016.
 * Copyright 2015-2016 qiji.tech. All rights reserved.
 */
public final class NetworkUtils<T> {

  private NetworkQueue<T> networkQueue;

  public static <T> NetworkUtils<T> create(MessageCallback<T> callback) {
    return new NetworkUtils<T>(callback);
  }

  private NetworkUtils(MessageCallback<T> callback) {
    this.networkQueue = new NetworkQueue<>(callback);
  }

  /**
   * @param call Call
   */
  public void enqueue(Call<T> call) {
    networkQueue.enqueue(call);
  }

  public void onDestroy() {
    networkQueue.cancel();
    networkQueue = null;
  }
}
