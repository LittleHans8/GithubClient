package littlehans.cn.githubclient.network.retrofit;

import littlehans.cn.githubclient.Profile;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by LittleHans on 2016/9/27.
 */
public class RetrofitBuilder {
  private static RetrofitBuilder ourInstance = new RetrofitBuilder();
  private static Retrofit mRetrofit;

  public static RetrofitBuilder get() {
    return ourInstance;
  }

  private RetrofitBuilder() {
    mRetrofit = new Retrofit.Builder()
        .baseUrl(Profile.API_ENDPOINT)
        .addConverterFactory(JacksonConverterFactory.create())
        .build();
  }

  public Retrofit retrofit() {
    return mRetrofit;
  }
}
