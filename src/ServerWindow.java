


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerWindow extends JFrame {


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
        this.setBounds(100, 10, width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        this.setVisible(true);

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

//    public static void main(String... args) {
//        new ServerWindow();
//
//    }

}
