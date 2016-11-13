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

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

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

    String login = receivedEvent.actor.login;
    String repo = receivedEvent.repo.name;

    switch (baseViewHolder.getItemViewType()) {
      case ReceivedEvent.TEXT:

        TextView textReceivedEventBody = baseViewHolder.getView(R.id.text_received_event_body);
        switch (receivedEvent.type) {
          case ReceivedEvent.DELETE_EVENT:
            textReceivedEventBody.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_event_branch, 0, 0, 0);
            String ref_type = receivedEvent.payload.ref_type;
            String ref = receivedEvent.payload.ref;
            String deleteFormat = "%s deleted %s %s at %s";
            String body = String.format(deleteFormat, login, ref_type, ref, repo);
            Spannable spannableBody = getSpanText(body, login, ref, repo);
            textReceivedEventBody.setText(spannableBody);
            baseViewHolder.setText(R.id.text_create_at,
                mDateFormatUtil.formatTime(receivedEvent.created_at));
            break;
          case ReceivedEvent.WATCH_EVENT:
            textReceivedEventBody.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_event_star,
                0, 0, 0);
            String action = receivedEvent.payload.action;
            String eventBodyFormat = "%s %s %s";
            String eventBody = String.format(eventBodyFormat, login, action, repo);
            textReceivedEventBody.setText(getSpanText(eventBody, login, repo));
            baseViewHolder.setText(R.id.text_create_at,
                mDateFormatUtil.formatTime(receivedEvent.created_at));
            break;

          /**
           * ref_type	The object that was created. Can be one of "repository", "branch", or "tag"
           */

          case ReceivedEvent.CREATE_EVENT:
            String type = receivedEvent.payload.ref_type;

            String createEventBodyFormat = "%s created %s %s";
            String createEventBody = String.format(createEventBodyFormat, login, type, repo);
            textReceivedEventBody.setText(getSpanText(createEventBody, login, repo));
            baseViewHolder.setText(R.id.text_create_at,
                mDateFormatUtil.formatTime(receivedEvent.created_at));
            break;
        }

        break;
      case ReceivedEvent.TEXT_AVATAR:
        TextView textCreateAtAndType = baseViewHolder.getView(R.id.text_create_at_and_type);
        SimpleDraweeView simpleDraweeView = baseViewHolder.getView(R.id.avatar);
        simpleDraweeView.setImageURI(receivedEvent.actor.avatar_url);
        switch (receivedEvent.type) {
          case ReceivedEvent.PUSH_EVENT:
            String createAt = mDateFormatUtil.formatTime(receivedEvent.created_at) + "\n";
            String ref[] = receivedEvent.payload.ref.split("/");
            String branch = ref[ref.length - 1];
            String pushFormat = "%s %s pushed to %s at %s";
            String pushBody = String.format(pushFormat, createAt, login, branch, repo);
            Spannable spannablePushBody = getSpanText(pushBody, login, branch, repo);
            textCreateAtAndType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_event_commint,
                0, 0, 0);
            textCreateAtAndType.setText(spannablePushBody);
            break;
        }
        break;
    }
  }

  private Spannable getSpanText(String fullString, String... spanText) {
    Spannable spannableString = new SpannableString(fullString);
    for (String text : spanText) {
      int start = fullString.indexOf(text);
      int end = start + text.length();
      spannableString.setSpan(getColorSpan(), start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    return spannableString;
  }

  @NonNull private ForegroundColorSpan getColorSpan() {
    return new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorAccent));
  }
}
