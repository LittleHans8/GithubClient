package littlehans.cn.githubclient.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Created by LittleHans on 2016/11/4.
 */
@JsonIgnoreProperties(ignoreUnknown = true) public class ReceivedEvent
    implements Parcelable, MultiItemEntity {

  public static final String DELETE_EVENT = "DeleteEvent";
  public static final String WATCH_EVENT = "WatchEvent";
  public static final String CREATE_EVENT = "CreateEvent";
  public static final String MEMBER_EVENT = "MemberEvent";
  public static final String FORK_EVENT = "ForkEvent";

  public static final String PUSH_EVENT = "PushEvent";
  public static final String ISSUE_COMMENT_EVENT = "IssueCommentEvent";
  public static final String RELEASE_EVENT = "ReleaseEvent";
  public static final int TEXT = 0;
  public static final int TEXT_AVATAR = 1;
  public static final Creator<ReceivedEvent> CREATOR = new Creator<ReceivedEvent>() {
    @Override public ReceivedEvent createFromParcel(Parcel source) {
      return new ReceivedEvent(source);
    }

    @Override public ReceivedEvent[] newArray(int size) {
      return new ReceivedEvent[size];
    }
  };
  public String id;
  public String type;
  public Actor actor;
  public Repo repo;
  public Payload payload;
  @JsonProperty("public") public boolean publicX;
  public String created_at;
  public Org org;
  public int itemType = 1;

  public ReceivedEvent() {
  }

  protected ReceivedEvent(Parcel in) {
    this.id = in.readString();
    this.type = in.readString();
    this.actor = in.readParcelable(Actor.class.getClassLoader());
    this.repo = in.readParcelable(Repo.class.getClassLoader());
    this.payload = in.readParcelable(Payload.class.getClassLoader());
    this.publicX = in.readByte() != 0;
    this.created_at = in.readString();
    this.org = in.readParcelable(Org.class.getClassLoader());
    this.itemType = in.readInt();
  }

  @Override public int getItemType() {
    return itemType;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.type);
    dest.writeParcelable(this.actor, flags);
    dest.writeParcelable(this.repo, flags);
    dest.writeParcelable(this.payload, flags);
    dest.writeByte(this.publicX ? (byte) 1 : (byte) 0);
    dest.writeString(this.created_at);
    dest.writeParcelable(this.org, flags);
    dest.writeInt(this.itemType);
  }

  @JsonIgnoreProperties(ignoreUnknown = true) public static class Actor implements Parcelable {
    public static final Creator<Actor> CREATOR = new Creator<Actor>() {
      @Override public Actor createFromParcel(Parcel source) {
        return new Actor(source);
      }

      @Override public Actor[] newArray(int size) {
        return new Actor[size];
      }
    };
    public int id;
    public String login;
    public String display_login;
    public String gravatar_id;
    public String url;
    public String avatar_url;

    public Actor() {
    }

    protected Actor(Parcel in) {
      this.id = in.readInt();
      this.login = in.readString();
      this.display_login = in.readString();
      this.gravatar_id = in.readString();
      this.url = in.readString();
      this.avatar_url = in.readString();
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.id);
      dest.writeString(this.login);
      dest.writeString(this.display_login);
      dest.writeString(this.gravatar_id);
      dest.writeString(this.url);
      dest.writeString(this.avatar_url);
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true) public static class Repo implements Parcelable {
    public static final Creator<Repo> CREATOR = new Creator<Repo>() {
      @Override public Repo createFromParcel(Parcel source) {
        return new Repo(source);
      }

      @Override public Repo[] newArray(int size) {
        return new Repo[size];
      }
    };
    public int id;
    public String name;
    public String url;

    public Repo() {
    }

    protected Repo(Parcel in) {
      this.id = in.readInt();
      this.name = in.readString();
      this.url = in.readString();
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.id);
      dest.writeString(this.name);
      dest.writeString(this.url);
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true) public static class Payload implements Parcelable {

    public static final Creator<Payload> CREATOR = new Creator<Payload>() {
      @Override public Payload createFromParcel(Parcel source) {
        return new Payload(source);
      }

      @Override public Payload[] newArray(int size) {
        return new Payload[size];
      }
    };
    public int push_id;
    public int size;
    public int distinct_size;
    public String ref;
    public String ref_type;
    public String head;
    public String before;
    public String action;
    public List<Commits> commits;
    public Release release;

    public Payload() {
    }

    protected Payload(Parcel in) {
      this.push_id = in.readInt();
      this.size = in.readInt();
      this.distinct_size = in.readInt();
      this.ref = in.readString();
      this.ref_type = in.readString();
      this.head = in.readString();
      this.before = in.readString();
      this.action = in.readString();
      this.commits = in.createTypedArrayList(Commits.CREATOR);
      this.release = in.readParcelable(Release.class.getClassLoader());
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.push_id);
      dest.writeInt(this.size);
      dest.writeInt(this.distinct_size);
      dest.writeString(this.ref);
      dest.writeString(this.ref_type);
      dest.writeString(this.head);
      dest.writeString(this.before);
      dest.writeString(this.action);
      dest.writeTypedList(this.commits);
      dest.writeParcelable(this.release, flags);
    }

    @JsonIgnoreProperties(ignoreUnknown = true) public static class Commits implements Parcelable {
      public static final Creator<Commits> CREATOR = new Creator<Commits>() {
        @Override public Commits createFromParcel(Parcel source) {
          return new Commits(source);
        }

        @Override public Commits[] newArray(int size) {
          return new Commits[size];
        }
      };
      public String sha;
      public Author author;
      public String message;
      public boolean distinct;
      public String url;

      public Commits() {
      }

      protected Commits(Parcel in) {
        this.sha = in.readString();
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.message = in.readString();
        this.distinct = in.readByte() != 0;
        this.url = in.readString();
      }

      @Override public int describeContents() {
        return 0;
      }

      @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sha);
        dest.writeParcelable(this.author, flags);
        dest.writeString(this.message);
        dest.writeByte(this.distinct ? (byte) 1 : (byte) 0);
        dest.writeString(this.url);
      }

      @JsonIgnoreProperties(ignoreUnknown = true) public static class Author implements Parcelable {
        public static final Creator<Author> CREATOR = new Creator<Author>() {
          @Override public Author createFromParcel(Parcel source) {
            return new Author(source);
          }

          @Override public Author[] newArray(int size) {
            return new Author[size];
          }
        };
        public String email;
        public String name;

        public Author() {
        }

        protected Author(Parcel in) {
          this.email = in.readString();
          this.name = in.readString();
        }

        @Override public int describeContents() {
          return 0;
        }

        @Override public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(this.email);
          dest.writeString(this.name);
        }
      }
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true) public static class Org implements Parcelable {
    public static final Creator<Org> CREATOR = new Creator<Org>() {
      @Override public Org createFromParcel(Parcel source) {
        return new Org(source);
      }

      @Override public Org[] newArray(int size) {
        return new Org[size];
      }
    };
    public int id;
    public String login;
    public String gravatar_id;
    public String url;
    public String avatar_url;

    public Org() {
    }

    protected Org(Parcel in) {
      this.id = in.readInt();
      this.login = in.readString();
      this.gravatar_id = in.readString();
      this.url = in.readString();
      this.avatar_url = in.readString();
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.id);
      dest.writeString(this.login);
      dest.writeString(this.gravatar_id);
      dest.writeString(this.url);
      dest.writeString(this.avatar_url);
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true) public static class Release implements Parcelable {
    public static final Creator<Release> CREATOR = new Creator<Release>() {
      @Override public Release createFromParcel(Parcel source) {
        return new Release(source);
      }

      @Override public Release[] newArray(int size) {
        return new Release[size];
      }
    };
    public String tag_name;

    public Release() {
    }

    protected Release(Parcel in) {
      this.tag_name = in.readString();
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.tag_name);
    }
  }

}
