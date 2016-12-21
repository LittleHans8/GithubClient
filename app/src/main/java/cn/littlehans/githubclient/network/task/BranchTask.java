package cn.littlehans.githubclient.network.task;

import android.os.AsyncTask;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.api.service.RepositoryService;
import cn.littlehans.githubclient.model.entity.Branch;
import java.io.IOException;
import java.util.List;

/**
 * Created by LittleHans on 2016/12/21.
 */

public class BranchTask extends AsyncTask<String, Void, List<Branch>> {

  @Override protected List<Branch> doInBackground(String... params) {
    String owner = params[0];
    String repo = params[1];
    RepositoryService repositoryService = GitHubService.createRepositoryService();
    List<Branch> branches = null;
    try {
      branches = repositoryService.getBranchList(owner, repo).execute().body();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return branches;
  }
}
