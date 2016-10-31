package littlehans.cn.githubclient.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by littlehans on 2016/9/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true) public class SearchRepos implements Parcelable {

  public static final Creator<SearchRepos> CREATOR = new Creator<SearchRepos>() {
    @Override public SearchRepos createFromParcel(Parcel source) {
      return new SearchRepos(source);
    }

    @Override public SearchRepos[] newArray(int size) {
      return new SearchRepos[size];
    }
  };
  public int total_count;
  public boolean incomplete_results;
  public List<Items> items;

  public SearchRepos() {
  }

  protected SearchRepos(Parcel in) {
    this.total_count = in.readInt();
    this.incomplete_results = in.readByte() != 0;
    this.items = new ArrayList<Items>();
    in.readList(this.items, Items.class.getClassLoader());
  }

  @Override public String toString() {
    return "SearchRepos{" +
        "total_count=" + total_count +
        ", incomplete_results=" + incomplete_results +
        ", items=" + items +
        '}';
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.total_count);
    dest.writeByte(this.incomplete_results ? (byte) 1 : (byte) 0);
    dest.writeList(this.items);
  }

  public static class Items implements Parcelable {
    public static final Creator<Items> CREATOR = new Creator<Items>() {
      @Override public Items createFromParcel(Parcel source) {
        return new Items(source);
      }

      @Override public Items[] newArray(int size) {
        return new Items[size];
      }
    };
    public int id;
    public String name;
    public String full_name;
    public Owner owner;
    @JsonProperty("private") public boolean privateX;
    public String html_url;
    public String description;
    public boolean fork;
    public String url;
    public String forks_url;
    public String keys_url;
    public String collaborators_url;
    public String teams_url;
    public String hooks_url;
    public String issue_events_url;
    public String events_url;
    public String assignees_url;
    public String branches_url;
    public String tags_url;
    public String blobs_url;
    public String git_tags_url;
    public String git_refs_url;
    public String trees_url;
    public String statuses_url;
    public String languages_url;
    public String stargazers_url;
    public String contributors_url;
    public String subscribers_url;
    public String subscription_url;
    public String commits_url;
    public String git_commits_url;
    public String comments_url;
    public String issue_comment_url;
    public String contents_url;
    public String compare_url;
    public String merges_url;
    public String archive_url;
    public String downloads_url;
    public String issues_url;
    public String pulls_url;
    public String milestones_url;
    public String notifications_url;
    public String labels_url;
    public String releases_url;
    public String deployments_url;
    public String created_at;
    public String updated_at;
    public String pushed_at;
    public String git_url;
    public String ssh_url;
    public String clone_url;
    public String svn_url;
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
    public String mirror_url;
    public int open_issues_count;
    public int forks;
    public int open_issues;
    public int watchers;
    public String default_branch;
    public double score;
    public List<TextMatches> text_matches;

    public Items() {
    }

    protected Items(Parcel in) {
      this.id = in.readInt();
      this.name = in.readString();
      this.full_name = in.readString();
      this.owner = in.readParcelable(Owner.class.getClassLoader());
      this.privateX = in.readByte() != 0;
      this.html_url = in.readString();
      this.description = in.readString();
      this.fork = in.readByte() != 0;
      this.url = in.readString();
      this.forks_url = in.readString();
      this.keys_url = in.readString();
      this.collaborators_url = in.readString();
      this.teams_url = in.readString();
      this.hooks_url = in.readString();
      this.issue_events_url = in.readString();
      this.events_url = in.readString();
      this.assignees_url = in.readString();
      this.branches_url = in.readString();
      this.tags_url = in.readString();
      this.blobs_url = in.readString();
      this.git_tags_url = in.readString();
      this.git_refs_url = in.readString();
      this.trees_url = in.readString();
      this.statuses_url = in.readString();
      this.languages_url = in.readString();
      this.stargazers_url = in.readString();
      this.contributors_url = in.readString();
      this.subscribers_url = in.readString();
      this.subscription_url = in.readString();
      this.commits_url = in.readString();
      this.git_commits_url = in.readString();
      this.comments_url = in.readString();
      this.issue_comment_url = in.readString();
      this.contents_url = in.readString();
      this.compare_url = in.readString();
      this.merges_url = in.readString();
      this.archive_url = in.readString();
      this.downloads_url = in.readString();
      this.issues_url = in.readString();
      this.pulls_url = in.readString();
      this.milestones_url = in.readString();
      this.notifications_url = in.readString();
      this.labels_url = in.readString();
      this.releases_url = in.readString();
      this.deployments_url = in.readString();
      this.created_at = in.readString();
      this.updated_at = in.readString();
      this.pushed_at = in.readString();
      this.git_url = in.readString();
      this.ssh_url = in.readString();
      this.clone_url = in.readString();
      this.svn_url = in.readString();
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
      this.mirror_url = in.readString();
      this.open_issues_count = in.readInt();
      this.forks = in.readInt();
      this.open_issues = in.readInt();
      this.watchers = in.readInt();
      this.default_branch = in.readString();
      this.score = in.readDouble();
      this.text_matches = in.createTypedArrayList(TextMatches.CREATOR);
    }

    @Override public String toString() {
      return "Items{" +
          "id=" + id +
          ", name='" + name + '\'' +
          ", full_name='" + full_name + '\'' +
          ", owner=" + owner +
          ", privateX=" + privateX +
          ", html_url='" + html_url + '\'' +
          ", description='" + description + '\'' +
          ", fork=" + fork +
          ", url='" + url + '\'' +
          ", forks_url='" + forks_url + '\'' +
          ", keys_url='" + keys_url + '\'' +
          ", collaborators_url='" + collaborators_url + '\'' +
          ", teams_url='" + teams_url + '\'' +
          ", hooks_url='" + hooks_url + '\'' +
          ", issue_events_url='" + issue_events_url + '\'' +
          ", events_url='" + events_url + '\'' +
          ", assignees_url='" + assignees_url + '\'' +
          ", branches_url='" + branches_url + '\'' +
          ", tags_url='" + tags_url + '\'' +
          ", blobs_url='" + blobs_url + '\'' +
          ", git_tags_url='" + git_tags_url + '\'' +
          ", git_refs_url='" + git_refs_url + '\'' +
          ", trees_url='" + trees_url + '\'' +
          ", statuses_url='" + statuses_url + '\'' +
          ", languages_url='" + languages_url + '\'' +
          ", stargazers_url='" + stargazers_url + '\'' +
          ", contributors_url='" + contributors_url + '\'' +
          ", subscribers_url='" + subscribers_url + '\'' +
          ", subscription_url='" + subscription_url + '\'' +
          ", commits_url='" + commits_url + '\'' +
          ", git_commits_url='" + git_commits_url + '\'' +
          ", comments_url='" + comments_url + '\'' +
          ", issue_comment_url='" + issue_comment_url + '\'' +
          ", contents_url='" + contents_url + '\'' +
          ", compare_url='" + compare_url + '\'' +
          ", merges_url='" + merges_url + '\'' +
          ", archive_url='" + archive_url + '\'' +
          ", downloads_url='" + downloads_url + '\'' +
          ", issues_url='" + issues_url + '\'' +
          ", pulls_url='" + pulls_url + '\'' +
          ", milestones_url='" + milestones_url + '\'' +
          ", notifications_url='" + notifications_url + '\'' +
          ", labels_url='" + labels_url + '\'' +
          ", releases_url='" + releases_url + '\'' +
          ", deployments_url='" + deployments_url + '\'' +
          ", created_at='" + created_at + '\'' +
          ", updated_at='" + updated_at + '\'' +
          ", pushed_at='" + pushed_at + '\'' +
          ", git_url='" + git_url + '\'' +
          ", ssh_url='" + ssh_url + '\'' +
          ", clone_url='" + clone_url + '\'' +
          ", svn_url='" + svn_url + '\'' +
          ", homepage=" + homepage +
          ", size=" + size +
          ", stargazers_count=" + stargazers_count +
          ", watchers_count=" + watchers_count +
          ", language='" + language + '\'' +
          ", has_issues=" + has_issues +
          ", has_downloads=" + has_downloads +
          ", has_wiki=" + has_wiki +
          ", has_pages=" + has_pages +
          ", forks_count=" + forks_count +
          ", mirror_url=" + mirror_url +
          ", open_issues_count=" + open_issues_count +
          ", forks=" + forks +
          ", open_issues=" + open_issues +
          ", watchers=" + watchers +
          ", default_branch='" + default_branch + '\'' +
          ", score=" + score +
          ", text_matches=" + text_matches +
          '}';
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
      dest.writeString(this.forks_url);
      dest.writeString(this.keys_url);
      dest.writeString(this.collaborators_url);
      dest.writeString(this.teams_url);
      dest.writeString(this.hooks_url);
      dest.writeString(this.issue_events_url);
      dest.writeString(this.events_url);
      dest.writeString(this.assignees_url);
      dest.writeString(this.branches_url);
      dest.writeString(this.tags_url);
      dest.writeString(this.blobs_url);
      dest.writeString(this.git_tags_url);
      dest.writeString(this.git_refs_url);
      dest.writeString(this.trees_url);
      dest.writeString(this.statuses_url);
      dest.writeString(this.languages_url);
      dest.writeString(this.stargazers_url);
      dest.writeString(this.contributors_url);
      dest.writeString(this.subscribers_url);
      dest.writeString(this.subscription_url);
      dest.writeString(this.commits_url);
      dest.writeString(this.git_commits_url);
      dest.writeString(this.comments_url);
      dest.writeString(this.issue_comment_url);
      dest.writeString(this.contents_url);
      dest.writeString(this.compare_url);
      dest.writeString(this.merges_url);
      dest.writeString(this.archive_url);
      dest.writeString(this.downloads_url);
      dest.writeString(this.issues_url);
      dest.writeString(this.pulls_url);
      dest.writeString(this.milestones_url);
      dest.writeString(this.notifications_url);
      dest.writeString(this.labels_url);
      dest.writeString(this.releases_url);
      dest.writeString(this.deployments_url);
      dest.writeString(this.created_at);
      dest.writeString(this.updated_at);
      dest.writeString(this.pushed_at);
      dest.writeString(this.git_url);
      dest.writeString(this.ssh_url);
      dest.writeString(this.clone_url);
      dest.writeString(this.svn_url);
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
      dest.writeString(this.mirror_url);
      dest.writeInt(this.open_issues_count);
      dest.writeInt(this.forks);
      dest.writeInt(this.open_issues);
      dest.writeInt(this.watchers);
      dest.writeString(this.default_branch);
      dest.writeDouble(this.score);
      dest.writeTypedList(this.text_matches);
    }

    public static class Owner implements Parcelable {
      public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override public Owner createFromParcel(Parcel source) {
          return new Owner(source);
        }

        @Override public Owner[] newArray(int size) {
          return new Owner[size];
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

      public Owner() {
      }

      protected Owner(Parcel in) {
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

    public static class TextMatches implements Parcelable {
      public static final Creator<TextMatches> CREATOR = new Creator<TextMatches>() {
        @Override public TextMatches createFromParcel(Parcel source) {
          return new TextMatches(source);
        }

        @Override public TextMatches[] newArray(int size) {
          return new TextMatches[size];
        }
      };
      public String object_url;
      public String object_type;
      public String property;
      public String fragment;
      public List<Matches> matches;

      public TextMatches() {
      }

      protected TextMatches(Parcel in) {
        this.object_url = in.readString();
        this.object_type = in.readString();
        this.property = in.readString();
        this.fragment = in.readString();
        this.matches = in.createTypedArrayList(Matches.CREATOR);
      }

      @Override public String toString() {
        return "TextMatches{" +
            "object_url='" + object_url + '\'' +
            ", object_type='" + object_type + '\'' +
            ", property='" + property + '\'' +
            ", fragment='" + fragment + '\'' +
            ", matches=" + matches +
            '}';
      }

      @Override public int describeContents() {
        return 0;
      }

      @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.object_url);
        dest.writeString(this.object_type);
        dest.writeString(this.property);
        dest.writeString(this.fragment);
        dest.writeTypedList(this.matches);
      }

      public static class Matches implements Parcelable {
        public static final Creator<Matches> CREATOR = new Creator<Matches>() {
          @Override public Matches createFromParcel(Parcel source) {
            return new Matches(source);
          }

          @Override public Matches[] newArray(int size) {
            return new Matches[size];
          }
        };
        public String text;
        public List<Integer> indices;

        public Matches() {
        }

        protected Matches(Parcel in) {
          this.text = in.readString();
          this.indices = new ArrayList<Integer>();
          in.readList(this.indices, Integer.class.getClassLoader());
        }

        @Override public String toString() {
          return "Matches{" +
              "text='" + text + '\'' +
              ", indices=" + indices +
              '}';
        }

        @Override public int describeContents() {
          return 0;
        }

        @Override public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(this.text);
          dest.writeList(this.indices);
        }
      }
    }
  }
}
