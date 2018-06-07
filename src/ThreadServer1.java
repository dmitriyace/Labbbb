import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadServer1 implements Runnable {
    private Socket client;
    private CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
    private String way;
    ServerWindow GUI;
    String command;


    public ThreadServer1(Socket client, ServerWindow GUI) {
        this.client = client;
        this.GUI = GUI;
    }

    @Override
    public void run() {
        File file = new File(".\\form.xml");
        String path = file.getAbsolutePath();
        In.getPjeys(path, collection);
        try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(client.getInputStream());) {
            if (!client.isClosed()) {
                try {
                    while (true) {
                        command = (String) in.readObject();
                        if (command.startsWith("list")) {
                            collection = GUI.collection;
                            out.writeObject(collection);
                        }
                    }
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
