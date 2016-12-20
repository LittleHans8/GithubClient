package cn.littlehans.githubclient.api.service;

import cn.littlehans.githubclient.model.entity.Comment;
import cn.littlehans.githubclient.model.entity.User;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by littlehans on 16/11/28.
 * https://developer.github.com/v3/users/
 */

public interface UsersService {



    @GET("/users/{username}") Call<User> getUser(@Path("username") String userName);

    /**
     * Get the authenticated user
     */

    @GET("/user") Call<User> getAuthUser();

    /**
     * List followers of a user
     */

    //List the authenticated user's followers:
    @GET("/user/followers") Call<ArrayList<User>> getOwnFollowers(@Query("page") int page);

    // List a user's followers
    @GET("/users/{username}/followers") Call<ArrayList<User>> getUserFollowers(
        @Path("username") String userName, @Query("page") int page);

    /**
     * List users followed by another user
     */

    //List who the authenticated user is following:
    @GET("/user/following") Call<ArrayList<Comment.User>> getOwnFollowing(@Query("page")int page);

    //List who a user is following
    @GET("/users/{username}/following") Call<ArrayList<User>> getUserFollowing(
        @Path("username") String userName, @Query("page") int page);


}
