package GUI;

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

    JButton checkBtn = new JButton();


    public AuthWindow() {
        this.setVisible(true);
        this.setBounds(100, 100, width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(Auth);

        addField(panel, logL, logTF);
        addField(panel, passL, passwordField);
        checkBtn.setText("OK");
        panel.add(checkBtn);
        actCheckBtn();
        this.setContentPane(panel);
    }

    static void addField(JPanel jPanel, JLabel lbl, JComponent cmp) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(lbl);
        cmp.setPreferredSize(new Dimension(200, 70));
        panel.add(cmp);
        jPanel.add(panel);
    }

    void actCheckBtn() {

        checkBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String password = "ss";
                String enteredPassword = new String(passwordField.getPassword());

                if (password.equals(enteredPassword) && logTF.getText().equals("d")) {
                    AuthWindow.super.setVisible(false);
                    new MenuWindow();
                } else {
                    checkBtn.setText("whatcha problem man??");
                }

            }
        });
    }

    public static void main(String... args) {
        new AuthWindow();
    }
}
