package littlehans.cn.githubclient.data;

import java.util.List;

import littlehans.cn.githubclient.api.GithubService;
import littlehans.cn.githubclient.model.entity.Search;
import littlehans.cn.githubclient.model.entity.Search.Items;
import littlehans.cn.githubclient.network2.Pagination.Emitter;
import littlehans.cn.githubclient.network2.Paginator;
import littlehans.cn.githubclient.network2.callback.PaginatorCallback;
import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by littlehans on 16/10/10.
 */
public class SearchReposServer extends Paginator<Search>{
    private int mCurrentPage;
    private static int mLastPage;
    private Search mSearch;
    private List<Items> mItems;

    protected SearchReposServer(Emitter<Search> emitter, PaginatorCallback<Search> delegate, int perPage) {
        super(emitter, delegate, perPage);
    }

    public static List<Items> getDataByPage() {
        return null;
    }

    public static int getLastPage() {
        return mLastPage;
    }

    @Override
    protected Call<Search> paginate() {
        return GithubService.createSearchService().repositories("bootstrap",null,null,mCurrentPage++);
    }

    @Override
    protected void processPage(Search dataArray) {
        mSearch = dataArray;
        mItems = dataArray.items;
    }

    @Override
    public void respondHeader(Headers headers) {

    }
}
