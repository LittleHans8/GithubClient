package littlehans.cn.githubclient.feature.owner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.ActivityService;
import littlehans.cn.githubclient.feature.owner.viewholder.StarsViewHolder;
import littlehans.cn.githubclient.model.entity.Repository;
import littlehans.cn.githubclient.ui.fragment.PagedFragment;
import retrofit2.Call;
import support.ui.adapters.EasyRecyclerAdapter;

/**
 * Created by LittleHans on 2016/11/18.
 */
public class StartsFragment extends PagedFragment<Repository> {

  private ActivityService mActivityService;

  public static Fragment create() {
    return new StartsFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityService = GithubService.createActivityService();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override public void bindViewHolders(EasyRecyclerAdapter adapter) {
    adapter.bind(Repository.class, StarsViewHolder.class);
  }

  @Override public Call<ArrayList<Repository>> paginate(int page, int perPage) {
    return mActivityService.getUserStarredRepos("LittleHans8",page);
  }

  @Override public Object getKeyForData(Repository item) {
    return item.id;
  }

  @Override public void onItemClick(int position, View view) {
    super.onItemClick(position, view);
    Repository repository = getItem(position);
    Toast.makeText(getActivity(), repository.full_name + position, Toast.LENGTH_SHORT).show();
  }
}
