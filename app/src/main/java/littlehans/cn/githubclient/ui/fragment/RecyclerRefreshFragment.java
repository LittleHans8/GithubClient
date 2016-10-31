package littlehans.cn.githubclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import littlehans.cn.githubclient.R;
import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by LittleHans on 2016/10/27.
 */

public abstract class RecyclerRefreshFragment<T> extends NetworkFragment<T> {
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(linearLayoutManager);
    networkQueue().enqueue(call());
  }

  protected abstract Call<T> call();

  @Override public void respondSuccess(T data) {
    BaseQuickAdapter<T> baseQuickAdapter = adapter();
    mRecyclerView.setAdapter(baseQuickAdapter);
  }

  @Override public void respondWithError(Throwable t) {

  }

  protected abstract BaseQuickAdapter<T> adapter();

  @Override public void respondHeader(Headers headers) {
    super.respondHeader(headers);
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_recycler_refresh;
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
