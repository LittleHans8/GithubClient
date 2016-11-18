package littlehans.cn.githubclient.feature.repos;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import butterknife.BindView;
import java.util.List;
import littlehans.cn.githubclient.Nav;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.IssuesService;
import littlehans.cn.githubclient.model.entity.Comment;
import littlehans.cn.githubclient.model.entity.Issue;
import littlehans.cn.githubclient.ui.activity.NetworkActivity;
import littlehans.cn.githubclient.utilities.DateFormatUtil;
import okhttp3.Headers;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by LittleHans on 2016/10/27.
 */

public class ReposIssueCommentActivity extends NetworkActivity<List<Comment>>
    implements SwipeRefreshLayout.OnRefreshListener {


  private static final String TAG = "ReposIssueComment";
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.text_create_at) TextView mTxtCreateAt;
  @BindView(R.id.text_issue_title) TextView mTxtIssueTitle;
  @BindView(R.id.text_state) TextView mTxtState;
  @BindView(R.id.text_login) TextView mTxtLogin;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.collapsing_app_bar) CollapsingToolbarLayout mCollapsingToolbarLayout;
  Comment mOwnIssueComment;
  private Intent mIntent;
  private String mOwner;
  private String mRepo;
  private String mNumber;
  private DateFormatUtil mDateFormat;
  private int mCurrentPage = 1;
  private GradientDrawable mGradientDrawable;
  private IssuesService mIssuesService;
  private Issue mIssue;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_issue_detail);
    initUI();
    initData();
    setupDate();
    networkQueue().enqueue(mIssuesService.getComments(mOwner, mRepo, mNumber, mCurrentPage));
  }

  private void setupDate() {

    String createAT = formatCreateTime(mIssue);
    mTxtCreateAt.setText(createAT);
    mTxtLogin.setText(mIssue.user.login);
    mGradientDrawable = (GradientDrawable) mTxtState.getBackground();
    mNumber = String.valueOf(mIssue.number);
    mToolbar.setTitle("#" + mNumber);
    mTxtIssueTitle.setText(mIssue.title);
    mCollapsingToolbarLayout.setTitle(mIssue.title);
    setStateBackground(mIssue.state);
    mTxtState.setText(mIssue.state);
  }

  private void initData() {
    mIntent = getIntent();
    mIssue = mIntent.getExtras().getParcelable(Nav.ISSUE);
    mOwner = mIntent.getExtras().getString(Nav.OWNER);
    mRepo = mIntent.getStringExtra(Nav.REPO);
    initOwnIssueComment();
    mIssuesService = GithubService.createIssuesService();
  }

  private void initOwnIssueComment() {
    mOwnIssueComment = new Comment();
    Comment.User user = new Comment.User();
    user.login = mOwner;
    user.avatar_url = mIssue.user.avatar_url;
    mOwnIssueComment.user = user;
    mOwnIssueComment.body = mIssue.body;
    mOwnIssueComment.created_at = mIssue.created_at;
  }

  /**
   * construct a string like "opened this ISSUE 9 Jun 2013 · 8 comments"
   *
   * @return FormatString
   */
  @NonNull private String formatCreateTime(Issue issue) {
    mDateFormat = new DateFormatUtil(getString(R.string.opened_this_issue));
    String createAT = mDateFormat.formatTime(issue.created_at);
    String format = getString(R.string.format_comment_count);
    createAT += String.format(format, issue.comments);
    return createAT;
  }

  private void initUI() {
    setSupportActionBar(mToolbar);
    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
    mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);
    mSwipeRefreshLayout.setOnRefreshListener(this);
  }

  private void setStateBackground(String state) {
    if (state.equals(IssuesService.OPEN)) {
      mTxtState.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_issue_open_white, 0, 0, 0);
      mGradientDrawable.setColor(ContextCompat.getColor(this, R.color.green));
    } else {
      mTxtState.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_issue_closed_white, 0, 0, 0);
      mGradientDrawable.setColor(ContextCompat.getColor(this, R.color.red));
    }
  }

  @Override public void respondHeader(Headers headers) {

  }

  @Override public void respondSuccess(List<Comment> data) {
    IssueCommentAdapter issueCommentAdapter = new IssueCommentAdapter(data);

    issueCommentAdapter.add(0, mOwnIssueComment);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setAdapter(issueCommentAdapter);
  }

  @Override public void respondWithError(Throwable t) {
    super.respondWithError(t);
    Log.d(TAG, "respondWithError: " + t.getMessage());
  }

  @Override public void startRequest() {
    super.startRequest();

    runOnUiThread(new Runnable() {
      @Override public void run() {
        mSwipeRefreshLayout.setRefreshing(true);
      }
    });
  }

  @Override public void endRequest() {
    super.endRequest();
    runOnUiThread(new Runnable() {
      @Override public void run() {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }

  @Override public void onRefresh() {
    networkQueue().enqueue(mIssuesService.getComments(mOwner, mRepo, mNumber, mCurrentPage));
  }
}
