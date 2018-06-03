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

public class Server1 {
    private final static int port = 1111;
    private final static int sizeOfPool = 5;
    private static CopyOnWriteArrayList<Pj> collection;
    private static ExecutorService executor = Executors.newFixedThreadPool(sizeOfPool);

    //    public static void main(String... args) {
    public static void go() {
//        String path = "C:\\Users\\chist\\Documents\\itmo\\proga\\Labbbb\\src\\form.xml";
//        String path = "D:\\0лабы\\Программирование(вуз)\\6\\Labbbb\\src\\form.xml";
//        In.getPjeys(path, PjCollection.pjeys);

        try {
            ServerSocket server = new ServerSocket(port);
            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("connected");
                executor.execute(new ThreadServer1(client));
            }
        } catch (IOException e) {

        }

    }
}
