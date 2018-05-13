import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuWindow extends JFrame {

    ////////MAKING DA TREEEEEEE
//    final String[] nodes = new String[]{"Коллекция1", "Коллекция2"};
//    final String[][] leafs = new String[][]{{"Чай", "Кофе", "Коктейль", "Сок", "Морс", "Минералка"},
//            {"Пирожное", "Мороженое", "Зефир", "Халва"}};


//    JPanel panel;
//    MenuWindow thisWindow;

    private static final int width = 1380;
    private static final int height = 768;
    MenuWindow thisOne = this;

    JButton in = new JButton("load pyjamas");
    JButton save = new JButton("save tree");
    JButton addBtn = new JButton("add pyjama");
    JButton delete = new JButton("delete pyjama");
    JButton show = new JButton("show");
    JLabel empty = new JLabel("e");


    MenuWindow() {
        super("Menu");
        PjCollection.commands("in");
        CopyOnWriteArrayList<Pj> collection = PjCollection.pjeys;
        String saveProperties[] = new String[collection.size()];

        int j = 0;
        for (Pj pj : collection) {
            saveProperties[j] = pj.name + "(id" + pj.id + ")";
            j++;
        }

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Collection");

        DefaultMutableTreeNode leaf = new DefaultMutableTreeNode();
        for (int i = 0; i < saveProperties.length; i++)
            leaf.add(new DefaultMutableTreeNode(saveProperties[i], false));
        this.setVisible(true);
        this.setBounds(100, 10, width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTree collectionTree = new JTree(new DefaultTreeModel(leaf, true));


        JPanel panel1 = new JPanel(new GridLayout(7, 1));
        JPanel panel2 = new JPanel(new FlowLayout());
//        save.setPreferredSize(new Dimension(40,40));
//        delete.setPreferredSize(new Dimension(40,40));
//        in.setPreferredSize(new Dimension(40,40));
//        empty.setPreferredSize(new Dimension(40,40));
//        addBtn.setPreferredSize(new Dimension(40,40));
//        show.setPreferredSize(new Dimension(40,40));
        panel1.add(save);
        panel2.add(collectionTree);
        panel1.add(delete);
        panel1.add(in);
        panel1.add(addBtn);
        panel1.add(show);
//        panel.add(new JScrollPane(tree2));

        this.add(panel1, BorderLayout.WEST);
        this.add(panel2, BorderLayout.CENTER);

        addBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                MenuWindow.super.setEnabled(false);
                new ActAdd(thisOne);
            }
        });
    }

    //
//    void addButtons(JPanel panel) {
//        JPanel jPanel = new JPanel();
//        jPanel.setLayout(new GridLayout(5, 5));
//
//        addBtn.setPreferredSize(new Dimension(120, 120));
//        jPanel.add(addBtn);
//        jPanel.add(new Label("mmmmmmmm"));
//
//    }
    public static void main(String... args) {
        MenuWindow m = new MenuWindow();
    }

}
