package littlehans.cn.githubclient.ui.adapter;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.SearchRepos;
import littlehans.cn.githubclient.model.entity.SearchRepos.Items.TextMatches;
import littlehans.cn.githubclient.model.entity.SearchRepos.Items.TextMatches.Matches;

/**
 * Created by littlehans on 2016/10/7.
 */

public class SearchReposAdapter extends BaseQuickAdapter<SearchRepos.Items> {

  public SearchReposAdapter(List<SearchRepos.Items> items) {
    super(R.layout.card_repos, items);
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, SearchRepos.Items items) {
    baseViewHolder.setText(R.id.text_full_name, getMatchString(items)[0]);
    baseViewHolder.setText(R.id.text_stargazers_count, String.valueOf(items.stargazers_count));
    baseViewHolder.setText(R.id.text_forks_count, String.valueOf(items.forks_count));
    baseViewHolder.setText(R.id.text_updated_at, items.updated_at);

    if (TextUtils.isEmpty(items.description)) {
      baseViewHolder.getView(R.id.text_description).setVisibility(View.GONE);
    } else {
      baseViewHolder.setText(R.id.text_description, getMatchString(items)[1]);
    }
    if (TextUtils.isEmpty(items.language)) {
      baseViewHolder.setVisible(R.id.text_language, false);
      View v;
    } else {
      baseViewHolder.setText(R.id.text_language, items.language);
    }
  }

  private Spannable[] getMatchString(SearchRepos.Items items) {
    Spannable spanTxtName = new SpannableString(items.full_name);
    Spannable spanTxtDescription;
    if (TextUtils.isEmpty(items.description)) {
      spanTxtDescription = new SpannableString("");
    } else {
      spanTxtDescription = new SpannableString(items.description);
    }
    Spannable spanArray[] = { spanTxtName, spanTxtDescription };

    List<TextMatches> originTextMatches = items.text_matches;

    for (TextMatches textMatches : originTextMatches) {
      switch (textMatches.property) {
        case "name":
          for (Matches matches : textMatches.matches) {
            //int start = matches.indices.get(0);
            int start = items.full_name.indexOf('/') + 1;
            int end = matches.indices.get(1) + start;
            spanTxtName.setSpan(new StyleSpan(Typeface.BOLD), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
          }
          spanArray[0] = spanTxtName;
          break;

        case "description":
          for (Matches matches : textMatches.matches) {
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