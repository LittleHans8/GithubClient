package littlehans.cn.githubclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import java.net.UnknownHostException;
import java.util.List;
import littlehans.cn.githubclient.model.ErrorModel;
import littlehans.cn.githubclient.network.callback.GenericCallback;
import littlehans.cn.githubclient.network.retrofit2.NetworkQueue;
import okhttp3.Headers;

/**
 * Created by littlehans on 2016/10/9.
 */

public abstract class NetworkFragment<T> extends BaseFragment implements GenericCallback<T>{
  private NetworkQueue<T> mNetworkQueue;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mNetworkQueue = new NetworkQueue<>(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mNetworkQueue.cancel();
    mNetworkQueue = null;
  }

  public NetworkQueue<T> networkQueue() {
    return this.mNetworkQueue;
  }

  @Override public void startRequest() {

  }

  @Override public void endRequest() {

  }

  @Override public void errorNotFound(ErrorModel errorModel) {
    showErrorModel(errorModel);
  }

  @Override public void errorUnProcessable(ErrorModel errorModel) {
    showErrorModel(errorModel);
  }

  @Override public void errorUnauthorized(ErrorModel errorModel) {
    showErrorModel(errorModel);
  }

  @Override public void errorForbidden(ErrorModel errorModel) {
    showErrorModel(errorModel);
  }

  @Override public void eNetUnReach(Throwable t, ErrorModel errorModel) {
    showException(t);
  }

  @Override public void errorSocketTimeout(Throwable t, ErrorModel errorModel) {
    showException(t);
  }

  @Override public void errorUnknownHost(UnknownHostException e, ErrorModel errorModel) {
    showException(e);
  }

  @Override public void respondWithError(Throwable t) {

  }

  @Override public void error(ErrorModel errorModel) {
    showErrorModel(errorModel);
  }

  private void showErrorModel(ErrorModel errorModel) {
    if (showMessage() && errorModel != null) {
      Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content),
          errorModel.getMessage(), Snackbar.LENGTH_SHORT).show();
    }
  }

  private void showException(Throwable e) {
    if (showMessage() && e != null) {
      Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content),
          e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }
  }

  public boolean showMessage() {
    return true;
  }

  @Override public void respondHeader(Headers headers) {
    List<String> remains = headers.values("X-RateLimit-Remaining");

    if (!remains.isEmpty()) {
      String remain = remains.get(0);
      Log.d("SearchUsersFragment", "X-RateLimit-Remaining: " + remain);
    }
  }
}
