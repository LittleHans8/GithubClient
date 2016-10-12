/**
 * Created by YuGang Yang on October 30, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package littlehans.cn.githubclient.network.callback;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import java.net.UnknownHostException;
import littlehans.cn.githubclient.model.ErrorModel;
import static littlehans.cn.githubclient.utilities.Preconditions.checkNotNull;

public class MessageCallback<T> extends SimpleCallback<T> {

  private final Activity activity;
  private final View view;
  public MessageCallback(Activity activity) {
    checkNotNull(activity, "activity == null");
    this.activity = activity;
    this.view = activity.getWindow().getDecorView();
  }

  public Activity getActivity() {
    return activity;
  }

  @Override public void errorNotFound(ErrorModel errorModel) {
    super.errorNotFound(errorModel);
    showMessage(errorModel);
  }

  @Override public void errorUnProcessable(ErrorModel errorModel) {
    showMessage(errorModel);
  }

  @Override public void respondWithError(Throwable t) {
    if (t != null) {
      Snackbar.make(view, t.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
    }
  }

  @Override public void errorUnauthorized(ErrorModel errorModel) {
    showMessage(errorModel);
  }

  @Override public void errorForbidden(ErrorModel errorModel) {
    showMessage(errorModel);
  }

  @Override public void errorUnknownHost(UnknownHostException e, ErrorModel errorModel) {
    showMessage(errorModel);
  }

  @Override public void error(ErrorModel errorModel) {
    showMessage(errorModel);
  }

  @Override public void errorSocketTimeout(Throwable t, ErrorModel errorModel) {
    showMessage(errorModel);
  }

  private void showMessage(ErrorModel errorModel) {
    if (errorModel != null) {
      Snackbar.make(view, errorModel.mMessage, Snackbar.LENGTH_SHORT).show();
    }
  }
}
