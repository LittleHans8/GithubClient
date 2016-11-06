package littlehans.cn.githubclient.ui.adapter;

import android.text.TextUtils;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.ReceivedEvent;
import littlehans.cn.githubclient.model.entity.ReceivedEvent.Payload.Commits;
import littlehans.cn.githubclient.utilities.DateFormatUtil;

/**
 * Created by LittleHans on 2016/11/6.
 */

public class ReceivedEventAdapter extends BaseQuickAdapter<ReceivedEvent, BaseViewHolder> {

  public static final String PUSH_EVENT = "PushEvent";
  private DateFormatUtil mDateFormatUtil;

  public ReceivedEventAdapter(List<ReceivedEvent> data) {
    super(R.layout.card_received_event, data);
    mDateFormatUtil = new DateFormatUtil();
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, ReceivedEvent receivedEvent) {
    SimpleDraweeView simpleDraweeView = baseViewHolder.getView(R.id.avatar);
    simpleDraweeView.setImageURI(receivedEvent.actor.avatar_url);

    List<Commits> commits = receivedEvent.payload.commits;
    TextView textMessage = baseViewHolder.getView(R.id.text_message);
    for (Commits commit : commits) {
      if (!TextUtils.isEmpty(textMessage.getText())) {
        String text = textMessage.getText() + commit.message + "\n";
        textMessage.setText(text);
      } else {
        textMessage.setText(commit.message);
      }
    }

    if (receivedEvent.type.equals(PUSH_EVENT)) {
      TextView textCreateAtAndType = baseViewHolder.getView(R.id.text_create_at_and_type);
      String createAt = mDateFormatUtil.formatTime(receivedEvent.created_at);
      CharSequence eventFormat = "%s pushed to %s at %s";
      String title = String.format(eventFormat.toString(), getBranchName(receivedEvent.payload.ref),
          receivedEvent.repo.name);
      String total = createAt + "\n" + title;
      textCreateAtAndType.setText(total);

      textCreateAtAndType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_event_commint, 0, 0,
          0);
    }
  }

  private String getBranchName(String ref) {
    String str[] = ref.split("/");
    return str[str.length - 1];
  }
}
