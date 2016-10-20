package littlehans.cn.githubclient.feature.repos;

import android.support.v4.app.Fragment;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.ui.fragment.BaseFragment;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposIssueFragment extends BaseFragment{
  public static Fragment create() {
    return new ReposIssueFragment();
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repos_issue;
  }
}
