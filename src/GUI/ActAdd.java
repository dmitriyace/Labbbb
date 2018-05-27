package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActAdd extends JFrame {
    final static int width = 500;
    final static int height = 500;

    boolean succeed;

    JLabel addLabel = new JLabel("fill object properties");
    JLabel empty = new JLabel();
    JButton checkBtn = new JButton("check and add");
    JButton cancel = new JButton("cancel");

    MenuWindow parent;

    JTextField[] fields = {
            new JTextField(""),
            new JTextField(""),
            new JTextField(""),
            new JTextField(""),
            new JTextField("")
    };

    public ActAdd(MenuWindow parent) {
        super("Adding new pyjama");

        this.parent = parent;
        parent.setEnabled(false);
        parent.setVisible(true);

        this.setVisible(true);
        this.setBounds(100, 10, width, height);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 2, 1));
        panel.add(addLabel);

        JLabel[] labels = {
                new JLabel("Name"),
                new JLabel("Size"),
                new JLabel("Clearance"),
                new JLabel("Wardrobe location"),
                new JLabel("Color")};

        panel.add(empty);
        for (int i = 0; i < labels.length; i++) {
            panel.add(labels[i]);
            panel.add(fields[i]);
        }
        panel.add(checkBtn);
        panel.add(cancel);
        this.setContentPane(panel);
        checkBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                succeed=false;
                ActAdd.super.setVisible(false);
                parent.setVisible(true);
                parent.setEnabled(true);
            }
        });



    }




    public static void main(String... args) {
        new ActAdd(new MenuWindow());
    }
}
