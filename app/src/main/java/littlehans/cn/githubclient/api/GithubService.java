package littlehans.cn.githubclient.api;

import littlehans.cn.githubclient.api.service.InfoService;
import littlehans.cn.githubclient.api.service.SearchService;
import littlehans.cn.githubclient.network.retrofit2.RetrofitBuilder;
import retrofit2.Retrofit;

/**
 * Created by littlehans on 2016/9/27.
 */

public class GithubService {

  public static SearchService createSearchService() {
    return retrofit().create(SearchService.class);
  }

  public static InfoService createInfoService() {
    return retrofit().create(InfoService.class);
  }

  private static Retrofit retrofit() {

    return RetrofitBuilder.get().retrofit();
  }
}
