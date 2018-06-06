import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;

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
    CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
    String colorCheckBox = "blueredwhitegrey";
    boolean ifSizeCorrect = false;
    EPj saveCorrectSize;
    ArrayList<Pj> paintCollection;
    JSpinner spinClearance;
    final int delay = 200;
    final int timeOfAnime = 2000;
    int red;
    int green;
    int blue;
    Timer timer1;
    PBtn btn;
    int i;
    //    int animR;
//    int animB;
//    final int colorGr = new Color(192, 192, 192).getRGB();
//    int animW;
    boolean isGrey;
    int step;
    int stepRed;
    int stepBlue;
    int stepGreen;
    final int greySpektr = 192;

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

        //создаю объект класса PBtn
        btn = new PBtn();

        File file = new File(".\\formtest.xml");
        String path = file.getAbsolutePath();
        In.getPjeys(path, collection);


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
            btn.addBtn(e.loca.getX(), e.loca.getY(), getSizeFromEnum(e.epj), (int) 1.3 * getSizeFromEnum(e.epj), e.name, btn.getColorFromEnum(e.color));
        });
        objectsPanel.add(btn);

        //кнопка очистки канваса
        JButton clear = new JButton("clear");
        clear.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.clearBtns();

            }
        });

        //кнопка фильтра
        JButton filter = new JButton("filter");

        filter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterM();
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
        checkTextField.setFont(new Font("Consolas", Font.PLAIN, 10));
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
        String[] clearance = {"doesn't matter", "unwashed", "washed"};
        SpinnerListModel model = new SpinnerListModel(clearance);
        spinClearance = new JSpinner(model);
        menuPanel.add(spinClearance);

        //кнопка анимации
        JButton anime = new JButton("anime");
        isGrey = false;

        ActionListener paintGrey = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                LinkedList<PBtn.MyBtn> save = btn.getMyBtns();
//                btn.clearBtns();
                i = 0;
                btn.getMyBtns().forEach(n -> {
                    if (!ifGrey(n.color)) {
                        n.color = new Color(changeToGrey(n.color.getRed()), changeToGrey(n.color.getGreen()), changeToGrey(n.color.getBlue()));
                        i++;
//                        btn.addBtn(n);
                    } else {
                        isGrey = true;
//                        System.out.println(isGrey);
                    }
                });
                if (i == 0) {
                    stop();
                    System.out.println("разворот");

                }
                btn.repaint();

            }
        };

        ActionListener paintBack = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.getMyBtns().forEach(n -> {
//                    if () {
//                        n.color = new Color()
//                    }
                });
            }
        };

        timer1 = new Timer(delay, paintGrey);
//        timer2 = new Timer(delay, );
        System.out.print("");
//        timer1.setInitialDelay(0);
//        timer1.start();
//        System.out.println(new Date());
//        try {
//            Thread.sleep(timeOfAnime);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        timer1.stop();
//        System.out.println(new Date());
        anime.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                start();

            }
        });
        JButton stop = new JButton("stop");
        stop.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        menuPanel.add(stop);
        menuPanel.add(anime);

        frame.setVisible(true);
    }

    public void start() {
        timer1.start();
    }

    public void stop() {
        timer1.stop();
    }

    public boolean ifGrey(Color color) {
        if (Math.abs(color.getRed() - 192) < 10 && (Math.abs(color.getBlue() - 192) < 10) && (Math.abs(color.getGreen() - 192) < 10)) {
            return true;
        } else return false;
    }

    public boolean ifRed(Color color) {
        if (Math.abs(color.getRed() - 255) < 10 && (Math.abs(color.getBlue() - 0) < 10) && (Math.abs(color.getGreen() - 0) < 10)) {
            return true;
        } else return false;
    }

    public boolean ifBlue(Color color) {
        if (Math.abs(color.getRed() - 0) < 10 && (Math.abs(color.getBlue() - 255) < 10) && (Math.abs(color.getGreen() - 0) < 10)) {
            return true;
        } else return false;
    }

    public boolean ifWhite(Color color) {
        if (Math.abs(color.getRed() - 255) < 10 && (Math.abs(color.getBlue() - 255) < 10) && (Math.abs(color.getGreen() - 255) < 10)) {
            return true;
        } else return false;
    }

//    public int changeToRed(int r, int g, int b) {
//        int rStep = getStep(r,255);
//        int gStep = getStep(g,0);
//        int bStep = getStep(b,0);
////        if ( ==)
//    }

    public int changeToGrey(int definedColor) {
        int step = getStep(definedColor, 192);
        if (definedColor == greySpektr) {
            return greySpektr;
        } else if (definedColor < greySpektr) {
            return definedColor += step;
        } else if (definedColor > greySpektr) {
            return definedColor -= step;
        }
        return greySpektr;
    }

    public int getStep(int definedColor, int spektr) {
        if (definedColor == spektr)
            return 0;
        else if (definedColor < spektr)
            return (spektr - definedColor) / (timeOfAnime / delay);
        else return (definedColor - spektr) / (timeOfAnime / delay);
    }


    public boolean spinnerSetted() {
        String value = spinClearance.getValue().toString();
        if (value == ("washed") || value == "unwashed") {
            return true;
        }
        return false;
    }

    public void filterM() {
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
        //по размеру
        if (ifSizeCorrect) {
            paintCollection = paintCollection.stream().filter(n -> {
                if (n.getSize().compareTo(saveCorrectSize) == 0) return true;
                return false;
            }).collect(Collectors.toCollection(ArrayList::new));
        }
        //по чистоте
        if (spinnerSetted()) {
            paintCollection = paintCollection.stream().filter(n -> {
                if (n.getClearance().compareTo(EPjc.valueOf(spinClearance.getValue().toString().toUpperCase())) == 0)
                    return true;
                return false;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        paintCollection.forEach(n -> {
            btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, btn.getColorFromEnum(n.color));
        });
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

    LinkedList<MyBtn> getMyBtns() {
        return myBtns;
    }

    public static Color getColorFromEnum(ColorsEnum e) {
        switch (e) {
            case WHITE:
                return new Color(255, 255, 255);
            case RED:
                return new Color(255, 0, 0);
            case BLUE:
                return new Color(0, 0, 255);
            case GREY:
                return new Color(192, 192, 192);
            default:
                return new Color(0, 0, 0);
        }
    }

    static class MyBtn extends Rectangle {

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
        myBtns.add(myBtn);
        repaint();
    }

    void addBtn(MyBtn m) {
        MyBtn myBtn = new MyBtn(m.x, m.y, m.width, m.height, m.name, m.color);
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (myBtn.contains(e.getPoint())) {
                    setToolTipText(m.name);
                }
                ToolTipManager.sharedInstance().mouseMoved(e);
            }
        });
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
            graphics2D.setColor(n.color);
            graphics2D.draw(n);
            graphics2D.fill(n);
        });

    }

}

