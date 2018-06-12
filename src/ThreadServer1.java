import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ThreadServer1 implements Runnable {
    private Socket client;
    private CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
    private String way;
    String command;
    ServerWindow s;
    static String dbURL = "jdbc:postgresql://localhost:5433/postgres";
    static String username = "postgres";
    static String password = "021198";
    static Connection conn;

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
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(dbURL, username, password);

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
                    out.flush();
                    in.close();
                    out.close();
                    client.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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

        file = new File(".\\form.xml");
        path = file.getAbsolutePath();
        In.getPjeys(path, collection);
        j = 0;
        saveProperties = new String[collection.size()];
        for (Pj pj : collection) {
            saveProperties[j] = pj.name + " - id" + pj.id;
            j++;
        }

        leaf = new DefaultMutableTreeNode();
        for (int i = 0; i < saveProperties.length; i++)
            leaf.add(new DefaultMutableTreeNode(saveProperties[i], false));
        model = new DefaultTreeModel(leaf, true);
        collectionTree = new JTree(model);
        collectionTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) collectionTree.getLastSelectedPathComponent();
                System.out.println(collectionTree.getLastSelectedPathComponent());
            }
        });
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
                if (!collectionTree.isSelectionEmpty()) {
                    removeBySelectedNode(collectionTree.getLastSelectedPathComponent().toString());
                }
            }
        });
        load.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser();
                int res = chooser.showDialog(null, "Choose file");
                if (res == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                    path = file.getAbsolutePath();
                    In.getPjeys(path, collection);
                } else JOptionPane.showMessageDialog(thisOne, "You hadn't chosen any file to save collection");
                refreshTree();
            }
        });
        this.setResizable(false);
        new AuthWindow(thisOne);
    }

    public void refreshTree() {
        j = 0;
        saveProperties = new String[collection.size()];
        for (Pj pj : collection) {
            saveProperties[j] = pj.name + " - id" + pj.id;
            j++;
        }
        leaf = new DefaultMutableTreeNode();
        for (int i = 0; i < saveProperties.length; i++)
            leaf.add(new DefaultMutableTreeNode(saveProperties[i], false));
        model = new DefaultTreeModel(leaf, true);
        collectionTree.setModel(model);
    }

    public void removeBySelectedNode(String selectedPath) {
        Scanner scanner = new Scanner(selectedPath);
        scanner.useDelimiter(" - id");
        String name = scanner.next();
        String sId = scanner.next();
        int id = Integer.valueOf(sId);
        collection = collection.stream().filter(n -> ((n.name.compareTo(name) != 0) && (n.id != id))).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        refreshTree();
    }

    public CopyOnWriteArrayList<Pj> getCollection() {
        return collection;
    }


}

