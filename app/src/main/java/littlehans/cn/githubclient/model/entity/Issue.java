package littlehans.cn.githubclient.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Created by LittleHans on 2016/10/26.
 * https://api.github.com/repos/twbs/bootstrap/issues
 */
@JsonIgnoreProperties(ignoreUnknown = true) public class Issue {

  public String url;
  public String repository_url;
  public String labels_url;
  public String comments_url;
  public String events_url;
  public String html_url;
  public int id;
  public int number;
  public String title;
  public User user;
  public String state;
  public boolean locked;
  public Object assignee;
  public Object milestone;
  public int comments;
  public String created_at;
  public String updated_at;
  public Object closed_at;
  public String body;
  public List<Labels> labels;
  public List<?> assignees;

  public static class User {
    public String login;
    public int id;
    public String avatar_url;
    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url;
    public String subscriptions_url;
    public String organizations_url;
    public String repos_url;
    public String events_url;
    public String received_events_url;
    public String type;
    public boolean site_admin;
  }

  public static class Labels {
    public String url;
    public String name;
    public String color;
  }
}
