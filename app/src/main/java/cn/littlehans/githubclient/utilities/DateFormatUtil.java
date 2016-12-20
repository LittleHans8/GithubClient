package cn.littlehans.githubclient.utilities;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LittleHans on 2016/10/24.
 */
public class DateFormatUtil {

  Date mDate;
  SimpleDateFormat mSimpleDateFormat;
  ISO8601DateFormat dateFormat;

  public DateFormatUtil(String replaceString) {
    dateFormat = new ISO8601DateFormat();
    String pattern = String.format("'%s' d MMM yyyy ", replaceString);
    mSimpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
  }

  public DateFormatUtil() {
    dateFormat = new ISO8601DateFormat();
    String pattern = "d MMM yyyy ";
    mSimpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
  }

  public String formatTime(String ISO860DateString) {
    try {
      mDate = dateFormat.parse(ISO860DateString);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return mSimpleDateFormat.format(mDate);
  }
}
