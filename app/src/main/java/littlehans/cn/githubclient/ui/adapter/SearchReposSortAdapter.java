package littlehans.cn.githubclient.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import littlehans.cn.githubclient.R;

/**
 * Created by LittleHans on 2016/10/7.
 */

public class SearchReposSortAdapter extends BaseQuickAdapter<String> {

  String sort[] = { "Best Match", "Most Start", "Most Forks" };
  String language[] = { "Java", "JavaScript", "Object-C", "C++/C", "Php" };

  public SearchReposSortAdapter(int layoutResId, List<String> data) {
    super(layoutResId,data);
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, String s) {
    baseViewHolder.setText(R.id.radio_tag_sort, s);
  }
}
