package littlehans.cn.githubclient.api.service;

import littlehans.cn.githubclient.model.entity.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by littlehans on 2016/10/17.
 */

public interface InfoService {

  @GET("/users/{login}")
  Call<User> user(
      @Path("login") String login);

}
