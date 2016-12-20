package cn.littlehans.githubclient.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by LittleHans on 2016/11/27.
 */

@JsonIgnoreProperties(ignoreUnknown = true) public class StarredRepository {

  public int id;
  public User owner;
  public String name;
  public String full_name;
  public String description;
  @JsonProperty("private") public boolean privateX;
  public boolean fork;
  public String url;

  public String homepage;
  public String language;
  public int forks_count;
  public int stargazers_count;
  public int watchers_count;
  public int size;
  public String default_branch;
  public int open_issues_count;
  public boolean has_issues;
  public boolean has_wiki;
  public boolean has_pages;
  public boolean has_downloads;
  public String pushed_at;
  public String created_at;
  public String updated_at;
  public Permissions permissions;

  public static class Permissions {
    public boolean admin;
    public boolean push;
    public boolean pull;
  }
}
