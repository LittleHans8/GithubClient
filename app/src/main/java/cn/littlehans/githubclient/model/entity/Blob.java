package cn.littlehans.githubclient.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by LittleHans on 2016/10/25.
 */

@JsonIgnoreProperties(ignoreUnknown = true) public class Blob {
  public String sha;
  public int size;
  public String url;
  public String content;
  public String encoding;
}
