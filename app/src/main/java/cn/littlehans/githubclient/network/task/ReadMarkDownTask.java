package cn.littlehans.githubclient.network.task;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Base64;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.api.service.GitDateService;
import cn.littlehans.githubclient.model.entity.Blob;
import cn.littlehans.githubclient.ui.activity.FileDetailActivity;
import com.mukesh.MarkdownView;
import java.io.IOException;

import static cn.littlehans.githubclient.feature.repos.ReposCodeFragment.CONTENT;
import static cn.littlehans.githubclient.feature.repos.ReposCodeFragment.IS_MARK_DOWN_FILE;
import static cn.littlehans.githubclient.feature.repos.ReposCodeFragment.MARKDOWN;
import static cn.littlehans.githubclient.feature.repos.ReposCodeFragment.MD;

/**
 * Created by LittleHans on 2016/12/21.
 */

public class ReadMarkDownTask extends WeakAsyncTask<String, Void, String, MarkdownView> {

  public static final int TYPE_SET_TEXT_IN_MARK_DOWN_VIEW = 0;
  public static final int TYPE_START_ACTIVITY_WITH_MARKDOWN = 1;
  private Context mContext;
  private int mType;
  private String mTreePath;

  public ReadMarkDownTask(@Nullable MarkdownView markdownView, int type, @Nullable Context context,
      String treePath) {
    super(markdownView);
    if (context != null) {
      this.mContext = context;
    }
    this.mType = type;

    this.mTreePath = treePath;
  }

  @Override protected String doInBackground(MarkdownView markdownView, String... params) {
    GitDateService gitDateService = GitHubService.createGitDateService();
    String owner = params[0];
    String repo = params[1];
    String treeSha = params[2];
    Blob blob = null;
    try {
      blob = gitDateService.getBlob(owner, repo, treeSha).execute().body();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new String(Base64.decode(blob.content, Base64.DEFAULT));
  }

  @Override protected void onPostExecute(MarkdownView markdownView, String s) {
    super.onPostExecute(markdownView, s);
    if (mType == TYPE_SET_TEXT_IN_MARK_DOWN_VIEW) {
      markdownView.setMarkDownText(s);
    } else {
      Intent intent = new Intent(mContext, FileDetailActivity.class);
      intent.putExtra(CONTENT, s);
      if (mTreePath.endsWith(MD) || mTreePath.endsWith(MARKDOWN)) {
        intent.putExtra(IS_MARK_DOWN_FILE, true);
      }
      mContext.startActivity(intent);
    }
  }
}
