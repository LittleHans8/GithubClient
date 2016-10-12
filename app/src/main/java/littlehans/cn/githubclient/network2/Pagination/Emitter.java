/**
 * Created by YuGang Yang on September 29, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package littlehans.cn.githubclient.network2.Pagination;

public interface Emitter<T> {

  void beforeRefresh(); // 正在刷新

  void beforeLoadMore(); // 正在加载

}