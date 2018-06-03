import javax.swing.*;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientWindow extends JFrame {


    ClientWindow(){
        super("Hello World!");
        CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
        File file = new File(".\\form.xml");
        String path = file.getAbsolutePath();
        In.getPjeys(path,collection);


    }

}
