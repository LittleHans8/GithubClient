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
import butterknife.Bind;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mukesh.MarkdownView;
import java.io.IOException;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GitHubService;
import littlehans.cn.githubclient.api.service.GitDateService;
import littlehans.cn.githubclient.api.service.RepositoryService;
import littlehans.cn.githubclient.model.entity.Blob;
import littlehans.cn.githubclient.model.entity.Branch;
import littlehans.cn.githubclient.model.entity.Repository;
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
  @Bind(R.id.markdown_view) MarkdownView mMarkdownView;
  @Bind(R.id.bottom_sheet) LinearLayout mBottomSheet;
  @Bind(R.id.avatar) SimpleDraweeView mAvatar;
  @Bind(R.id.text_login) TextView mTxtLogin;
  @Bind(R.id.text_repo_name) TextView mTxtRepoName;
  @Bind(R.id.text_watch) TextView mTxtWatch;
  @Bind(R.id.text_start) TextView mTxtStart;
  @Bind(R.id.text_watch_count) TextView mTxtWatchCount;
  @Bind(R.id.text_start_count) TextView mTxtStartCount;
  @Bind(R.id.text_fork_counts) TextView mTxtForkCount;

  @Bind(R.id.layout_fork) LinearLayout mLayoutFork;
  @Bind(R.id.text_description) TextView mTxtDescription;
  @Bind(R.id.text_create_at) TextView mTxtCreateAt;
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
    return R.layout.fragment_reop_overview;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mBehavior = BottomSheetBehavior.from(mBottomSheet);
    mGitDateService = GitHubService.createGitDateService();
    mRepositoryService = GitHubService.createRepositoryService();
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

  @Override public void onCardTouchListener(Parcelable date) {
    Log.d(TAG, "onCardTouchListener: " + "11");
    mItems = (Repository) date;
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
