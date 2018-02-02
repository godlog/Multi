package lsj.bio;

import java.io.*;

import java.net.Socket;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-22 18:41
 * @version:1.0.0
 */
public class ServerHandler implements Runnable {
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String body = null;
            while (true) {
                body = in.readLine();
                if (body == null) break;
                System.err.println("server:" + body);
                out.println("服务器回送响应数据.");
            }
        } catch (Exception e) {

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {

                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket = null;
        }

    }
}
