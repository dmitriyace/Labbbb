import Enums.Emotional;
import Enums.Type;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scenary {

    static Heroes Carlson = new Heroes("�������", Emotional.HAPPY, Type.FAIRY);

    static Heroes Boy = new Heroes("�����", Emotional.HAPPY, Type.HUMAN);

    static Heroes Bimbo = new Heroes("�����", Emotional.HAPPY, Type.ANIMAL);
    protected static Heroes heroes;


    public static void main(String[] args) {
    }

    static void starting(ObjectOutputStream out, ObjectInputStream in, CopyOnWriteArrayList<Pj> collection) {
        try {
            Carlson.testChoosing(collection, out, in);
            System.out.println("��� ����� �����. ��� ������ ���������� �� ���");
            Carlson.preparingProcess(Hero_Pj.h_p, 0, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static String help() {
        String result="";
        result+=("\npshow - ���������� �������� ��������� � ����������������� �����");
        result+=("\npsort - ��������� �������� ���������");
        result+=("\npin - ��������� �������� � ���������");
        result+=("\npst - ��������� �����");
        result+=("\npsize - ���������� ������ ���������");
        result+=("\nprl - ������� �������� ��������� ���� ����������");
        result+=("\nprg - ������� �������� ��������� ���� ����������");
        result+=("\nprv - ������� ������� �� ��������� ��������");
        result+=("\npout - ��������� ��������� � ����");
        result+=("\nph - ���������� ������");
        return result;
    }
}