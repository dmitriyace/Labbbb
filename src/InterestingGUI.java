import Enums.ColorsEnum;
import Enums.EPj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class InterestingGUI {
    public boolean alreadyReleased = false;
    int kostyl = 0;
    CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
    String colorCheckBox;

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

//        File file = new File(".\\formtest.xml");
//        String path = file.getAbsolutePath();
//        In.getPjeys(path, collection);
//        collection.forEach(n -> {
//            btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, getColorFromEnum(n.color));
//        });

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
//        objectsPanel.add(clear);
        clear.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.clearBtns();
                collection.clear();
//                if (!alreadyReleased) {
//                    if (kostyl!=3){
                File f = new File(".\\formtest.xml");
                String path = f.getAbsolutePath();
                In.getPjeys(path, collection);
                collection = collection.stream().filter(n -> {
//                    n.color.compareTo(ColorsEnum.WHITE);
                    if (n.color.compareTo(ColorsEnum.WHITE) != 0) return true;
                    else return false;
                }).collect(Collectors.toCollection(CopyOnWriteArrayList<Pj>::new));
                collection.forEach(n -> {
                    btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, getColorFromEnum(n.color));
                });

            }
        });
        JPanel menuPanel = new JPanel(new GridLayout(10, 1));
        menuPanel.setLocation(0, 355);
        menuPanel.setSize(400, 200);
        panel.add(menuPanel);
//        JLabel lbl = new JLabel("fsrgr");
//        menuPanel.add(lbl);
        menuPanel.add(clear);
//        lbl.setLocation(10, 10);
//        lbl.setSize(25, 25);
        List<JCheckBox> cList = new ArrayList<>();
        JCheckBox blue = new JCheckBox("blue");
        blue.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getColorsString(cList);
            }
        });
        JCheckBox white = new JCheckBox("white");
        white.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getColorsString(cList);
            }
        });
        JCheckBox grey = new JCheckBox("grey");
        grey.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getColorsString(cList);
            }
        });
        JCheckBox red = new JCheckBox("red");
        red.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getColorsString(cList);
            }
        });
        cList.add(blue);
        cList.add(white);
        cList.add(grey);
        cList.add(red);
        cList.forEach(e -> {
            menuPanel.add(e);
        });
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

//    public void updColorsFilter(ArrayList<JCheckBox> cList) {
//        colorCheckBox = getColorsString(cList);
//    }

    public String getColorsString(List<JCheckBox> cList) {
        colorCheckBox = "";
        cList.forEach(e -> {
            if (e.isSelected()) {
                colorCheckBox += e.getText();
            }
        });
        System.out.println(colorCheckBox);
        return colorCheckBox;
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
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (myBtn.contains(e.getPoint())) {
                    setToolTipText(name);
                }
                ToolTipManager.sharedInstance().mouseMoved(e);
            }
        });
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                setToolTipText(name);
//            }
//        });
        myBtns.add(myBtn);
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
            graphics2D.fill(n);
        });

    }

}

