package lsj.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-22 18:41
 * @version:1.0.0
 */
public class Server {
    private static final int PORT = 6001;

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
            Socket socket = server.accept();
            new Thread(new ServerHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            server = null;

        }

    }
}
