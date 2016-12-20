package cn.littlehans.githubclient.feature.owner.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.model.entity.User;
import com.facebook.drawee.view.SimpleDraweeView;
import support.ui.adapters.EasyViewHolder;

import static com.smartydroid.android.starter.kit.utilities.ViewUtils.setGone;

/**
 * Created by littlehans on 16/11/28.
 */

public class FollowerViewHolder extends EasyViewHolder<User> {

    @Bind(R.id.image_avatar) SimpleDraweeView mImageAvatar;
    @Bind(R.id.text_name) TextView mTextName;
    @Bind(R.id.text_login_name) TextView mTextLoginName;
    @Bind(R.id.text_organization) TextView mTextOrganization;
    @Bind(R.id.text_location) TextView mTextLocation;


    public FollowerViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_user);
        ButterKnife.bind(this,itemView);
    }

    @Override public void bindTo(int position, User value) {
        mImageAvatar.setImageURI(value.avatar_url);
        mTextLoginName.setText(value.login);
        setGone(mTextName, true);
        setGone(mTextOrganization,true);
        setGone(mTextLocation,true);
    }
}
