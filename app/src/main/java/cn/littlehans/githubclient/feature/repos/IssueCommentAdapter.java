package cn.littlehans.githubclient.feature.repos;

import android.text.TextUtils;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.model.entity.Comment;
import cn.littlehans.githubclient.utilities.DateFormatUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mukesh.MarkdownView;
import java.util.List;

/**
 * Created by LittleHans on 2016/10/28.
 */

public class IssueCommentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {
  private DateFormatUtil mDateFormat;
  public IssueCommentAdapter(List<Comment> data) {
    super(R.layout.item_issue_detail, data);
    mDateFormat = new DateFormatUtil("commented");
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, Comment comment) {
    baseViewHolder.setText(R.id.text_login, comment.user.login);
    baseViewHolder.setText(R.id.text_create_at, mDateFormat.formatTime(comment.created_at));
    MarkdownView markdownView = baseViewHolder.getView(R.id.markdown_view);
    markdownView.setOpenUrlInBrowser(true);
    if (!TextUtils.isEmpty(comment.body)) {
      markdownView.setMarkDownText(comment.body);
    } else {
      markdownView.setMarkDownText("No Description!");
    }

    SimpleDraweeView avatar = baseViewHolder.getView(R.id.avatar);
    avatar.setImageURI(comment.user.avatar_url);
  }
}
