package littlehans.cn.githubclient.feature.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import littlehans.cn.githubclient.Nav;
import littlehans.cn.githubclient.api.GitHubService;
import littlehans.cn.githubclient.api.service.RepositoryService;
import littlehans.cn.githubclient.feature.owner.viewholder.AllReposViewHolder;
import littlehans.cn.githubclient.feature.repos.ReposActivity;
import littlehans.cn.githubclient.model.AccountManager;
import littlehans.cn.githubclient.model.entity.Repository;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.ui.fragment.PagedFragment;
import retrofit2.Call;
import support.ui.adapters.EasyRecyclerAdapter;
import timber.log.Timber;

/**
 * Created by LittleHans on 2016/11/18.
 */
public class AllReposFragment extends PagedFragment<Repository> {

  private RepositoryService mRepositoryService;
  private User mUser;

  public static Fragment create() {
    return new AllReposFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mRepositoryService = GitHubService.createRepositoryService();
    mUser = AccountManager.getAccount();
    Log.d("TAG", "onCreate: " + mUser.login);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override public void bindViewHolders(EasyRecyclerAdapter adapter) {
    adapter.bind(Repository.class, AllReposViewHolder.class);
  }

  @Override public Call<ArrayList<Repository>> paginate(int page, int perPage) {
    Timber.d(mUser.login);
    return mRepositoryService.getOwnRepos(page);
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
