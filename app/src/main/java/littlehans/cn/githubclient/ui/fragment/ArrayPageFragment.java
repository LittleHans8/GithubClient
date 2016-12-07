package littlehans.cn.githubclient.ui.fragment;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

/**
 * Created by LittleHans on 2016/11/21.
 */

public abstract class ArrayPageFragment<T> extends PageFragment<T> {

  abstract BaseMultiItemQuickAdapter getMultiItemAdapter();

  @Override public void respondSuccess(T data) {
  }
}
