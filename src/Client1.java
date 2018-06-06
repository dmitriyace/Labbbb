import Enums.ColorsEnum;
import Enums.EPj;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client1 {
    private final static int port = 1111;
    private static final String host = "127.0.0.1";

        public static void main(String... args) throws ExcFall {
        Scanner in = new Scanner(System.in);
        CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
        String answer = "";
        String result = "";
        Socket s = null;
        ObjectInputStream reader = null;
        ObjectOutputStream writer = null;

        while (true) {
            try {
                s = new Socket(host, port);
                reader = new ObjectInputStream(s.getInputStream());
                writer = new ObjectOutputStream(s.getOutputStream());
                break;
            } catch (IOException e) {
            }
        }

        try {
            File file = new File(".\\form.xml");
            String path = file.getAbsolutePath();
            In.getPjeys(path, collection);

            while (true) {
                writer.writeObject(collection);
                String command = in.nextLine();
                if (s.isConnected()) {
                    writer.flush();
                    writer.writeObject(command);
                    if (command.startsWith("prl") || command.startsWith("prg") || command.startsWith("prv"))
                        command = command.substring(0, 3);
                    switch (command) {
                        case "pshow":
                            result = "";
                            answer = "";
                            while (!(answer = (String) reader.readObject()).startsWith("end")) {
                                result += "\n" + answer;
                            }
                            System.out.println(result);
                            break;
                        case "pst":
                            while (true) {
                                answer = (String) reader.readObject();
                                System.out.println(answer);
                                if (answer.startsWith("end")) {
                                    break;
                                } else writer.writeObject(in.nextLine());
                            }

                            break;
                        case "psort":
                            System.out.println("sorted");
                            break;
                        case "prl":
                            collection = (CopyOnWriteArrayList<Pj>) reader.readObject();
                            System.out.println("ok");
                            break;
                        case "prg":
                            collection = (CopyOnWriteArrayList<Pj>) reader.readObject();
                            System.out.println("ok");
                            break;
                        case "prv":
                            collection = (CopyOnWriteArrayList<Pj>) reader.readObject();
                            System.out.println("ok");
                            break;
                        case "pin":
                            result = "";
                            answer = "";
                            while (!(answer = (String) reader.readObject()).startsWith("end")) {
                                result += "\n" + answer;
                            }
                            System.out.println("Collection now views like: \n" + result);
                            break;
                        case "psize":
                            System.out.println(collection.size());
                            break;
                        case "pout":
                            System.out.println("ok");
                            break;
                        case "ph":
                            System.out.println((String) reader.readObject());
                            break;

                    }


                } else throw new ConnectException();
            }
        } catch (UnknownHostException e) {
            System.err.println("Host is incorrect");
        } catch (IOException e) {
            System.err.println("IO exception has come to us");
        } catch (ClassNotFoundException e) {
            System.err.println("R u sure 'bout da class?");
        }

    }
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(ClientGUI::new);
//    }
//
//    public static class ClientGUI extends JFrame {
//        //        private  static Font font = new Font("")
//        CopyOnWriteArrayList<Pj> collection;
//
//        class PButton extends JButton {
//            private Pj pj;
//            private EPj psize;
//            private int size;
//
//            private void setSize(double size) {
//                this.size = (int) size;
//            }
//
//            private Pj getPj() {
//                return pj;
//            }
//
//            private void setPsize(EPj psize) {
//                this.psize = psize;
//            }
//
//            //            private EPj getPSize() {
////                return psize;
////            }
////
////            private void setPSize(EPj psize) {
////                this.psize = psize;
////            }
////
////            private Pj getPj() {
////                return pj;
////            }
//
//            public Color getColorFromEnum(ColorsEnum e) {
//                Color color;
//                switch (e) {
//                    case WHITE:
//                        return color = new Color(255, 255, 255);
//                    case RED:
//                        return color = new Color(255, 0, 0);
//                    case BLUE:
//                        return color = new Color(0, 0, 255);
//                    case GREY:
//                        return color = new Color(192, 192, 192);
//                    default:
//                        return color = new Color(0, 0, 0);
//                }
//            }
//
//            public int getSizeFromEnum(EPj size) {
//                switch (size) {
//                    case OK:
//                        return 30;
//                    case LONG:
//                        return 45;
//                    case SHORT:
//                        return 15;
//                    default:
//                        return 30;
//                }
//            }
//
//            private PButton(Pj pj) {
//                super("");
//                System.out.println(pj);
//                this.pj = pj;
//                psize = pj.getSize();
//                setBackground(getColorFromEnum(pj.color));
//                setBounds(pj.loca.getX(), pj.loca.getY(), getSizeFromEnum(psize), (int) (getSizeFromEnum(psize) * 1.3));
//                // setForeground ?????
//                setToolTipText(this.pj.name);
//                setOpaque(false);
//                setEnabled(false);
//            }
//
//            @Override
//            public void paintComponent(Graphics g) {
//                g.fillRect(0, 10, getSizeFromEnum(psize), (int) (getSizeFromEnum(psize) * 1.3));
//            }
//        }
//
//        List<PButton> bList = new ArrayList<>();
//
//        private void updateCollection() {
//            while (true) {
//                try {
//                    collection = getCollection();
//                } catch (Exception e) {
//                    System.exit(0);
//                }
//
//            }
//        }
//
//        private void initList() {
//            bList.clear();
//            collection.forEach(n -> {
//                bList.add(new PButton(collection.get(n.id)));
//            });
//        }
//
//        private ClientGUI() {
//            super("client");
////            UIManager.put()
//            updateCollection();
//            init();
//        }
//
//        private void init() {
//            this.setSize(700, 700);
//            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
////            this.addWindowListener(new WindowAdapter() {
////                @Override
////                public void windowOpened(WindowEvent e) {
////                    super.windowOpened(e);
////                }
////            });
//            JPanel border = new JPanel();
//            border.setPreferredSize(new Dimension(700, 1));
//            border.setBackground(Color.black);
//            this.add(border, BorderLayout.PAGE_START);
//
//            class Canvas extends JPanel {
//                private Graphics2D gr;
//                private boolean staticDraw;
//
//                public boolean isStaticDraw() {
//                    return staticDraw;
//                }
//
//                public void setStaticDraw(boolean staticDraw) {
//                    this.staticDraw = staticDraw;
//                }
//
//                private Canvas(boolean staticDraw) {
//                    this.staticDraw = staticDraw;
//                    this.setLayout(null);
//                }
//
//                public void paintComponent(Graphics g) {
//                    gr = (Graphics2D) g;
//                    Rectangle2D rect = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
//                    gr.setPaint(Color.white);
//                    gr.fill(rect);
//                    gr.draw(rect);
//
//                    this.removeAll();
//                    if (staticDraw) {
//                        initList();
//                    }
//                    bList.forEach(this::add);
//                }
//            }
//            Canvas canvas = new Canvas(true);
//            canvas.setMinimumSize(new Dimension(500, 500));
//            canvas.setPreferredSize(canvas.getMinimumSize());
//
//            this.add(canvas, BorderLayout.CENTER);
//
//            JPanel panel = new JPanel();
//            panel.setPreferredSize(new Dimension(900, 230));
//
//            JButton start = new JButton("start");
//
//            panel.add(start);
//
//            class DoubleTimer {
//                private Timer timer1, timer2;
//
//                private void set(ActionListener aL1, int delay1, ActionListener aL2, int delay2) {
//                    timer1 = new Timer(delay1, aL1);
//                    timer2 = new Timer(delay2, aL2);
//                }
//
//                private void start1() {
//                    timer1.start();
//                }
//
//                private void start2() {
//                    timer2.start();
//                }
//
//                private void stop1() {
//                    timer1.stop();
//                }
//
//                private void stop2() {
//                    timer2.stop();
//                }
//
//                private void stop() {
//                    timer1.stop();
//                    timer2.stop();
//                }
//
//                private void reset() {
//                    timer1 = timer2 = null;
//                }
//
//                private boolean isRunning() throws NullPointerException {
//                    return timer1.isRunning() || timer2.isRunning();
//                }
//            }
//            DoubleTimer timer = new DoubleTimer();
//            JButton stop = new JButton("stop");
//            stop.addActionListener((event) -> {
//                try {
//                    if (!timer.isRunning())
//                        throw new NullPointerException();
//                    else {
//                        timer.stop();
//                        timer.reset();
//                        canvas.setStaticDraw(true);
//                        canvas.repaint();
//                        JOptionPane.showMessageDialog(this, "Анимация остановлена!", "Stop", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                } catch (NullPointerException e) {
//                    JOptionPane.showMessageDialog(this, "Анимация не запущена!", "Ошибка", JOptionPane.ERROR_MESSAGE);
//                }
//            });
//            panel.add(stop);
//
//            JButton update = new JButton("Update");
//            update.addActionListener((event) -> {
//                updateCollection();
//                initList();
//                if (!canvas.isStaticDraw())
//                    stop.doClick();
//                canvas.repaint();
//            });
//            panel.add(update);
//
//            JPanel namePanel = new JPanel();
//            JLabel nameLbl = new JLabel("Введите имя: ");
//            JTextField namefield = new JTextField();
//            namefield.setPreferredSize(new Dimension(100, 20));
//            namePanel.add(nameLbl);
//            namePanel.add(namefield);
//            panel.add(namePanel);
//
//            JPanel colorPanel = new JPanel();
//            JCheckBox blue = new JCheckBox("blue");
//            JCheckBox white = new JCheckBox("white");
//            JCheckBox red = new JCheckBox("red");
//            JCheckBox grey = new JCheckBox("grey");
//            colorPanel.add(grey);
//            colorPanel.add(blue);
//            colorPanel.add(red);
//            colorPanel.add(white);
//            panel.add(colorPanel);
//
////            start.addActionListener((event) -> {
////                if (timer.isRunning()) {
////                    JOptionPane.showMessageDialog(this, "Анимация уже запущена!", "Ошибка", JOptionPane.ERROR_MESSAGE);
////                    return;
////                }
////                List<Pair<PButton, Integer>> animation = new ArrayList<>();
////                canvas.setStaticDraw(false);
////                bList.forEach(n -> {
////                    animation.add(new Pair<>(n, n.getSizeFromEnum(n.psize)));
////
////                });
////                if (animation.isEmpty()) {
////                    JOptionPane.showMessageDialog(this, "Нет подходящих животных!", "Ошибка", JOptionPane.ERROR_MESSAGE);
////                    return;
////                }
////                timer.set((event1)->{
////                    animation.forEach(e->{
////                       e.getKey().setSize(e.getKey().getWidth());
////                    });
////                },100,(event2)->{
////                    animation.forEach(e->{
////                        e.getKey().setSize(e.getKey().getWidth());
////                    });
////                },200);
////            });
//       this.setVisible(true); }
//    }
//
//
//    public static CopyOnWriteArrayList<Pj> getCollection() throws IOException, ClassNotFoundException {
//
//        SocketChannel socketChannel = SocketChannel.open();
//        socketChannel.configureBlocking(true);
//        if (socketChannel.connect(new InetSocketAddress(host, port))) {
//            ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());
//            ObjectOutputStream oos = new ObjectOutputStream(socketChannel.socket().getOutputStream());
//            oos.writeObject("list");
//            Object o = ois.readObject();
//            return (CopyOnWriteArrayList<Pj>) o;
//
//        } else throw new ConnectException();
//    }

}



