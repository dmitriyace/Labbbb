import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;
import javafx.util.Pair;
import org.pushingpixels.trident.Timeline;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ClientLaunch {
    //vars of connect
    private final static int port = 1111;
    private static final String host = "127.0.0.1";
    //collection
    CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
    //vars of filters
    String colorCheckBox = "blueredwhitegrey";
    boolean ifSizeCorrect = false;
    int sizeWidth = 0;
    EPj saveCorrectSize;

    ArrayList<Pj> paintCollection;
    //    List<Pair<Integer, Color>> gradients = new LinkedList<>();
    JSpinner spinClearance;

    //variables of animation step
    final int delay = 200;
    final int timeOfAnime = 2000;
    int stepRR, stepRG, stepRB, stepWR, stepWG, stepWB, stepBR, stepBG, stepBB;
    int[] stepR = {}, stepB, stepW;
    int countSteps;
    Timer timer1;
    Timer timer2;

    PBtn btn;
    boolean isGrey;
    final int greySpektr = 192;
    HashMap<Rectangle, Timeline> trHashMap = new HashMap<>();

    public static void main(String[] args) {
        connect();
        new ClientLaunch().go();

    }

    static Socket s = null;
    static ObjectInputStream reader = null;
    static ObjectOutputStream writer = null;

    public static void connect() {
        while (true) {
            try {
                s = new Socket(host, port);
                reader = new ObjectInputStream(s.getInputStream());
                writer = new ObjectOutputStream(s.getOutputStream());
                break;
            } catch (IOException e) {
            }
        }

    }

    public void go() {
        //расчитал шаги анимации
        countSteps = timeOfAnime / delay;

        stepRR = (greySpektr - 255) / countSteps;
        stepRG = (greySpektr - 0) / countSteps;
        stepRB = stepRG;
        stepBR = stepRG;
        stepBG = stepBR;
        stepBB = stepRR;
        stepWR = stepRR;
        stepWG = stepRR;
        stepWB = stepRR;
        stepR = new int[]{stepRR, stepRG, stepRB};
        stepB = new int[]{stepBR, stepBG, stepBB};
        stepW = new int[]{stepWR, stepWG, stepWB};

        //создал фрейм и panel
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(null);
        frame.setContentPane(panel);
        frame.setSize(800, 800);
        panel.setLocation(0, 0);

        //создаю объект класса PBtn
        btn = new PBtn();

        System.out.println("get pjs first time");
        try {
            writer.writeObject("list");
            collection = (CopyOnWriteArrayList<Pj>) reader.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(collection.size());

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
            btn.addBtn(e.loca.getX(), e.loca.getY(), getSizeFromEnum(e.epj), (int) 1.3 * getSizeFromEnum(e.epj), e.name, btn.getColorFromEnum(e.color), e.epjc, btn);
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

        //кнопка обновления
        JButton refresh = new JButton();
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collection = refreshCollection();
                btn.clearBtns();
                collection.forEach(n -> {
                    btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, btn.getColorFromEnum(n.color), n.epjc, btn);
                });

            }
        });


        //кнопка фильтра
        JButton filter = new JButton("filter");

        filter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterAnimated();
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


        //Новая кнопка анимации
//        JButton anime1 = new JButton("normalnoeAnime");
//        menuPanel.add(anime1);
//        anime1.addActionListener(new ActionListener() {
//
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                trHashMap.clear();
//                btn.getMyBtns().forEach(n -> {
//                    initAnimation(n);
//                    Timeline t = trHashMap.get(n);
//                    t.playLoop(Timeline.RepeatBehavior.REVERSE);
//
//                });
//                while (true) {
//                    Timer timer = new Timer(200, new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            btn.repaint();
//                        }
//                    });
//                }
//            }
//        });


        //кнопка анимации
        JButton anime = new JButton("anime");
        isGrey = false;

        ActionListener paintGrey = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

