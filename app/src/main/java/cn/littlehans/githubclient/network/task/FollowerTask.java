package cn.littlehans.githubclient.network.task;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import cn.littlehans.githubclient.GitHubApplication;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.api.service.UsersService;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by LittleHans on 2016/12/24.
 */

public class FollowerTask extends WeakAsyncTask<String, Void, Integer, TextView> {

  public static final int CHECK_FOLLOW = 0;
  public static final int FOLLOW = 1;
  public static final int UNFOLLOW = 2;

  public static final String STRING_FOLLOW = "Follow";
  public static final String STRING_UNFOLLOW = "Unfollow";

  private int mType;

  public UsersService mUsersService;

  public FollowerTask(TextView textView, UsersService usersService, int type) {
    super(textView);
    this.mUsersService = usersService;
    this.mType = type;
  }

  @Override protected void onPreExecute(TextView textView) {
    super.onPreExecute(textView);
    textView.setEnabled(false);
    textView.setBackgroundColor(
        ContextCompat.getColor(GitHubApplication.appContext(), R.color.lightGray));
  }

  @Override protected Integer doInBackground(TextView textView, String... params) {
    String login = params[0];
    Call<ResponseBody> responseBodyCall = null;
    textView.setEnabled(false);
    switch (mType) {
      case CHECK_FOLLOW:
        responseBodyCall = mUsersService.checkFollowingUser(login);
        break;
      case FOLLOW:
        responseBodyCall = mUsersService.followUser(login);
        break;
      case UNFOLLOW:
        responseBodyCall = mUsersService.unFollowUser(login);
        break;
    }
    int statusCode = 0;
    try {
      statusCode = responseBodyCall.execute().code();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return statusCode;
  }

  @Override protected void onPostExecute(TextView textView, Integer code) {
    super.onPostExecute(textView, code);
    textView.setEnabled(true);
    textView.setBackgroundResource(R.drawable.selector_btn_sign_in);
    if (code == 204) {
      switch (mType) {
        case CHECK_FOLLOW:
          textView.setText(STRING_UNFOLLOW);
          break;
        case FOLLOW:
          textView.setText(STRING_UNFOLLOW);
          break;
        case UNFOLLOW:
          textView.setText(STRING_FOLLOW);
          break;
      }
    }
  }
}
