import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;
import Enums.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActDelete extends JFrame {

    final static int width = 500;
    final static int height = 500;
    static EPj size;
    static EPjc clearance;
    static ColorsEnum colors;
    static Location location;

    boolean succeed;

    JLabel deleteLabel = new JLabel("fill object properties");

    JButton checkBtn = new JButton("check and delete");
    JButton cancel = new JButton("cancel");

    ServerWindow parent;

    JTextField[] fields = {
            new JTextField(""),
            new JTextField(""),
            new JTextField(""),
            new JTextField(""),
            new JTextField(""),
            new JTextField("")
    };

    public ActDelete(ServerWindow parent, CopyOnWriteArrayList<Pj> collection) {
        super("Delete pyjama by properties");

        this.parent = parent;
        parent.setEnabled(false);
        parent.setVisible(true);

        this.setVisible(true);
        this.setBounds(100, 10, width, height);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 2, 1));
        panel.add(deleteLabel);

        JLabel[] labels = {
                new JLabel("Name"),
                new JLabel("Size"),
                new JLabel("Clearance"),
                new JLabel("Wardrobe location"),
                new JLabel("Color"),
                new JLabel(("Time"))
        };

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

//                PjCollection.remove()

            }
        });
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ActDelete.super.setVisible(false);
                parent.setVisible(true);
                parent.setEnabled(true);
            }
        });


    }

    public Pj returnPj() {

        String name = fields[0].getText().toUpperCase();
        String ssize = fields[1].getText().toUpperCase();
        String sclearance = fields[2].getText().toUpperCase();
        String slocation = fields[3].getText().toUpperCase();
        String scolor = fields[4].getText().toUpperCase();
        size = EPj.valueOf(ssize);
        clearance = EPjc.valueOf(sclearance);
        location = Location.valueOf(slocation);
        colors = ColorsEnum.valueOf(scolor);
        return Pj.defaultPj;
    }

    public static void main(String... args) {
        CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
        File file = new File(".\\form.xml");
        String path = file.getAbsolutePath();
        In.getPjeys(path, collection);
        new ActDelete(new ServerWindow(), collection);

    }
}


