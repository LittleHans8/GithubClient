package littlehans.cn.githubclient.api.service;

import java.util.ArrayList;
import java.util.List;
import littlehans.cn.githubclient.model.entity.Branch;
import littlehans.cn.githubclient.model.entity.Repository;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by LittleHans on 2016/10/22.
 */

public interface RepositoryService {

  // need auth
  @GET("/user/repos") Call<ArrayList<Repository>> getOwnRepos(@Query("page") int page);

  @GET("/users/{username}/repos") Call<ArrayList<Repository>> getUserRepos(
      @Path("username") String username, @Query("page") int page);

  //List Branches
  @GET("/repos/{owner}/{repo}/branches") Call<List<Branch>> getBranchList(
      @Path("owner") String owner, @Path("repo") String repo);
}
