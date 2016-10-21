package littlehans.cn.githubclient.feature.repos;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import littlehans.cn.githubclient.R;

/**
 * Created by littlehans on 16/10/21.
 */

public class ReposCodePathAdapter extends BaseQuickAdapter<String> {
    public ReposCodePathAdapter(List<String> data) {
        super(R.layout.item_text_path,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.text_path,s);
    }
}
