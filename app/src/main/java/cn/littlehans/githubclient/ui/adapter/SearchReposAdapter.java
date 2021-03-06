package cn.littlehans.githubclient.ui.adapter;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.model.entity.Repository;
import cn.littlehans.githubclient.utilities.DateFormatUtil;
import cn.littlehans.githubclient.utilities.FormatUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

/**
 * Created by littlehans on 2016/10/7.
 */

public class SearchReposAdapter extends BaseQuickAdapter<Repository, BaseViewHolder> {
  DateFormatUtil mDateFormat;

  public SearchReposAdapter(List<Repository> items) {
    super(R.layout.item_search_repos, items);
    mDateFormat = new DateFormatUtil("Updated");
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, Repository items) {
    baseViewHolder.setText(R.id.text_full_name, getMatchString(items)[0]);
    baseViewHolder.setText(R.id.text_stargazers_count,
        FormatUtils.decimalFormat(items.stargazers_count));
    baseViewHolder.setText(R.id.text_forks_count, FormatUtils.decimalFormat(items.forks_count));
    baseViewHolder.setText(R.id.text_updated_at, items.updated_at);
    checkSet(baseViewHolder, R.id.text_updated_at, mDateFormat.formatTime(items.updated_at));

    if (TextUtils.isEmpty(items.description)) {
      baseViewHolder.getView(R.id.text_description).setVisibility(View.GONE);
    } else {
      baseViewHolder.getView(R.id.text_description).setVisibility(View.VISIBLE);
      baseViewHolder.setText(R.id.text_description, getMatchString(items)[1]);
    }
    if (TextUtils.isEmpty(items.language)) {
      baseViewHolder.setVisible(R.id.text_language, false);
    } else {
      baseViewHolder.setText(R.id.text_language, items.language);
    }
  }

  public void checkSet(BaseViewHolder baseViewHolder, int resId, String data) {
    if (TextUtils.isEmpty(data)) {
      baseViewHolder.setVisible(resId, false);
    } else {
      baseViewHolder.setVisible(resId, true);
      baseViewHolder.setText(resId, data);
    }
  }

  private Spannable[] getMatchString(Repository items) {
    Spannable spanTxtName = new SpannableString(items.full_name);
    Spannable spanTxtDescription;
    if (TextUtils.isEmpty(items.description)) {
      spanTxtDescription = new SpannableString("");
    } else {
      spanTxtDescription = new SpannableString(items.description);
    }
    Spannable spanArray[] = { spanTxtName, spanTxtDescription };

    List<Repository.TextMatches> originTextMatches = items.text_matches;

    for (Repository.TextMatches textMatches : originTextMatches) {
      switch (textMatches.property) {
        case "name":
          for (Repository.TextMatches.Matches matches : textMatches.matches) {
            //int start = matches.indices.get(0);
            int start = items.full_name.indexOf('/') + 1;
            int end = matches.indices.get(1) + start;
            spanTxtName.setSpan(new StyleSpan(Typeface.BOLD), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
          }
          spanArray[0] = spanTxtName;
          break;

        case "description":
          for (Repository.TextMatches.Matches matches : textMatches.matches) {
            int start = matches.indices.get(0);
            int end = matches.indices.get(1);

            spanTxtDescription.setSpan(new StyleSpan(Typeface.BOLD), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanArray[1] = spanTxtDescription;
          }
          break;
      }
    }
    return spanArray;
  }
}
