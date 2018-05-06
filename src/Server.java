package Server;

import 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    static int port = 1234;

    public static void main(String...args) throws Throwable{
        ServerSocket ss = new ServerSocket(port);
        while (true){
            Socket s = ss.accept();
            System.err.println("Client accept is ok");
            new Thread(new SocketProcessor(s)).start();
        }
    }

    public static class SocketProcessor implements Runnable{
        private Socket socket;
        private BufferedReader br;
        private OutputStream outputStream;

        private SocketProcessor(Socket socket) throws Throwable {
            this.socket = socket;
            this.outputStream = socket.getOutputStream();
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }



        @Override
        public void run() {
            try {
                String str = readInputStr();
                if (str.startsWith("w")){

                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        private String readInputStr() throws Throwable{
            String command = br.readLine();
            System.out.println(command);
            return command;
        }
    }
}