//                i = 0;
//                btn.getMyBtns().forEach(n -> {
//                    if (!ifGrey(n.color)) {
//                        n.color = new Color(changeToGrey(n.color.getRed()), changeToGrey(n.color.getGreen()), changeToGrey(n.color.getBlue()));
////                        gradients.add(new Pair<>(gradients.size(), n.color));
//                        i++;
//                    } else {
//                        isGrey = true;
//                    }
//                });
//                if (i == 0) {
//                    stop1();
//                    System.out.println("reverse");
//                    timer2.start();
//
//                }
//                btn.repaint();
            }
        };

        ActionListener paintBack = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.getMyBtns().forEach(n -> {
                    {


                    }
                });
                btn.repaint();
            }
        };

        timer1 = new Timer(delay, paintGrey);
        timer2 = new Timer(delay, paintBack);
        anime.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                filterAnimated();
                btn.getMyBtns().forEach(n -> {
//                    if (n.color.getBlue()==255&&n.color.getRed()!=255)
                        n.changeColor();
                });
            }
        });
        JButton stop = new JButton("stop1");
        stop.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                stop1();
                btn.getMyBtns().forEach(n->{
                    n.timer.stop();
                });
            }
        });
        menuPanel.add(stop);
        menuPanel.add(anime);
        menuPanel.add(refresh);

        frame.setVisible(true);
    }

