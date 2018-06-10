
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ActAdd extends JFrame {
    final static int width = 800;
    final static int height = 500;

    boolean succeed;
    String checkFields;
    JLabel titleLabel = new JLabel("fill object properties");
    JLabel empty = new JLabel();
    JButton checkBtn = new JButton("check and add");
    JButton cancel = new JButton("cancel");
    CopyOnWriteArrayList<Pj> checkCollection = new CopyOnWriteArrayList<>();

    ServerWindow parent;

    JTextField[] fields = {
            new JTextField(""),
            new JTextField(""),
            new JTextField(""),
            new JTextField(""),
            new JTextField(""),
            new JTextField("")
    };

    public ActAdd(ServerWindow parent, CopyOnWriteArrayList<Pj> collection) {
        super("Adding new pyjama");

        this.parent = parent;
        parent.setEnabled(false);
        parent.setVisible(true);
        checkCollection = collection;
        this.setVisible(true);
        this.setBounds(100, 10, width, height);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 2, 1));
        panel.add(titleLabel);

        JLabel[] labels = {
                new JLabel("Name (any you want)"),
                new JLabel("Size (ok, long, short)"),
                new JLabel("Clearance (washed, unwashed)"),
                new JLabel("Wardrobe location (near_bed, living_room, near_kitchen, ss)"),
                new JLabel("Color (red, blue, white, grey)"),
                new JLabel("id (may be any, but not already taken)")};

        panel.add(empty);
        for (int i = 0; i < labels.length; i++) {
            panel.add(labels[i]);
            panel.add(fields[i]);
        }
        panel.add(checkBtn);
        panel.add(cancel);
        this.setContentPane(panel);
        checkBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Pj pj;
                try {
                    pj = PjCollection.getElemByString(makeElem(fields));
                    checkCollection = checkCollection.stream().filter(n -> n.compareTo(pj) == 0).collect(Collectors.toCollection(CopyOnWriteArrayList::new));

                    ActAdd.super.setVisible(false);
                    collection.add(pj);
                    parent.refreshTree();
                    parent.setVisible(true);
                    parent.setEnabled(true);


                } catch (NoSuchElementException nnn) {
                    titleLabel.setText("Set correct object properties!");
                    titleLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
                }

            }
        });
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ActAdd.super.setVisible(false);
                parent.setVisible(true);
                parent.setEnabled(true);

            }
        });
    }

    public String makeElem(JTextField[] fields) {
        checkFields = (
                "epj\":\"" + fields[1].getText() + "\"," +
                        "epjc\":\"" + fields[2].getText() + "\"," +
                        "name\":\"" + fields[0].getText() + "\"," +
                        "loca\":\"" + fields[3].getText() + "\"," +
                        "color\":\"" + fields[4].getText() + "\"," +
                        "dt\":\"" + new SimpleDateFormat("SSS").format(new Date()) + "\"," +
                        "id\":" + fields[5].getText() + "}");
        return checkFields;
    }


//        public static void main (String...args){
//            CopyOnWriteArrayList<Pj> collection = new CopyOnWriteArrayList<>();
//            File file = new File(".\\form.xml");
//            String path = file.getAbsolutePath();
//            In.getPjeys(path, collection);
//            new ActAdd(new ServerWindow(), collection);
//
//        }
}
