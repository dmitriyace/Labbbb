import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    String command;
    ServerWindow s;

    public ThreadServer1(Socket client, ServerWindow s) {
        this.client = client;
        this.s = s;
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
                            collection = s.getCollection();
                            out.flush();
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
//                    out.flush();
//                    in.close();
//                    out.close();
                    client.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class ServerWindow extends JFrame {


    private static final int width = 1000;
    private static final int height = 600;
    ServerWindow thisOne = this;
    int j;
    JButton save = new JButton("save collection");
    JButton addBtn = new JButton("add pyjama");
    JButton delete = new JButton("delete pyjama");
    JButton load = new JButton("load collection");
    JTree collectionTree;
    DefaultMutableTreeNode leaf;
    DefaultTreeModel model;
    String saveProperties[];
    File file;
    String path;
    CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();

    ServerWindow() {
        super("Menu");
        setBounds(100, 10, width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel(new GridLayout(7, 1));
        JPanel panel2 = new JPanel(new FlowLayout());

        j = 0;
        saveProperties = new String[collection.size()];
        for (Pj pj : collection) {
            saveProperties[j] = pj.name + "(id" + pj.id + ")";
            j++;
        }

        leaf = new DefaultMutableTreeNode();
        for (int i = 0; i < saveProperties.length; i++)
            leaf.add(new DefaultMutableTreeNode(saveProperties[i], false));
        model = new DefaultTreeModel(leaf, true);
        collectionTree = new JTree(model);

        panel1.add(save);
        panel2.add(collectionTree);
        panel1.add(delete);
        panel1.add(addBtn);
        panel1.add(load);

        this.add(panel1, BorderLayout.WEST);
        this.add(panel2, BorderLayout.CENTER);


        addBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ServerWindow.super.setEnabled(false);
                new ActAdd(thisOne, collection);
            }
        });
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser();
                int res = chooser.showDialog(null, "Choose File");
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file_save = chooser.getSelectedFile();
                    String path_save = file_save.getAbsolutePath();
                    Output.save(path_save, collection);
                } else JOptionPane.showMessageDialog(thisOne, "You hadn't chosen any file to save collection");
            }
        });
        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ServerWindow.super.setEnabled(false);
                new ActDelete(thisOne, collection);
            }
        });
        load.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                file = new File(".\\form.xml");
                path = file.getAbsolutePath();
                In.getPjeys(path, collection);
                refreshTree();
            }
        });
        this.setResizable(false);
        new AuthWindow(thisOne);
//        this.setVisible(true);

//        ServerWindow.super.setEnabled(false);
//        ServerWindow.super.setVisible(false);

//        new AuthWindow(thisOne);
    }

    public void refreshTree() {
        j = 0;
        saveProperties = new String[collection.size()];
        for (Pj pj : collection) {
            saveProperties[j] = pj.name + "(id" + pj.id + ")";
            j++;
        }
        leaf = new DefaultMutableTreeNode();
        for (int i = 0; i < saveProperties.length; i++)
            leaf.add(new DefaultMutableTreeNode(saveProperties[i], false));
        model = new DefaultTreeModel(leaf, true);
        collectionTree.setModel(model);
    }

    public CopyOnWriteArrayList<Pj> getCollection() {
        return collection;
    }

    //    public static void main(String... args) {
//        new ServerWindow();
//
//    }

}

