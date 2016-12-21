package cn.littlehans.githubclient.network.task;

import android.util.Base64;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.api.service.GitDateService;
import cn.littlehans.githubclient.model.entity.Blob;
import cn.littlehans.githubclient.utilities.WeakAsyncTask;
import com.mukesh.MarkdownView;
import java.io.IOException;

/**
 * Created by LittleHans on 2016/12/21.
 */

public class ReadMarkDownTask extends WeakAsyncTask<String, Void, String, MarkdownView> {

  public ReadMarkDownTask(MarkdownView markdownView) {
    super(markdownView);
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
    String textMarkdown = new String(Base64.decode(blob.content, Base64.DEFAULT));
    return textMarkdown;
  }

  @Override protected void onPostExecute(MarkdownView markdownView, String s) {
    super.onPostExecute(markdownView, s);
    markdownView.setMarkDownText(s);
  }
}
