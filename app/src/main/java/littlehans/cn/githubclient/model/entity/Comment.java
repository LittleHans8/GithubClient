package littlehans.cn.githubclient.model.entity;

/**
 * Created by LittleHans on 2016/10/28.
 */
public class Comment {

  public String url;
  public String html_url;
  public String issue_url;
  public int id;
  public User user;
  public String created_at;
  public String updated_at;
  public String body;

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
}
