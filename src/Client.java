
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Scanner;

public class Client {
    private static String end_messages = "It's all.";
    static PjCollection pjCollection = null;
    static boolean end = false;
//    static Room_list rooms = null;
//    static Rocket rocket_with_passageres = null;

    public static void main(String[] args) throws IOException {

        String hostName = "localhost";
        int portNumber = 1235;
        Scanner input = new Scanner(System.in);
        try {

                Socket socket = new Socket(hostName, portNumber);



            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());



            String userInput = "kk";ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (!end) {
                userInput = sendMessage(input, socket);
                listenAnswer(inputStream);
                work_with_commands(userInput);
            }

            while (!socket.isOutputShutdown()) {
                if (br.ready()) {
                    System.out.println("starting writing");
                    Thread.sleep(300);
                    String clientCommand = br.readLine();
                    outputStream.writeUTF(clientCommand);
                    outputStream.flush();
                    System.out.println("We sent message to server:\" " + clientCommand + ".\"");
                    Thread.sleep(300);

                    if (clientCommand.equalsIgnoreCase("qq")) {
                        System.out.println("client going down");
                        Thread.sleep(400);
                        if (inputStream.read() > -1) {
                            System.out.println("reading last server words");
                            String answer = inputStream.readUTF();
                            System.out.println(answer);

                        }
                        break;

                    }
                    System.out.println("waiting from server answer...");
                    Thread.sleep(200);
                    if ((inputStream.read() > -1)) {
                        System.out.println("reading answer");
                        String answer = inputStream.readUTF();
                        System.out.println(answer);
                    }

                }


            }
            System.out.println("Connectiom from client is closed");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (Exception e) {
//            System.err.println("Couldn't get I/O for the connection to " +
//                    hostName);
            System.err.println("Server is currently unreachable");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {

                System.out.println("interrupted");
            }

        }
    }


    private static boolean verification_client(Socket socket) throws IOException {
//        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//        out.println("ok");

        byte b[] = {1};
        SocketAddress adress = new InetSocketAddress("127.0.0.1", 1235);
        SocketChannel s = SocketChannel.open(adress);
        ByteBuffer bb = ByteBuffer.wrap(b);
        socket.getOutputStream();
//        bb.flip();
//        s.write((bb));
//        bb.clear();
//        s.read(bb);
//        s.close();
        return true;

    }

    private static String sendMessage(Scanner input, Socket echoSocket) throws Exception {
        String inputline;
        System.out.println("daaa");
        ObjectOutputStream OutObject = new ObjectOutputStream(echoSocket.getOutputStream());
        inputline = input.nextLine();
        System.out.println(inputline);
        OutObject.writeObject(inputline);
        OutObject.flush();
        return inputline;
    }

    private static void listenAnswer(ObjectInputStream InObject) throws IOException {
        Object line;
        pjCollection = null;
        try {
            do {

                line = InObject.readObject();

                if (line.getClass() == String.class) {
                    System.out.println((String) line);
                } else if (line.getClass() == pjCollection.getClass()) {
                    pjCollection = (PjCollection) line;
                    System.out.println("pijamas taken");
                }

            } while ((line.getClass() != String.class) || (!((String) line).equals("It's all.")));
        } catch (ClassNotFoundException e) {
            System.out.println("Некорректный объект");
        }

    }

    private static void work_with_commands(String userInput) throws ExcFall {
        //st=start
        if (userInput.equals("st")) {
            if (pjCollection != null) {
                Scenary scenary = new Scenary();
//                scenary.start();
            } else {
                System.out.println("Пижамы не получены");
            }
        } else if (userInput.equals("q")) {
            end = true;
        }

    }

    private static boolean get_success_answer() throws IOException {
        byte b[] = {1};
        SocketAddress a =
                new InetSocketAddress("127.0.0.1", 8081);

        SocketChannel s =
                SocketChannel.open(a);
        ByteBuffer f = ByteBuffer.wrap(b);

        f.flip();
        s.write(f);
        f.clear();
        s.read(f);
        s.close();
        return true;
    }
}

