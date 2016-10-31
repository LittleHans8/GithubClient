package littlehans.cn.githubclient.feature.repos;

import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import littlehans.cn.githubclient.R;

/**
 * Created by littlehans on 16/10/21.
 */

public class ReposCodePathAdapter extends BaseQuickAdapter<ReposCodePath, BaseViewHolder> {

  public ReposCodePathAdapter(List<ReposCodePath> data) {
    super(R.layout.item_text_path,data);
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, ReposCodePath reposCodePath) {
    TextView textView = baseViewHolder.getView(R.id.text_path);
    textView.setText(reposCodePath.name);
    textView.setContentDescription(reposCodePath.sha);
  }
}
