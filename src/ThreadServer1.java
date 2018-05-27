import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadServer1 implements Runnable {
    private Socket client;
    private CopyOnWriteArrayList<Pj> collection;
    private String way;
    String command;
    String answer;

    public ThreadServer1(Socket client, CopyOnWriteArrayList<Pj> collection) {
        this.client = client;
        this.collection = collection;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(client.getInputStream());) {
            if (!client.isClosed()) {
                try {
                    command = (String) in.readObject();
//                    while (!command.equals("end") || !command.equals("q")) {
                    while (true) {

                        if (command.startsWith("p"))
                            PjCollection.commands(command.substring(1), collection, out, in);
//                        CommandHandling.treat(answer);
                    }
//                    collection = PjCollection.pjeys;

//                    out.writeObject(PjCollection.pjeysSrt(collection));

                } catch (ClassNotFoundException e) {
                    out.writeObject("File handle mistake!!!");
                } catch (IllegalArgumentException e) {
                    out.writeObject("Command format trouble");
                } catch (SocketException se) {
                    System.err.println("client disconnected");
                } finally {
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
