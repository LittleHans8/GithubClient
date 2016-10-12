package littlehans.cn.githubclient.network;

import java.net.UnknownHostException;
import littlehans.cn.githubclient.model.ErrorModel;
import littlehans.cn.githubclient.network.Pagination.Emitter;
import littlehans.cn.githubclient.network.Pagination.PaginatorContract;
import littlehans.cn.githubclient.network.callback.GenericCallback;
import littlehans.cn.githubclient.network.callback.PaginatorCallback;
import littlehans.cn.githubclient.network.retrofit2.NetworkQueue;
import retrofit2.Call;

import static littlehans.cn.githubclient.utilities.Preconditions.checkNotNull;

/**
 * Created by LittleHans on 2016/10/9.
 */

public abstract class Paginator_<T> implements PaginatorContract<T>, GenericCallback<T> {

  static final int DEFAULT_PER_PAGE = 30;
  static final int DEFAULT_FIRST_PAGE = 1;

  int mPerPage;
  boolean mHasMore;
  boolean mIsLoading = false;
  boolean mDataHasLoaded = false;
  boolean mHasError = false;

  protected Emitter<T> mEmitter;
  private PaginatorCallback<T> mDelegate;
  private LoadStyle mLoadStyle = LoadStyle.REFRESH;

  private NetworkQueue<T> mNetworkQueue;

  // 这三个接口都是要回调出去的

  protected Paginator_(Emitter<T> emitter, PaginatorCallback<T> mDelegate, int perPage) {
    checkNotNull(emitter, "emitter == null");
    checkNotNull(mDelegate, "mDelegate == null");

    mEmitter = emitter;
    this.mDelegate = mDelegate;
    mPerPage = perPage; // 每页要请求多少个数据

    mNetworkQueue = new NetworkQueue<>(this);

  }

  protected abstract Call<T> paginate(); // Call 应该从外面传入的

  protected abstract void processPage(T data); // 请求数据成功后要怎么处理数据

  @Override public int perPage() {
    return mPerPage;
  }

  @Override public boolean hasMorePages() {
    return mHasMore;
  }

  @Override public boolean isRefresh() {
    return mLoadStyle == LoadStyle.REFRESH;
  }

  @Override public boolean isLoading() {
    return mIsLoading;
  }

  @Override public void cancel() {
    mIsLoading = false;
    mNetworkQueue.cancel();
  }

  @Override public void refresh() {
    if (mIsLoading) {
      return;
    }
    mLoadStyle = LoadStyle.REFRESH;
    mEmitter.beforeRefresh();
    requestData();
  }

  @Override public void loadMore() {
    if (mIsLoading) {
      return;
    }
    mLoadStyle = LoadStyle.LOAD_MORE;
    mEmitter.beforeLoadMore();
    requestData();
  }

  private void requestData() {
    Call<T> call = paginate();
    mNetworkQueue.enqueue(call);
  }

  @Override public void respondSuccess(T data) {
    if (!isNull(data)) {
      mHasError = false;
      processPage(data);
      mDelegate.respondSuccess(data);
    } else {
      mHasMore = false;
      mHasError = false;
      mDelegate.errorNotFound(new ErrorModel(404, "暂时没有数据"));
    }
  }

  @Override public void startRequest() {
    mIsLoading = true;
    mDelegate.startRequest();
  }

  private boolean isNull(T dataArray) {
    return dataArray == null;
  }

  @Override public void respondWithError(Throwable t) {
    mDelegate.respondWithError(t);
  }

  private void setupError() {
    mHasError = true;
    mHasMore = false;
  }

  @Override public void endRequest() {
    mIsLoading = false;
    mDataHasLoaded = true;
    mDelegate.endRequest();
  }

  @Override public void errorNotFound(ErrorModel errorModel) {
    mDelegate.errorNotFound(errorModel);
  }

  @Override public void errorUnProcessable(ErrorModel errorModel) {
    setupError();
    mDelegate.errorUnProcessable(errorModel);
  }

  @Override public void errorUnauthorized(ErrorModel errorModel) {
    setupError();
    mDelegate.errorUnauthorized(errorModel);
  }

  @Override public void errorForbidden(ErrorModel errorModel) {
    setupError();
    mDelegate.errorForbidden(errorModel);
  }

  @Override public void eNetUnReach(Throwable t, ErrorModel errorModel) {
    setupError();
    mDelegate.eNetUnReach(t, errorModel);
  }

  @Override public void errorSocketTimeout(Throwable t, ErrorModel errorModel) {
    setupError();
    mDelegate.errorSocketTimeout(t, errorModel);
  }

  @Override public void errorUnknownHost(UnknownHostException e, ErrorModel errorModel) {
    setupError();
    mDelegate.errorUnknownHost(e, errorModel);
  }

  @Override public void error(ErrorModel errorModel) {
    setupError();
    mDelegate.error(errorModel);
  }
}
