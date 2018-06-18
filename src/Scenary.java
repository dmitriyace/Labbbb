import Enums.Emotional;
import Enums.Type;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scenary {

    static Heroes Carlson = new Heroes("Карлсон", Emotional.HAPPY, Type.FAIRY);

    static Heroes Boy = new Heroes("Малыш", Emotional.HAPPY, Type.HUMAN);

    static Heroes Bimbo = new Heroes("Бимбо", Emotional.HAPPY, Type.ANIMAL);
    protected static Heroes heroes;


    public static void main(String[] args) {
    }

    static void starting(ObjectOutputStream out, ObjectInputStream in, CopyOnWriteArrayList<Pj> collection) {
        try {
            Carlson.testChoosing(collection, out, in);
            System.out.println("Все герои одеты. Все начали готовиться ко сну");
            Carlson.preparingProcess(Hero_Pj.h_p, 0, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static String help() {
        String result="";
        result+=("\npshow - отображает элементы коллекции в кастомизированной форме");
        result+=("\npsort - сортирует элементы коллекции");
        result+=("\npin - добавляет элементы в коллекцию");
        result+=("\npst - запускает сюжет");
        result+=("\npsize - показывает размер коллекции");
        result+=("\nprl - удаляет элементы коллекции ниже выбранного");
        result+=("\nprg - удаляет элементы коллекции выше выбранного");
        result+=("\nprv - удаляет элемент по заданному значению");
        result+=("\npout - сохраняет коллекцию в файл");
        result+=("\nph - справочник команд");
        return result;
    }
}