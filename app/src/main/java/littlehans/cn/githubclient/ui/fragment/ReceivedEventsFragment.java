package littlehans.cn.githubclient.ui.fragment;

import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.ReceivedEvent;

/**
 * Created by LittleHans on 2016/11/4.
 */

public class ReceivedEventsFragment extends NetworkFragment<List<ReceivedEvent>> {

  @Override public void respondSuccess(List<ReceivedEvent> data) {

  }

  @Override public void respondWithError(Throwable t) {

  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_events;
  }
}
