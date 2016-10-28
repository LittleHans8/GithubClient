package littlehans.cn.githubclient.feature.repos;

import android.text.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mukesh.MarkdownView;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.Comment;

/**
 * Created by LittleHans on 2016/10/28.
 */

public class IssueCommentAdapter extends BaseQuickAdapter<Comment> {
  public IssueCommentAdapter(List<Comment> data) {
    super(R.layout.card_issue_detail, data);
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, Comment comment) {
    baseViewHolder.setText(R.id.text_login, comment.user.login);
    baseViewHolder.setText(R.id.text_create_at, comment.created_at);
    MarkdownView markdownView = baseViewHolder.getView(R.id.markdown_view);
    if (!TextUtils.isEmpty(comment.body)) {
      markdownView.setMarkDownText(comment.body);
    } else {
      markdownView.setMarkDownText("No Description!");
    }
  }
}
