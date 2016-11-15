package littlehans.cn.githubclient.ui.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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
    String eventBodyFormat;

    switch (baseViewHolder.getItemViewType()) {
      case ReceivedEvent.TEXT:
        TextView textReceivedEventBody = baseViewHolder.getView(R.id.text_received_event_body);

        switch (receivedEvent.type) {
          case ReceivedEvent.DELETE_EVENT:
            setupDrawable(textReceivedEventBody, R.drawable.ic_event_branch);
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
            setupDrawable(textReceivedEventBody, R.drawable.ic_event_star);
            String action = receivedEvent.payload.action;
            eventBodyFormat = "%s %s %s";
            String eventBody = String.format(eventBodyFormat, login, action, repo);
            textReceivedEventBody.setText(getSpanText(eventBody, login, repo));
            baseViewHolder.setText(R.id.text_create_at,
                mDateFormatUtil.formatTime(receivedEvent.created_at));
            break;

          case ReceivedEvent.CREATE_EVENT:
            String type = receivedEvent.payload.ref_type;
            switch (type) {
              case "repository":
                setupDrawable(textReceivedEventBody, R.drawable.ic_event_repo);
                eventBodyFormat = "%s created %s %s";
                String createEventBody = String.format(eventBodyFormat, login, type, repo);
                textReceivedEventBody.setText(getSpanText(createEventBody, login, repo));
                break;
              case "branch":
                textReceivedEventBody.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_event_branch, 0, 0, 0);
                ref = receivedEvent.payload.ref;
                eventBodyFormat = "%s created branch %s at %s";
                eventBody = String.format(eventBodyFormat, login, ref, repo);
                textReceivedEventBody.setText(getSpanText(eventBody, login, ref, repo));
                break;
              case "tag":
                textReceivedEventBody.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_event_tag, 0, 0, 0);
                ref = receivedEvent.payload.ref;
                eventBodyFormat = "%s created tag %s at %s";
                eventBody = String.format(eventBodyFormat, login, ref, repo);
                textReceivedEventBody.setText(getSpanText(eventBody, login, ref, repo));
                break;
            }

            baseViewHolder.setText(R.id.text_create_at,
                mDateFormatUtil.formatTime(receivedEvent.created_at));
            break;
        }

        break;
      case ReceivedEvent.TEXT_AVATAR:
        String createAt = mDateFormatUtil.formatTime(receivedEvent.created_at) + "\n";
        TextView textCreateAtAndType = baseViewHolder.getView(R.id.text_create_at_and_type);
        SimpleDraweeView simpleDraweeView = baseViewHolder.getView(R.id.avatar);
        simpleDraweeView.setImageURI(receivedEvent.actor.avatar_url);

        switch (receivedEvent.type) {
          case ReceivedEvent.PUSH_EVENT:
            String branchRef[] = receivedEvent.payload.ref.split("/");
            String branch = branchRef[branchRef.length - 1];
            String pushFormat = "%s %s pushed to %s at %s";
            String pushBody = String.format(pushFormat, createAt, login, branch, repo);
            Spannable spannablePushBody = getSpanText(pushBody, login, branch, repo);
            setupDrawable(textCreateAtAndType, R.drawable.ic_event_commint);
            textCreateAtAndType.setText(spannablePushBody);
            break;
          case ReceivedEvent.RELEASE_EVENT:
            eventBodyFormat = "%s released %s at %s";
            String tagName = receivedEvent.payload.release.tag_name;
            Log.d(TAG, "convert: " + tagName);
            setupDrawable(textCreateAtAndType, R.drawable.ic_event_realease);
            TextView textMessage = baseViewHolder.getView(R.id.text_message);
            setupDrawable(textMessage, R.drawable.ic_event_cloud_download);
            String sourceZip = "Source code (zip)";
            String eventBody = String.format(eventBodyFormat, login, tagName, repo);
            textMessage.setText(getSpanText(sourceZip, "Source", "code", "(zip)"));
            textCreateAtAndType.setText(getSpanText(eventBody, login, tagName, repo));
            break;
        }
        break;
    }
  }

  private void setupDrawable(TextView textView, @DrawableRes int drawableRes) {
    textView.setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0);
  }

  private Spannable getSpanText(String fullString, String... spanText) {
    Spannable spannableString = new SpannableString(fullString);
    if (spanText.length == 1) {
      Log.d(TAG, "getSpanText: " + fullString.indexOf(spanText[0]));
      Log.d(TAG, "getSpanText: " + spanText[0].length());
    }

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
