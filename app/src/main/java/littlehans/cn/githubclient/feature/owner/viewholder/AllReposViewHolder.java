package littlehans.cn.githubclient.feature.owner.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.Repository;
import littlehans.cn.githubclient.utilities.DateFormatUtil;
import support.ui.adapters.EasyViewHolder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by LittleHans on 2016/11/27.
 */

public class AllReposViewHolder extends EasyViewHolder<Repository> {

  @Bind(R.id.text_name) TextView mTextName;
  @Bind(R.id.text_description) TextView mTextDescription;
  @Bind(R.id.text_language) TextView mTextLanguage;
  @Bind(R.id.text_updated_at) TextView mTextUpdatedAt;
  private DateFormatUtil mDateFormatUtil;

  public AllReposViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.item_repos);
    ButterKnife.bind(this, itemView);
    mDateFormatUtil = new DateFormatUtil("Updated");
  }

  @Override public void bindTo(int position, Repository value) {
    mTextName.setText(value.name);
    if (!TextUtils.isEmpty(value.description)) {
      mTextDescription.setVisibility(VISIBLE);
      mTextDescription.setText(value.description);
    } else {
      mTextDescription.setVisibility(GONE);
    }
    if (!TextUtils.isEmpty(value.language)) {
      mTextLanguage.setVisibility(VISIBLE);
      mTextLanguage.setText(value.language);
    } else {
      mTextLanguage.setVisibility(GONE);
    }
    mTextUpdatedAt.setText(mDateFormatUtil.formatTime(value.updated_at));
  }
}