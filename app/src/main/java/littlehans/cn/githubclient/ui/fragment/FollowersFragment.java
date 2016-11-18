package littlehans.cn.githubclient.ui.fragment;

import android.support.v4.app.Fragment;
import littlehans.cn.githubclient.R;

/**
 * Created by LittleHans on 2016/11/18.
 */
public class FollowersFragment extends BaseFragment {
  public static Fragment create() {
    return new FollowersFragment();
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_followers;
  }
}
