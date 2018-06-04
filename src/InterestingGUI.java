import Enums.ColorsEnum;
import Enums.EPj;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InterestingGUI {

    int x;
    int y;
    int width;
    int height;
    String name;
    Color color;

    public static void main(String[] args) {
        new InterestingGUI().go();
    }

    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(null);
        frame.setContentPane(panel);
        frame.setSize(800, 800);
        panel.setLocation(0, 0);

        CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
        File file = new File(".\\form.xml");
        String path = file.getAbsolutePath();
        In.getPjeys(path, collection);


//        java.util.List<PBtn> list = new ArrayList<>();
//        for (Pj pj : collection) {
//            x = pj.loca.getX();
//            y = pj.loca.getY();
//            name = pj.name;
//            color = getColorFromEnum(pj.color);
//            width = getSizeFromEnum(pj.epj);
//            height = (int) 1.8 * width;
//            list.add(new PBtn(x, y, width, height, name, color));
//
//        }
        PBtn btn = new PBtn();
        JPanel objectsPanel = new JPanel(null);

        collection.forEach(e -> {
            btn.addBtn(e.loca.getX(),e.loca.getY(),getSizeFromEnum(e.epj),2*getSizeFromEnum(e.epj),e.name,getColorFromEnum(e.color));
        });
        objectsPanel.add(btn);
        panel.add(objectsPanel);
        objectsPanel.setLocation(0, 0);
        objectsPanel.setSize(300, 300);

//        list.forEach(n -> {
//            objectsPanel.add(n);
//            n.setBounds(n.x, n.y, n.width, n.height);
////            n.repaint();
//        });

        JButton clear = new JButton("clear");
        clear.setLocation(250, 250);
        clear.setSize(30, 30);
        objectsPanel.add(clear);
//        clear.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                list.clear();
//                list.forEach(e1->e1.repaint());
//            }
//        });
//        clear.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                list.clear();
//                list.forEach(e1 -> e1.repaint());
//            }
//        });
        JPanel menuPanel = new JPanel(new GridLayout(10, 1));
//        menuPanel.setBounds(0,335,200,300);
        menuPanel.setLocation(0, 355);
        menuPanel.setSize(400, 200);
        panel.add(menuPanel);
        JLabel lbl = new JLabel("fsrgr");
        menuPanel.add(lbl);
        lbl.setLocation(10, 10);
        lbl.setSize(25, 25);
        List<JCheckBox> cList = new ArrayList<>();
        JCheckBox blue = new JCheckBox("blue");
        JCheckBox white = new JCheckBox("white");
        JCheckBox grey = new JCheckBox("grey");
        JCheckBox red = new JCheckBox("red");
        cList.add(blue);
        cList.add(white);
        cList.add(grey);
        cList.add(red);
        int changeLocation;
        cList.forEach(e -> {
//            e.setLocation(25, 25);
//            e.setSize(50, 15);
            menuPanel.add(e);
        });
//        panel.remove(objectsPanel);
//        menuPanel.add(blue);
//        menuPanel.add(white);
//        menuPanel.add(grey);
//        menuPanel.add(red);
        frame.setVisible(true);
    }


    public Color getColorFromEnum(ColorsEnum e) {
        Color color;
        switch (e) {
            case WHITE:
                return color = new Color(255, 255, 255);
            case RED:
                return color = new Color(255, 0, 0);
            case BLUE:
                return color = new Color(0, 0, 255);
            case GREY:
                return color = new Color(192, 192, 192);
            default:
                return color = new Color(0, 0, 0);
        }
    }

    public int getSizeFromEnum(EPj size) {
        switch (size) {
            case OK:
                return 30;
            case LONG:
                return 45;
            case SHORT:
                return 15;
            default:
                return 30;
        }
    }
}

class PBtn extends JComponent {
    private LinkedList<MyBtn> myBtns = new LinkedList<>();

    static class MyBtn {
        int x;
        int y;
        int width;
        int height;
        String name;
        Color color;

        MyBtn(int x, int y, int width, int height, String name, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.height = height;
            this.width = width;
            this.name = name;

        }

    }

    void addBtn(int x, int y, int width, int height, String name, Color color) {
        myBtns.add(new MyBtn(x, y, width, height, name, color));
        repaint();
    }

    void clearBtns() {
        myBtns.clear();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        myBtns.forEach(n -> {
            graphics2D.setColor(n.color);
            graphics2D.fillRect(n.x, n.y, n.width, n.height);
        });

    }

}