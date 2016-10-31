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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.IssuesService;
import littlehans.cn.githubclient.feature.search.PageLink;
import littlehans.cn.githubclient.model.entity.Issue;
import littlehans.cn.githubclient.model.entity.SearchRepos;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;
import okhttp3.Headers;

import static android.content.ContentValues.TAG;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposIssueFragment extends NetworkFragment<List<Issue>>
    implements OnCardTouchListener, BaseQuickAdapter.RequestLoadMoreListener,
    SwipeRefreshLayout.OnRefreshListener {
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.spinner) Spinner mSpinner;
  private IssuesService mIssuesService;
  private ReposIssueAdapter mIssueAdapter;
  private SimpleOnItemSelectedListener mSpinnerOnItemSelectedListener;
  private OnItemClickListener mOnItemClickListener;
  private String mOwner;
  private String mRepo;
  private int mLastPage = 0;
  private int mCurrentPage = 1;
  private LinearLayoutManager mLinearLayoutManager;
  private String mCurrentState = "open";

  public static Fragment create() {
    return new ReposIssueFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    ReposActivity reposActivity = (ReposActivity) context;
    reposActivity.setOnCardTouchListenerB(this);
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repos_issue;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mIssuesService = GithubService.createIssuesService();
    loadOpenIssues();
    initUI();
    addListener();
    mSwipeRefreshLayout.setOnRefreshListener(this);
  }

  private void initUI() {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);

    List<String> state = new ArrayList<>();
    state.add(IssuesService.OPEN);
    state.add(IssuesService.CLOSED);
    ArrayAdapter<String> spinnerAdapter =
        new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, state);
    mSpinner.setAdapter(spinnerAdapter);
    mLinearLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(mLinearLayoutManager);
  }

  private void loadOpenIssues() {
    mCurrentState = IssuesService.OPEN;
    mCurrentPage = 1;
    networkQueue().enqueue(
        mIssuesService.getIssuesForReposList(mOwner, mRepo, mCurrentState, mCurrentPage));
  }

  private void loadClosedIssues() {
    mCurrentState = IssuesService.CLOSED;
    mCurrentPage = 1;
    networkQueue().enqueue(
        mIssuesService.getIssuesForReposList(mOwner, mRepo, mCurrentState, mCurrentPage));
  }

  private void addListener() {
    mSpinnerOnItemSelectedListener = new SimpleOnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
          loadOpenIssues();
        } else {
          loadClosedIssues();
        }
      }
    };

    mSpinner.setOnItemSelectedListener(mSpinnerOnItemSelectedListener);
  }

  private void loadMoreDate() {
    networkQueue().enqueue(
        mIssuesService.getIssuesForReposList(mOwner, mRepo, mCurrentState, mCurrentPage));
  }

  private void addOnItemClickListener() {
    mOnItemClickListener = new OnItemClickListener() {
      @Override public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Issue issue = (Issue) baseQuickAdapter.getItem(i);
        Intent intent = new Intent(getActivity(), ReposIssueCommentActivity.class);
        intent.putExtra(ReposIssueCommentActivity.ISSUE_TITLE, issue.title);
        intent.putExtra(ReposIssueCommentActivity.NUMBER, String.valueOf(issue.number));
        intent.putExtra(ReposIssueCommentActivity.LOGIN, issue.user.login);
        intent.putExtra(ReposIssueCommentActivity.CREATE_AT, issue.created_at);
        intent.putExtra(ReposIssueCommentActivity.STATE, issue.state);
        intent.putExtra(ReposIssueCommentActivity.OWNER, mOwner);
        intent.putExtra(ReposIssueCommentActivity.Repo, mRepo);
        intent.putExtra(ReposIssueCommentActivity.COMMENT_COUNT, String.valueOf(issue.comments));
        startActivity(intent);
      }
    };
    mRecyclerView.addOnItemTouchListener(mOnItemClickListener);
  }

  @Override public void respondSuccess(List<Issue> data) {
    updateRecyclerView(data);
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override public void respondHeader(Headers headers) {
    super.respondHeader(headers);
    List<String> links = headers.values("Link");

    if (!links.isEmpty()) {
      String link = links.get(0);
      PageLink pageLink = new PageLink(link);
      mLastPage = pageLink.getLastPage();
      Log.d(TAG, "respondHeader: mLastPage:" + mLastPage);
    }
  }

  private void updateRecyclerView(final List<Issue> issues) {
    removeOnItemClickListener();
    addOnItemClickListener();
    mRecyclerView.post(new Runnable() {
      @Override public void run() {
        if (mCurrentPage == 1) {

          mIssueAdapter = new ReposIssueAdapter(issues);
          mIssueAdapter.openLoadMore(30);
          mRecyclerView.setAdapter(mIssueAdapter);
          mIssueAdapter.setOnLoadMoreListener(ReposIssueFragment.this);
          mCurrentPage++;
          return;
        }
        if (mCurrentPage > mLastPage) {
          mIssueAdapter.loadComplete();
        } else {
          mIssueAdapter.addData(issues);
          mCurrentPage++;
        }
      }
    });
  }

  private void removeOnItemClickListener() {
    if (mOnItemClickListener != null) {
      mRecyclerView.removeOnItemTouchListener(mOnItemClickListener);
      mOnItemClickListener = null;
    }
  }

  @Override public void respondWithError(Throwable t) {
    Log.d(TAG, "respondWithError: " + t.getMessage());
  }

  @Override public void onCardTouchListener(Parcelable parcelableDate) {
    SearchRepos.Items item = (SearchRepos.Items) parcelableDate;
    mOwner = item.owner.login;
    mRepo = item.name;
    removeOnItemClickListener();
  }

  @Override public void onDetach() {
    super.onDetach();
  }

  @Override public void onLoadMoreRequested() {
    loadMoreDate();
  }

  @Override public void onRefresh() {
    mCurrentPage = 1;
    networkQueue().enqueue(
        mIssuesService.getIssuesForReposList(mOwner, mRepo, mCurrentState, mCurrentPage));
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
}
