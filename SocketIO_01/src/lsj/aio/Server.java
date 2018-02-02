package lsj.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-28 19:16
 * @version:1.0.0
 */
public class Server {
    private ExecutorService executorService;
    private AsynchronousChannelGroup channelGroup;
    public AsynchronousServerSocketChannel assc;

    public Server(int port) {
        try {
            executorService = Executors.newCachedThreadPool();
            //创建线程组
            channelGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            //创建服务器通道
            assc = AsynchronousServerSocketChannel.open(channelGroup);
            //绑定端口
            assc.bind(new InetSocketAddress(port));
            System.out.println("server port: " + port);
            //监听请求数据
            assc.accept(this, new ServerCompletionHandler());

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        Server server = new Server(9876);
    }
}
