package littlehans.cn.githubclient.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Created by LittleHans on 2016/11/20.
 */

@JsonIgnoreProperties(ignoreUnknown = true) public class Event {

  public String id;
  public String type;
  public Actor actor;
  public Repo repo;
  public Payload payload;
  @JsonProperty("public") public boolean publicX;
  public String created_at;
  public Org org;

  public static class Actor {
    public int id;
    public String login;
    public String display_login;
    public String gravatar_id;
    public String url;
    public String avatar_url;
  }

  public static class Repo {
    public int id;
    public String name;
    public String url;
  }

  public static class Payload {
    public String action;
    public Issue issue;
    public Comment comment;

    public static class Issue {
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
      public String closed_at;
      public PullRequest pull_request;
      public String body;
      public List<?> labels;
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

      public static class PullRequest {
        public String url;
        public String html_url;
        public String diff_url;
        public String patch_url;
      }
    }

    public static class Comment {
      public String url;
      public String html_url;
      public String issue_url;
      public int id;
      public UserX user;
      public String created_at;
      public String updated_at;
      public String body;

      public static class UserX {
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
  }

  public static class Org {
    public int id;
    public String login;
    public String gravatar_id;
    public String url;
    public String avatar_url;
  }
}
