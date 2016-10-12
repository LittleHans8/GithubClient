/**
 * Created by YuGang Yang on September 25, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package littlehans.cn.githubclient.network.Pagination;

/**
 * 这个 PaginatorContract 在 network 包下被 Paginator 的类所实现
 *
 */

public interface PaginatorContract<T > {

  /**
   * Get all of the items being paginated.
   *
   * @return collection
   */
  T item();

  /**
   * Determine how many items are being shown per page.
   */
  int perPage();

  /**
   * Determine if there is more items in the data store.
   *
   * @return has more
   */
  boolean hasMorePages();

  /**
   * Determine if the list of items is empty or not.
   */
  boolean isEmpty();

  boolean hasError();

  boolean dataHasLoaded();

  boolean canLoadMore();

  boolean isRefresh();

  boolean isLoading();

  void refresh();

  void loadMore();

  void cancel();

  void clearAll();

  enum LoadStyle {
    REFRESH,
    LOAD_MORE,
  }
}
