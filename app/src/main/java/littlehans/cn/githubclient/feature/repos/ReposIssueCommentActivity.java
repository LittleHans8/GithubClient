package littlehans.cn.githubclient.feature.repos;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.service.IssuesService;
import littlehans.cn.githubclient.model.entity.Comment;
import littlehans.cn.githubclient.ui.activity.NetworkActivity;
import okhttp3.Headers;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by LittleHans on 2016/10/27.
 */

public class ReposIssueCommentActivity extends NetworkActivity<List<Comment>> {

  public static final String ISSUE_TITLE = "title";
  public static final String NUMBER = "number";
  public static final String STATE = "state";
  public static final String LOGIN = "login";
  public static final String CREATE_AT = "create_at";
  public static final String OWNER = "owner";
  public static final String Repo = "repo";
  @BindView(R.id.text_issue_title) TextView mTxtIssueTitle;
  @BindView(R.id.text_state) TextView mTxtState;
  @BindView(R.id.text_login) TextView mTxtLogin;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mLayoutSwipeRefresh;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  private Intent mIntent;
  private String mOwner;
  private String mRepo;
  private int mCurrent;
  private String mNumber;
  private GradientDrawable mGradientDrawable;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_issue_detail);
    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
    mIntent = getIntent();
    initDate();
    setSupportActionBar(mToolbar);

    //networkQueue().enqueue(GithubService.createIssuesService().getComments(mOwner, mRepo,mNumber,mCurrent));
  }

  private void initDate() {
    mGradientDrawable = (GradientDrawable) mTxtState.getBackground();
    mNumber = mIntent.getStringExtra(NUMBER);

    mToolbar.setTitle("#" + mNumber);
    mTxtIssueTitle.setText(mIntent.getStringExtra(ISSUE_TITLE));
    String state = mIntent.getStringExtra(STATE);
    setStateBackground(state);
    mTxtState.setText(state);
    mTxtLogin.setText(mIntent.getStringExtra(LOGIN));
    mTxtLogin.setText(mIntent.getStringExtra(CREATE_AT));
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
    super.respondSuccess(data);
  }
}
