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
import java.util.ArrayList;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.IssuesService;
import littlehans.cn.githubclient.model.entity.Issue;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;

import static littlehans.cn.githubclient.feature.repos.ReposCodeFragment.OWNER;
import static littlehans.cn.githubclient.feature.repos.ReposCodeFragment.REPO;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposIssueFragment extends NetworkFragment<List<Issue>>
    implements OnCardTouchListener {
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.spinner) Spinner mSpinner;
  private ReposIssueAdapter mIssueAdapter;
  private SimpleOnItemSelectedListener mOnItemSelectedListener;
  private String mOwner;
  private String mRepo;

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
    networkQueue().enqueue(GithubService.createIssuesService()
        .getIssuesForReposList(mOwner, mRepo, IssuesService.OPEN));
    List<String> state = new ArrayList<>();
    state.add(IssuesService.OPEN);
    state.add(IssuesService.CLOSE);
    ArrayAdapter<String> spinnerAdapter =
        new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, state);
    mSpinner.setAdapter(spinnerAdapter);
    mOnItemSelectedListener = new SimpleOnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
          networkQueue().enqueue(GithubService.createIssuesService()
              .getIssuesForReposList(mOwner, mRepo, IssuesService.OPEN));
        } else {
          networkQueue().enqueue(GithubService.createIssuesService()
              .getIssuesForReposList(mOwner, mRepo, IssuesService.CLOSE));
        }
      }
    };

    mSpinner.setOnItemSelectedListener(mOnItemSelectedListener);
  }

  @Override public void respondSuccess(List<Issue> data) {
    mIssueAdapter = new ReposIssueAdapter(data);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setAdapter(mIssueAdapter);
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
}
