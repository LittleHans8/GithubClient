package cn.littlehans.githubclient.model;

import android.content.Context;
import android.util.Base64;
import cn.littlehans.githubclient.model.entity.User;
import com.smartydroid.android.starter.kit.app.StarterKitApp;
import me.alexrs.prefs.lib.Prefs;

/**
 * Created by LittleHans on 2016/12/7.
 */

public class AccountManager {
  public static final String PREFS_KEY_ACCOUNT_STORAGE = "account_storage";
  public static final String PREFS_KEY_ACCOUNT_AUTH_BASIC = "account_basic";
  public static final String PREFS_KEY_ACCOUNT_JSON ="account_info";
  public static final String PREFS_KEY_ACCOUNT_IS_LOGIN_="is_login";

  public boolean isLogin() {
    return prefs().getBoolean(PREFS_KEY_ACCOUNT_IS_LOGIN_,false);
  }

  public static void clearAllData() {
    prefs().removeAll();
  }

  public static void setIsLogin(boolean isLogin){
    prefs().save(PREFS_KEY_ACCOUNT_IS_LOGIN_,isLogin);
  }

  public static boolean getIsLogin() {
    return prefs().getBoolean(PREFS_KEY_ACCOUNT_IS_LOGIN_, false);
  }

  public static void storeAccount(User user){
    prefs().save(PREFS_KEY_ACCOUNT_JSON,user.toJson());
    setIsLogin(true);
  }

  public static void storeBasic(String userName,String password){
    String credentials = userName + ":" + password;
    String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    prefs().save(PREFS_KEY_ACCOUNT_AUTH_BASIC,basic);
  }

  public static String getBasic() {
    return prefs().getString(PREFS_KEY_ACCOUNT_AUTH_BASIC, null);
  }


  public static User getAccount(){
    String accountJson = prefs().getString(PREFS_KEY_ACCOUNT_JSON,null);
    return (User) ((StarterKitApp)StarterKitApp.getInstance()).accountFromJson(accountJson);
  }

  private static Prefs prefs(){
    return Prefs.with(StarterKitApp.appContext(), PREFS_KEY_ACCOUNT_STORAGE,Context.MODE_PRIVATE);
  }

}
