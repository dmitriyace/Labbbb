import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Scenary {

    // �������� �������� ����� ��� ������� ���������
    static Pj CPj;
    static Pj BoyPj;
    static Pj BimPj;
    Heroes Carlson = new Heroes("�������", Emotional.HAPPY, Type.FAIRY);
    // �������� �������� ����������

    static Heroes Boy = new Heroes("�����", Emotional.HAPPY, Type.FAIRY);
    static Heroes Bimbo;

    public static void main(String[] args) throws ExcFall {


        Scanner scn = new Scanner(System.in);
        starting();

        while (true) {
            System.out.println("enter the move");
            String line = "lc";
            if (line.equals("sort")) PjCollection.pjeysSrt();
            else if (line.equals("lc") || line.equals("lb") || line.equals("lbi")) {


                String path = "C:\\Users\\chist\\Documents\\����\\�����\\Lab3\\src\\form.xml";
                switch (line) {
                    case "lc":
                        In.getPjeys(path, "UTF-8", Scenary.Boy, PjCollection.pjeys);
                        break;
                    case "lb":
                        In.getPjeys(path, "UTF-8", Boy, PjCollection.pjeys);
                        break;
                    case "lbi":
                        In.getPjeys(path, "UTF-8", Bimbo, PjCollection.pjeys);
                        break;
                }

            } else if (line.equals("save")) {

            } else if (line.equals("show")) {
                PjCollection.show();
            } else if (line.equals("go")) {

            } else if (line.equals("remove_first")) {

            }
        }


    }

    static void starting() throws ExcFall {
//        System.out.println("������� ������...");
//        Heroes Carlson = new Heroes("�������", Emotional.HAPPY, Type.FAIRY) {
//            public String toString() {
//                return  name;
//            }
//        };
//        System.out.println(Carlson.toString());
//        Boy = new Heroes("�����", Emotional.HAPPY, Type.HUMAN) {
//            public String toString() {
//                return  name;
//            }
//        };
//        System.out.println(Boy.toString());
//        Bimbo = new Heroes("�����", Emotional.HAPPY, Type.ANIMAL) {
//            public String toString() {
//                return  name;
//            }
//        };
//        System.out.println(Bimbo.toString());

//		System.out.println();
//		System.out.println("������� ������: ");
//
//		CPj = new Pj(EPj.SHORT, EPjc.WASHED);
//		BoyPj = new Pj(EPj.SHORT, EPjc.WASHED);
//		BimPj = new Pj(EPj.OK, EPjc.WASHED);


//        System.out.println();
//        System.out.println("��� ������ ���������� �� ���");
//        System.out.println();
//        Carlson.preparingProcess(Carlson, CPj);
//        Boy.preparingProcess(Boy, BoyPj);
//        Bimbo.preparingProcess(Bimbo, BimPj);
//
//        System.out.printf("%n �������, �� ���� �������. ��� ������ �� ���.");

    }

}
