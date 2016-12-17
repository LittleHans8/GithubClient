package littlehans.cn.githubclient.feature.owner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import littlehans.cn.githubclient.api.GitHubService;
import littlehans.cn.githubclient.api.service.UsersService;
import littlehans.cn.githubclient.feature.owner.viewholder.FollowerViewHolder;
import littlehans.cn.githubclient.model.AccountManager;
import littlehans.cn.githubclient.model.entity.Comment;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.ui.fragment.PagedFragment;
import retrofit2.Call;
import support.ui.adapters.EasyRecyclerAdapter;

/**
 * Created by LittleHans on 2016/11/18.
 */
public class FollowingFragment extends PagedFragment<Comment.User> {
  private UsersService mUsersService;
  private User mUser;

  public static Fragment create() {
    return new FollowingFragment();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mUser = AccountManager.getAccount();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mUsersService = GitHubService.createUserService();
  }

  @Override
  public void bindViewHolders(EasyRecyclerAdapter adapter) {
    adapter.bind(Comment.User.class, FollowerViewHolder.class);
  }

  @Override
  public Call<ArrayList<Comment.User>> paginate(int page, int perPage) {
    return mUsersService.getUserFollowing(mUser.login,page);
  }

  @Override
  public Object getKeyForData(Comment.User item) {
    return item.id;
  }

  @Override
  public void onItemClick(int position, View view) {
    super.onItemClick(position, view);
    Comment.User user = getItem(position);
    Toast.makeText(getActivity(), user.login + position, Toast.LENGTH_SHORT).show();
  }
}
