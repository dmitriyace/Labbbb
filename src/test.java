import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
//import java.util.Timer;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

class MyTimerTask extends TimerTask {
    boolean ok = true;

//    public static void main(String[] args) {
//        final Timer time = new Timer();
//
//        time.schedule(new TimerTask() {
//            int i = 0;
//            @Override
//            public void run() { //ПЕРЕЗАГРУЖАЕМ МЕТОД RUN В КОТОРОМ ДЕЛАЕТЕ ТО ЧТО ВАМ НАДО
//                if(i>=2){
//                    System.out.println("Таймер завершил свою работу");
//                    time.cancel();
//                    return;
//                }
//                System.out.println("Прошло 4 секунды");
//                i = i + 1;
//            }
//        }, 1000, 1000); //(4000 - ПОДОЖДАТЬ ПЕРЕД НАЧАЛОМ В МИЛИСЕК, ПОВТОРЯТСЯ 4 СЕКУНДЫ (1 СЕК = 1000 МИЛИСЕК))
//    }

    public static void main(String[] args) {
        TimerTask task = new MyTimerTask();
        Timer t = new Timer(true);
        t.scheduleAtFixedRate(task, 0, 10000);
        System.out.println("str");
        t.cancel();
//        int r = 255;
//        int g = 130;
//        int b = 25;
//        int grey = 192;
//
//        ActionListener al = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int r = 0;
//                if (r >= 255)
//                    System.out.println(r += 15);
//            }
//        };
//        Timer t = new Timer(20, al);
//        t.start();
//        System.out.println("vse");
    }

    @Override
    public void run() {
//        if(ok)
        System.out.println("lalalala");

    }
}
//        int colorGrey=new Color(192, 192, 192).getRGB();
//        System.out.println(colorGrey);
//        int colorRed = new Color(255, 0, 0).getRGB();
//        int colorBlue = new Color(0, 0, 255).getRGB();
//
//        int colorWhite = new Color(255, 255, 255).getRGB();
//        System.out.println(colorBlue);
//        System.out.println(colorRed);
//        System.out.println(colorWhite);
//
//        System.out.println("/////////////////");
//        System.out.println(colorBlue-colorGrey);
//        System.out.println(colorRed-colorGrey);
//        System.out.println(colorWhite-colorGrey);
//        System.out.println("/////////////////");
//
//        System.out.println((colorBlue-colorGrey)/2000);
//        System.out.println((colorRed-colorGrey)/2000);
//        System.out.println((colorWhite-colorGrey)/2000);
//
//        System.out.println("///////////");
//
//        System.out.println(((colorBlue-colorGrey)/2000)*2000+colorGrey-colorBlue);
//        System.out.println(((colorRed-colorGrey)/2000)*2000+colorGrey-colorRed);
//        System.out.println(((colorWhite-colorGrey)/2000)*2000+colorGrey-colorWhite);


//        test gui = new test();
//        gui.go();
////        CopyOnWriteArrayList<Pj> cor = new CopyOnWriteArrayList<>();
////        ArrayList<Pj> al = new ArrayList<>();
////
////        cor.add(Pj.defaultPj);
////
////        Pj p=new Pj("KEK",Enums.EPj.OK, Enums.EPjc.UNWASHED, Enums.Location.NEAR_BED, Enums.ColorsEnum.WHITE, 44444444) ;
////
////        cor.add(p);
////        al = (ArrayList<Pj>) cor.stream().collect(Collectors.toList());
////        System.out.println(al.contains(Pj.defaultPj));
////        System.out.println(al.contains(p));
////al.stream().filter(n->n.epj.equals(Enums.EPj.OK)).forEach(n->System.out.println(n.name));
//
//
//    }
//
//    public void go() {
//        JFrame jf = new JFrame();
//        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        DrawPanel dp = new DrawPanel();
//        jf.getContentPane().add(dp);
////        JPanel j = new JPanel();
//        jf.setVisible(true);
//        jf.setSize(300, 300);
////        jf.setResizable(false);
//
//
//        for (int i = 0; i < 130; i++) {
//            x++;
//            y++;
//            dp.repaint();
//            try {
//                Thread.sleep(0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    class DrawPanel extends JPanel {
//        @Override
//        public void paintComponent(Graphics g) {
//            g.setColor(Color.CYAN);
//            g.fillRect(x, y, w, h);
//        }
//    }
//    }
//
//}

//import java.awt.AlphaComposite;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.geom.Rectangle2D;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//class ColorFadingAnimation extends JPanel {
//    private Rectangle2D rect = new Rectangle2D.Float(20f, 20f, 80f, 50f);
//
//    private float alpha_rectangle = 1f;
//
//    public ColorFadingAnimation() {
//        new RectRunnable();
//    }
//
//    public void paint(Graphics g) {
//        super.paint(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(50, 50, 50));
//        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g2d.setRenderingHints(rh);
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha_rectangle));
//        g2d.fill(rect);
//    }
//
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Color fading aniamtion");
//        frame.add(new ColorFadingAnimation());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(250, 150);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//
//    class RectRunnable implements Runnable {
//        private Thread runner;
//
//        public RectRunnable() {
//            runner = new Thread(this);
//            runner.start();
//        }
//
//        public void run() {
//            while (alpha_rectangle >= 0) {
//                repaint();
//                alpha_rectangle += -0.01f;
//
//                if (alpha_rectangle < 0) {
//                    alpha_rectangle = 0;
//                }
//                try {
//                    Thread.sleep(50);
//                } catch (Exception e) {
//                }
//            }
//        }
//    }
