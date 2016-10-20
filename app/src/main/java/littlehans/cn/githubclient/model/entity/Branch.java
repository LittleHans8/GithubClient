package littlehans.cn.githubclient.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by LittleHans on 2016/10/20.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Branch {

  public String name;
  public Commit commit;

  public static class Commit {
    public String sha;
    public String url;
  }
}
