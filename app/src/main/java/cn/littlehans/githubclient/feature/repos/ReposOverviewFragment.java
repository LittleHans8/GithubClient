package cn.littlehans.githubclient.feature.repos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import cn.littlehans.githubclient.model.entity.Blob;
import cn.littlehans.githubclient.model.entity.Branch;
import cn.littlehans.githubclient.model.entity.Repository;
import cn.littlehans.githubclient.model.entity.Trees;
import cn.littlehans.githubclient.network.StarRepoAsyncTask;
import cn.littlehans.githubclient.ui.fragment.NetworkFragment;
import cn.littlehans.githubclient.utilities.DateFormatUtil;
import cn.littlehans.githubclient.utilities.FormatUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mukesh.MarkdownView;
import java.io.IOException;
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

  public static Fragment create() {
    return new ReposOverviewFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    ReposActivity reposActivity = (ReposActivity) context;
    reposActivity.addOnDatePassListener(this);
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repo_overview;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mBehavior = BottomSheetBehavior.from(mBottomSheet);
    mGitDateService = GitHubService.createGitDateService();
    mRepositoryService = GitHubService.createRepositoryService();
    setupData();
    executeStarRepoTask(StarRepoAsyncTask.TYPE_CHECK_STAR);
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

  private void executeStarRepoTask(int type) {
    new StarRepoAsyncTask(new TextView[] { mTextStart, mTextStartCount }, type).execute(mOwner,
        mRepo);
  }

  @OnClick(R.id.text_star) void startRepo() {
    if (mTextStart.getText().toString().equals("Star")) {
      executeStarRepoTask(StarRepoAsyncTask.TYPE_STAR);
    } else {
      executeStarRepoTask(StarRepoAsyncTask.TYPE_UNSTAR);
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
    Thread thread = new Thread(new Runnable() {
      @Override public void run() {
        try {
          Blob blob = mGitDateService.getBlob(mOwner, mRepo, tree.sha).execute().body();
          final String txtMarkdown = new String(Base64.decode(blob.content, Base64.DEFAULT));
          getActivity().runOnUiThread(new Runnable() {
            @Override public void run() {
              mMarkdownView.setMarkDownText(txtMarkdown);
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
  }

  @Override public void respondWithError(Throwable t) {
    Log.d(TAG, "respondWithError: " + t.getMessage());
  }
}
