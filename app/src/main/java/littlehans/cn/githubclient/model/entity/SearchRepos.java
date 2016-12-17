package littlehans.cn.githubclient.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
  public List<Repository> items;

  public SearchRepos() {
  }

  protected SearchRepos(Parcel in) {
    this.total_count = in.readInt();
    this.incomplete_results = in.readByte() != 0;
    this.items = new ArrayList<Repository>();
    in.readList(this.items, Repository.class.getClassLoader());
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

}
