package cn.littlehans.githubclient.model.entity;

import java.util.List;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class Trees {

  public String sha;
  public String url;
  public boolean truncated;
  public List<Tree> tree;

  public static class Tree {
    public String path;
    public String mode;
    public String type;
    public String sha;
    public int size;
    public String url;
  }
}
