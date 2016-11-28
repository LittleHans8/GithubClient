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
import littlehans.cn.githubclient.utilities.FormatUtils;
import support.ui.adapters.EasyViewHolder;

import static littlehans.cn.githubclient.utilities.TextViewUtils.setTextNotEmpty;

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
    mTextStartCount.setText(FormatUtils.decimalFormat(value.stargazers_count));
    mTextForkCount.setText(FormatUtils.decimalFormat(value.forks_count));
    mTextUpdatedAt.setText(mDateFormatUtil.formatTime(value.updated_at));
  }

}
