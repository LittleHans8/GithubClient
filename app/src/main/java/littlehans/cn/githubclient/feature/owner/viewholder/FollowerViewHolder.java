package littlehans.cn.githubclient.feature.owner.viewholder;

import android.content.Context;
import android.support.v7.widget.ViewUtils;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.Comment;
import littlehans.cn.githubclient.utilities.TextViewUtils;
import support.ui.adapters.EasyViewHolder;

import static com.smartydroid.android.starter.kit.utilities.ViewUtils.*;

/**
 * Created by littlehans on 16/11/28.
 */

public class FollowerViewHolder extends EasyViewHolder<Comment.User>{

    @Bind(R.id.image_avatar) SimpleDraweeView mImageAvatar;
    @Bind(R.id.text_name) TextView mTextName;
    @Bind(R.id.text_login_name) TextView mTextLoginName;
    @Bind(R.id.text_organization) TextView mTextOrganization;
    @Bind(R.id.text_location) TextView mTextLocation;


    public FollowerViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_user);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void bindTo(int position, Comment.User value) {
        mImageAvatar.setImageURI(value.avatar_url);
        mTextLoginName.setText(value.login);
        setGone(mTextName, true);
        setGone(mTextOrganization,true);
        setGone(mTextLocation,true);
    }
}
