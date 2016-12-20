/**
 * Created by YuGang Yang on October 28, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package cn.littlehans.githubclient.network.Pagination;

import retrofit2.Call;

public interface PageEmitter<T > extends Emitter<T> {

  /**
   * @param page 当前页面
   * @param perPage 每页显示多少
   * @return Call
   */
  Call<T> paginate(int page, int perPage);
}