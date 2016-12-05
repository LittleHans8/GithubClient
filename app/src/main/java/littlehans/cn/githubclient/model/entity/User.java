package littlehans.cn.githubclient.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by littlehans on 2016/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true) public class User implements Parcelable{


  /**
   * login : octocat
   * id : 1
   * avatar_url : https://github.com/images/error/octocat_happy.gif
   * gravatar_id :
   * url : https://api.github.com/users/octocat
   * html_url : https://github.com/octocat
   * followers_url : https://api.github.com/users/octocat/followers
   * following_url : https://api.github.com/users/octocat/following{/other_user}
   * gists_url : https://api.github.com/users/octocat/gists{/gist_id}
   * starred_url : https://api.github.com/users/octocat/starred{/owner}{/repo}
   * subscriptions_url : https://api.github.com/users/octocat/subscriptions
   * organizations_url : https://api.github.com/users/octocat/orgs
   * repos_url : https://api.github.com/users/octocat/repos
   * events_url : https://api.github.com/users/octocat/events{/privacy}
   * received_events_url : https://api.github.com/users/octocat/received_events
   * type : User
   * site_admin : false
   * name : monalisa octocat
   * company : GitHub
   * blog : https://github.com/blog
   * location : San Francisco
   * email : octocat@github.com
   * hireable : false
   * bio : There once was...
   * public_repos : 2
   * public_gists : 1
   * followers : 20
   * following : 0
   * created_at : 2008-01-14T04:33:35Z
   * updated_at : 2008-01-14T04:33:35Z
   * total_private_repos : 100
   * owned_private_repos : 100
   * private_gists : 81
   * disk_usage : 10000
   * collaborators : 8
   * plan : {"name":"Medium","space":400,"private_repos":20,"collaborators":0}
   */

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
  public String name;
  public String company;
  public String blog;
  public String location;
  public String email;
  public boolean hireable;
  public String bio;
  public int public_repos;
  public int public_gists;
  public int followers;
  public int following;
  public String created_at;
  public String updated_at;
  public int total_private_repos;
  public int owned_private_repos;
  public int private_gists;
  public int disk_usage;
  public int collaborators;
  public PlanBean plan;

  public User(){}

  protected User(Parcel in) {
    login = in.readString();
    id = in.readInt();
    avatar_url = in.readString();
    gravatar_id = in.readString();
    url = in.readString();
    html_url = in.readString();
    followers_url = in.readString();
    following_url = in.readString();
    gists_url = in.readString();
    starred_url = in.readString();
    subscriptions_url = in.readString();
    organizations_url = in.readString();
    repos_url = in.readString();
    events_url = in.readString();
    received_events_url = in.readString();
    type = in.readString();
    site_admin = in.readByte() != 0;
    name = in.readString();
    company = in.readString();
    blog = in.readString();
    location = in.readString();
    email = in.readString();
    hireable = in.readByte() != 0;
    bio = in.readString();
    public_repos = in.readInt();
    public_gists = in.readInt();
    followers = in.readInt();
    following = in.readInt();
    created_at = in.readString();
    updated_at = in.readString();
    total_private_repos = in.readInt();
    owned_private_repos = in.readInt();
    private_gists = in.readInt();
    disk_usage = in.readInt();
    collaborators = in.readInt();
    plan = in.readParcelable(PlanBean.class.getClassLoader());
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    @Override
    public User createFromParcel(Parcel in) {
      return new User(in);
    }

    @Override
    public User[] newArray(int size) {
      return new User[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(login);
    dest.writeInt(id);
    dest.writeString(avatar_url);
    dest.writeString(gravatar_id);
    dest.writeString(url);
    dest.writeString(html_url);
    dest.writeString(followers_url);
    dest.writeString(following_url);
    dest.writeString(gists_url);
    dest.writeString(starred_url);
    dest.writeString(subscriptions_url);
    dest.writeString(organizations_url);
    dest.writeString(repos_url);
    dest.writeString(events_url);
    dest.writeString(received_events_url);
    dest.writeString(type);
    dest.writeByte((byte) (site_admin ? 1 : 0));
    dest.writeString(name);
    dest.writeString(company);
    dest.writeString(blog);
    dest.writeString(location);
    dest.writeString(email);
    dest.writeByte((byte) (hireable ? 1 : 0));
    dest.writeString(bio);
    dest.writeInt(public_repos);
    dest.writeInt(public_gists);
    dest.writeInt(followers);
    dest.writeInt(following);
    dest.writeString(created_at);
    dest.writeString(updated_at);
    dest.writeInt(total_private_repos);
    dest.writeInt(owned_private_repos);
    dest.writeInt(private_gists);
    dest.writeInt(disk_usage);
    dest.writeInt(collaborators);
    dest.writeParcelable(plan, flags);
  }

  @JsonIgnoreProperties(ignoreUnknown = true) public static class PlanBean implements Parcelable{
    /**
     * name : Medium
     * space : 400
     * private_repos : 20
     * collaborators : 0
     */

    public String name;
    public int space;
    public int private_repos;
    public int collaborators;

    public PlanBean(){}

    protected PlanBean(Parcel in) {
      name = in.readString();
      space = in.readInt();
      private_repos = in.readInt();
      collaborators = in.readInt();
    }

    public static final Creator<PlanBean> CREATOR = new Creator<PlanBean>() {
      @Override
      public PlanBean createFromParcel(Parcel in) {
        return new PlanBean(in);
      }

      @Override
      public PlanBean[] newArray(int size) {
        return new PlanBean[size];
      }
    };

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(name);
      dest.writeInt(space);
      dest.writeInt(private_repos);
      dest.writeInt(collaborators);
    }
  }
}
