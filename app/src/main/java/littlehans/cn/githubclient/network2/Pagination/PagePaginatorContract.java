/**
 * Created by YuGang Yang on September 29, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package littlehans.cn.githubclient.network2.Pagination;

public interface PagePaginatorContract<T > extends PaginatorContract<T> {

  /**
   * Determine the current page being paginated.
   *
   * @return curent page
   */
  int currentPage();

}
