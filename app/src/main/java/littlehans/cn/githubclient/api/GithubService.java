package littlehans.cn.githubclient.api;

import littlehans.cn.githubclient.api.service.SearchService;
import littlehans.cn.githubclient.network.retrofit.RetrofitBuilder;
import retrofit2.Retrofit;

/**
 * Created by LittleHans on 2016/9/27.
 */

public class GithubService {

  public static SearchService createSearchService() {
    return retrofit().create(SearchService.class);
  }

  private static Retrofit retrofit() {

    return RetrofitBuilder.get().retrofit();
  }
}
