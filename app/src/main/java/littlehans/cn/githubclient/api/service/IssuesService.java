package littlehans.cn.githubclient.api.service;

import android.support.annotation.Nullable;
import java.util.List;
import littlehans.cn.githubclient.model.entity.Issue;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by LittleHans on 2016/10/26.
 */

public interface IssuesService {
  String OPEN = "open";
  String CLOSED = "closed";

  /**
   * GET /repos/:owner/:repo/issues
   * see:https://developer.github.com/v3/issues/#list-issues-for-a-repository
   * query Can be either open, closed, or all. Default: open
   */

  @GET("/repos/{owner}/{repo}/issues") Call<List<Issue>> getIssuesForReposList(
      @Path("owner") String owner, @Path("repo") String repo,
      @Nullable @Query("state") String state, @Query("page") int page);
}
