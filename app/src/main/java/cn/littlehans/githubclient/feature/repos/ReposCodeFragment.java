package cn.littlehans.githubclient.feature.repos;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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
import butterknife.Bind;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.api.service.GitDateService;
import cn.littlehans.githubclient.api.service.RepositoryService;
import cn.littlehans.githubclient.model.entity.Branch;
import cn.littlehans.githubclient.model.entity.Repository;
import cn.littlehans.githubclient.model.entity.Trees;
import cn.littlehans.githubclient.network.task.ReadMarkDownTask;
import cn.littlehans.githubclient.ui.fragment.NetworkFragment;
import cn.littlehans.githubclient.utilities.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposCodeFragment extends NetworkFragment<Trees>
    implements OnDatePassListener, SwipeRefreshLayout.OnRefreshListener {

  public static final String TREE = "tree";
  public static final String BLOB = "blob";
  public static final String CONTENT = "content";
  public static final String MD = "md";
  public static final String MARKDOWN = "markdown";
  public static final String IS_MARK_DOWN_FILE = "isMarkDownFile";
  public static final String KEY_OWNER = "owner";
  public static final String KEY_REPO = "repo";
  public static final String KEY_SHA = "sha";

  @Bind(R.id.recycler_view_path) RecyclerView mRecyclerViewPath;
  @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
  @Bind(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  Comparator<Trees.Tree> mTreeComparator;
  ReposCodePathAdapter mPathAdapter;
  private GitDateService mGitDateService;
  private LinearLayoutManager mLinearLayoutManager;
  private List<ReposCodePath> mPath;
  private OnItemClickListener mItemClickListener;
  private OnItemClickListener mPathItemClickListener;
  private String mOwner;
  private String mRepo;
  private String mDefaultBranch;
  private String mSha;
  private RepositoryService mRepositoryService;

  public static Fragment create() {
    return new ReposCodeFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    setHasOptionsMenu(true);
    ReposActivity reposActivity = (ReposActivity) context;
    reposActivity.addOnDatePassListener(this);
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repos_code;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mGitDateService = GitHubService.createGitDateService();
    networkQueue().enqueue(mGitDateService.getTree(mOwner, mRepo, mSha));
    mPath = new ArrayList<>();
    initUI();
  }

  private void initUI() {

    mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    mLinearLayoutManager = new LinearLayoutManager(getActivity());
    LinearLayoutManager linearLayoutManagerPath =
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    mRecyclerViewPath.setLayoutManager(linearLayoutManagerPath);
    mRecyclerView.setLayoutManager(mLinearLayoutManager);
    mPathAdapter = new ReposCodePathAdapter(mPath);
    mRecyclerViewPath.setAdapter(mPathAdapter);
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

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
        final Trees.Tree tree = (Trees.Tree) baseQuickAdapter.getData().get(i);
        if (tree.type.equals(TREE)) {
          networkQueue().enqueue(mGitDateService.getTree(mOwner, mRepo, tree.sha));
          mPathAdapter.add(mPathAdapter.getItemCount(), new ReposCodePath(tree.path, tree.sha));
          mPathAdapter.notifyDataSetChanged();
        }

        if (tree.type.equals(BLOB)) {
          new ReadMarkDownTask(null, ReadMarkDownTask.TYPE_START_ACTIVITY_WITH_MARKDOWN,
              getActivity(), tree.path).execute(mOwner, mRepo, tree.sha);
        }
      }
    };

    mPathItemClickListener = new OnItemClickListener() {
      @Override public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int itemCount = baseQuickAdapter.getItemCount();

        if (!(i + 1 == itemCount)) {
          for (int position = i + 1; position < itemCount; position++) {
            baseQuickAdapter.remove(position);
          }

          baseQuickAdapter.notifyItemRangeRemoved(i + 1, itemCount);
        }
        TextView textView = (TextView) view;
        String sha = textView.getContentDescription().toString();
        networkQueue().enqueue(mGitDateService.getTree(mOwner, mRepo, sha));
      }
    };

    mRecyclerViewPath.addOnItemTouchListener(mPathItemClickListener);
    mRecyclerView.addOnItemTouchListener(mItemClickListener);
  }

  private void removeItemTouchListener() {
    mRecyclerView.removeOnItemTouchListener(mItemClickListener);
    mRecyclerViewPath.removeOnItemTouchListener(mPathItemClickListener);
  }

  @Override public void onCardTouchListener(Parcelable parcelableDate) {

    Repository items = (Repository) parcelableDate;

    mOwner = items.owner.login;
    mRepo = items.name;
    mDefaultBranch = items.default_branch;
    Thread thread = new Thread(new Runnable() {
      @Override public void run() {
        try {
          mRepositoryService = GitHubService.createRepositoryService();
          List<Branch> branches = mRepositoryService.getBranchList(mOwner, mRepo).execute().body();
          for (Branch branch : branches) {
            if (mDefaultBranch.equals(branch.name)) {
              mSha = branch.commit.sha;
              networkQueue().enqueue(mGitDateService.getTree(mOwner, mRepo, mSha));
              mRecyclerViewPath.post(new Runnable() {
                @Override public void run() {
                  mPathAdapter.add(0, new ReposCodePath("root", mSha));
                  Log.d("TAG", "run: " + mSha);
                }
              });
              break;
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(KEY_OWNER, mOwner);
    outState.putString(KEY_REPO, mRepo);
  }

  @Override public void onDetach() {
    super.onDetach();
    removeItemTouchListener();
  }

  @Override public void onRefresh() {
    networkQueue().enqueue(mGitDateService.getTree(mOwner, mRepo, mSha));
  }

  @Override public void startRequest() {
    super.startRequest();
    mSwipeRefreshLayout.post(new Runnable() {
      @Override public void run() {
        mSwipeRefreshLayout.setRefreshing(true);
      }
    });
  }

  @Override public void endRequest() {
    super.endRequest();
    mSwipeRefreshLayout.post(new Runnable() {
      @Override public void run() {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }

  @Override public void respondWithError(Throwable t) {

  }
}
