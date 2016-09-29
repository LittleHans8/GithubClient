package littlehans.cn.githubclient.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
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
    // // TODO: 2016/9/29
    //Spannable spannableString = new SpannableString(mSearch.items.get(position).full_name);
    //int start = mSearch.items.get(position).text_matches.get(position).matches.get(0).indices.get(0);
    //int end = mSearch.items.get(position).text_matches.get(position).matches.get(0).indices.get(1);
    //spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    //holder.mTxtFullName.setText(spannableString);

    if (TextUtils.isEmpty(mSearch.items.get(position).language)) {
      holder.mTxtLanguage.setVisibility(GONE);
    }
    holder.mTxtStargazersCount.setText(
        String.valueOf(mSearch.items.get(position).stargazers_count));
    holder.mTxtForksCount.setText(String.valueOf(mSearch.items.get(position).forks_count));
    if (TextUtils.isEmpty(mSearch.items.get(position).description)) {
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
