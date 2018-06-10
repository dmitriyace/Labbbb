
import sun.nio.ch.WindowsSelectorProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AuthWindow extends JFrame {


    final static int width = 500;
    final static int height = 500;
    JLabel Auth = new JLabel("enter login and pass");
    JLabel logL = new JLabel("Login:");
    JTextField logTF = new JTextField("");
    JLabel passL = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField();
    static ServerWindow serverGUI;
    JButton checkBtn = new JButton();


        public AuthWindow(ServerWindow parent) {
//    public AuthWindow() {

        this.setBounds(100, 100, width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(Auth);

        addField(panel, logL, logTF);
        addField(panel, passL, passwordField);
        checkBtn.setText("log in");
        panel.add(checkBtn);

        checkBtn.addMouseListener(new MouseAdapter() {
                                      @Override
                                      public void mouseClicked(MouseEvent e) {
                                          super.mouseClicked(e);

                                          String password = "s";
                                          String enteredPassword = new String(passwordField.getPassword());

                                          if (password.equals(enteredPassword) && logTF.getText().equals("s")) {
                                              setVisible(false);
//                                              Server1.go();
                                                parent.setVisible(true);
//                                              new ServerWindow().setVisible(true);
                                          } else {
                                              Auth.setText("please write correct login and password");
                                              checkBtn.setText("try press me again");
                                          }
                                      }

                                  }
        );

        this.setContentPane(panel);
        this.setVisible(true);
//        ServerWindow s = new ServerWindow();
//        s.setVisible(true);

    }

    static void addField(JPanel jPanel, JLabel lbl, JComponent cmp) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(lbl);
        cmp.setPreferredSize(new Dimension(200, 70));
        panel.add(cmp);
        jPanel.add(panel);
    }


//    public static void main(String... args) {
//        new AuthWindow();
//    }
}
