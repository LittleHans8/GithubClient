package littlehans.cn.githubclient.feature.repos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.ArrayList;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.IssuesService;
import littlehans.cn.githubclient.feature.search.PageLink;
import littlehans.cn.githubclient.model.entity.Issue;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;
import okhttp3.Headers;

import static littlehans.cn.githubclient.feature.repos.ReposCodeFragment.OWNER;
import static littlehans.cn.githubclient.feature.repos.ReposCodeFragment.REPO;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposIssueFragment extends NetworkFragment<List<Issue>>
    implements OnCardTouchListener, BaseQuickAdapter.RequestLoadMoreListener {
  private final IssuesService mIssuesService = GithubService.createIssuesService();
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.spinner) Spinner mSpinner;
  private ReposIssueAdapter mIssueAdapter;
  private SimpleOnItemSelectedListener mOnItemSelectedListener;
  private String mOwner;
  private String mRepo;
  private int mLastPage;
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
    loadOpenIssues();
    initAdapter();
    addListener();
  }

  private void initAdapter() {
    List<String> state = new ArrayList<>();
    state.add(IssuesService.OPEN);
    state.add(IssuesService.CLOSED);
    ArrayAdapter<String> spinnerAdapter =
        new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, state);
    mSpinner.setAdapter(spinnerAdapter);
  }

  private void addListener() {
    mOnItemSelectedListener = new SimpleOnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
          loadOpenIssues();
        } else {
          loadClosedIssues();
        }
      }
    };

    mSpinner.setOnItemSelectedListener(mOnItemSelectedListener);
  }

  private void loadOpenIssues() {
    mCurrentState = IssuesService.OPEN;
    mCurrentPage = 1;
    networkQueue().enqueue(
        mIssuesService.getIssuesForReposList(mOwner, mRepo, IssuesService.OPEN, mCurrentPage));
  }

  private void loadClosedIssues() {
    mCurrentState = IssuesService.CLOSED;
    mCurrentPage = 1;
    networkQueue().enqueue(
        mIssuesService.getIssuesForReposList(mOwner, mRepo, IssuesService.CLOSED, mCurrentPage));
  }

  private void loadMoreDate() {
    networkQueue().enqueue(
        mIssuesService.getIssuesForReposList(mOwner, mRepo, mCurrentState, mCurrentPage));
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
    }
  }

  private void updateRecyclerView(final List<Issue> issues) {
    mRecyclerView.post(new Runnable() {
      @Override public void run() {
        if (mCurrentPage == 1) {
          mLinearLayoutManager = new LinearLayoutManager(getActivity());
          mRecyclerView.setLayoutManager(mLinearLayoutManager);
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

  @Override public void respondWithError(Throwable t) {

  }

  @Override public void onCardTouchListener(Intent intent) {
    mOwner = intent.getStringExtra(OWNER);
    mRepo = intent.getStringExtra(REPO);
  }

  @Override public void onDetach() {
    super.onDetach();
  }

  @Override public void onLoadMoreRequested() {
    loadMoreDate();
  }
}
