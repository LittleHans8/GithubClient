package littlehans.cn.githubclient.feature.repos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
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
import littlehans.cn.githubclient.utilities.DividerItemDecoration;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposCodeFragment extends NetworkFragment<Trees> {

  @BindView(R.id.recycler_view_path) RecyclerView mRecyclerViewPath;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  Comparator<Trees.Tree> mTreeComparator;
  ReposCodePathAdapter pathAdapter;
  private LinearLayoutManager mLinearLayoutManager;
  private List<ReposCodePath> mPath;
  private OnItemClickListener mItemClickListener;
  private OnItemClickListener mPathItemClickListener;

  public static Fragment create() {
    return new ReposCodeFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    setHasOptionsMenu(true);

    mPath = new ArrayList<>();
    mPath.add(new ReposCodePath("root", "fdb5af3bd919c90720c47953f191c652a9c8fd93"));
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repos_code;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    networkQueue().enqueue(GithubService.createGitDateService()
        .getTree("twbs", "bootstrap", "fdb5af3bd919c90720c47953f191c652a9c8fd93"));

    initUI();
  }

  private void initUI() {
    mLinearLayoutManager = new LinearLayoutManager(getActivity());
    LinearLayoutManager linearLayoutManagerPath =
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    mRecyclerViewPath.setLayoutManager(linearLayoutManagerPath);
    mRecyclerView.setLayoutManager(mLinearLayoutManager);

    pathAdapter = new ReposCodePathAdapter(mPath);
    mRecyclerViewPath.setAdapter(pathAdapter);
    mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

    mTreeComparator = new Comparator<Trees.Tree>() {
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
    removeItemTouchListener();
    Collections.sort(data.tree, mTreeComparator);
    ReposCodeAdapter adapter = new ReposCodeAdapter(data.tree);
    mRecyclerView.setAdapter(adapter);
    addItemClickListener();
  }

  private void addItemClickListener() {
    mItemClickListener = new OnItemClickListener() {
      @Override public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        baseQuickAdapter.notifyDataSetChanged();
        Trees.Tree tree = (Trees.Tree) baseQuickAdapter.getData().get(i);
        if (tree.type.equals("tree")) {
          pathAdapter.add(pathAdapter.getItemCount(), new ReposCodePath(tree.path, tree.sha));
          pathAdapter.notifyDataSetChanged();
          networkQueue().enqueue(
              GithubService.createGitDateService().getTree("twbs", "bootstrap", tree.sha));
        }
      }
    };

    mPathItemClickListener = new OnItemClickListener() {
      @Override public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

        for (int position = i + 1; position < baseQuickAdapter.getItemCount(); position++) {
          baseQuickAdapter.remove(position);
        }
        baseQuickAdapter.notifyItemRangeRemoved(i + 1, baseQuickAdapter.getItemCount());

        TextView textView = (TextView) view;
        String sha = textView.getContentDescription().toString();
        networkQueue().enqueue(
            GithubService.createGitDateService().getTree("twbs", "bootstrap", sha));
      }
    };

    mRecyclerViewPath.addOnItemTouchListener(mPathItemClickListener);
    mRecyclerView.addOnItemTouchListener(mItemClickListener);
  }

  private void removeItemTouchListener() {
    mRecyclerView.removeOnItemTouchListener(mItemClickListener);
    mRecyclerViewPath.removeOnItemTouchListener(mPathItemClickListener);
  }

  @Override public void respondWithError(Throwable t) {

  }
}
