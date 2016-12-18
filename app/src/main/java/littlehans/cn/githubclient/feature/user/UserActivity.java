package littlehans.cn.githubclient.feature.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.facebook.drawee.view.SimpleDraweeView;
import littlehans.cn.githubclient.Nav;
import littlehans.cn.githubclient.R;
import littlehans.cn.githubclient.model.entity.User;
import littlehans.cn.githubclient.ui.activity.BaseActivity;

/**
 * Created by LittleHans on 2016/12/19.
 */

public class UserActivity extends BaseActivity {

  @Bind(R.id.small_avatar) SimpleDraweeView mSmallAvatar;
  @Bind(R.id.text_small_name) TextView mTextSmallName;
  @Bind(R.id.fragment_container) FrameLayout mFragmentContainer;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);
    User user = getIntent().getExtras().getParcelable(Nav.USER);
    mSmallAvatar.setImageURI(user.avatar_url);
    mTextSmallName.setText(user.login);
    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, UserFragment.create(user))
        .commit();
  }
}
