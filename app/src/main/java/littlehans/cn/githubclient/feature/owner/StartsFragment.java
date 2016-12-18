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
import littlehans.cn.githubclient.api.service.ActivityService;
import littlehans.cn.githubclient.feature.owner.viewholder.StarsViewHolder;
import littlehans.cn.githubclient.feature.repos.ReposActivity;
import littlehans.cn.githubclient.model.AccountManager;
import littlehans.cn.githubclient.model.entity.Repository;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.ui.fragment.PagedFragment;
import retrofit2.Call;
import support.ui.adapters.EasyRecyclerAdapter;

/**
 * Created by LittleHans on 2016/11/18.
 */
public class StartsFragment extends PagedFragment<Repository> {

  private ActivityService mActivityService;
  private User mUser;

  public static Fragment create() {
    return new StartsFragment();
  }

  public static Fragment create(Parcelable data) {
    StartsFragment fragment = new StartsFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Nav.USER, data);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityService = GitHubService.createActivityService();
    if (getArguments() != null) {
      User user = getArguments().getParcelable(Nav.USER);
      mUser = user;
    } else {
      mUser = AccountManager.getAccount();
    }
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override public void bindViewHolders(EasyRecyclerAdapter adapter) {
    adapter.bind(Repository.class, StarsViewHolder.class);
  }

  @Override public Call<ArrayList<Repository>> paginate(int page, int perPage) {
    return mActivityService.getUserStarredRepos(mUser.login, page);
  }

  @Override public Object getKeyForData(Repository item) {
    return item.id;
  }

  @Override public void onItemClick(int position, View view) {
    super.onItemClick(position, view);
    Repository repository = getItem(position);
    Intent intent = new Intent(getActivity(), ReposActivity.class);
    intent.putExtra(Nav.REPO_ITEM, repository); //reposTests
    startActivity(intent);
  }
}
