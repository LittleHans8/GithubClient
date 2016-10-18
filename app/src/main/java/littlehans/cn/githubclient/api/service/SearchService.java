package littlehans.cn.githubclient.api.service;

import littlehans.cn.githubclient.model.entity.SearchRepos;
import littlehans.cn.githubclient.model.entity.SearchUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by littlehans on 2016/9/27.
 */

public interface SearchService {
  // ?q=tetris+language:assembly&sort=stars&order=desc&page=1&per_page=2

  //@Headers(Profile.API_SEARCH_REPOS_TEXT_MATCH)
  @GET("/search/repositories")
  Call<SearchRepos> repositories(
      @Query("q") String q,
      @Query("sort") String sort,
      @Query("order") String order,
      @Query("page") int page);

  @GET("/search/users")
  Call<SearchUser> users(
      @Query("q") String q,
      @Query("sort") String sort,
      @Query("order") String order,
      @Query("page") int page
  );
}
