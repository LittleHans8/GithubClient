package littlehans.cn.githubclient.feature.repos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
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
import littlehans.cn.githubclient.model.entity.Blob;
import littlehans.cn.githubclient.model.entity.Branch;
import littlehans.cn.githubclient.model.entity.Trees;
import littlehans.cn.githubclient.ui.activity.FileDetailActivity;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;
import littlehans.cn.githubclient.utilities.DividerItemDecoration;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposCodeFragment extends NetworkFragment<Trees> implements OnCardTouchListener {

  @BindView(R.id.recycler_view_path) RecyclerView mRecyclerViewPath;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  Comparator<Trees.Tree> mTreeComparator;
  ReposCodePathAdapter mPathAdapter;
  private LinearLayoutManager mLinearLayoutManager;
  private List<ReposCodePath> mPath;
  private OnItemClickListener mItemClickListener;
  private OnItemClickListener mPathItemClickListener;
  private String mOwner;
  private String mRepo;
  private String mDefaultBranch;
  private String mSha;

  public static Fragment create() {
    return new ReposCodeFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    setHasOptionsMenu(true);
    ReposActivity reposActivity = (ReposActivity) context;
    reposActivity.setOnCardTouchListener(this);
    mPath = new ArrayList<>();
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repos_code;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //networkQueue().enqueue(GithubService.createGitDateService()
    //    .getTree("twbs", "bootstrap", "fdb5af3bd919c90720c47953f191c652a9c8fd93"));
    networkQueue().enqueue(GithubService.createGitDateService().getTree(mOwner, mRepo, mSha));
    initUI();
  }

  private void initUI() {
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
        if (tree.type.equals("tree")) {
          mPathAdapter.add(mPathAdapter.getItemCount(), new ReposCodePath(tree.path, tree.sha));
          mPathAdapter.notifyDataSetChanged();
          networkQueue().enqueue(
              GithubService.createGitDateService().getTree(mOwner, mRepo, tree.sha));
        }

        if (tree.type.equals("blob")) {

          if (tree.path.endsWith("md")) {
            new Thread(new Runnable() {
              @Override public void run() {
                try {
                  Blob blob = GithubService.createGitDateService()
                      .getBlob(mOwner, mRepo, tree.sha)
                      .execute()
                      .body();
                  String content = new String(Base64.decode(blob.content, Base64.DEFAULT));
                  Log.d(TAG, "run: " + content);
                  Intent intent = new Intent(getActivity(), FileDetailActivity.class);
                  intent.putExtra("content", content);
                  startActivity(intent);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            }).start();
          }
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
        networkQueue().enqueue(GithubService.createGitDateService().getTree(mOwner, mRepo, sha));
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

  @Override public void onCardTouchListener(Intent intent) {
    mOwner = intent.getStringExtra("owner");
    mRepo = intent.getStringExtra("repo");
    mDefaultBranch = intent.getStringExtra("defaultBranch");
    Thread thread = new Thread(new Runnable() {
      @Override public void run() {
        try {
          List<Branch> branches =
              GithubService.createRepositoryService().getBranchList(mOwner, mRepo).execute().body();
          for (Branch branch : branches) {
            if (branch.name.equals(mDefaultBranch)) {
              mSha = branch.commit.sha;
              networkQueue().enqueue(
                  GithubService.createGitDateService().getTree(mOwner, mRepo, mSha));
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
}
