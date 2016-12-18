package littlehans.cn.githubclient.feature.owner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import java.util.ArrayList;
import littlehans.cn.githubclient.Nav;
import littlehans.cn.githubclient.api.GitHubService;
import littlehans.cn.githubclient.api.service.UsersService;
import littlehans.cn.githubclient.feature.owner.viewholder.FollowerViewHolder;
import littlehans.cn.githubclient.feature.user.UserActivity;
import littlehans.cn.githubclient.model.AccountManager;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.ui.fragment.PagedFragment;
import retrofit2.Call;
import support.ui.adapters.EasyRecyclerAdapter;

/**
 * Created by LittleHans on 2016/11/18.
 */
public class FollowingFragment extends PagedFragment<User> {
  private UsersService mUsersService;
  private User mUser;

  public static Fragment create() {
    return new FollowingFragment();
  }

  public static Fragment create(Parcelable data) {
    FollowingFragment fragment = new FollowingFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Nav.USER, data);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getArguments() != null) {
      User user = getArguments().getParcelable(Nav.USER);
      mUser = user;
    } else {
      mUser = AccountManager.getAccount();
    }
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mUsersService = GitHubService.createUsersService();
  }

  @Override public void bindViewHolders(EasyRecyclerAdapter adapter) {
    adapter.bind(User.class, FollowerViewHolder.class);
  }

  @Override public Call<ArrayList<User>> paginate(int page, int perPage) {
    return mUsersService.getUserFollowing(mUser.login, page);
  }

  @Override public Object getKeyForData(User item) {
    return item.id;
  }

  @Override public void onItemClick(int position, View view) {
    super.onItemClick(position, view);
    User user = getItem(position);
    Intent intent = new Intent(getActivity(), UserActivity.class);
    intent.putExtra(Nav.USER, user);
    startActivity(intent);
  }
}