//    public void initAnimation(PBtn.MyBtn n) {
//        Timeline timeline = new Timeline(n);
//        timeline.addPropertyToInterpolate("color", n.color, new Color(192, 192, 192));
//        timeline.setDuration(2000);
//        timeline.setName(n.name);
//        trHashMap.put(n, timeline);
//    }

    public void animation() {
        for (int j = 0; j < countSteps; j++) {
            btn.getMyBtns().forEach(n -> {
                if (n.animated == true) {
                    if (n.nativeColor == Color.red) {
                        n.color = new Color(n.color.getRed() + stepR[0], n.color.getGreen() + stepR[1], n.color.getBlue() + stepR[2]);
                    } else if (n.nativeColor == Color.white) {
                        n.color = new Color(n.color.getRed() + stepW[0], n.color.getGreen() + stepW[1], n.color.getBlue() + stepW[2]);
                    } else if (n.nativeColor == Color.blue) {
                        n.color = new Color(n.color.getRed() + stepB[0], n.color.getGreen() + stepB[1], n.color.getBlue() + stepB[2]);
                    }
                }
            });
            btn.repaint();

        }
        for (int j = 0; j < countSteps; j++) {
            btn.getMyBtns().forEach(n -> {
                if (n.animated == true) {
                    if (n.nativeColor == Color.red) {
                        n.color = new Color(n.color.getRed() - stepR[0], n.color.getGreen() - stepR[1], n.color.getBlue() - stepR[2]);
                    } else if (n.nativeColor == Color.white) {
                        n.color = new Color(n.color.getRed() - stepW[0], n.color.getGreen() - stepW[1], n.color.getBlue() - stepW[2]);
                    } else if (n.nativeColor == Color.blue) {
                        n.color = new Color(n.color.getRed() - stepB[0], n.color.getGreen() - stepB[1], n.color.getBlue() - stepB[2]);
                    }
                }
            });

        }
    }

    public CopyOnWriteArrayList<Pj> refreshCollection() {
        try {
            writer.writeObject("list");
            return (CopyOnWriteArrayList<Pj>) reader.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new CopyOnWriteArrayList<>();
    }

    public void start1() {
        timer1.start();
    }

    public void stop1() {
        timer1.stop();
    }

    public void start2() {
        timer2.start();
    }

    public void stop2() {
        timer2.stop();
    }


    public boolean ifGrey(Color color) {
        if (Math.abs(color.getRed() - 192) < 10 && (Math.abs(color.getBlue() - 192) < 10) && (Math.abs(color.getGreen() - 192) < 10)) {
            return true;
        } else return false;
    }

//    public Color ifRed(Color color) {
//        if (Math.abs(color.getRed() - 255) < 10 && (Math.abs(color.getBlue() - 0) < 10) && (Math.abs(color.getGreen() - 0) < 10)) {
//
//        } else {
//            color = new Color(changeToRed(color.getRGB()));
//        }
//        return color;
//    }
//
//    public boolean ifBlue(Color color) {
//        if (Math.abs(color.getRed() - 0) < 10 && (Math.abs(color.getBlue() - 255) < 10) && (Math.abs(color.getGreen() - 0) < 10)) {
//            return true;
//        } else return false;
//    }
//
//    public boolean ifWhite(Color color) {
//        if (Math.abs(color.getRed() - 255) < 10 && (Math.abs(color.getBlue() - 255) < 10) && (Math.abs(color.getGreen() - 255) < 10)) {
//            return true;
//        } else return false;
//    }
//
//    public int changeToRed(int rgb) {
//        int step = getStep(rgb, -65536);
//        if (rgb == -65536) {
//            return rgb;
//        } else if (rgb < -65536) {
//            return rgb -= step;
//        } else return rgb += step;
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

    public void filterAnimated() {
        ArrayList<ColorsEnum> filteredColors = getColorFromCheckBox(colorCheckBox);

        btn.getMyBtns().forEach(n -> {
            n.animated = true;
            if (ifSizeCorrect) {
                if (!(n.width == sizeWidth)) n.animated = false;
            }
            if (spinnerSetted()) {
                if (!(n.clearance == EPjc.valueOf(spinClearance.getValue().toString().toUpperCase())))
                    n.animated = false;
            }
            filteredColors.forEach(color -> {
                if ((n.color.getRed() == btn.getColorFromEnum(color).getRed()) && (n.color.getGreen() == btn.getColorFromEnum(color).getGreen()) && (n.color.getBlue() == btn.getColorFromEnum(color).getBlue())) {
                    n.animated = true;
                    return;
                } else n.animated = false;
            });


        });


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
            btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, btn.getColorFromEnum(n.color), n.epjc, btn);
        });
    }

    public boolean checkSize(String s) {
        try {
            saveCorrectSize = EPj.valueOf(s.toUpperCase());
            ifSizeCorrect = true;
            sizeWidth = getSizeFromEnum(saveCorrectSize);
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
        Color nativeColor;
        JComponent parent;

         Timer timer;
        private float R;
        private float G;
        private float B;
        private float deltaR;
        private float deltaG;
        private float deltaB;

        boolean animated = false;
        EPjc clearance;

        MyBtn(int x, int y, int width, int height, String name, Color compColor, EPjc clearance, JComponent parent) {
            this.setBounds(x, y, width, height);
            this.color = compColor;
            this.nativeColor = compColor;
            this.clearance = clearance;
            this.parent = parent;
            R = compColor.getRed();
            G = compColor.getGreen();
            B = compColor.getBlue();
            deltaB = -(compColor.getBlue() - 192) / 500f;
            deltaG = -(compColor.getGreen() - 192) / 500f;
            deltaR = -(compColor.getRed() - 192) / 500f;
            timer = new Timer(5, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//(теоретически, новые объекты можно было бы организовать в пул, но я решил не усложнять алгоритм)
                    //вычисление нового цвета
                    R = Math.abs(R + deltaR);
                    G = Math.abs(G + deltaG);
                    B = Math.abs(B + deltaB);
                    //плодить новые объекты - плохая мысль, но awt.Color не поддерживает настройку..
                    System.out.println((int) R + "; " + (int) G + "; " + (int) B);
                    color = new Color((int) R, (int) G, (int) B);

                    //если достигнут серый цвет, дельта меняет знак, и начинается перекрашивание обратно
                    if (isGray()) {
                        deltaR = -deltaR;
                        deltaG = -deltaG;
                        deltaB = -deltaB;
                    }
                    //если достигнут дефолт, таймер останавливается
                    if (isDefault()) {
                        deltaR = -deltaR;
                        deltaG = -deltaG;
                        deltaB = -deltaB;
                        timer.stop();
                    }
                    parent.repaint();
                }
            });

        }

        private boolean isGray() {
            //из-за особенностей хранения float в памяти, обычные проверки тут не подходят
            boolean redEqual = Math.abs(R - 192) < 0.1f;
            boolean greenEqual = Math.abs(G - 192) < 0.1f;
            boolean blueEqual = Math.abs(B - 192) < 0.1f;
            return redEqual && greenEqual && blueEqual;
        }

        private boolean isDefault() {
            boolean redEqual = Math.abs(R - nativeColor.getRed()) < 0.1f;
            boolean greenEqual = Math.abs(G - nativeColor.getGreen()) < 0.1f;
            boolean blueEqual = Math.abs(B - nativeColor.getBlue()) < 0.1f;
            return redEqual && greenEqual && blueEqual;
        }

        public void changeColor() {
            if (!timer.isRunning()) {
                timer.start();
            }
        }


    }


    void addBtn(int x, int y, int width, int height, String name, Color color, EPjc clearance, JComponent parent) {
        MyBtn myBtn = new MyBtn(x, y, width, height, name, color, clearance, parent);
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
        MyBtn myBtn = new MyBtn(m.x, m.y, m.width, m.height, m.name, m.color, m.clearance, m.parent);
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

