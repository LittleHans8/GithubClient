package littlehans.cn.githubclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import java.util.ArrayList;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.EventService;
import littlehans.cn.githubclient.model.entity.ReceivedEvent;
import littlehans.cn.githubclient.ui.adapter.ReceivedEventAdapter;
import littlehans.cn.githubclient.utilities.DividerItemDecoration;

import static android.content.ContentValues.TAG;

/**
 * Created by LittleHans on 2016/11/4.
 */

public class ReceivedEventsFragment extends NetworkFragment<List<ReceivedEvent>> {

  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout mLayoutSwipeRefresh;
  private EventService mEventService;

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_events;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mEventService = GithubService.createEventService();
    Log.d(TAG, "onViewCreated: ");
    networkQueue().enqueue(mEventService.getReceivedEvent("LittleHans8"));
  }

  @Override public void respondSuccess(List<ReceivedEvent> data) {
    Log.d(TAG, "respondSuccess: ");
    List<ReceivedEvent> copyDate = new ArrayList<>();
    for (ReceivedEvent receivedEvent : data) {
      String type = receivedEvent.type;
      if (type.equals(ReceivedEvent.DELETE_EVENT)
          || type.equals(ReceivedEvent.WATCH_EVENT)
          || type.equals(ReceivedEvent.CREATE_EVENT)
          || type.equals(ReceivedEvent.MEMBER_EVENT)
          || type.equals(ReceivedEvent.FORK_EVENT)) {
        receivedEvent.itemType = ReceivedEvent.TEXT;
      }
      copyDate.add(receivedEvent);
    }

    ReceivedEventAdapter eventAdapter = new ReceivedEventAdapter(getActivity(), copyDate);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    mRecyclerView.setAdapter(eventAdapter);
  }

  @Override public void respondWithError(Throwable t) {
    Log.d(TAG, "respondWithError: " + t.getMessage());
  }

  @Override public void startRequest() {
    super.startRequest();
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        mLayoutSwipeRefresh.setRefreshing(true);
        Log.d(TAG, "run: " + "setRefreshing");
      }
    });
  }

  @Override public void endRequest() {
    super.endRequest();
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        mLayoutSwipeRefresh.setRefreshing(false);
      }
    });
  }
}
