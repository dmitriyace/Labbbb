
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    static int port = 1235;

    public static void main(String... args) throws Throwable {
        ServerSocket ss = new ServerSocket(port);

//        DataOutputStream out=new DataOutputStream(client.getOutputStream());
//        System.out.println("data output");
//
//        DataInputStream in =new DataInputStream( client.getInputS tream());
//        System.out.println("in created");new Thread(new SocketProcessor(client)).start();
        while (true) {
//            System.out.println("start reading from client");
//            System.out.println("FCK YOU");
            Socket client = ss.accept();
            System.out.println("Connected");
            new Thread(new SocketProcessor(client)).start();

        }

    }

    public static class SocketProcessor implements Runnable {
        private Socket socket;
        private BufferedReader br;
        private DataOutputStream out;
        private DataInputStream in;

        private SocketProcessor(Socket client) throws Throwable {
            this.socket = client;
            this.out = new DataOutputStream(client.getOutputStream());
            this.in = new DataInputStream(client.getInputStream());
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("hey from constructor");

        }


        @Override
        public void run() {
            String entry = null;
            try {
                entry = in.readUTF();
                System.out.println("read from client message: " + entry);
                out.writeUTF("Shihsiihihshissih: " + entry);
                out.flush();
            } catch (IOException e) {
                System.err.println("input output is fckdup");
            }


        }

        private void sendStr() throws Throwable {
            System.out.println("hey there");
            out.writeUTF("gglity is coming");

        }
    }
}
