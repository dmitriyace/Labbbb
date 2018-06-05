import Enums.ColorsEnum;
import Enums.EPj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InterestingGUI {
    public boolean alreadyReleased = false;
    int kostyl = 0;

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
        PBtn btn = new PBtn();
        CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();

        File f = new File(".\\formtest.xml");
        String path = f.getAbsolutePath();
        In.getPjeys(path, collection);
        collection.forEach(n -> {
            btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, getColorFromEnum(n.color));
        });

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
//        btn.clearBtns();
        JPanel objectsPanel = new JPanel(null);
        panel.add(objectsPanel);
        objectsPanel.setLocation(0, 0);
        objectsPanel.setSize(300, 300);


        btn.setLocation(0, 0);
        btn.setSize(300, 300);
        collection.forEach(e -> {
            btn.addBtn(e.loca.getX(), e.loca.getY(), getSizeFromEnum(e.epj), (int) 1.3 * getSizeFromEnum(e.epj), e.name, getColorFromEnum(e.color));
        });
        objectsPanel.add(btn);


//        list.forEach(n -> {
//            objectsPanel.add(n);
//            n.setBounds(n.x, n.y, n.width, n.height);
////            n.repaint();
//        });

        JButton clear = new JButton("clear");
        clear.setLocation(250, 250);
        clear.setSize(30, 30);
        objectsPanel.add(clear);
        clear.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.clearBtns();
                if (!alreadyReleased) {
//                    if (kostyl!=3){
                    File f = new File(".\\formtest.xml");
                    String path = f.getAbsolutePath();
                    In.getPjeys(path, collection);
                    collection.forEach(n -> {
                        btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, getColorFromEnum(n.color));
                    });
//                    kostyl++;
                    alreadyReleased = true;
                } else {
//                    kostyl=0;
//                    File file = new File(".\\form.xml");
//                    String path = file.getAbsolutePath();
//                    In.getPjeys(path, collection);
//                    collection.forEach(n -> {
//                        btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, getColorFromEnum(n.color));
//                    });
                    alreadyReleased = false;
                }

            }
        });
        JPanel menuPanel = new JPanel(new GridLayout(10, 1));
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
//    static class MyBtn {
//        int x;
//        int y;
//        int width;
//        int height;
//        String name;
//        Color color;
//
//        MyBtn(int x, int y, int width, int height, String name, Color color) {
//            this.x = x;
//            this.y = y;
//            this.color = color;
//            this.height = height;
//            this.width = width;
//            this.name = name;
//        }
//
//    }
    static class MyBtn extends Rectangle {
        int x;
        int y;
        int width;
        int height;
        String name;
        Color color;

        MyBtn(int x, int y, int width, int height, String name, Color color) {
            this.setBounds(x, y, width, height);
            this.color = color;

        }

    }
    void addBtn(int x, int y, int width, int height, String name, Color color) {
        MyBtn myBtn = new MyBtn(x, y, width, height, name, color);
//        this.addMouseMotionListener(new MouseMotionListener() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                if (myBtn.contains(e.getPoint())){
//                    setToolTipText(name);
//                }
//                ToolTipManager.sharedInstance().mouseMoved(e);
//            }
//        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setToolTipText(name);
            }
        });
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
//            graphics2D.setColor(n.color);
//            graphics2D.fillRect(n.x, n.y, n.width, n.height);
            graphics2D.setColor(n.color);
            graphics2D.draw(n);
        });

    }

}