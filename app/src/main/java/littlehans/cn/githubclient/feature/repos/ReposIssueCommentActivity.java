package littlehans.cn.githubclient.feature.repos;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
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
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.IssuesService;
import littlehans.cn.githubclient.model.entity.Comment;
import littlehans.cn.githubclient.ui.activity.NetworkActivity;
import littlehans.cn.githubclient.utilities.DateFormatUtil;
import okhttp3.Headers;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by LittleHans on 2016/10/27.
 */

public class ReposIssueCommentActivity extends NetworkActivity<List<Comment>>
    implements SwipeRefreshLayout.OnRefreshListener {
  public static final String COMMENT_COUNT = "comment_count";
  public static final String ISSUE_TITLE = "title";
  public static final String NUMBER = "number";
  public static final String STATE = "state";
  public static final String LOGIN = "login";
  public static final String CREATE_AT = "create_at";
  public static final String OWNER = "owner";
  public static final String Repo = "repo";
  private static final String TAG = "ReposIssueComment";
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.text_create_at) TextView mTxtCreateAt;
  @BindView(R.id.text_issue_title) TextView mTxtIssueTitle;
  @BindView(R.id.text_state) TextView mTxtState;
  @BindView(R.id.text_login) TextView mTxtLogin;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.collapsing_app_bar) CollapsingToolbarLayout mCollapsingToolbarLayout;
  private DateFormatUtil mDateFormat;
  private Intent mIntent;
  private String mOwner;
  private String mRepo;
  private int mCurrentPage = 1;
  private String mNumber;
  private GradientDrawable mGradientDrawable;
  private IssuesService mIssuesService;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_issue_detail);
    setSupportActionBar(mToolbar);
    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
    mIntent = getIntent();
    initDate();
    mIssuesService = GithubService.createIssuesService();
    networkQueue().enqueue(mIssuesService.getComments(mOwner, mRepo, mNumber, mCurrentPage));
  }

  private void initDate() {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    String commentCount = mIntent.getStringExtra(COMMENT_COUNT);
    mDateFormat = new DateFormatUtil("opened this issue");
    String createAT = mDateFormat.formatTime(mIntent.getStringExtra(CREATE_AT));
    String format = "· %s comments";
    createAT += String.format(format, commentCount);
    mTxtCreateAt.setText(createAT);
    mTxtLogin.setText(mIntent.getStringExtra(LOGIN));
    mGradientDrawable = (GradientDrawable) mTxtState.getBackground();
    mNumber = mIntent.getStringExtra(NUMBER);
    mToolbar.setTitle("#" + mNumber);
    String issueTitle = mIntent.getStringExtra(ISSUE_TITLE);
    mTxtIssueTitle.setText(issueTitle);
    mCollapsingToolbarLayout.setTitle(issueTitle);
    String state = mIntent.getStringExtra(STATE);
    setStateBackground(state);
    mTxtState.setText(state);
    mOwner = mIntent.getStringExtra(OWNER);
    mRepo = mIntent.getStringExtra(Repo);
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
