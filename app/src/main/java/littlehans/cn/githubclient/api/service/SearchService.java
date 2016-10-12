package littlehans.cn.githubclient.api.service;

import littlehans.cn.githubclient.model.entity.SearchRepos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by LittleHans on 2016/9/27.
 */

public interface SearchService {
  // ?q=tetris+language:assembly&sort=stars&order=desc&page=1&per_page=2

  /**
   *
   * @param q
   * @param sort
   * @param order
   * @return
   */
  //@Headers(Profile.API_SEARCH_REPOS_TEXT_MATCH)
  @GET("/search/repositories")
  Call<SearchRepos> repositories(
      @Query("q") String q,
      @Query("sort") String sort,
      @Query("order") String order,
      @Query("page") int page);
}
