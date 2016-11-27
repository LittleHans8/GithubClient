package littlehans.cn.githubclient.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartydroid.android.starter.kit.model.entity.Entity;

/**
 * Created by LittleHans on 2016/11/27.
 */

@JsonIgnoreProperties(ignoreUnknown = true) public class Repository extends Entity {

  public static final Creator<Repository> CREATOR = new Creator<Repository>() {
    @Override public Repository createFromParcel(Parcel source) {
      return new Repository(source);
    }

    @Override public Repository[] newArray(int size) {
      return new Repository[size];
    }
  };
  public int id;
  public String name;
  public String full_name;
  public SearchRepos.Items.Owner owner;
  @JsonProperty("private") public boolean privateX;
  public String html_url;
  public String description;
  public boolean fork;
  public String url;
  public String created_at;
  public String updated_at;
  public String pushed_at;
  public String homepage;
  public int size;
  public int stargazers_count;
  public int watchers_count;
  public String language;
  public boolean has_issues;
  public boolean has_downloads;
  public boolean has_wiki;
  public boolean has_pages;
  public int forks_count;
  public int open_issues_count;
  public int forks;
  public int open_issues;
  public int watchers;
  public String default_branch;
  public Permissions permissions;

  public Repository() {
  }

  protected Repository(Parcel in) {
    this.id = in.readInt();
    this.name = in.readString();
    this.full_name = in.readString();
    this.owner = in.readParcelable(SearchRepos.Items.Owner.class.getClassLoader());
    this.privateX = in.readByte() != 0;
    this.html_url = in.readString();
    this.description = in.readString();
    this.fork = in.readByte() != 0;
    this.url = in.readString();
    this.created_at = in.readString();
    this.updated_at = in.readString();
    this.pushed_at = in.readString();
    this.homepage = in.readString();
    this.size = in.readInt();
    this.stargazers_count = in.readInt();
    this.watchers_count = in.readInt();
    this.language = in.readString();
    this.has_issues = in.readByte() != 0;
    this.has_downloads = in.readByte() != 0;
    this.has_wiki = in.readByte() != 0;
    this.has_pages = in.readByte() != 0;
    this.forks_count = in.readInt();
    this.open_issues_count = in.readInt();
    this.forks = in.readInt();
    this.open_issues = in.readInt();
    this.watchers = in.readInt();
    this.default_branch = in.readString();
    this.permissions = in.readParcelable(Permissions.class.getClassLoader());
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.name);
    dest.writeString(this.full_name);
    dest.writeParcelable(this.owner, flags);
    dest.writeByte(this.privateX ? (byte) 1 : (byte) 0);
    dest.writeString(this.html_url);
    dest.writeString(this.description);
    dest.writeByte(this.fork ? (byte) 1 : (byte) 0);
    dest.writeString(this.url);
    dest.writeString(this.created_at);
    dest.writeString(this.updated_at);
    dest.writeString(this.pushed_at);
    dest.writeString(this.homepage);
    dest.writeInt(this.size);
    dest.writeInt(this.stargazers_count);
    dest.writeInt(this.watchers_count);
    dest.writeString(this.language);
    dest.writeByte(this.has_issues ? (byte) 1 : (byte) 0);
    dest.writeByte(this.has_downloads ? (byte) 1 : (byte) 0);
    dest.writeByte(this.has_wiki ? (byte) 1 : (byte) 0);
    dest.writeByte(this.has_pages ? (byte) 1 : (byte) 0);
    dest.writeInt(this.forks_count);
    dest.writeInt(this.open_issues_count);
    dest.writeInt(this.forks);
    dest.writeInt(this.open_issues);
    dest.writeInt(this.watchers);
    dest.writeString(this.default_branch);
    dest.writeParcelable(this.permissions, flags);
  }

  public static class Permissions implements Parcelable {
    public static final Creator<Permissions> CREATOR = new Creator<Permissions>() {
      @Override public Permissions createFromParcel(Parcel source) {
        return new Permissions(source);
      }

      @Override public Permissions[] newArray(int size) {
        return new Permissions[size];
      }
    };
    public boolean admin;
    public boolean push;
    public boolean pull;

    public Permissions() {
    }

    protected Permissions(Parcel in) {
      this.admin = in.readByte() != 0;
      this.push = in.readByte() != 0;
      this.pull = in.readByte() != 0;
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeByte(this.admin ? (byte) 1 : (byte) 0);
      dest.writeByte(this.push ? (byte) 1 : (byte) 0);
      dest.writeByte(this.pull ? (byte) 1 : (byte) 0);
    }
  }
}
