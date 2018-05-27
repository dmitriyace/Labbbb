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
//        PjCollection myCollectionClass = new PjCollection();
        String answer = "";
        String result = "";
        Socket s = null;
        ObjectInputStream reader = null;
        ObjectOutputStream writer = null;

        while (true) {
            try {
                s = new Socket("localhost", port);
                reader = new ObjectInputStream(s.getInputStream());
                writer = new ObjectOutputStream(s.getOutputStream());
                break;
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }

        try {
            String path = "D:\\0лабы\\Программирование(вуз)\\6\\Labbbb\\src\\form.xml";
            In.getPjeys(path, collection);
            writer.writeObject(collection);
            while (true) {
                String command = in.nextLine();
                writer.flush();

                if (s.isConnected()) {
                    writer.writeObject(command);
                    switch (command) {
                        case "pshow":

                            while (!(answer = (String) reader.readObject()).startsWith("end")) {
                                result += "\n" + answer;
                            }
                            System.out.println(result);
                            break;
                        case "ps":
//                            writer.writeObject(command);
                            while (true) {
                                answer = (String) reader.readObject();
                                System.out.println(answer);
                                if (answer.startsWith("end")) {
                                    break;
                                } else writer.writeObject(in.nextLine());
                            }

                            break;
                        case "psort":
                            System.out.println((String) reader.readObject());
                            break;
                        case "prl":
                            break;
                        case "prg":
                            break;
                        case "pin":
                            while (!(answer = (String) reader.readObject()).startsWith("end")) {
                                result += "\n" + answer;
                            }
                            System.out.println("Collection now views like: \n"+result);
                            break;
//                        case "pq":
//                            break;
                        case "psize":
                            break;
                        case "pout":
                            answer = (String) reader.readObject();
                            System.out.println(answer);
                            break;
                        case "phelp":
                            System.out.println((String) reader.readObject());
                            break;

                    }
//                    writer.writeObject(command);
//                    Object o = reader.readObject();
//                    if (o instanceof String)
//                        answer = o.toString();
//                    System.out.println(answer);
//                        String switchS = startsWithCheck(answer);
//                        switch (switchS) {
//                            case "pj1":
//                                break;
//                            case "pj2":
//                                break;
//                            case "pj3":
//                                break;
//                        }


//                    else{
//                        collection = (CopyOnWriteArrayList<Pj>) o;
//                        System.out.println(collection.size() > 0);
//                    }
//                    if (command.startsWith("show")) {
//                        PjCollection.show(collection);
//                    } else {
//                        if (!command.startsWith("start"))
//                            PjCollection.commands(command);
//                    }


                } else throw new ConnectException();
            }
        } catch (UnknownHostException e) {
            System.err.println("Host is incorrect");
        } catch (IOException e) {
            System.err.println("IO exception has come to us");
        } catch (ClassNotFoundException e) {
            System.err.println("R u sure 'bout da class?");
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
