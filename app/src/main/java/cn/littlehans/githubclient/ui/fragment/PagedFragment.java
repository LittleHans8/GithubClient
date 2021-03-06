package cn.littlehans.githubclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.utilities.DividerItemDecoration;
import com.smartydroid.android.starter.kit.app.StarterPagedFragment;
import com.smartydroid.android.starter.kit.model.entity.Entity;

/**
 * Created by LittleHans on 2016/11/27.
 */

public abstract class PagedFragment<E extends Entity> extends StarterPagedFragment<E> {
  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getRecyclerView().addItemDecoration(
        new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    getSwipeRefreshLayout().setColorSchemeResources(R.color.refresh_progress_1,
        R.color.refresh_progress_2, R.color.refresh_progress_3);
  }
}
