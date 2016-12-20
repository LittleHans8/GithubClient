package cn.littlehans.githubclient.api.service;

import cn.littlehans.githubclient.model.entity.Blob;
import cn.littlehans.githubclient.model.entity.Trees;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by LittleHans on 2016/10/20.
 */

public interface GitDateService {

  /**
   * Get a Trees
   * GET /items/:owner/:repo/git/trees/:sha
   * see: https://developer.github.com/v3/git/trees/#get-a-tree
   */
  @GET("/repos/{owner}/{repo}/git/trees/{sha}") Call<Trees> getTree(
      @Path("owner") String owner,
      @Path("repo") String repo,
      @Path("sha") String sha);

  /**
   * /items/:owner/:repo/git/blobs/:sha
   */
  @GET("/repos/{owner}/{repo}/git/blobs/{sha}") Call<Blob> getBlob(
      @Path("owner") String owner,
      @Path("repo") String repo,
      @Path("sha") String sha
  );
}
