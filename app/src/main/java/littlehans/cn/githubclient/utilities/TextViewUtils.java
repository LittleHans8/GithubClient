package littlehans.cn.githubclient.utilities;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by littlehans on 16/11/28.
 */

public class TextViewUtils {
    public static void setTextNotEmpty(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
