package cn.littlehans.githubclient.api.service;

import cn.littlehans.githubclient.model.entity.ReceivedEvent;
import cn.littlehans.githubclient.model.entity.Repository;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by LittleHans on 2016/11/4.
 */

public interface ActivityService {

  /**
   * List events performed by a user
   * https://api.github.com/users/LittleHans8/received_events/public
   * https://developer.github.com/v3/activity/events/#list-events-performed-by-a-user
   */
  @GET("/users/{username}/received_events/public") Call<List<ReceivedEvent>> getReceivedEvents(
      @Path("username") String userName, @Query("page") int page);

  /**
   * https://developer.github.com/v3/activity/starring/
   */
  //List repositories being starred
  @GET("/user/starred") Call<ArrayList<Repository>> getOwnStarredRepos(@Query("page") int page);

  //List repositories being starred by the authenticated user.
  @GET("/users/{username}/starred") Call<ArrayList<Repository>> getUserStarredRepos(
      @Path("username") String userName, @Query("page") int page);

  //Check if you are starring a repository
  @GET("/user/starred/{owner}/{repo}") Call<ResponseBody> checkRepo(@Path("owner") String own,
      @Path("repo") String repo);

  // Star a repository
  @PUT("/user/starred/{owner}/{repo}") Call<ResponseBody> starRepo(@Path("owner") String own,
      @Path("repo") String repo);

  // Unstar a repository
  @DELETE("/user/starred/{owner}/{repo}") Call<ResponseBody> unstarRepo(@Path("owner") String own,
      @Path("repo") String repo);
}
