package littlehans.cn.githubclient.ui.adapter;

import android.text.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.utilities.DateFormatUtil;

/**
 * Created by littlehans on 2016/10/17.
 */

public class SearchUserAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
  DateFormatUtil mDateFormat;

  public SearchUserAdapter(List<User> data) {
    super(R.layout.item_search_user, data);
    mDateFormat = new DateFormatUtil("Joined on");
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, User user) {
    SimpleDraweeView avatar = baseViewHolder.getView(R.id.img_avatar);
    if (!TextUtils.isEmpty(user.avatar_url)) {
      avatar.setImageURI(user.avatar_url);
    }
    checkSet(baseViewHolder, R.id.text_repo_name, user.login);
    checkSet(baseViewHolder, R.id.text_email, user.email);
    checkSet(baseViewHolder, R.id.text_location, user.location);
    ISO8601DateFormat dateFormat = new ISO8601DateFormat();
      checkSet(baseViewHolder, R.id.text_join_time, mDateFormat.formatTime(user.created_at));
  }

  public void checkSet(BaseViewHolder baseViewHolder, int resId, String data) {
    if (TextUtils.isEmpty(data)) {
      baseViewHolder.setVisible(resId, false);
    } else {
      baseViewHolder.setText(resId, data);
    }
  }
}
