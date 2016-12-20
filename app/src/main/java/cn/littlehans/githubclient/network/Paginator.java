/**
 * Created by YuGang Yang on October 28, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package cn.littlehans.githubclient.network;

import cn.littlehans.githubclient.model.ErrorModel;
import cn.littlehans.githubclient.network.Pagination.Emitter;
import cn.littlehans.githubclient.network.Pagination.PaginatorContract;
import cn.littlehans.githubclient.network.callback.GenericCallback;
import cn.littlehans.githubclient.network.callback.PaginatorCallback;
import cn.littlehans.githubclient.network.retrofit2.NetworkQueue;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;

import static cn.littlehans.githubclient.utilities.Preconditions.checkNotNull;

// 抽象类
public abstract class Paginator<T >
    implements PaginatorContract<T>, // 这个接口定义了分页时的基本操作以及网络操作的标准
    GenericCallback<T> { // 这个接口实现了 ErrorCallback 和 NetworkCallback 两个接口，实际上这两个接口是作为回调的接口来使用

  static final int DEFAULT_PER_PAGE = 30;
  static final int DEFAULT_FIRST_PAGE = 1;

  int mPerPage;


  boolean mHasMore;
  boolean mIsLoading = false;
  boolean mDataHasLoaded = false;
  boolean mHasError = false;
  List<T> item = new ArrayList<>();

  protected Emitter<T> mEmitter;
  private PaginatorCallback<T> delegate; // PaginatorCallback 目前实际上就是 GenericCallback 接口，GenericCallback 的 delegate 模式
  private LoadStyle mLoadStyle = LoadStyle.REFRESH;

  private NetworkQueue<T> networkQueue;

  protected Paginator(Emitter<T> emitter, PaginatorCallback<T> delegate, int perPage) {
    checkNotNull(emitter, "emitter == null");
    checkNotNull(delegate, "delegate == null");

    mEmitter = emitter;
    this.delegate = delegate;
    mPerPage = perPage;

    networkQueue = new NetworkQueue<>(this);
  }

  protected abstract Call<T> paginate();
  protected abstract void processPage(T dataArray);

  @Override public T item() {
    return null ;// k-v 的 values
  }

  @Override public int perPage() {
    return mPerPage;
  }

  @Override public boolean hasMorePages() {
    return mHasMore;
  }

  @Override public boolean isEmpty() {
    return item.isEmpty();
  }

  @Override public boolean hasError() {
    return mHasError;
  }

  @Override public boolean dataHasLoaded() {
    return mDataHasLoaded;
  }

  @Override public boolean canLoadMore() {
    return !isLoading() && hasMorePages() && !hasError();
  }

  @Override public boolean isRefresh() {
    return mLoadStyle == LoadStyle.REFRESH;
  }

  @Override public boolean isLoading() {
    return mIsLoading;
  }

  @Override public void clearAll() {
    item.clear();
  }

  @Override public void cancel() {
    mIsLoading = false;
    networkQueue.cancel();
  }

  @Override public void refresh() {
    if (mIsLoading) return; // 如果正在加载数据就不用重新刷新了，等待数据加载完毕
    mLoadStyle = LoadStyle.REFRESH;
    mEmitter.beforeRefresh(); // beforeRefresh 应该理解为在刷新
    requestData();
  }

  private void requestData() {
    final Call<T> call = paginate();
    networkQueue.enqueue(call);
  }

  @Override public void loadMore() {
    if (mIsLoading) return;
    mLoadStyle = LoadStyle.LOAD_MORE; //  判断要刷新还是加载更多是通过枚举常量 LoadStyle，这个常量是定义在 PaginatorContract 的接口里面
    mEmitter.beforeLoadMore(); // 在加载更多
    requestData();
  }

  @Override public void respondSuccess(T data) {
    if (!isNull(data)) {
      mHasError = false;
      processPage(data);
      handDataArray(data);

      delegate.respondSuccess(data);
    } else {
      mHasMore = false;
      mHasError = false;
      delegate.errorNotFound(new ErrorModel(404, "暂时没有数据", "点进屏幕刷新数据"));
    }
  }

  @Override public void startRequest() {
    mIsLoading = true; // startRequest 是一个网络请求的开始，定义在接口 NetworkQueue 中。
    delegate.startRequest();
  }

  @Override public void endRequest() {
    mIsLoading = false;
    mDataHasLoaded = true; // 标记数据已经加载完毕
    delegate.endRequest();
  }

  @Override public void errorNotFound(ErrorModel errorModel) {
    delegate.errorNotFound(errorModel);
  }

  @Override public void errorUnProcessable(ErrorModel errorModel) {
    setupError();
    delegate.errorUnProcessable(errorModel);
  }

  @Override public void errorUnauthorized(ErrorModel errorModel) {
    setupError();
    delegate.errorUnauthorized(errorModel);
  }

  @Override public void errorForbidden(ErrorModel errorModel) {
    setupError();
    delegate.errorForbidden(errorModel);
  }

  @Override public void eNetUnReach(Throwable t, ErrorModel errorModel) {
    setupError();
    delegate.eNetUnReach(t, errorModel);
  }

  @Override public void errorSocketTimeout(Throwable t, ErrorModel errorModel) {
    setupError();
    delegate.errorSocketTimeout(t, errorModel);
  }

  @Override public void errorUnknownHost(UnknownHostException e, ErrorModel errorModel) {
    setupError();
    delegate.errorUnknownHost(e, errorModel);
  }

  @Override public void error(ErrorModel errorModel) {
    setupError();
    delegate.error(errorModel);
  }

  private void setupError() {
    mHasError = true;
    mHasMore = false;
  }

  @Override public void respondWithError(Throwable t) {
    delegate.respondWithError(t);
  }

  private boolean isNull(T dataArray) {
    return dataArray == null;
  }

  /**
   * 将访问网络请求的数据存储到一个 map->mResources中，
   * @param dataArray
   */

  private void handDataArray(T dataArray) {
    if (isRefresh()) {
      item.clear();
    }


  }

}
