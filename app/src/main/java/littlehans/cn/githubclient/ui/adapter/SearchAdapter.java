package littlehans.cn.githubclient.ui.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.Search;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

/**
 * Created by LittleHans on 2016/9/29.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
  private Search mSearch;

  public SearchAdapter(Search search) {
    this.mSearch = search;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_repos, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.mTxtFullName.setText(mSearch.items.get(position).full_name);
    holder.mTxtDescription.setText(mSearch.items.get(position).description);

    List<Search.Items.TextMatches> originTextMatches = mSearch.items.get(position).text_matches;

    for (Search.Items.TextMatches textMatches : originTextMatches) {
      Spannable spanTxtName = new SpannableString(mSearch.items.get(position).full_name);
      Spannable spanTxtDescription = new SpannableString(mSearch.items.get(position).description);

      switch (textMatches.property) {
        case "name":
          for (Search.Items.TextMatches.Matches matches : textMatches.matches) {
            //int start = matches.indices.get(0);
            int start = holder.mTxtFullName.getText().toString().indexOf('/') + 1;
            int end = matches.indices.get(1) + start;
            spanTxtName.setSpan(new StyleSpan(Typeface.BOLD), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mTxtFullName.setText(spanTxtName);
          }
          break;

        case "description":
          for (Search.Items.TextMatches.Matches matches : textMatches.matches) {

            int start = matches.indices.get(0);
            int end = matches.indices.get(1);

            spanTxtDescription.setSpan(new StyleSpan(Typeface.BOLD), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mTxtDescription.setText(spanTxtDescription);
          }
          break;
      }
    }

    if (TextUtils.isEmpty(mSearch.items.get(position).language)) {
      holder.mTxtLanguage.setVisibility(GONE);
    } else {
      holder.mTxtLanguage.setText(mSearch.items.get(position).language);
    }
    holder.mTxtStargazersCount.setText(
        String.valueOf(mSearch.items.get(position).stargazers_count));
    holder.mTxtForksCount.setText(String.valueOf(mSearch.items.get(position).forks_count));
    if (TextUtils.isEmpty(holder.mTxtDescription.getText())) {
      holder.mTxtDescription.setVisibility(GONE);
    }

    holder.mTxtUpdatedAt.setText(mSearch.items.get(position).updated_at);
  }

  @Override public int getItemCount() {
    Log.v(TAG, String.valueOf(mSearch.items.size()));
    return mSearch.items.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_full_name) TextView mTxtFullName;
    @BindView(R.id.text_language) TextView mTxtLanguage;
    @BindView(R.id.text_stargazers_count) TextView mTxtStargazersCount;
    @BindView(R.id.text_forks_count) TextView mTxtForksCount;
    @BindView(R.id.text_description) TextView mTxtDescription;
    @BindView(R.id.text_updated_at) TextView mTxtUpdatedAt;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
