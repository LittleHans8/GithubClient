package cn.littlehans.githubclient.api;

import cn.littlehans.githubclient.api.service.ActivityService;
import cn.littlehans.githubclient.api.service.GitDateService;
import cn.littlehans.githubclient.api.service.InfoService;
import cn.littlehans.githubclient.api.service.IssuesService;
import cn.littlehans.githubclient.api.service.RepositoryService;
import cn.littlehans.githubclient.api.service.SearchService;
import cn.littlehans.githubclient.api.service.UsersService;
import cn.littlehans.githubclient.network.retrofit2.RetrofitBuilder;
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

  public static UsersService createUsersService() {
    return retrofit().create(UsersService.class);
  }

  private static Retrofit retrofit() {

    return RetrofitBuilder.get().retrofit();
  }
}
