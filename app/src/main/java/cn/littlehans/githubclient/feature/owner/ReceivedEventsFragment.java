package cn.littlehans.githubclient.feature.owner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.api.service.ActivityService;
import cn.littlehans.githubclient.model.entity.ReceivedEvent;
import cn.littlehans.githubclient.ui.adapter.ReceivedEventAdapter;
import cn.littlehans.githubclient.ui.fragment.PageFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Headers;

import static android.content.ContentValues.TAG;

/**
 * Created by LittleHans on 2016/11/4.
 */

public class ReceivedEventsFragment extends PageFragment<List<ReceivedEvent>>
    implements BaseQuickAdapter.RequestLoadMoreListener {

  ReceivedEventAdapter mEventAdapter;
  private ActivityService mActivityService;
  private boolean mIsFirstLoad = true;
  private boolean mOnRefreshing = false;

  public static Fragment create() {
    return new ReceivedEventsFragment();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mActivityService = GitHubService.createActivityService();
    loadData();
  }

  @Override public void respondSuccess(final List<ReceivedEvent> data) {
    final List<ReceivedEvent> typeData = addTypeToData(data);
    mRecyclerView.post(new Runnable() {
      @Override public void run() {
        if (mIsFirstLoad) {
          initAdapter(typeData);
          mIsFirstLoad = false;
          mCurrentPage++;
          return;
        }

        if (mCurrentPage == 1) {
          if (mOnRefreshing) {
            mEventAdapter.setNewData(data);
            mRecyclerView.invalidate();
            mOnRefreshing = false;
            mCurrentPage++;
          }
        } else {
          if (mCurrentPage >= mLastPage) {
            mEventAdapter.loadMoreEnd();
          } else {
            mEventAdapter.addData(typeData);
            mCurrentPage++;
          }
        }
      }
    });
  }

  @Override public void respondHeader(Headers headers) {
    mLastPage = getLastPage(headers);
  }

  private void initAdapter(List<ReceivedEvent> typeData) {
    mEventAdapter = new ReceivedEventAdapter(getActivity(), typeData);
    mEventAdapter.setOnLoadMoreListener(this);
    mRecyclerView.setAdapter(mEventAdapter);
    mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
      @Override public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Toast.makeText(getActivity(), "i" + i, Toast.LENGTH_SHORT).show();
      }
    });
  }

  @NonNull private List<ReceivedEvent> addTypeToData(List<ReceivedEvent> data) {
    // add type to received data
    List<ReceivedEvent> typeData = new ArrayList<>();
    for (ReceivedEvent receivedEvent : data) {
      String type = receivedEvent.type;
      if (type.equals(ReceivedEvent.DELETE_EVENT)
          || type.equals(ReceivedEvent.WATCH_EVENT)
          || type.equals(ReceivedEvent.CREATE_EVENT)
          || type.equals(ReceivedEvent.MEMBER_EVENT)
          || type.equals(ReceivedEvent.FORK_EVENT)) {
        receivedEvent.itemType = ReceivedEvent.TEXT;
      }
      typeData.add(receivedEvent);
    }
    return typeData;
  }

  private void loadData() {
    networkQueue().enqueue(mActivityService.getReceivedEvents("LittleHans8", mCurrentPage));
    Log.d(TAG, "loadData: " + mLastPage);
  }

  @Override public void onLoadMoreRequested() {
    loadData();
  }

  @Override public void onRefresh() {
    super.onRefresh();
    mOnRefreshing = true;
    loadData();
  }

  @Override public void respondWithError(Throwable t) {
    Log.d(TAG, "respondWithError: " + t.getMessage());
  }
}
