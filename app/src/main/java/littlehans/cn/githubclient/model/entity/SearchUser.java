package littlehans.cn.githubclient.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Created by littlehans on 2016/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true) public class SearchUser {

  public int total_count;
  public boolean incomplete_results;
  public List<Items> items;

  public static class Items {
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
    public double score;
    public List<TextMatches> text_matches;

    public static class TextMatches {
      public String object_url;
      public String object_type;
      public String property;
      public String fragment;
      public List<Matches> matches;

      public static class Matches {
        public String text;
        public List<Integer> indices;
      }
    }
  }
}
