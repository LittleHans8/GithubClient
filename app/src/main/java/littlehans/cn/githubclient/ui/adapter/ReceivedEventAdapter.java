package littlehans.cn.githubclient.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.ReceivedEvent;
import littlehans.cn.githubclient.utilities.DateFormatUtil;

/**
 * Created by LittleHans on 2016/11/6.
 */

public class ReceivedEventAdapter extends BaseMultiItemQuickAdapter<ReceivedEvent, BaseViewHolder> {

  private DateFormatUtil mDateFormatUtil;

  public ReceivedEventAdapter(Context context, List<ReceivedEvent> data) {
    super(data);
    mContext = context;
    addItemType(ReceivedEvent.TEXT, R.layout.card_simple_received_event);
    addItemType(ReceivedEvent.TEXT_AVATAR, R.layout.card_received_event);
    mDateFormatUtil = new DateFormatUtil();
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, ReceivedEvent receivedEvent) {
    switch (baseViewHolder.getItemViewType()) {
      case ReceivedEvent.TEXT:
        TextView textReceivedEventBody = baseViewHolder.getView(R.id.text_received_event_body);
        switch (receivedEvent.type) {
          case ReceivedEvent.DELETE_EVENT:
            textReceivedEventBody.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_event_branch, 0, 0, 0);
            Spannable login = new SpannableString(receivedEvent.actor.login);

            String ref_type = receivedEvent.payload.ref_type;
            String ref = receivedEvent.payload.ref;
            String repo = receivedEvent.repo.name;
            String deleteFormat = "%s deleted %s %s at %s";
            String body = String.format(deleteFormat, login, ref_type, ref, repo);
            Spannable spannableBody = new SpannableString(body);
            spannableBody.setSpan(getColorSpan(), 0, login.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            int refStart = spannableBody.toString().indexOf(ref);
            spannableBody.setSpan(getColorSpan(), refStart, refStart + ref.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            int repoStart = spannableBody.toString().indexOf(repo);
            spannableBody.setSpan(getColorSpan(), repoStart, repoStart + repo.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textReceivedEventBody.setText(spannableBody);
            baseViewHolder.setText(R.id.text_create_at,
                mDateFormatUtil.formatTime(receivedEvent.created_at));
            break;
        }

        break;
      case ReceivedEvent.TEXT_AVATAR:
        SimpleDraweeView simpleDraweeView = baseViewHolder.getView(R.id.avatar);
        simpleDraweeView.setImageURI(receivedEvent.actor.avatar_url);
        break;
    }
  }

  @NonNull private ForegroundColorSpan getColorSpan() {
    return new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorAccent));
  }
}
