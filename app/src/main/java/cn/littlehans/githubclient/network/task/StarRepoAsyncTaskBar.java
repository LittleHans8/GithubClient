package cn.littlehans.githubclient.network.task;

import android.widget.TextView;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.api.service.ActivityService;
import cn.littlehans.githubclient.utilities.FormatUtils;
import cn.littlehans.githubclient.utilities.WeakAsyncTask;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by LittleHans on 2016/12/21.
 */

public class StarRepoAsyncTaskBar extends WeakAsyncTask<String, Void, Integer, TextView[]> {

  public static final int TYPE_CHECK_STAR = 0;
  public static final int TYPE_STAR = 1;
  public static final int TYPE_UNSTAR = 2;
  public static final String UNSTAR = "Unstar";
  public static final String STAR = "Star";
  private int mType;
  private ActivityService mActivityService;

  public StarRepoAsyncTaskBar(TextView[] textViews, int type) {
    super(textViews);
    this.mType = type;
  }

  @Override protected Integer doInBackground(TextView[] textViews, String... params) {
    Call<ResponseBody> call = null;
    String owner = params[0];
    String repo = params[1];
    mActivityService = GitHubService.createActivityService();
    switch (mType) {
      case TYPE_CHECK_STAR:
        call = mActivityService.checkRepo(owner, repo);
        break;
      case TYPE_STAR:
        call = mActivityService.starRepo(owner, repo);
        break;
      case TYPE_UNSTAR:
        call = mActivityService.unstarRepo(owner, repo);
        break;
    }

    int code = 0;
    try {
      code = call.execute().code();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return code;
  }

  @Override protected void onPostExecute(TextView[] textViews, Integer code) {
    super.onPostExecute(textViews, code);
    TextView textStart = textViews[0];
    TextView textStartCount = textViews[1];
    int count = FormatUtils.parse(textStartCount.getText().toString());
    if (code == 204) {
      switch (mType) {
        case TYPE_CHECK_STAR:
          textStart.setText(UNSTAR);
          break;
        case TYPE_STAR:
          textStart.setText(UNSTAR);
          textStartCount.setText(FormatUtils.decimalFormat(count + 1));
          break;
        case TYPE_UNSTAR:
          textStart.setText(STAR);
          textStartCount.setText(FormatUtils.decimalFormat(count - 1));
          break;
      }
    }
  }
}
