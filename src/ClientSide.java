


import Enums.ColorsEnum;
import Enums.EPj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientSide {
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    String name = "";
    boolean opaque = false;
    Color color = Color.black;

    public static void main(String... args) {
        new ClientSide().go();
    }

    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel dp = new JPanel();
        frame.getContentPane().add(dp);

        CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
        File file = new File(".\\form.xml");
        String path = file.getAbsolutePath();
        In.getPjeys(path, collection);

        frame.setSize(500, 500);
        frame.setVisible(true);
        opaque = true;
        for (Pj pj : collection) {
            Rects rect = new Rects();
            x = pj.loca.getX();
            y = pj.loca.getY();
            name = pj.name;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            color = getColorFromEnum(pj.color);
            width = getSizeFromEnum(pj.epj);
            height = (int) 1.8 * width;

        }
        opaque = false;
        System.out.println("ooo");


    }

    //////////////////////////////////////
    class PaintButton extends JButton {
        private Rectangle rectangle = new Rectangle();

        public PaintButton() {
            rectangle.setBounds(x, y, width, height);

//            addMouseListener(new MouseMotionListener(){
//                @Override
//                public void mouseDragged(MouseEvent e) {
//
//                }
//
//                @Override
//                public void mouseMoved(MouseEvent e){
//                    if (rectangle.contains(e.getPoint())){
//                        setToolTipText("ggg");
//                    }
//                    ToolTipManager.sharedInstance().mouseMoved(e);
//                }
//
//            });
            setToolTipText(name);
        }

//        private void addMouseListener(MouseMotionListener mouseMotionListener) {
//
//        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setToolTipText(name);

            if (opaque == true) {
                g.setColor(color);
                g.fillRect(x, y, width, height);
            }
        }


    }

    public void fillEverything(CopyOnWriteArrayList<Pj> col, Graphics g) {
        col.stream().forEach(n -> fillStream(n, g));
    }

    public void fillStream(Pj pj, Graphics g) {
        g.setColor(getColorFromEnum(pj.color));
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

class Rects extends JComponent {
    private LinkedList<MyRect> myRects = new LinkedList<>();
    private Color color;

    static class MyRect {
        int x;
        int y;
        int width;
        int height;

        MyRect(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
        }
    }

    void addRect(int x, int y, int w, int h, Color color) {
        myRects.add(new MyRect(x, y, w, h));
        this.color = color;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(1.0f));
        myRects.forEach(n -> graphics2D.drawRect(n.x, n.y, n.width, n.height));
    }
}