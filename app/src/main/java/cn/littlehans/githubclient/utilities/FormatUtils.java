package cn.littlehans.githubclient.utilities;

import android.util.Log;
import java.text.DecimalFormat;
import java.text.ParseException;

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

  public static int parse(String value) {
    int result = 0;
    try {
      result = mDecimalFormat.parse(value).intValue();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return result;
  }
}
