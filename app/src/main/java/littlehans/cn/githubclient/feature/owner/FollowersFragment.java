package littlehans.cn.githubclient.feature.owner;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import littlehans.cn.githubclient.api.GithubService;
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
public class FollowersFragment extends PagedFragment<Comment.User> {
  private UsersService mUsersService;
  private User mUser;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mUsersService = GithubService.createUserService();
    mUser = AccountManager.getAccount();
  }

  public static Fragment create() {
    return new FollowersFragment();
  }

  @Override
  public void bindViewHolders(EasyRecyclerAdapter adapter) {
    adapter.bind(Comment.User.class, FollowerViewHolder.class);
  }

  @Override
  public Call<ArrayList<Comment.User>> paginate(int page, int perPage) {
    return mUsersService.getUserFollowers(mUser.name,page);
  }

  @Override
  public Object getKeyForData(Comment.User item) {
    return item.id;
  }
}
