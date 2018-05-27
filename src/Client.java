import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    JTextField tosend;
    JTextArea incoming;
    ObjectInputStream reader;
    ObjectOutputStream writer;
    Socket socket;
    String msg;
    String answer;
//    Boolean connected;

    public void go() {
        JFrame frame = new JFrame("Chat Client");
        JPanel panel = new JPanel();
        incoming = new JTextArea(15, 10);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane jScroller = new JScrollPane(incoming);
        jScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        tosend = new JTextField(20);
        JButton btn = new JButton("Send");
        btn.addActionListener(new SendBtnListener());
        panel.add(jScroller);
        panel.add(tosend);
        panel.add(btn);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(410, 500);
        frame.setVisible(true);

        setUpNetwork();
        Thread readThread = new Thread(new IncomingReader());
        readThread.start();
    }

    public class IncomingReader implements Runnable {

        @Override
        public void run() {

            String message;
            Object o;
            boolean end = false;
            try {

                while ((o = reader.readObject()) != null) {
                    if (o instanceof String) {
                        message = (String) o;
                        analyseInput(message, incoming);

                    }
                }
            } catch (ConnectException ce) {
                System.err.println("not connected");
            } catch (IOException ex) {
                incoming.append("IO exception");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public void setUpNetwork() {
        try {

            socket = new Socket("localhost", 1111);
            if (socket.isConnected()) {
                writer = new ObjectOutputStream(socket.getOutputStream());
                reader = new ObjectInputStream(socket.getInputStream());
                System.out.println("setted up network");
            } else throw new ConnectException();
        } catch (UnknownHostException e) {
            System.err.println("Host is incorrect");
        } catch (IOException e) {
            System.err.println("IO exception has come to us");
        }
    }

    public class SendBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                msg = tosend.getText();
                writer.writeObject(msg);
                writer.flush();
//                writer.println(tosend.getText());
//                writer.flush();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            tosend.setText("");
            tosend.requestFocus();

        }
    }

    static boolean analyseInput(String s, JTextArea jta) {
        if (s.startsWith("cont")) {
            s.substring(0, 3);
            jta.append(s);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String... args) {
        new Client().go();
    }
}
