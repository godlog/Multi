package lsj.bio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-22 18:41
 * @version:1.0.0
 */
public class Client {
    final static String ADDRESS = "127.0.0.1";
    final static int PORT = 6001;
    public static void main(String[] args){
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            socket = new Socket(ADDRESS,PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);

            //向服务端发送数据
            out.println("接收到客户端的请求");
            String response = in.readLine();
            System.err.println(response);
        }catch (Exception e){

        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                try{
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
