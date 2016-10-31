package littlehans.cn.githubclient.feature.repos;

import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.Issue;
import littlehans.cn.githubclient.utilities.DateFormatUtil;

/**
 * Created by LittleHans on 2016/10/26.
 */

public class ReposIssueAdapter extends BaseQuickAdapter<Issue, BaseViewHolder> {
  private DateFormatUtil mDateFormat;

  public ReposIssueAdapter(List<Issue> data) {
    super(R.layout.card_issue, data);
    mDateFormat = new DateFormatUtil("opened");
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, Issue issue) {
    TextView txtTitle = baseViewHolder.getView(R.id.text_login);
    if (issue.state.equals("open")) {
      txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_issue_open, 0, 0, 0);
    } else {
      txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_issue_closed, 0, 0, 0);
    }
    txtTitle.setText(issue.title);
    if (issue.comments > 0) {
      baseViewHolder.getView(R.id.text_comment_count).setVisibility(View.VISIBLE);
      String commentCount = String.valueOf(issue.comments);
      baseViewHolder.setText(R.id.text_comment_count, commentCount);
    } else {
      baseViewHolder.getView(R.id.text_comment_count).setVisibility(View.GONE);
    }
    String openBy = "#%s %s by %s";
    String createTime = mDateFormat.formatTime(issue.created_at);
    String formatOpenBy = String.format(openBy, issue.number, createTime, issue.user.login);
    baseViewHolder.setText(R.id.text_open_by, formatOpenBy);
  }
}
