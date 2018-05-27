import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client1 {
    private final static int port = 1111;

    public static void main(String... args) throws ExcFall {
        Scanner in = new Scanner(System.in);
        CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
        String answer;
        while (true) {
            try {
                String command = in.nextLine();
                Socket s = new Socket("localhost", port);
                if (s.isConnected()) {
                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    if (command.equalsIgnoreCase("qq")) System.exit(0);
                    oos.writeObject(command);
                    Object o = ois.readObject();
                    if (o instanceof String) {
                        answer = o.toString();
                        String switchS = startsWithCheck(answer);
                        switch (switchS) {
                            case "pj1":
                                break;
                            case "pj2":
                                break;
                            case "pj3":
                                break;
                        }

                    }
                    else{
                        collection = (CopyOnWriteArrayList<Pj>) o;
                        System.out.println(collection.size() > 0);
                    }
//                    if (command.startsWith("show")) {
//                        PjCollection.show(collection);
//                    } else {
//                        if (!command.startsWith("start"))
//                            PjCollection.commands(command);
//                    }


                } else throw new ConnectException();
            } catch (UnknownHostException e) {
                System.err.println("Host is incorrect");
            } catch (IOException e) {
                System.err.println("IO exception has come to us");
            } catch (ClassNotFoundException e) {
                System.err.println("R u sure 'bout da class?");
            }
        }
    }

    static String startsWithCheck(String s) {
        if (s.startsWith("pj1")) return "pj1";
        if (s.startsWith("pj2")) return "pj2";
        if (s.startsWith("pj3")) return "pj3";
        if (s.startsWith("pj4")) return "pj4";
        return "hm";
    }

}
