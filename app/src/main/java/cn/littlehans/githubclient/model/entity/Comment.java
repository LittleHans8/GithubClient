package cn.littlehans.githubclient.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smartydroid.android.starter.kit.model.entity.Entity;

/**
 * Created by LittleHans on 2016/10/28.
 */
@JsonIgnoreProperties(ignoreUnknown = true) public class Comment implements Parcelable {

  public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
    @Override public Comment createFromParcel(Parcel source) {
      return new Comment(source);
    }

    @Override public Comment[] newArray(int size) {
      return new Comment[size];
    }
  };
  public String url;
  public String html_url;
  public String issue_url;
  public int id;
  public User user;
  public String created_at;
  public String updated_at;
  public String body;

  public Comment() {
  }

  protected Comment(Parcel in) {
    this.url = in.readString();
    this.html_url = in.readString();
    this.issue_url = in.readString();
    this.id = in.readInt();
    this.user = in.readParcelable(User.class.getClassLoader());
    this.created_at = in.readString();
    this.updated_at = in.readString();
    this.body = in.readString();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.url);
    dest.writeString(this.html_url);
    dest.writeString(this.issue_url);
    dest.writeInt(this.id);
    dest.writeParcelable(this.user, flags);
    dest.writeString(this.created_at);
    dest.writeString(this.updated_at);
    dest.writeString(this.body);
  }

  @JsonIgnoreProperties(ignoreUnknown = true) public static class User extends Entity {
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
      @Override public User createFromParcel(Parcel source) {
        return new User(source);
      }

      @Override public User[] newArray(int size) {
        return new User[size];
      }
    };
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

    public User() {
    }

    protected User(Parcel in) {
      this.login = in.readString();
      this.id = in.readInt();
      this.avatar_url = in.readString();
      this.gravatar_id = in.readString();
      this.url = in.readString();
      this.html_url = in.readString();
      this.followers_url = in.readString();
      this.following_url = in.readString();
      this.gists_url = in.readString();
      this.starred_url = in.readString();
      this.subscriptions_url = in.readString();
      this.organizations_url = in.readString();
      this.repos_url = in.readString();
      this.events_url = in.readString();
      this.received_events_url = in.readString();
      this.type = in.readString();
      this.site_admin = in.readByte() != 0;
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.login);
      dest.writeInt(this.id);
      dest.writeString(this.avatar_url);
      dest.writeString(this.gravatar_id);
      dest.writeString(this.url);
      dest.writeString(this.html_url);
      dest.writeString(this.followers_url);
      dest.writeString(this.following_url);
      dest.writeString(this.gists_url);
      dest.writeString(this.starred_url);
      dest.writeString(this.subscriptions_url);
      dest.writeString(this.organizations_url);
      dest.writeString(this.repos_url);
      dest.writeString(this.events_url);
      dest.writeString(this.received_events_url);
      dest.writeString(this.type);
      dest.writeByte(this.site_admin ? (byte) 1 : (byte) 0);
    }
  }
}
