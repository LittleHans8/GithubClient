package cn.littlehans.githubclient.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Created by littlehans on 2016/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true) public class SearchUser implements Parcelable {

  public int total_count;
  public boolean incomplete_results;
  public List<User> items;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.total_count);
    dest.writeByte(this.incomplete_results ? (byte) 1 : (byte) 0);
    dest.writeTypedList(this.items);
  }

  public SearchUser() {
  }

  protected SearchUser(Parcel in) {
    this.total_count = in.readInt();
    this.incomplete_results = in.readByte() != 0;
    this.items = in.createTypedArrayList(User.CREATOR);
  }

  public static final Creator<SearchUser> CREATOR = new Creator<SearchUser>() {
    @Override public SearchUser createFromParcel(Parcel source) {
      return new SearchUser(source);
    }

    @Override public SearchUser[] newArray(int size) {
      return new SearchUser[size];
    }
  };
}
