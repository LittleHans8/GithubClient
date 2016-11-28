package littlehans.cn.githubclient.api.service;

import littlehans.cn.githubclient.model.entity.Comment;
import littlehans.cn.githubclient.model.entity.Issue;
import littlehans.cn.githubclient.model.entity.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by littlehans on 16/11/28.
 * https://developer.github.com/v3/users/
 */

public interface UserService {

    /**
     * List followers of a user
     */

    //List the authenticated user's followers:
    @GET("/user/followers") Call<Comment.User> getUserFollowers(@Query("page") int page);

    // List a user's followers
    @GET("/users/{username}/followers")
    Call<Comment.User> getOwnFollowers(@Path("username")String userName, @Query("page")int page);

    /**
     * List users followed by another user
     */
    
}
