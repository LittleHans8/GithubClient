package cn.littlehans.githubclient.feature.repos;

import android.support.v4.app.Fragment;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.ui.fragment.BaseFragment;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposPullRequestFragment extends BaseFragment{
  public static Fragment create() {
    return new ReposPullRequestFragment();
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_repos_pull_request;
  }
}
