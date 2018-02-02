package lsj.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-24 17:02
 * @version:1.0.0
 */
public class Server implements Runnable {

    //1 多路复用器
    private Selector selector;
    //2 建立缓冲区
    private ByteBuffer readBuf = ByteBuffer.allocate(1024);
    //3
    private ByteBuffer writeBuf = ByteBuffer.allocate(1024);

    public Server (int port){
        try{
            //1 打开多路复用器
            this.selector = Selector.open();
            //2 打开服务器通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //3 设置服务器通道为非阻塞模式
            ssc.configureBlocking(false);
            //4 绑定地址
            ssc.bind(new InetSocketAddress(port));
            //5 把服务器通道注册到多路复用器,并且监听监听阻塞事件
            ssc.register(this.selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server start,port:" + port);

        }catch (IOException e){

        }
    }

    @Override
    public void run() {
        while (true){
            try {
                //1 必须要让多路复用器开始监听
                this.selector.select();
                //2 返回多路复用器以及选择
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                //3 进行遍历
                while (keys.hasNext()){
                    //4 获取一个选择的元素
                    SelectionKey key = keys.next();
                    //5 直接从容器中移除
                    keys.remove();
                    //6 判断是否有效
                    if(key.isValid()){
                        //如果是阻塞状态
                        if(key.isAcceptable()){
                            this.accept(key);
                        }
                        if(key.isReadable()){
                            this.read(key);
                        }
                        //如果是可读状态
                    }
                }
            }catch (Exception e){

            }
        }
    }

    private void read(SelectionKey key) {
        try{
            //1 清空缓冲区旧的数据
            this.readBuf.clear();
            //2 获取之前注册的socket通道对象
            SocketChannel sc = (SocketChannel) key.channel();
            //3 读取数据
            int count = sc.read(this.readBuf);
            //4 如果没有数据
            if(count == -1){
                key.channel().close();
                key.cancel();
                return;
            }
            //5 有数据则进行读取
            this.readBuf.flip();
            //6 更具缓冲区创建一个相应大小的byte数组
            byte[] bytes = new byte[this.readBuf.remaining()];
            //7 接受缓冲区数据
            this.readBuf.get(bytes);
            //8 打印结果
            String body = new String(bytes).trim();
            System.out.println("server:" + body);
            // 可以给客户返回数据.
        }catch(Exception e){

        }
    }

    private void accept(SelectionKey key) {
        try{
            //1 获取服务通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            //2 执行阻塞方法
            SocketChannel sc = ssc.accept();
            //3 设置阻塞模式
            sc.configureBlocking(false);
            //4 注册到多路复用器上,并且设置读取标示
            sc.register(this.selector, SelectionKey.OP_READ);
        }catch (Exception e){

        }
    }
    public static void main(String[] args){
        new Thread(new Server(8379)).start();
    }
}
