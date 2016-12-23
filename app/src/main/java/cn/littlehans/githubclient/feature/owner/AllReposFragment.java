package cn.littlehans.githubclient.feature.owner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import cn.littlehans.githubclient.Nav;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.api.service.RepositoryService;
import cn.littlehans.githubclient.feature.owner.viewholder.AllReposViewHolder;
import cn.littlehans.githubclient.feature.repos.ReposActivity;
import cn.littlehans.githubclient.model.AccountManager;
import cn.littlehans.githubclient.model.entity.Repository;
import cn.littlehans.githubclient.model.entity.User;
import cn.littlehans.githubclient.ui.fragment.PagedFragment;
import java.util.ArrayList;
import retrofit2.Call;
import support.ui.adapters.EasyRecyclerAdapter;
import timber.log.Timber;

/**
 * Created by LittleHans on 2016/11/18.
 */
public class AllReposFragment extends PagedFragment<Repository> {

  private RepositoryService mRepositoryService;
  private User mUser;
  private boolean isUser = false;

  public static Fragment create() {
    return new AllReposFragment();
  }

  public static Fragment create(Parcelable data) {
    AllReposFragment fragment = new AllReposFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Nav.USER, data);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mRepositoryService = GitHubService.createRepositoryService();

    if (getArguments() != null) {
      User user = getArguments().getParcelable(Nav.USER);
      mUser = user;
      isUser = true;
    } else {
      mUser = AccountManager.getAccount();
    }
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override public void bindViewHolders(EasyRecyclerAdapter adapter) {
    adapter.bind(Repository.class, AllReposViewHolder.class);
  }

  @Override public Call<ArrayList<Repository>> paginate(int page, int perPage) {
    if (isUser) {
      return mRepositoryService.getUserRepos(mUser.login, page);
    } else {
      return mRepositoryService.getOwnRepos(page);
    }
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

  @Override public void respondWithError(Throwable t) {
    super.respondWithError(t);
    Timber.d(t);
  }
}
