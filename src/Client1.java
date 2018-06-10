import Enums.ColorsEnum;
import Enums.EPj;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client1 {
    private final static int port = 1111;
    private static final String host = "127.0.0.1";

        public static void main(String... args) throws ExcFall {
        Scanner in = new Scanner(System.in);
        CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
        String answer = "";
        String result = "";
        Socket s = null;
        ObjectInputStream reader = null;
        ObjectOutputStream writer = null;

        while (true) {
            try {
                s = new Socket(host, port);
                reader = new ObjectInputStream(s.getInputStream());
                writer = new ObjectOutputStream(s.getOutputStream());
                break;
            } catch (IOException e) {
            }
        }

        try {
            File file = new File(".\\form.xml");
            String path = file.getAbsolutePath();
            In.getPjeys(path, collection);

            while (true) {
                writer.writeObject(collection);
                String command = in.nextLine();
                if (s.isConnected()) {
                    writer.flush();
                    writer.writeObject(command);
                    if (command.startsWith("prl") || command.startsWith("prg") || command.startsWith("prv"))
                        command = command.substring(0, 3);
                    switch (command) {
                        case "pshow":
                            result = "";
                            answer = "";
                            while (!(answer = (String) reader.readObject()).startsWith("end")) {
                                result += "\n" + answer;
                            }
                            System.out.println(result);
                            break;
                        case "pst":
                            while (true) {
                                answer = (String) reader.readObject();
                                System.out.println(answer);
                                if (answer.startsWith("end")) {
                                    break;
                                } else writer.writeObject(in.nextLine());
                            }

                            break;
                        case "psort":
                            System.out.println("sorted");
                            break;
                        case "prl":
                            collection = (CopyOnWriteArrayList<Pj>) reader.readObject();
                            System.out.println("ok");
                            break;
                        case "prg":
                            collection = (CopyOnWriteArrayList<Pj>) reader.readObject();
                            System.out.println("ok");
                            break;
                        case "prv":
                            collection = (CopyOnWriteArrayList<Pj>) reader.readObject();
                            System.out.println("ok");
                            break;
                        case "pin":
                            result = "";
                            answer = "";
                            while (!(answer = (String) reader.readObject()).startsWith("end")) {
                                result += "\n" + answer;
                            }
                            System.out.println("Collection now views like: \n" + result);
                            break;
                        case "psize":
                            System.out.println(collection.size());
                            break;
                        case "pout":
                            System.out.println("ok");
                            break;
                        case "ph":
                            System.out.println((String) reader.readObject());
                            break;

                    }


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
}



