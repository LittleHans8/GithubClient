package cn.littlehans.githubclient.feature.repos;

import android.widget.TextView;
import cn.littlehans.githubclient.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

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
