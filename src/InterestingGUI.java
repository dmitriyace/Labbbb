import Enums.ColorsEnum;
import Enums.EPj;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class InterestingGUI {
    //    public boolean alreadyReleased = false;
//    int kostyl = 0;
    CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
    String colorCheckBox = "blueredwhitegrey";
    //    boolean gotIt;
    boolean ifSizeCorrect = false;
    EPj saveCorrectSize;
    ArrayList<Pj> paintCollection;

    public static void main(String[] args) {
        new InterestingGUI().go();
    }

    public void go() {
        //создал фрейм и panel
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(null);
        frame.setContentPane(panel);
        frame.setSize(800, 800);
        panel.setLocation(0, 0);
        //создал объект класса PBtn
        PBtn btn = new PBtn();

        File file = new File(".\\formtest.xml");
        String path = file.getAbsolutePath();
        In.getPjeys(path, collection);
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

        System.out.println("создаю панель объектов");
        //создал панель объектов
        JPanel objectsPanel = new JPanel(null);
        panel.add(objectsPanel);
        objectsPanel.setLocation(0, 0);
        objectsPanel.setSize(300, 300);
        btn.setLocation(0, 0);
        btn.setSize(300, 300);

        //нарисовал
        collection.forEach(e -> {
            btn.addBtn(e.loca.getX(), e.loca.getY(), getSizeFromEnum(e.epj), (int) 1.3 * getSizeFromEnum(e.epj), e.name, getColorFromEnum(e.color));
        });
        objectsPanel.add(btn);

        //кнопка очистки канваса
        JButton clear = new JButton("clear");
        clear.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.clearBtns();
//                collection.clear();
//                if (!alreadyReleased) {
//                    if (kostyl!=3){

//                File f = new File(".\\formtest.xml");
//                String path = f.getAbsolutePath();
//                In.getPjeys(path, collection);
                // фильтр по цвету
            }
        });

        //кнопка фильтра
        JButton filter = new JButton("filter");
        filter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ColorsEnum> filteredColors = getColorFromCheckBox(colorCheckBox);
                paintCollection = new ArrayList<>();
                btn.clearBtns();

                //по цветам
                collection.forEach(n -> {
                    filteredColors.forEach(cl -> {
                        if (n.color.compareTo(cl) == 0) {
                            paintCollection.add(n);
                            return;
                        }
                    });
                });
//                filteredColors.forEach(cl->{
//                    paintCollection.stream().filter(n->{
//                     if (n.color.compareTo(cl)==0) return true;
//                     else return false;
//                    });
//                });

//                collection = collection.stream().filter(n -> {
//
//                    if (n.color.compareTo(ColorsEnum.WHITE) != 0) return true;
//                    else return false;
//                }).collect(Collectors.toCollection(CopyOnWriteArrayList<Pj>::new));

//                collection.forEach(n -> {
//                    btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, getColorFromEnum(n.color));
//                });
                if (ifSizeCorrect) {
                    paintCollection = paintCollection.stream().filter(n -> {
                        if (n.getSize().compareTo(saveCorrectSize) == 0) return true;
                        return false;
//                                System.out.println(n.name);
//                                return true;
                    }).collect(Collectors.toCollection(ArrayList::new));
                }


                paintCollection.forEach(n -> {
                    btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, getColorFromEnum(n.color));
                });
            }
        });

        //добавляю панель меню, на него добавляю фильтры и кнопки
        JPanel menuPanel = new JPanel(new GridLayout(10, 1));
        menuPanel.setLocation(0, 355);
        menuPanel.setSize(400, 200);
        panel.add(menuPanel);
        menuPanel.add(clear);
        menuPanel.add(filter);
        //чекбоксы
        List<JCheckBox> cList = new ArrayList<>();
        JCheckBox blue = new JCheckBox("blue");
        JCheckBox white = new JCheckBox("white");
        JCheckBox grey = new JCheckBox("grey");
        JCheckBox red = new JCheckBox("red");
        cList.add(blue);
        cList.add(white);
        cList.add(grey);
        cList.add(red);
        cList.forEach(e -> {
            menuPanel.add(e);
            e.setSelected(true);
            e.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    getColorsString(cList);
                }
            });
        });
        //текстфилд
        JTextField sizeField = new JTextField();
        JLabel checkTextField = new JLabel();
        sizeField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!checkSize(sizeField.getText())) {
                    checkTextField.setText("Enter correct size, \nVariants: LONG, SHORT, OK");
                } else checkTextField.setText("");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!checkSize(sizeField.getText())) {
                    checkTextField.setText("Enter correct size, \nVariants: LONG, SHORT, OK");
                } else checkTextField.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
//                if (!checkSize(sizeField.getText())) {
//                    checkTextField.setText("Enter correct size, \nVariants: LONG, SHORT, OK");
//                }else checkTextField.setText("");
            }
        });
        menuPanel.add(new JLabel("show pj by her name"));
        menuPanel.add(sizeField);
        menuPanel.add(checkTextField);

        //спиннер
        String[] clearance = {"washed", "unwashed", "none"};
        SpinnerListModel model = new SpinnerListModel(clearance);
        JSpinner spinClearance = new JSpinner(model);
        menuPanel.add(spinClearance);

        frame.setVisible(true);
    }


    public boolean checkSize(String s) {
        try {
            saveCorrectSize = EPj.valueOf(s.toUpperCase());
            ifSizeCorrect = true;
            return ifSizeCorrect;
        } catch (IllegalArgumentException iae) {
            ifSizeCorrect = false;
            return ifSizeCorrect;
        }
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

    public ArrayList<ColorsEnum> getColorFromCheckBox(String s) {
        ArrayList<ColorsEnum> colorArray = new ArrayList<>();
        if (s.contains("blue")) {
            colorArray.add(ColorsEnum.BLUE);
        }
        if (s.contains("red")) {
            colorArray.add(ColorsEnum.RED);
        }
        if (s.contains("white")) {
            colorArray.add(ColorsEnum.WHITE);
        }
        if (s.contains("grey")) {
            colorArray.add(ColorsEnum.GREY);
        }
        return colorArray;
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

