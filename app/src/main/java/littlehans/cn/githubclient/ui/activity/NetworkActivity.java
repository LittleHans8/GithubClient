package littlehans.cn.githubclient.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.Toast;
import java.net.UnknownHostException;
import littlehans.cn.githubclient.Nav;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.ErrorModel;
import littlehans.cn.githubclient.network.callback.GenericCallback;
import littlehans.cn.githubclient.network.retrofit2.NetworkQueue;

/**
 * Created by LittleHans on 2016/10/28.
 */

public abstract class NetworkActivity<T> extends BaseActivity implements GenericCallback<T> {
  private NetworkQueue<T> networkQueue;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    networkQueue = new NetworkQueue<>(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    networkQueue.cancel();
    networkQueue = null;
  }

  public NetworkQueue<T> networkQueue() {
    return networkQueue;
  }

  public boolean showMessage() {
    return true;
  }

  @Override public void errorNotFound(ErrorModel errorModel) {
    showErrorModel(errorModel);
  }

  @Override public void errorUnProcessable(ErrorModel errorModel) {
    showErrorModel(errorModel);
  }

  @Override public void errorUnauthorized(ErrorModel errorModel) {
    showErrorModel(errorModel);
    Toast.makeText(this, getString(R.string.error_404), Toast.LENGTH_SHORT).show();
    Nav.startLoginActivity(this);
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

  @Override public void error(ErrorModel errorModel) {
    showErrorModel(errorModel);
  }

  private void showErrorModel(ErrorModel errorModel) {
    if (showMessage() && errorModel != null) {
      Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
          errorModel.getMessage(), Snackbar.LENGTH_SHORT).show();
    }
  }

  private void showException(Throwable e) {
    if (showMessage() && e != null) {
      Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), e.getMessage(),
          Snackbar.LENGTH_SHORT).show();
    }
  }

  @Override public void startRequest() {
  }

  @Override public void respondWithError(Throwable t) {
    showException(t);
  }

  @Override public void endRequest() {

  }
}
