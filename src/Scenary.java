import Enums.Emotional;
import Enums.Type;

import java.util.Scanner;

public class Scenary {

    static Heroes Carlson = new Heroes("Карлсон", Emotional.HAPPY, Type.FAIRY);

    static Heroes Boy = new Heroes("Малыш", Emotional.HAPPY, Type.HUMAN);

    static Heroes Bimbo = new Heroes("Бимбо", Emotional.HAPPY, Type.ANIMAL);
    protected static Heroes heroes;


    public static void main(String[] args) {
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                String path_save = "C:\\Users\\chist\\Documents\\itmo\\proga\\Lab3\\src\\Output.txt";
//                Output.save(path_save, PjCollection.pjeys);
//            }
//        });

        Scanner scn = new Scanner(System.in);
//        String path = "D:\\0лабы\\Программирование(вуз)\\6\\Labbbb\\src\\form.xml";
        String path = "C:\\Users\\chist\\Documents\\itmo\\proga\\Lab3\\src\\form.xml";
        In.getPjeys(path, PjCollection.pjeys);
//        PjCollection p = new PjCollection();
        String path_save = "C:\\Users\\chist\\Documents\\itmo\\proga\\Lab3\\src\\Output.txt";
//        String path_save="D:\\0лабы\\Программирование(вуз)\\6\\Labbbb\\src\\Output.txt";
        Output.save(path_save, PjCollection.pjeys);


//        help();
        starting();

//        try {
//            while (true) {
//                System.out.println("enter the move");
//                String command = scn.nextLine();
//                if (command.equals("sort")) PjCollection.pjeysSrt();
//                else if (command.equals("show")) PjCollection.show();
//                else if (command.equals("start")) starting();
//                else if (command.equals("remove_first")) PjCollection.removeFirst();
//                else if (command.equals("size")) System.out.println(PjCollection.pjeys.size());
//                else if (command.startsWith("remove_lower") || command.startsWith("rl")) {
//                    PjCollection.getElemByString(command);
//                    PjCollection.removeLower(PjCollection.pj_save);
//                } else if (command.startsWith("remove_greater")) {
//                    PjCollection.getElemByString(command);
//                    PjCollection.removeGreater(PjCollection.pj_save);
//
//                } else if (command.startsWith("remove_by_value")) {
//                    Scanner scan_element = new Scanner(command);
//                    PjCollection.getElemByString(command);
//                    PjCollection.remove(PjCollection.pj_save);
//                } else if (command.equals("out")) {
//// String path_save = "D:\\PROGAYU\\Eclipseeeee\\Lab3\\src\\Output.txt";
//                    Output.save(path_save, PjCollection.pjeys);
//                } else if (command.equals("help")) help();
//                else if (command.equals("q"))
//                    System.exit(0);
//
//
//            }
//        } catch (NoSuchElementException nse) {
//            System.out.println("element_not_found");
//        }

    }

    static void starting() {
        Carlson.choosingPj(PjCollection.pjeys);
//        Boy.choosingPj(PjCollection.pjeys);
//        Bimbo.choosingPj(PjCollection.pjeys);
        System.out.println("Все герои одеты. Все начали готовиться ко сну");
        Carlson.preparingProcess(Hero_Pj.h_p, 0);
//        Boy.preparingProcess(Hero_Pj.h_p, 1);
//        Bimbo.preparingProcess(Hero_Pj.h_p, 2);
    }

    static void help() {
        System.out.println("show - отображает элементы коллекции в кастомизированной форме");
        System.out.println("start - запускает сюжет");
        System.out.println("size - показывает размер коллекции");
        System.out.println("remove_lower - удаляет элементы коллекции ниже выбранного");
        System.out.println("remove_greater - удаляет элементы коллекции выше выбранного");
        System.out.println("remove_by_value - удаляет элемент по заданному значению");
        System.out.println("out - сохраняет коллекцию в файл");
        System.out.println("help - справочник команд");
    }
}