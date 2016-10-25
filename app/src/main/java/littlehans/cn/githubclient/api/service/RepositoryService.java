package littlehans.cn.githubclient.api.service;

import java.util.List;
import littlehans.cn.githubclient.model.entity.Branch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by LittleHans on 2016/10/22.
 */

public interface RepositoryService {
  //List Branches
  ///repos/:owner/:repo/branches
  @GET("/repos/{owner}/{repo}/branches") Call<List<Branch>> getBranchList(
      @Path("owner") String owner, @Path("repo") String repo);
}
