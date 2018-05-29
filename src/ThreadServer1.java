import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadServer1 implements Runnable {
    private Socket client;
    private CopyOnWriteArrayList<Pj> collection;
    private String way;
    String command;
    String answer;
//    PjCollection collectionFromClient;

    public ThreadServer1(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(client.getInputStream());) {
            if (!client.isClosed()) {
                try {

//                    while (!command.equals("end") || !command.equals("q")) {
                    while (true) {
                        collection = (CopyOnWriteArrayList<Pj>) in.readObject();
                        command = (String) in.readObject();
                        if (command.startsWith("p"))
                            CommandHandling.treat(command.substring(1), collection, out, in);
                    }
                } catch (ClassNotFoundException e) {
                    out.writeObject("File handle mistake!!!");
                } catch (IllegalArgumentException e) {
                    out.writeObject("Command format trouble");
                } catch (SocketException se) {
                    System.err.println("client disconnected");
                }
                finally {
                    out.flush();
                    in.close();
                    out.close();
                    client.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
