package littlehans.cn.githubclient.network2.callback;

/**
 * Created by LittleHans on 2016/9/12.
 *
 * 总结：对外开放的类主要是 MessageCallback ，调用它可以实现弹出网络操作部分的提醒信息。
 *
 *
 *
 * 网络操作部分回掉接口，GenericCallback 继承了 ErrorCallback 和 NetworkCallback 的接口，这是一个很好的解耦操作
 *
 * SimpleCallback 本身是一个类，它实现了 GenericCallback 类,所以需要重写 GenericCallback 里所有的方法
 *
 * MessageCallback，这个包下的主要实现类，继承自 SimpleCallback
 *
 * 这个类的主要作用是通过传入的 activity 来实现 网络部分相关的提醒信息(Snackbar)
 *
 */
public class Z_README {
}
