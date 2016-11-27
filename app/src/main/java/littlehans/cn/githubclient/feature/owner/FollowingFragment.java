package littlehans.cn.githubclient.feature.owner;

import android.support.v4.app.Fragment;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.ui.fragment.BaseFragment;

/**
 * Created by LittleHans on 2016/11/18.
 */
public class FollowingFragment extends BaseFragment {
  public static Fragment create() {
    return new FollowersFragment();
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_followering;
  }
}
