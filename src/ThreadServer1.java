import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;
import Enums.Location;

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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static java.lang.Integer.valueOf;

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

class ServerWindow extends JFrame {


    private static final int width = 1000;
    private static final int height = 600;
    ServerWindow thisOne = this;
    int j;
    JButton save = new JButton("save collection");
    JButton addBtn = new JButton("add pyjama");
    JButton delete = new JButton("delete pyjama");
    JButton deleteById = new JButton("delete by id");
    JButton load = new JButton("load collection");
    JTree collectionTree;
    DefaultMutableTreeNode leaf;
    DefaultTreeModel model;
    String saveProperties[];
    File file;
    String path;
    CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
    DataBaseWork dbWork;
    ReflectEnum re = new ReflectEnum(dbWork);

    ServerWindow() {
        super("Menu");
        setBounds(100, 10, width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel1 = new JPanel(new GridLayout(7, 1));
        JPanel panel2 = new JPanel(new FlowLayout());

        dbWork = new DataBaseWork(collection);
        dbWork.initDB();
        re.reflectEnums();
        dbWork.loadFromFileToDB(collection);
        dbWork.getCollectionFromDB();
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
        panel1.add(deleteById);
        panel1.add(addBtn);
        panel1.add(load);

        this.add(panel1, BorderLayout.WEST);
        this.add(panel2, BorderLayout.CENTER);


        addBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ServerWindow.super.setEnabled(false);
                new ActAdd(thisOne, collection, dbWork);
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
        deleteById.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String answer = JOptionPane.showInputDialog(thisOne, "Enter id of pj to delete", "insert a number");
                if (answer != null) {
                    try {
                        int a = valueOf(answer);
                        dbWork.deleteRow(a);
                        refreshTree();
                    } catch (Exception exc) {
                        System.err.println("incorrect id");
                    }

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
        int id = valueOf(sId);
        collection = collection.stream().filter(n -> ((n.name.compareTo(name) != 0) && (n.id != id))).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        refreshTree();
    }

    public CopyOnWriteArrayList<Pj> getCollection() {
        return collection;
    }


}

class DataBaseWork {
    static String dbURL = "jdbc:postgresql://localhost:5433/postgres";
    static String username = "postgres";
    static String password = "021198";
    static Connection conn;
    static Statement statement;
    CopyOnWriteArrayList<Pj> collection;

    DataBaseWork(CopyOnWriteArrayList<Pj> collection) {
        this.collection = collection;
    }

    public void initDB() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromFileToDB(CopyOnWriteArrayList<Pj> collection) {
        File file = new File(".\\form.xml");
        String path = file.getAbsolutePath();
        In.getPjeys(path, collection);
        collection.forEach(n -> fillPyjamasInDB(n));

    }

    public static void fillPyjamasInDB(Pj pj) {
        String insert = "INSERT INTO pyjamas VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement s = conn.prepareStatement(insert);
            s.setInt(1, pj.id);
            s.setString(2, pj.name);
            s.setInt(3, pj.epj.getId());
            s.setInt(4, pj.epjc.getId());
            s.setInt(5, pj.loca.getId());
            s.setInt(6, pj.color.getId());
            s.executeUpdate();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void fillEnumTable(Enum e, String tableName, int id) {
        String insertEnum = "INSERT INTO " + tableName + "(id,value) " + " VALUES (" + id + ",'" + e + "');";
        try {
            statement.executeUpdate(insertEnum);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public static void deleteFromEnumTables(String tableName) {
        String delete = "DELETE FROM " + tableName;
        try {
            statement.executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCollectionFromDB() {
        String selectPjs = "SELECT name, size, clear, location, color, id FROM pyjamas;";
        try {
            ResultSet pjsDB = statement.executeQuery(selectPjs);
            collection.clear();
            while (pjsDB.next()) {
                String name = pjsDB.getString("name");
                EPj size = EPj.values()[valueOf(pjsDB.getString("size"))-1];
                EPjc clear = EPjc.values()[valueOf((pjsDB.getString("clear")))-1];
                Location location = Location.values()[valueOf((pjsDB.getString("location")))-1];
                ColorsEnum color = ColorsEnum.values()[valueOf((pjsDB.getString("color")))-1];
                int id = pjsDB.getInt("id");
                Pj pj = new Pj(name, size, clear, location, color, id);
                collection.add(pj);
            }
            System.out.println(collection.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void deleteRow(String name, int id) {
        String delete = "DELETE FROM pyjamas WHERE pyjamas.name = ? and pyjamas.id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(delete);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
            getCollectionFromDB();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRow(int id) {
        String delete = "DELETE FROM pyjamas WHERE pyjamas.id = ?";
        try {
            PreparedStatement s = conn.prepareStatement(delete);
            s.setInt(1, id);
            s.executeUpdate();
            getCollectionFromDB();

        } catch (SQLException e) {
            System.err.println("incorrect id: " + id + ". Please insert correct id");
        }

    }

    public void deleteRow(String name, String size, String color, String clear, String location) {
        String delete = "DELETE FROM pyjamas WHERE pyjamas.name = ? AND " +
                "pyjamas.size = (?,CAST(? AS valid_size) AND pyjamas.color = CAST(? AS valid_color)" +
                " AND pyjamas.clear = CAST(? AS clearance) AND pyjamas.location = CAST(? AS loca)";

        try {
            PreparedStatement statement = conn.prepareStatement(delete);
            statement.setString(1, name);
            statement.setString(2, size.toUpperCase());
            statement.setString(4, clear.toUpperCase());
            statement.setString(5, location.toUpperCase());
            statement.setString(3, color.toUpperCase());

            statement.executeUpdate();
            getCollectionFromDB();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

