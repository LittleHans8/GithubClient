package cn.littlehans.githubclient.feature.repos;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.littlehans.githubclient.Nav;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.api.service.GitDateService;
import cn.littlehans.githubclient.api.service.RepositoryService;
import cn.littlehans.githubclient.feature.user.UserActivity;
import cn.littlehans.githubclient.model.entity.Branch;
import cn.littlehans.githubclient.model.entity.Repository;
import cn.littlehans.githubclient.model.entity.Trees;
import cn.littlehans.githubclient.network.task.ReadMarkDownTask;
import cn.littlehans.githubclient.network.task.StarRepoAsyncTaskBar;
import cn.littlehans.githubclient.ui.fragment.NetworkFragment;
import cn.littlehans.githubclient.utilities.DateFormatUtil;
import cn.littlehans.githubclient.utilities.FormatUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mukesh.MarkdownView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by LittleHans on 2016/10/30.
 */

public class ReposOverviewFragment extends NetworkFragment<Trees> implements OnDatePassListener {
  public static final String README_MD = "README.md";
  @Bind(R.id.markdown_view) MarkdownView mMarkdownView;
  @Bind(R.id.bottom_sheet) LinearLayout mBottomSheet;
  @Bind(R.id.avatar) SimpleDraweeView mAvatar;
  @Bind(R.id.text_login) TextView mTextLogin;
  @Bind(R.id.text_repo_name) TextView mTextRepoName;
  @Bind(R.id.text_watch) TextView mTextWatch;
  @Bind(R.id.text_star) TextView mTextStart;

  @Bind(R.id.text_watch_count) TextView mTextWatchCount;
  @Bind(R.id.text_start_count) TextView mTextStartCount;
  @Bind(R.id.text_fork_counts) TextView mTextForkCount;

  @Bind(R.id.layout_fork) LinearLayout mLayoutFork;
  @Bind(R.id.text_description) TextView mTextDescription;
  @Bind(R.id.text_create_at) TextView mTextCreateAt;
  @Bind(R.id.coordinator) CoordinatorLayout mCoordinator;
  private BottomSheetBehavior<LinearLayout> mBehavior;
  private Repository mItems;
  private GitDateService mGitDateService;
  private String mOwner;
  private String mRepo;
  private String mDefaultBranch;
  private String mSha;
  private RepositoryService mRepositoryService;
  private List<AsyncTask> mAsyncTasks;

  public static Fragment create() {
    return new ReposOverviewFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    ReposActivity reposActivity = (ReposActivity) context;
    reposActivity.addOnDatePassListener(this);
  }

  /**
   * Usually, you should implement at least the following lifecycle methods:
   * onCreate()/onCreateView()/onPause()
   * 在实际开发中，onCreateView() 通常会被 getFragmentLayout() 方法所代替，来返回一个视图
   *
   * The system calls this when creating the fragment.
   * Within your implementation,
   * you should initialize essential components of the fragment
   * that you want to retain when the fragment is paused or stopped, then resumed.
   */
  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mGitDateService = GitHubService.createGitDateService();
    mRepositoryService = GitHubService.createRepositoryService();
    mAsyncTasks = new ArrayList<>();
  }

  // The fragment is visible in the running activity.
  @Override public void onResume() {
    super.onResume();
  }

  /**
   * The fragment is not visible. Either the host activity has been stopped or the fragment
   * has been removed from the activity but added to the back stack.
   * A stopped fragment is still alive (all state and member information is retained by the
   * system).
   * However, it is no longer visible to the user and will be killed if the activity is killed.
   */

  @Override public void onStop() {
    super.onStop();
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repo_overview;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mBehavior = BottomSheetBehavior.from(mBottomSheet);
    setupData();
    mAsyncTasks.add(executeStarRepoTask(StarRepoAsyncTaskBar.TYPE_CHECK_STAR));
    Thread threadX = new Thread(new Runnable() {
      @Override public void run() {
        try {
          List<Branch> branches = mRepositoryService.getBranchList(mOwner, mRepo).execute().body();
          for (Branch branch : branches) {
            if (branch.name.equals(mDefaultBranch)) {
              mSha = branch.commit.sha;
              networkQueue().enqueue(
                  mGitDateService.getTree(mOwner, mRepo, mSha)); // get the default tree
              break;
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    threadX.start();
  }

  /**
   * Another activity is in the foreground and has focus,
   * but the activity in which this fragment lives is still visible
   * (the foreground activity is partially transparent or doesn't cover the entire screen).
   */

  @Override public void onPause() {
    super.onPause();
  }

  private AsyncTask<String, Void, Integer> executeStarRepoTask(int type) {
    return new StarRepoAsyncTaskBar(new TextView[] { mTextStart, mTextStartCount }, type).execute(
        mOwner, mRepo);
  }

  @OnClick(R.id.text_star) void starRepo() {
    if (mTextStart.getText().toString().equals("Star")) {
      mAsyncTasks.add(executeStarRepoTask(StarRepoAsyncTaskBar.TYPE_STAR));
    } else {
      mAsyncTasks.add(executeStarRepoTask(StarRepoAsyncTaskBar.TYPE_UNSTAR));
    }
  }

  @Override public void onCardTouchListener(Parcelable date) {
    mItems = (Repository) date;
    mOwner = mItems.owner.login;
    mRepo = mItems.name;
    mDefaultBranch = mItems.default_branch;
  }

  @OnClick({ R.id.avatar, R.id.text_login }) public void click() {
    Intent intent = new Intent(getActivity(), UserActivity.class);
    intent.putExtra(Nav.USER, mItems.owner);
    startActivity(intent);
  }

  public void setupData() {
    mAvatar.setImageURI(mItems.owner.avatar_url);
    mTextLogin.setText(mItems.owner.login);
    mTextRepoName.setText(mItems.name);

    //mTextWatchCount.setText(FormatUtils.decimalFormat(mItems.watchers)); // where is the watch api?
    mTextStartCount.setText(FormatUtils.decimalFormat(mItems.stargazers_count));
    mTextForkCount.setText(FormatUtils.decimalFormat(mItems.forks_count));

    mTextDescription.setText(mItems.description);
    DateFormatUtil dateFormat = new DateFormatUtil(getString(R.string.create_at));
    String createAt = dateFormat.formatTime(mItems.created_at);
    mTextCreateAt.setText(createAt);
  }

  @Override public void respondSuccess(Trees data) {
    for (Trees.Tree tree : data.tree) {
      if (tree.path.equals(README_MD)) {
        setMarkDownText(tree);
        break;
      }
    }
  }

  private void setMarkDownText(final Trees.Tree tree) {
    new ReadMarkDownTask(mMarkdownView).execute(mOwner, mRepo, tree.sha);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    for (AsyncTask asyncTask : mAsyncTasks) {
      if (asyncTask != null) {
        asyncTask.cancel(true);
      }
    }
  }

  @Override public void respondWithError(Throwable t) {
    Log.d(TAG, "respondWithError: " + t.getMessage());
  }
}
