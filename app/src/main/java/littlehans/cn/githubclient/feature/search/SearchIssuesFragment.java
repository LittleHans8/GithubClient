package littlehans.cn.githubclient.feature.search;

import android.support.v4.app.Fragment;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.ui.fragment.BaseFragment;

/**
 * Created by LittleHans on 2016/10/1.
 */
public class SearchIssuesFragment extends BaseFragment {
  @Override protected int getFragmentLayout() {
    return R.layout.fragment_search_issuses;
  }

  public static Fragment create() {
    return new SearchIssuesFragment();
  }
}
