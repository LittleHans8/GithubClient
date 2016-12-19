package littlehans.cn.githubclient;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

/**
 * Created by LittleHans on 2016/12/19.
 */

public class GitHubWelcomeActivity extends WelcomeActivity {

  @Override protected WelcomeConfiguration configuration() {
    return new WelcomeConfiguration.Builder(this).defaultBackgroundColor(R.color.blue_400)
        .page(new BasicPage(R.drawable.home_hero_sm, getString(R.string.welcome_title),
            getString(R.string.welcome_content)))
        .page(new BasicPage(R.drawable.home_ill_build, getString(R.string.welcome_build_title),
            getString(R.string.welcome_build_content)))
        .page(new BasicPage(R.drawable.home_ill_work, getString(R.string.welcome_work_title),
            getString(R.string.welcome_work_content)))
        .page(
            new BasicPage(R.drawable.home_ill_projects, getString(R.string.welcome_projects_title),
                getString(R.string.welcome_projects_content)))
        .page(
            new BasicPage(R.drawable.home_ill_platform, getString(R.string.welcome_platform_title),
                getString(R.string.welcome_platform_content)))
        .swipeToDismiss(true)
        .build();
  }
}
