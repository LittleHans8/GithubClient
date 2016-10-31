package littlehans.cn.githubclient.feature.repos;

import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.Trees;

/**
 * Created by LittleHans on 2016/10/20.
 */

public class ReposCodeAdapter extends BaseQuickAdapter<Trees.Tree, BaseViewHolder> {

  static final String BLOB = "blob";
  static final String TREE = "tree";

  public ReposCodeAdapter(List<Trees.Tree> data) {
    super(R.layout.item_code_file, data);
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, Trees.Tree tree) {
    TextView textView = baseViewHolder.getView(R.id.text_code_file);
    switch (tree.type) {
      case BLOB:
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_file_text, 0, 0, 0);
        textView.setText(tree.path);
        break;
      case TREE:
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_file_directory, 0, 0, 0);
        textView.setText(tree.path);
    }
  }

  public String getName(String path) {
    String paths[] = path.split("/");
    return paths[paths.length-1];
  }
}
