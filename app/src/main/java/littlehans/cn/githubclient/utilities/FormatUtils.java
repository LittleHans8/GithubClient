package littlehans.cn.githubclient.utilities;

import android.util.Log;
import java.text.DecimalFormat;

import static android.content.ContentValues.TAG;

/**
 * Created by LittleHans on 2016/11/1.
 */

public class FormatUtils {

  private static DecimalFormat mDecimalFormat = new DecimalFormat();

  public FormatUtils() {
    Log.d(TAG, "FormatUtils: ");
  }

  public static String decimalFormat(int value) {
    return mDecimalFormat.format(value);
  }
}
