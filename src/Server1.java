import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Server1 {
    private final static int port = 1111;
    private final static int sizeOfPool = 5;
    static Handler handler;
    static ServerWindow serverWindow;
    private static ExecutorService executor = Executors.newFixedThreadPool(sizeOfPool);
    static Thread auth;

    public static void main(String[] args) {
        go();
    }

    public static void go() {
        try {
            serverWindow = new ServerWindow();
            ServerSocket server = new ServerSocket(port);
            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("connected");
                executor.execute(new ThreadServer1(client, serverWindow));
            }
        } catch (IOException e) {
            System.exit(0);
        }
    }
}

