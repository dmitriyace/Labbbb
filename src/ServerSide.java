import com.sun.security.ntlm.Server;

import javax.swing.*;

public class ServerSide extends JFrame{
    ServerSide thisOne = this;

    ServerSide(){
        super("Server SIDE");




        Server1 s1 = new Server1();
//        s1.go();

    }


    public static void main(String...args){
        new ServerSide();
    }

}
