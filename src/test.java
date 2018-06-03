import javax.swing.*;
import java.awt.*;

public class test {
    static int x = 70;
    static int y = 70;
    Integer w = 20;
    Integer h = 40;

    public static void main(String... args) {
        test gui = new test();
        gui.go();
//        CopyOnWriteArrayList<Pj> cor = new CopyOnWriteArrayList<>();
//        ArrayList<Pj> al = new ArrayList<>();
//
//        cor.add(Pj.defaultPj);
//
//        Pj p=new Pj("KEK",Enums.EPj.OK, Enums.EPjc.UNWASHED, Enums.Location.NEAR_BED, Enums.ColorsEnum.WHITE, 44444444) ;
//
//        cor.add(p);
//        al = (ArrayList<Pj>) cor.stream().collect(Collectors.toList());
//        System.out.println(al.contains(Pj.defaultPj));
//        System.out.println(al.contains(p));
//al.stream().filter(n->n.epj.equals(Enums.EPj.OK)).forEach(n->System.out.println(n.name));


    }

    public void go() {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        DrawPanel dp = new DrawPanel();
        jf.getContentPane().add(dp);
//        JPanel j = new JPanel();
        jf.setVisible(true);
        jf.setSize(300, 300);
//        jf.setResizable(false);


        for (int i = 0; i < 130; i++) {
            x++;
            y++;
            dp.repaint();
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class DrawPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.CYAN);
            g.fillRect(x, y, w, h);
        }
    }


}
