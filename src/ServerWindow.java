


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

    JButton save = new JButton("save collection");
    JButton addBtn = new JButton("add pyjama");
    JButton delete = new JButton("delete pyjama");
    JButton show = new JButton("show");

    CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();


    ServerWindow() {
        super("Menu");

        File file = new File(".\\form.xml");
        String path = file.getAbsolutePath();
        In.getPjeys(path, collection);


        String saveProperties[] = new String[collection.size()];
        int j = 0;
        for (Pj pj : collection) {
            saveProperties[j] = pj.name + "(id" + pj.id + ")";
            j++;
        }

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Collection");

        DefaultMutableTreeNode leaf = new DefaultMutableTreeNode();
        for (int i = 0; i < saveProperties.length; i++)
            leaf.add(new DefaultMutableTreeNode(saveProperties[i], false));

        this.setBounds(100, 10, width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTree collectionTree = new JTree(new DefaultTreeModel(leaf, true));


        JPanel panel1 = new JPanel(new GridLayout(7, 1));
        JPanel panel2 = new JPanel(new FlowLayout());

        panel1.add(save);
        panel2.add(collectionTree);
        panel1.add(delete);
        panel1.add(addBtn);
        panel1.add(show);

        this.add(panel1, BorderLayout.WEST);
        this.add(panel2, BorderLayout.CENTER);
        this.setVisible(true);
        this.setResizable(false);

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
                File file_save = new File(".\\Output.txt");
                String path_save = file_save.getAbsolutePath();
                Output.save(path_save, collection);
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
    }


    public static void main(String... args) {
        new ServerWindow();

    }

}
