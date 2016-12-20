package cn.littlehans.githubclient.feature.user;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import cn.littlehans.githubclient.Nav;
import cn.littlehans.githubclient.R;
import cn.littlehans.githubclient.api.GitHubService;
import cn.littlehans.githubclient.api.service.UsersService;
import cn.littlehans.githubclient.model.entity.User;
import cn.littlehans.githubclient.ui.fragment.NetworkFragment;
import cn.littlehans.githubclient.utilities.DateFormatUtil;
import cn.littlehans.githubclient.utilities.TextViewUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by LittleHans on 2016/12/18.
 */

public class UserOverviewFragment extends NetworkFragment<User> {

  @Bind(R.id.avatar) SimpleDraweeView mAvatar;
  @Bind(R.id.text_name) TextView mTextName;
  @Bind(R.id.text_login) TextView mTextLogin;
  @Bind(R.id.text_bio) TextView mTextBio;
  @Bind(R.id.text_follow) TextView mTextFollow;
  @Bind(R.id.text_company) TextView mTextCompany;
  @Bind(R.id.text_location) TextView mTextLocation;
  @Bind(R.id.text_email) TextView mTextEmail;
  @Bind(R.id.text_blog) TextView mTextBlog;
  @Bind(R.id.text_create_at) TextView mTextCreateAt;
  private UsersService mUsersService;


  @Override protected int getFragmentLayout() {
    return R.layout.fragment_user_overview;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mUsersService = GitHubService.createUsersService();
    User user = getArguments().getParcelable(Nav.USER);
    networkQueue().enqueue(mUsersService.getUser(user.login));
  }

  public static Fragment create(Parcelable data) {
    UserOverviewFragment fragment = new UserOverviewFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Nav.USER, data);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void respondSuccess(User user) {
    mAvatar.setImageURI(user.avatar_url);
    mTextLogin.setText(user.login);
    TextViewUtils.setTextNotEmpty(mTextName, user.name);
    TextViewUtils.setTextNotEmpty(mTextBio, user.bio);
    TextViewUtils.setTextNotEmpty(mTextCompany, user.company);
    TextViewUtils.setTextNotEmpty(mTextLocation, user.location);
    TextViewUtils.setTextNotEmpty(mTextEmail, user.email);
    TextViewUtils.setTextNotEmpty(mTextBlog, user.blog);
    mTextCreateAt.setText(new DateFormatUtil("Joined on").formatTime(user.created_at));
  }
}
