import sun.swing.FilePane;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientSideWithLabels {
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    String name = "";
    boolean opaque = false;
    Color color = Color.black;
    public static void main(String[] args) {
        new ClientSideWithLabels();
    }

    public void go(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        frame.getContentPane().add(mainPanel);

        CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
        File file = new File(".\\form.xml");
        String path = file.getAbsolutePath();
        In.getPjeys(path, collection);
        frame.setSize(500, 500);

        for (Pj pj: collection){

        }
    }
}
