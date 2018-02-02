package lsj.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-28 19:17
 * @version:1.0.0
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {
        //循环调用,让服务器一直监听端口
        attachment.assc.accept(attachment, this);
        read(result);
    }

    private void read(AsynchronousSocketChannel asc) {
        //读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        asc.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer resultSize, ByteBuffer attachment) {
                //进行读取之后,充值标示位
                attachment.flip();
                //获得读取的字节数
                String resultData = new String(attachment.array()).trim();
                System.out.println("Server-> 收到字节" + resultSize);
                String response = "服务器响应,收到了客户端发出的数据:" + resultData;
                
                write(asc,response);

            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }

    private void write(AsynchronousSocketChannel asc, String response) {
        try {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.put(response.getBytes());
            buf.flip();
            asc.write(buf).get();
        }catch (Exception e){

        }
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }
}
