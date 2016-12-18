package littlehans.cn.githubclient.feature.user;

import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.ui.fragment.NetworkFragment;

/**
 * Created by LittleHans on 2016/12/18.
 */

public class UserOverviewFragment extends NetworkFragment<User> {

  @Override public void respondSuccess(User data) {

  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_user_overview;
  }
}
