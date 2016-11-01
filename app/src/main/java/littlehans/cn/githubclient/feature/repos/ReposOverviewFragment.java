package littlehans.cn.githubclient.feature.repos;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mukesh.MarkdownView;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.api.service.GitDateService;
import littlehans.cn.githubclient.model.entity.SearchRepos;
import littlehans.cn.githubclient.ui.fragment.BaseFragment;
import littlehans.cn.githubclient.utilities.DateFormatUtil;
import littlehans.cn.githubclient.utilities.FormatUtils;

/**
 * Created by LittleHans on 2016/10/30.
 */

public class ReposOverviewFragment extends BaseFragment implements OnDatePassListener {
  @BindView(R.id.markdown_view) MarkdownView mMarkdownView;
  @BindView(R.id.bottom_sheet) LinearLayout mBottomSheet;
  @BindView(R.id.avatar) SimpleDraweeView mAvatar;
  @BindView(R.id.text_login) TextView mTxtLogin;
  @BindView(R.id.text_repo_name) TextView mTxtRepoName;
  @BindView(R.id.text_watch) TextView mTxtWatch;
  @BindView(R.id.text_start) TextView mTxtStart;
  @BindView(R.id.text_fork) TextView mTxtFork;

  @BindView(R.id.text_watch_count) TextView mTxtWatchCount;
  @BindView(R.id.text_start_count) TextView mTxtStartCount;
  @BindView(R.id.text_fork_count) TextView mTxtForkCount;

  @BindView(R.id.layout_fork) LinearLayout mLayoutFork;
  @BindView(R.id.text_description) TextView mTxtDescription;
  @BindView(R.id.text_create_at) TextView mTxtCreateAt;
  @BindView(R.id.coordinator) CoordinatorLayout mCoordinator;
  private BottomSheetBehavior<LinearLayout> mBehavior;
  private SearchRepos.Items mItems;
  private GitDateService mGitDateService;

  public static Fragment create() {
    return new ReposOverviewFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    ReposActivity reposActivity = (ReposActivity) context;
    reposActivity.setOnDatePassListenerA(this);
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_reop_overview;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mBehavior = BottomSheetBehavior.from(mBottomSheet);
    mGitDateService = GithubService.createGitDateService();

    setupData();

    // Blob blob = mGitDateService.getBlob(mOwner, mRepo, tree.sha).execute().body();

    mMarkdownView.setMarkDownText(
        "[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BaseRecyclerViewAdapterHelper-green.svg?style=true)](https://android-arsenal.com/details/1/3644)\n"
            + "[![](https://jitpack.io/v/CymChad/BaseRecyclerViewAdapterHelper.svg)](https://jitpack.io/#CymChad/BaseRecyclerViewAdapterHelper)  \n"
            + "Powerful and flexible RecyclerAdapter \n"
            + "Please feel free to use this.(Welcome to **Star** and **Fork**)\n"
            + "## Google Play Demo\n"
            + "\n"
            + "[![Get it on Google Play](https://developer.android.com/images/brand/en_generic_rgb_wo_60.png)](https://play.google.com/store/apps/details?id=com.chad.baserecyclerviewadapterhelper)\n"
            + "#Document\n"
            + "##v1.9.8\n"
            + "- [English](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/old_doc)\n"
            + "- [中文](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/old_doc-cn)\n"
            + "\n"
            + "##v2.0.0\n"
            + "- [English](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki)\n"
            + "- [中文](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E9%A6%96%E9%A1%B5)\n"
            + "\n"
            + "#Extension library\n"
            + "[PinnedSectionItemDecoration](https://github.com/oubowu/PinnedSectionItemDecoration)  \n"
            + "[EasyRefreshLayout](https://github.com/anzaizai/EasyRefreshLayout)\n"
            + "\n"
            + "#Thanks\n"
            + "[JoanZapata / base-adapter-helper](https://github.com/JoanZapata/base-adapter-helper)\n"
            + "\n"
            + "#License\n"
            + "```\n"
            + "Copyright 2016 陈宇明\n"
            + "\n"
            + "Licensed under the Apache License, Version 2.0 (the \"License\");\n"
            + "you may not use this file except in compliance with the License.\n"
            + "You may obtain a copy of the License at\n"
            + "\n"
            + "   http://www.apache.org/licenses/LICENSE-2.0\n"
            + "\n"
            + "Unless required by applicable law or agreed to in writing, software\n"
            + "distributed under the License is distributed on an \"AS IS\" BASIS,\n"
            + "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
            + "See the License for the specific language governing permissions and\n"
            + "limitations under the License.\n"
            + "```\n");
  }

  @OnClick({
      R.id.avatar, R.id.text_login, R.id.text_repo_name, R.id.text_watch, R.id.text_watch_count,
      R.id.text_start, R.id.text_start_count, R.id.text_fork, R.id.text_fork_count,
      R.id.text_description, R.id.text_create_at
  }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.avatar:
        break;
      case R.id.text_login:
        break;
      case R.id.text_repo_name:
        break;
      case R.id.text_watch:
        break;
      case R.id.text_watch_count:
        break;
      case R.id.text_start:
        break;
      case R.id.text_start_count:
        break;
      case R.id.text_fork:
        break;
      case R.id.text_fork_count:
        break;
      case R.id.text_description:
        break;
      case R.id.text_create_at:
        break;
    }
  }

  @Override public void onCardTouchListener(Parcelable date) {
    mItems = (SearchRepos.Items) date;
  }

  public void setupData() {
    mAvatar.setImageURI(mItems.owner.avatar_url);
    mTxtLogin.setText(mItems.owner.login);
    mTxtRepoName.setText(mItems.name);

    //mTxtWatchCount.setText(FormatUtils.decimalFormat(mItems.watchers));
    mTxtStartCount.setText(FormatUtils.decimalFormat(mItems.stargazers_count));
    mTxtForkCount.setText(FormatUtils.decimalFormat(mItems.forks_count));

    mTxtDescription.setText(mItems.description);
    DateFormatUtil dateFormat = new DateFormatUtil(getString(R.string.create_at));
    String createAt = dateFormat.formatTime(mItems.created_at);
    mTxtCreateAt.setText(createAt);
  }
}
