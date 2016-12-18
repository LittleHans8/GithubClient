package littlehans.cn.githubclient;

import android.content.Context;
import android.content.Intent;
import littlehans.cn.githubclient.feature.search.SearchActivity;
import littlehans.cn.githubclient.ui.MainActivity;
import littlehans.cn.githubclient.ui.activity.LoginActivity;

/**
 * Created by littlehans on 16/10/14.
 */

public class Nav {

  public static final String OWNER = "owner"; // ps:twbs
  public static final String REPO = "repo"; // ps:bootstrap
  public static final String USER = "user";

  public static final String REPO_ITEM = "REPO_ITEM"; //  SearchRepos.Item
  public static final String ISSUE = "ISSUE"; //  Issue

  public static void startSearchActivity(Context context) {
    Intent intent = new Intent(context, SearchActivity.class);
    context.startActivity(intent);
  }

  public static void startMainActivity(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    context.startActivity(intent);
  }

  public static void startLoginActivity(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    context.startActivity(intent);
  }
}
