package cn.littlehans.githubclient.feature.search;

import android.support.v4.app.Fragment;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.ui.fragment.BaseFragment;

/**
 * Created by littlehans on 2016/10/1.
 */
public class SearchCodeFragment extends BaseFragment {
  @Override protected int getFragmentLayout() {
    return R.layout.fragment_search_code;
  }

  public static Fragment create() {
    return new SearchCodeFragment();
  }
}
