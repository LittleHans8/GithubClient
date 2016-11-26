package littlehans.cn.githubclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.feature.search.PageLink;
import littlehans.cn.githubclient.utilities.DividerItemDecoration;
import okhttp3.Headers;

/**
 * Created by LittleHans on 2016/11/15.
 */

public abstract class PageFragment<T> extends NetworkFragment<T>
    implements SwipeRefreshLayout.OnRefreshListener {

  protected @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  protected @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mLayoutSwipeRefresh;
  protected int mLastPage;
  protected int mCurrentPage = 1;

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_page_fragemnt;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initUI();
  }

  private void initUI() {
    mLayoutSwipeRefresh.setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(linearLayoutManager);
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    mLayoutSwipeRefresh.setOnRefreshListener(this);
  }

  @Override public void respondWithError(Throwable t) {

  }

  protected int getLastPage(Headers headers) {
    List<String> links = headers.values("Link");
    if (!links.isEmpty()) {
      String link = links.get(0);
      PageLink pageLink = new PageLink(link);
      if (pageLink.getLastPage() != 0) {
        return pageLink.getLastPage();
      }
    }
    return 0;
  }

  @Override public void startRequest() {
    super.startRequest();
    mLayoutSwipeRefresh.setRefreshing(true);
  }

  @Override public void endRequest() {
    super.endRequest();
    mLayoutSwipeRefresh.setRefreshing(false);
  }

  @Override public void onRefresh() {
    mCurrentPage = 1;
  }
}
