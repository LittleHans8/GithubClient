package littlehans.cn.githubclient.network;

/**
 * Created by LittleHans on 2016/9/12.
 *
 * 先来解析下 这个 Paginator 抽象类，它是这个包下另外两个接口的父类
 *
 * 它实现了两个接口: PaginatorContracts，这个接口定义了网络操作的基本 function，另外一个是 network callback GenericCallback(NetworkCallback + ErrorCallback)
 *
 * Paginator 可以说是一个 delegate design pattern ，它有一个 'PaginatorCallback' 的字段，然后它实际上就是一个 GenericCallback
 */
public class Z_README {
}
