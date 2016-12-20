package cn.littlehans.githubclient.network;

import android.os.AsyncTask;
import android.widget.TextView;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.api.service.ActivityService;
import cn.littlehans.githubclient.utilities.FormatUtils;
import java.io.IOException;
import java.lang.ref.WeakReference;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by LittleHans on 2016/12/21.
 */

public class StarRepoAsyncTask extends AsyncTask<String, Void, Integer> {

  public static final int TYPE_CHECK_STAR = 0;
  public static final int TYPE_STAR = 1;
  public static final int TYPE_UNSTAR = 2;
  public static final String UNSTAR = "Unstar";
  public static final String STAR = "Star";

  private final WeakReference<TextView[]> mTextViewsWeakReference;
  private int mType;

  private ActivityService mActivityService;

  public StarRepoAsyncTask(TextView[] textViews, int type) {
    this.mTextViewsWeakReference = new WeakReference<>(textViews);
    this.mType = type;
  }

  @Override protected void onPreExecute() {
    super.onPreExecute();
    mActivityService = GitHubService.createActivityService();
  }

  @Override protected Integer doInBackground(String... params) {
    Call<ResponseBody> call = null;
    String owner = params[0];
    String repo = params[1];
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

  @Override protected void onPostExecute(Integer code) {
    super.onPostExecute(code);
    TextView textStart = mTextViewsWeakReference.get()[0];
    TextView textStartCount = mTextViewsWeakReference.get()[1];
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
