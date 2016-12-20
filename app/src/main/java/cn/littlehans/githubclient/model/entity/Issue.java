package cn.littlehans.githubclient.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Created by LittleHans on 2016/10/26.
 * https://api.github.com/repos/twbs/bootstrap/issues
 */
@JsonIgnoreProperties(ignoreUnknown = true) public class Issue implements Parcelable {

  public static final Creator<Issue> CREATOR = new Creator<Issue>() {
    @Override public Issue createFromParcel(Parcel source) {
      return new Issue(source);
    }

    @Override public Issue[] newArray(int size) {
      return new Issue[size];
    }
  };
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
  public Assignee assignee;
  public Milestone milestone;
  public int comments;
  public String created_at;
  public String updated_at;
  public String closed_at;
  public String body;
  public ClosedBy closed_by;
  public List<Labels> labels;
  public List<Assignees> assignees;
  public PullRequest pull_request;

  public Issue() {
  }

  protected Issue(Parcel in) {
    this.url = in.readString();
    this.repository_url = in.readString();
    this.labels_url = in.readString();
    this.comments_url = in.readString();
    this.events_url = in.readString();
    this.html_url = in.readString();
    this.id = in.readInt();
    this.number = in.readInt();
    this.title = in.readString();
    this.user = in.readParcelable(User.class.getClassLoader());
    this.state = in.readString();
    this.locked = in.readByte() != 0;
    this.assignee = in.readParcelable(Assignee.class.getClassLoader());
    this.milestone = in.readParcelable(Milestone.class.getClassLoader());
    this.comments = in.readInt();
    this.created_at = in.readString();
    this.updated_at = in.readString();
    this.closed_at = in.readString();
    this.body = in.readString();
    this.closed_by = in.readParcelable(ClosedBy.class.getClassLoader());
    this.labels = in.createTypedArrayList(Labels.CREATOR);
    this.assignees = in.createTypedArrayList(Assignees.CREATOR);
    this.pull_request = in.readParcelable(PullRequest.class.getClassLoader());
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.url);
    dest.writeString(this.repository_url);
    dest.writeString(this.labels_url);
    dest.writeString(this.comments_url);
    dest.writeString(this.events_url);
    dest.writeString(this.html_url);
    dest.writeInt(this.id);
    dest.writeInt(this.number);
    dest.writeString(this.title);
    dest.writeParcelable(this.user, flags);
    dest.writeString(this.state);
    dest.writeByte(this.locked ? (byte) 1 : (byte) 0);
    dest.writeParcelable(this.assignee, flags);
    dest.writeParcelable(this.milestone, flags);
    dest.writeInt(this.comments);
    dest.writeString(this.created_at);
    dest.writeString(this.updated_at);
    dest.writeString(this.closed_at);
    dest.writeString(this.body);
    dest.writeParcelable(this.closed_by, flags);
    dest.writeTypedList(this.labels);
    dest.writeTypedList(this.assignees);
    dest.writeParcelable(this.pull_request, flags);
  }

  public static class PullRequest implements Parcelable {
    public static final Creator<PullRequest> CREATOR = new Creator<PullRequest>() {
      @Override public PullRequest createFromParcel(Parcel source) {
        return new PullRequest(source);
      }

      @Override public PullRequest[] newArray(int size) {
        return new PullRequest[size];
      }
    };
    public String url;
    public String html_url;
    public String diff_url;
    public String patch_url;

    public PullRequest() {
    }

    protected PullRequest(Parcel in) {
      this.url = in.readString();
      this.html_url = in.readString();
      this.diff_url = in.readString();
      this.patch_url = in.readString();
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.url);
      dest.writeString(this.html_url);
      dest.writeString(this.diff_url);
      dest.writeString(this.patch_url);
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true) public static class User implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
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

  @JsonIgnoreProperties(ignoreUnknown = true) public static class Assignee implements Parcelable {
    public static final Creator<Assignee> CREATOR = new Creator<Assignee>() {
      @Override public Assignee createFromParcel(Parcel source) {
        return new Assignee(source);
      }

      @Override public Assignee[] newArray(int size) {
        return new Assignee[size];
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

    public Assignee() {
    }

    protected Assignee(Parcel in) {
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

  @JsonIgnoreProperties(ignoreUnknown = true) public static class Milestone implements Parcelable {
    public static final Creator<Milestone> CREATOR = new Creator<Milestone>() {
      @Override public Milestone createFromParcel(Parcel source) {
        return new Milestone(source);
      }

      @Override public Milestone[] newArray(int size) {
        return new Milestone[size];
      }
    };
    public String url;
    public String html_url;
    public String labels_url;
    public int id;
    public int number;
    public String title;
    public String description;
    @JsonProperty("creator") public CreatorX creator;
    public int open_issues;
    public int closed_issues;
    public String state;
    public String created_at;
    public String updated_at;
    public String due_on;
    public String closed_at;

    public Milestone() {
    }

    protected Milestone(Parcel in) {
      this.url = in.readString();
      this.html_url = in.readString();
      this.labels_url = in.readString();
      this.id = in.readInt();
      this.number = in.readInt();
      this.title = in.readString();
      this.description = in.readString();
      this.creator = in.readParcelable(CreatorX.class.getClassLoader());
      this.open_issues = in.readInt();
      this.closed_issues = in.readInt();
      this.state = in.readString();
      this.created_at = in.readString();
      this.updated_at = in.readString();
      this.due_on = in.readString();
      this.closed_at = in.readString();
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.url);
      dest.writeString(this.html_url);
      dest.writeString(this.labels_url);
      dest.writeInt(this.id);
      dest.writeInt(this.number);
      dest.writeString(this.title);
      dest.writeString(this.description);
      dest.writeParcelable(this.creator, flags);
      dest.writeInt(this.open_issues);
      dest.writeInt(this.closed_issues);
      dest.writeString(this.state);
      dest.writeString(this.created_at);
      dest.writeString(this.updated_at);
      dest.writeString(this.due_on);
      dest.writeString(this.closed_at);
    }

    @JsonIgnoreProperties(ignoreUnknown = true) public static class CreatorX implements Parcelable {
      public static final Creator<CreatorX> CREATOR = new Creator<CreatorX>() {
        @Override public CreatorX createFromParcel(Parcel source) {
          return new CreatorX(source);
        }

        @Override public CreatorX[] newArray(int size) {
          return new CreatorX[size];
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

      public CreatorX() {
      }

      protected CreatorX(Parcel in) {
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

  @JsonIgnoreProperties(ignoreUnknown = true) public static class ClosedBy implements Parcelable {
    public static final Creator<ClosedBy> CREATOR = new Creator<ClosedBy>() {
      @Override public ClosedBy createFromParcel(Parcel source) {
        return new ClosedBy(source);
      }

      @Override public ClosedBy[] newArray(int size) {
        return new ClosedBy[size];
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

    public ClosedBy() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true) protected ClosedBy(Parcel in) {
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

  @JsonIgnoreProperties(ignoreUnknown = true) public static class Labels implements Parcelable {
    public static final Creator<Labels> CREATOR = new Creator<Labels>() {
      @Override public Labels createFromParcel(Parcel source) {
        return new Labels(source);
      }

      @Override public Labels[] newArray(int size) {
        return new Labels[size];
      }
    };
    public int id;
    public String url;
    public String name;
    public String color;
    @JsonProperty("default") public boolean defaultX;

    public Labels() {
    }

    protected Labels(Parcel in) {
      this.id = in.readInt();
      this.url = in.readString();
      this.name = in.readString();
      this.color = in.readString();
      this.defaultX = in.readByte() != 0;
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.id);
      dest.writeString(this.url);
      dest.writeString(this.name);
      dest.writeString(this.color);
      dest.writeByte(this.defaultX ? (byte) 1 : (byte) 0);
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true) public static class Assignees implements Parcelable {
    public static final Creator<Assignees> CREATOR = new Creator<Assignees>() {
      @Override public Assignees createFromParcel(Parcel source) {
        return new Assignees(source);
      }

      @Override public Assignees[] newArray(int size) {
        return new Assignees[size];
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
    public String events_urfl;
    public String received_events_url;
    public String type;
    public boolean site_admin;

    public Assignees() {
    }

    protected Assignees(Parcel in) {
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
      this.events_urfl = in.readString();
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
      dest.writeString(this.events_urfl);
      dest.writeString(this.received_events_url);
      dest.writeString(this.type);
      dest.writeByte(this.site_admin ? (byte) 1 : (byte) 0);
    }
  }
}
