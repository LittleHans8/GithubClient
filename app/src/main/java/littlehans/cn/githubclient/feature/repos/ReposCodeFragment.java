package littlehans.cn.githubclient.feature.repos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.GitDateService;
import littlehans.cn.githubclient.api.service.RepositoryService;
import littlehans.cn.githubclient.model.entity.Blob;
import littlehans.cn.githubclient.model.entity.Branch;
import littlehans.cn.githubclient.model.entity.SearchRepos;
import littlehans.cn.githubclient.model.entity.Trees;
import littlehans.cn.githubclient.ui.activity.FileDetailActivity;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;
import littlehans.cn.githubclient.utilities.DividerItemDecoration;

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


  Comparator<Trees.Tree> mTreeComparator;
  ReposCodePathAdapter mPathAdapter;
  @BindView(R.id.recycler_view_path) RecyclerView mRecyclerViewPath;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
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
    mPath = new ArrayList<>();
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repos_code;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mGitDateService = GithubService.createGitDateService();
    networkQueue().enqueue(mGitDateService.getTree(mOwner, mRepo, mSha));
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
        final Trees.Tree tree = (Trees.Tree) baseQuickAdapter.getData().get(i);
        if (tree.type.equals(TREE)) {
          networkQueue().enqueue(mGitDateService.getTree(mOwner, mRepo, tree.sha));
          mPathAdapter.add(mPathAdapter.getItemCount(), new ReposCodePath(tree.path, tree.sha));
          mPathAdapter.notifyDataSetChanged();
        }

        if (tree.type.equals(BLOB)) {
          new Thread(new Runnable() {
            @Override public void run() {
              try {
                Blob blob = mGitDateService.getBlob(mOwner, mRepo, tree.sha).execute().body();
                String content = new String(Base64.decode(blob.content, Base64.DEFAULT));
                Intent intent = new Intent(getActivity(), FileDetailActivity.class);
                intent.putExtra(CONTENT, content);
                if (tree.path.endsWith(MD) || tree.path.endsWith(MARKDOWN)) {
                  intent.putExtra(IS_MARK_DOWN_FILE, true);
                }

                startActivity(intent);
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }).start();
        }
      }
    };

    mPathItemClickListener = new OnItemClickListener() {
      @Override public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int itemCount = baseQuickAdapter.getItemCount();
        for (int position = i + 1; position < itemCount; position++) {
          baseQuickAdapter.remove(position);
        }

        baseQuickAdapter.notifyItemRangeRemoved(i + 1, itemCount);
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

    SearchRepos.Items items = (SearchRepos.Items) parcelableDate;

    mOwner = items.owner.login;
    mRepo = items.name;
    mDefaultBranch = items.default_branch;

    Thread thread = new Thread(new Runnable() {
      @Override public void run() {
        try {
          mRepositoryService = GithubService.createRepositoryService();
          List<Branch> branches = mRepositoryService.getBranchList(mOwner, mRepo).execute().body();
          for (Branch branch : branches) {
            if (branch.name.equals(mDefaultBranch)) {
              mSha = branch.commit.sha;
              networkQueue().enqueue(mGitDateService.getTree(mOwner, mRepo, mSha));
              mRecyclerView.post(new Runnable() {
                @Override public void run() {
                  mPathAdapter.add(0, new ReposCodePath("root", mSha));
                }
              });
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
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
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        mSwipeRefreshLayout.setRefreshing(true);
      }
    });
  }

  @Override public void endRequest() {
    super.endRequest();
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }

  @Override public void respondWithError(Throwable t) {

  }

}
