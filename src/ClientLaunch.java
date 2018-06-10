import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    boolean animeColor, animeSize, animeClearance;

    ArrayList<Pj> paintCollection;
    //    List<Pair<Integer, Color>> gradients = new LinkedList<>();
    JSpinner spinClearance;


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
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

        JButton refresh = new JButton("обновить");
        refresh.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshCollection();
                btn.clearBtns();
                collection.forEach(n -> {
                    btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, btn.getColorFromEnum(n.color), n.epjc, btn);
                });
                System.out.println(collection.size());
            }
        });


        //добавляю панель меню, на него добавляю фильтры и кнопки
        JPanel menuPanel = new JPanel(new GridLayout(10, 1));
        menuPanel.setLocation(0, 355);
        menuPanel.setSize(400, 200);
        panel.add(menuPanel);
        menuPanel.add(refresh);
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
        menuPanel.add(new JLabel("load pj by her name"));
        menuPanel.add(sizeField);
        menuPanel.add(checkTextField);

        //спиннер
        String[] clearance = {"doesn't matter", "unwashed", "washed"};
        SpinnerListModel model = new SpinnerListModel(clearance);
        spinClearance = new JSpinner(model);
        menuPanel.add(spinClearance);


        //кнопка анимации
        JButton anime = new JButton("anime");
        anime.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterActors();
                btn.getMyBtns().forEach(n -> {
                    if (n.animated)
                        n.changeColor();
                });
            }
        });

        JButton stop = new JButton("stop1");
        stop.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.getMyBtns().forEach(n -> {
                    n.timer.stop();
                });
            }
        });
        menuPanel.add(stop);
        menuPanel.add(anime);

        frame.setVisible(true);
    }

    public void refreshCollection() {
        try {

            writer.writeObject("list");
            System.out.println(reader.toString());
            collection = new CopyOnWriteArrayList<>();
            System.out.println(collection.size()+" new");
            collection = (CopyOnWriteArrayList<Pj>) reader.readObject();
            System.out.println(collection.size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean spinnerSetted() {
        String value = spinClearance.getValue().toString();
        if (value == ("washed") || value == "unwashed") {
            return true;
        }
        return false;
    }

    public void filterActors() {
        ArrayList<ColorsEnum> filteredColors = getColorFromCheckBox(colorCheckBox);
        btn.getMyBtns().forEach(n -> {
            animeClearance = true;
            animeColor = true;
            animeSize = true;
            if (ifSizeCorrect) {
                if (!(n.width == sizeWidth)) animeSize = false;
            }
            if (spinnerSetted()) {
                if (!(n.clearance == EPjc.valueOf(spinClearance.getValue().toString().toUpperCase())))
                    animeClearance = false;
            }
            if (filteredColors.size() != 0)
                animeColor = filteredColors.stream().anyMatch(color -> ((n.nativeColor.getRed() == btn.getColorFromEnum(color).getRed()) &&
                        (n.nativeColor.getGreen() == btn.getColorFromEnum(color).getGreen()) &&
                        (n.nativeColor.getBlue() == btn.getColorFromEnum(color).getBlue())));
//                filteredColors.forEach(color -> {
//                    if ((n.nativeColor.getRed() == btn.getColorFromEnum(color).getRed()) && (n.nativeColor.getGreen() == btn.getColorFromEnum(color).getGreen()) && (n.nativeColor.getBlue() == btn.getColorFromEnum(color).getBlue())) {
//                        animeColor = true;
//                        return;
//                    }
//                    else animeColor = false;
//                });
            n.animated = (animeClearance && animeColor && animeSize);
        });


    }

//    public void filterM() {
//        ArrayList<ColorsEnum> filteredColors = getColorFromCheckBox(colorCheckBox);
//        paintCollection = new ArrayList<>();
//        btn.clearBtns();
//
//        //по цветам
//        collection.forEach(n -> {
//            filteredColors.forEach(cl -> {
//                if (n.color.compareTo(cl) == 0) {
//                    paintCollection.add(n);
//                    return;
//                }
//            });
//        });
//        //по размеру
//        if (ifSizeCorrect) {
//            paintCollection = paintCollection.stream().filter(n -> {
//                if (n.getSize().compareTo(saveCorrectSize) == 0) return true;
//                return false;
//            }).collect(Collectors.toCollection(ArrayList::new));
//        }
//        //по чистоте
//        if (spinnerSetted()) {
//            paintCollection = paintCollection.stream().filter(n -> {
//                if (n.getClearance().compareTo(EPjc.valueOf(spinClearance.getValue().toString().toUpperCase())) == 0)
//                    return true;
//                return false;
//            }).collect(Collectors.toCollection(ArrayList::new));
//        }
//
//        paintCollection.forEach(n -> {
//            btn.addBtn(n.loca.getX(), n.loca.getY(), getSizeFromEnum(n.epj), (int) 1.3 * getSizeFromEnum(n.epj), n.name, btn.getColorFromEnum(n.color), n.epjc, btn);
//        });
//    }


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
            deltaB = -(compColor.getBlue() - 192) / 400f;
            deltaG = -(compColor.getGreen() - 192) / 400f;
            deltaR = -(compColor.getRed() - 192) / 400f;
            timer = new Timer(5, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //вычисление нового цвета
                    R = Math.abs(R + deltaR);
                    G = Math.abs(G + deltaG);
                    B = Math.abs(B + deltaB);
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

