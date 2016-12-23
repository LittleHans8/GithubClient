package cn.littlehans.githubclient.network.task;

import android.util.Log;
import cn.littlehans.githubclient.api.service.UsersService;
import cn.littlehans.githubclient.model.entity.User;
import com.facebook.drawee.view.SimpleDraweeView;
import java.io.IOException;

/**
 * Created by LittleHans on 2016/12/23.
 */

public class UserAvatarTask extends WeakAsyncTask<String, Void, String, SimpleDraweeView> {

  private UsersService mUsersService;

  public UserAvatarTask(SimpleDraweeView simpleDraweeView, UsersService usersService) {
    super(simpleDraweeView);
    this.mUsersService = usersService;
  }

  @Override protected String doInBackground(SimpleDraweeView simpleDraweeView, String... params) {
    String login = params[0];
    User user = null;
    try {
      user = mUsersService.getUser(login).execute().body();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Log.d("TAG", "doInBackground: " + login);
    return user != null ? user.avatar_url : null;
  }

  @Override protected void onPostExecute(SimpleDraweeView simpleDraweeView, String s) {
    super.onPostExecute(simpleDraweeView, s);
    if (s != null) {
      simpleDraweeView.setImageURI(s);
    }
  }
}
