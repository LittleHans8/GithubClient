package littlehans.cn.githubclient.feature.repos;

import android.content.Context;
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
import butterknife.BindView;
import butterknife.OnClick;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mukesh.MarkdownView;
import java.io.IOException;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.GitDateService;
import littlehans.cn.githubclient.api.service.RepositoryService;
import littlehans.cn.githubclient.model.entity.Blob;
import littlehans.cn.githubclient.model.entity.Branch;
import littlehans.cn.githubclient.model.entity.SearchRepos;
import littlehans.cn.githubclient.model.entity.Trees;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;
import littlehans.cn.githubclient.utilities.DateFormatUtil;
import littlehans.cn.githubclient.utilities.FormatUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by LittleHans on 2016/10/30.
 */

public class ReposOverviewFragment extends NetworkFragment<Trees> implements OnDatePassListener {
  public static final String README_MD = "README.md";
  @BindView(R.id.markdown_view) MarkdownView mMarkdownView;
  @BindView(R.id.bottom_sheet) LinearLayout mBottomSheet;
  @BindView(R.id.avatar) SimpleDraweeView mAvatar;
  @BindView(R.id.text_login) TextView mTxtLogin;
  @BindView(R.id.text_repo_name) TextView mTxtRepoName;
  @BindView(R.id.text_watch) TextView mTxtWatch;
  @BindView(R.id.text_start) TextView mTxtStart;
  @BindView(R.id.text_fork) TextView mTxtFork;

  @BindView(R.id.text_watch_count) TextView mTxtWatchCount;
  @BindView(R.id.text_start_count) TextView mTxtStartCount;
  @BindView(R.id.text_fork_count) TextView mTxtForkCount;

  @BindView(R.id.layout_fork) LinearLayout mLayoutFork;
  @BindView(R.id.text_description) TextView mTxtDescription;
  @BindView(R.id.text_create_at) TextView mTxtCreateAt;
  @BindView(R.id.coordinator) CoordinatorLayout mCoordinator;
  private BottomSheetBehavior<LinearLayout> mBehavior;
  private SearchRepos.Items mItems;
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
    return R.layout.fragment_reop_overview;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mBehavior = BottomSheetBehavior.from(mBottomSheet);
    mGitDateService = GithubService.createGitDateService();
    mRepositoryService = GithubService.createRepositoryService();
    setupData();
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

  @OnClick({
      R.id.avatar, R.id.text_login, R.id.text_repo_name, R.id.text_watch, R.id.text_watch_count,
      R.id.text_start, R.id.text_start_count, R.id.text_fork, R.id.text_fork_count,
      R.id.text_description, R.id.text_create_at
  }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.avatar:
        break;
      case R.id.text_login:
        break;
      case R.id.text_repo_name:
        break;
      case R.id.text_watch:
        break;
      case R.id.text_watch_count:
        break;
      case R.id.text_start:
        break;
      case R.id.text_start_count:
        break;
      case R.id.text_fork:
        break;
      case R.id.text_fork_count:
        break;
      case R.id.text_description:
        break;
      case R.id.text_create_at:
        break;
    }
  }

  @Override public void onCardTouchListener(Parcelable date) {
    Log.d(TAG, "onCardTouchListener: " + "11");
    mItems = (SearchRepos.Items) date;
    mOwner = mItems.owner.login;
    mRepo = mItems.name;
    mDefaultBranch = mItems.default_branch;
  }

  public void setupData() {
    mAvatar.setImageURI(mItems.owner.avatar_url);
    mTxtLogin.setText(mItems.owner.login);
    mTxtRepoName.setText(mItems.name);

    //mTxtWatchCount.setText(FormatUtils.decimalFormat(mItems.watchers)); // where is the watch api?
    mTxtStartCount.setText(FormatUtils.decimalFormat(mItems.stargazers_count));
    mTxtForkCount.setText(FormatUtils.decimalFormat(mItems.forks_count));

    mTxtDescription.setText(mItems.description);
    DateFormatUtil dateFormat = new DateFormatUtil(getString(R.string.create_at));
    String createAt = dateFormat.formatTime(mItems.created_at);
    mTxtCreateAt.setText(createAt);
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
