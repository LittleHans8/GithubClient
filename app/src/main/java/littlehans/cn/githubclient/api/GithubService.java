package littlehans.cn.githubclient.api;

import littlehans.cn.githubclient.api.service.ActivityService;
import littlehans.cn.githubclient.api.service.GitDateService;
import littlehans.cn.githubclient.api.service.InfoService;
import littlehans.cn.githubclient.api.service.IssuesService;
import littlehans.cn.githubclient.api.service.RepositoryService;
import littlehans.cn.githubclient.api.service.SearchService;
import littlehans.cn.githubclient.api.service.UsersService;
import littlehans.cn.githubclient.network.retrofit2.RetrofitBuilder;
import retrofit2.Retrofit;

/**
 * Created by littlehans on 2016/9/27.
 */

public class GitHubService {

  public static ActivityService createActivityService() {
    return retrofit().create(ActivityService.class);
  }

  public static SearchService createSearchService() {
    return retrofit().create(SearchService.class);
  }

  public static InfoService createInfoService() {
    return retrofit().create(InfoService.class);
  }

  public static GitDateService createGitDateService() {
    return retrofit().create(GitDateService.class);
  }

  public static RepositoryService createRepositoryService() {
    return retrofit().create(RepositoryService.class);
  }

  public static IssuesService createIssuesService() {
    return retrofit().create(IssuesService.class);
  }

  public static UsersService createUserService() {
    return retrofit().create(UsersService.class);
  }

  private static Retrofit retrofit() {

    return RetrofitBuilder.get().retrofit();
  }
}
