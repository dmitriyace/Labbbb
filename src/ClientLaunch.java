import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;
import org.pushingpixels.trident.Timeline;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
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
    JSpinner spinClearance;
    String selectedLocale;
    PBtn btn;
    ResourceBundle lb;
    JComboBox locCB;
    static ClientLaunch c;
    String localesList[] = {"en_IE", "da_DK", "ru_RU", "nl_NL"};

    public static void main(String[] args) {
        connect();
        c = new ClientLaunch();
        c.go();

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
        locCB = new JComboBox(localesList);
        lb = ResourceBundle.getBundle("Locales",
                new Locale(locCB.getSelectedObjects().toString().substring(0, 2),
                        locCB.getSelectedObjects().toString().substring(3, 5)), new EncodingControl());

        //создал фрейм и panel
        JFrame frame = new JFrame();
        frame.setTitle(lb.getString("Client.title"));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(null);
        frame.setContentPane(panel);
        frame.setSize(800, 800);
        panel.setLocation(0, 0);

        //создаю объект класса PBtn
        btn = new PBtn();

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


        //добавляю панель меню, на него добавляю фильтры и кнопки
        JPanel menuPanel = new JPanel(new GridLayout(11, 1));
        menuPanel.setLocation(0, 355);
        menuPanel.setSize(400, 200);
        panel.add(menuPanel);
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
                    checkTextField.setText(lb.getString("Client.CTF"));
                } else checkTextField.setText("");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!checkSize(sizeField.getText())) {
                    checkTextField.setText(lb.getString("Client.CTF"));
                } else checkTextField.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        menuPanel.add(new JLabel("choose pyjama by her size"));
        menuPanel.add(sizeField);
        menuPanel.add(checkTextField);

        //спиннер
        String[] clearance = {"doesn't matter", "unwashed", "washed"};
        SpinnerListModel model = new SpinnerListModel(clearance);
        spinClearance = new JSpinner(model);
        JLabel spinLabel = new JLabel("choose pyjama clearance parameter");
        menuPanel.add(spinLabel);
        menuPanel.add(spinClearance);


        //кнопка анимации
        JButton anime = new JButton(lb.getString("Client.anime"));
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

        JButton stop = new JButton(lb.getString("Client.stop"));
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

        JPanel localePanel = new JPanel();
        localePanel.setLocation(300, 0);
        localePanel.setSize(100, 100);
        panel.add(localePanel);

        locCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedLocale = (String) locCB.getSelectedItem();
                lb = ResourceBundle.getBundle("Locales",
                        new Locale(selectedLocale.substring(0, 2),
                                selectedLocale.substring(3, 5)), new EncodingControl());
                frame.setTitle(lb.getString("Client.title"));
                anime.setText(lb.getString("Client.anime"));
                stop.setText(lb.getString("Client.stop"));

                if (!checkSize(sizeField.getText())) {
                    checkTextField.setText(lb.getString("Client.CTF"));
                } else checkTextField.setText("");
            }
        });
        localePanel.add(locCB);


        frame.setVisible(true);
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
            n.animated = (animeClearance && animeColor && animeSize);
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

