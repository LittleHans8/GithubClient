package littlehans.cn.githubclient.feature.owner.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.Repository;
import littlehans.cn.githubclient.utilities.DateFormatUtil;
import support.ui.adapters.EasyViewHolder;

/**
 * Created by LittleHans on 2016/11/27.
 */

public class StarsViewHolder extends EasyViewHolder<Repository> {

  @Bind(R.id.text_name) TextView mTextName;
  @Bind(R.id.text_description) TextView mTextDescription;
  @Bind(R.id.text_language) TextView mTextLanguage;
  @Bind(R.id.text_start_count) TextView mTextStartCount;
  @Bind(R.id.text_fork_counts) TextView mTextForkCount;
  @Bind(R.id.text_updated_at) TextView mTextUpdatedAt;
  private DateFormatUtil mDateFormatUtil;

  public StarsViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.item_starts);
    ButterKnife.bind(this, itemView);
    mDateFormatUtil = new DateFormatUtil("Updated");
  }

  @Override public void bindTo(int position, Repository value) {
    mTextName.setText(value.full_name);
    setTextNotEmpty(mTextDescription, value.description);
    setTextNotEmpty(mTextLanguage, value.language);
    mTextStartCount.setText(String.valueOf(value.stargazers_count));
    mTextForkCount.setText(String.valueOf(value.forks_count));
    mTextUpdatedAt.setText(mDateFormatUtil.formatTime(value.updated_at));
  }

  public void setTextNotEmpty(TextView textView, String text) {
    if (TextUtils.isEmpty(text)) {
      textView.setVisibility(View.VISIBLE);
      textView.setText(text);
    } else {
      textView.setVisibility(View.GONE);
    }
  }
}
