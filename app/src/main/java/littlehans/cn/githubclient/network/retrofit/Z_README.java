package littlehans.cn.githubclient.network.retrofit;

/**
 * Created by LittleHans on 2016/9/11.
 *
 * 这个包下主要通过 RETROFIT 对网络请求进行了一次封装，主要有以下的封装
 *
 * 1.在 DefaultHeaderInterceptor 里对 header 头部的封装，主要在头部存入 token 以便服务端验证，accept 以及设备信息的存储
 * 2. response 的封装。
 * 在 NetworkQueue 使用了 delegate(委托)模式对 GenericCallback 进行封装，这是个回调的接口，将请求的结果（成功或失败）返回出去
 *
 * 对返回的 json 信息 通过 ErrorMode 进行输出，另外回调给自己的接口 GenericCallback(ErrorCallback,NetworkCallback)
 *
 * 3.RetrofitBuilder 的封装，对Retrofit进行初始化工作
 * include baseUrl,accept,OkHttpClient,HttpLoggingInterceptor,Timber
 *
 *
 */
public class Z_README {
}
