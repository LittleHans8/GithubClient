package littlehans.cn.githubclient.feature.repos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.model.entity.Trees;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposCodeFragment extends NetworkFragment<Trees> implements OnItemClickListener{
  @BindView(R.id.recycler_view_path) RecyclerView mRecyclerViewPath;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  private LinearLayoutManager mLinearLayoutManager;
  private LinearLayoutManager mLinearLayoutManagerPath;
  ReposCodePathAdapter pathAdapter;
  private List<String> mPath;
  Comparator<Trees.Tree> treeComparator;


  public static Fragment create() {
    return new ReposCodeFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    setHasOptionsMenu(true);
    mLinearLayoutManager = new LinearLayoutManager(getActivity());
    mLinearLayoutManagerPath = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
    mPath = new ArrayList<>();
    mPath.add("Root");

  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repos_code;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    networkQueue().enqueue(GithubService.createGitDateService()
        .getTree("twbs", "bootstrap", "fdb5af3bd919c90720c47953f191c652a9c8fd93"));

    mRecyclerViewPath.setLayoutManager(mLinearLayoutManagerPath);
    pathAdapter = new ReposCodePathAdapter(mPath);
    mRecyclerViewPath.setAdapter(pathAdapter);

      treeComparator = new Comparator<Trees.Tree>() {
      @Override public int compare(Trees.Tree o1, Trees.Tree o2) {
        String blob = o1.type;
        String tree = o2.type;
        return tree.compareTo(blob);
      }
    };
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.fragment_repos_code, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public void respondSuccess(Trees data) {


    mRecyclerView.setLayoutManager(mLinearLayoutManager);
    Collections.sort(data.tree,treeComparator);
    ReposCodeAdapter adapter = new ReposCodeAdapter(data.tree);
    mRecyclerView.setAdapter(adapter);

    mRecyclerView.addOnItemTouchListener(this);
  }

  @Override public void respondWithError(Throwable t) {

  }

  @Override
  public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    Trees.Tree  tree = (Trees.Tree) baseQuickAdapter.getData().get(i);
    if (tree.type.equals("tree")) {
      Toast.makeText(getActivity(), tree.path, Toast.LENGTH_SHORT).show();
      Log.d(TAG, "SimpleOnItemClick: getItemCount" + pathAdapter.getItemCount());
      Log.d(TAG, "SimpleOnItemClick: tree.path=" + tree.path);
      pathAdapter.add(pathAdapter.getItemCount(),tree.path);
      pathAdapter.notifyDataSetChanged();
      networkQueue().enqueue(GithubService.createGitDateService().getTree("twbs","bootstrap",tree.sha));
    }
  }
}
