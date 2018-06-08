import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.pushingpixels.trident.*;

import javax.swing.*;


class test extends JFrame {
    public test() {
        JButton button = new JButton("sample");
        button.setForeground(Color.blue);

        this.setLayout(new FlowLayout());
        this.add(button);

        final Timeline rolloverTimeline = new Timeline(button);
        rolloverTimeline.addPropertyToInterpolate("foreground", Color.blue, new Color(192,192,192));
        rolloverTimeline.setDuration(2000);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rolloverTimeline.play();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rolloverTimeline.playReverse();
            }
        });

        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new test().setVisible(true));
    }
}
