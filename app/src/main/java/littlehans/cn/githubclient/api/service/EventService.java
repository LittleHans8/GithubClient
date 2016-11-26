package littlehans.cn.githubclient.api.service;

import java.util.List;
import littlehans.cn.githubclient.model.entity.ReceivedEvent;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by LittleHans on 2016/11/4.
 */

public interface EventService {

  /**
   * List events performed by a user
   * https://api.github.com/users/LittleHans8/received_events/public
   * https://developer.github.com/v3/activity/events/#list-events-performed-by-a-user
   */
  @GET("/users/{username}/received_events/public") Call<List<ReceivedEvent>> getReceivedEvent(
      @Path("username") String userName, @Query("page") int page);
}
