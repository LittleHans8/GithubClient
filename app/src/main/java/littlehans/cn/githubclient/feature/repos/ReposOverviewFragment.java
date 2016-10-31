package littlehans.cn.githubclient.feature.repos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import com.mukesh.MarkdownView;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.ui.fragment.BaseFragment;

/**
 * Created by LittleHans on 2016/10/30.
 */

public class ReposOverviewFragment extends BaseFragment {
  @BindView(R.id.markdown_view) MarkdownView mMarkdownView;
  @BindView(R.id.bottom_sheet) LinearLayout mBottomSheet;
  private BottomSheetBehavior<LinearLayout> mBehavior;

  public static Fragment create() {
    return new ReposOverviewFragment();
  }

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_reop_overview;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mBehavior = BottomSheetBehavior.from(mBottomSheet);
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
}
