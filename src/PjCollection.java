import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;
import Enums.Location;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

class PjCollection implements Serializable {

    public static CopyOnWriteArrayList<Pj> pjeys = new CopyOnWriteArrayList<>();
    static Pj pj_save;


//    protected static void commands(String command, CopyOnWriteArrayList<Pj> col, ObjectOutputStream out, ObjectInputStream in) throws IOException {
//        String[] commands = {"sort", "show", "s", "size", "remove_lower", "remove_greater",
//                "remove_by_value", "out", "help", "q", "in"};
//        int i;
//        for (i = 0; i < commands.length; i++) if (command.startsWith(commands[i])) break;
//        switch (i) {
//            case 0:
//                PjCollection.pjeysSrt(pjeys);
//                break;
//            case 1:
//                PjCollection.show(pjeys,out);
//                break;
//            case 2:
//                Scenary.starting(out, in);
//                break;
//            case 3:
//                out.writeObject(PjCollection.pjeys.size());
//                break;
//            case 4:
//                PjCollection.getElemByString(command);
//                PjCollection.removeLower(PjCollection.pj_save);
//                break;
//            case 5:
//                PjCollection.getElemByString(command);
//                PjCollection.removeGreater(PjCollection.pj_save);
//                break;
//            case 6:
//                PjCollection.getElemByString(command);
//                PjCollection.remove(PjCollection.pj_save);
//                break;
//            case 7:
////                String path_save = "C:\\Users\\chist\\Documents\\itmo\\proga\\Labbbb\\src\\Output.txt";
//                String path_save = "D:\\0лабы\\Программирование(вуз)\\6\\Labbbb\\src\\Output.txt";
//                Output.save(path_save, PjCollection.pjeys);
//                break;
//            case 8:
//                Scenary.help();
//                break;
//            case 9:
//                System.exit(0);
//                break;
//            case 10:
////                String path = "C:\\Users\\chist\\Documents\\itmo\\proga\\Labbbb\\src\\form.xml";
//                String path = "D:\\0лабы\\Программирование(вуз)\\6\\Labbbb\\src\\form.xml";
//                In.getPjeys(path, PjCollection.pjeys);
//                break;
//            default:
//                System.out.println("command is Illegal");
//        }
//
//
//    }

    protected static void show(CopyOnWriteArrayList<Pj> collectionName, ObjectOutputStream out) {
///ВЕРНУТЬСЯ К ЭТОМУ
        String answer = "";
        collectionName.
                forEach(n -> PjCollection.showOut(n, out));
        try {
            out.writeObject("end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void getAnswer(String s, Pj pj) {
        s += formatOut(pj) + "\n";
    }

    static void showOut(Pj pj, ObjectOutputStream out) {
        try {
            out.writeObject(formatOut(pj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pjeysSrt(CopyOnWriteArrayList<Pj> pjs) {
        Collections.sort(pjs, (Pj p1, Pj p2) -> p1.name.compareTo(p2.name));

    }


    /**
     * <p>Удаляет из коллекции все элементы, которые меньше заданного</p>
     * <p>Меньше данного считаются элементы, параметр id которых меньше параметра id данного элемента</p>
     *
     * @param pj Объект класса Pj, требующий удаления из коллекции элементов меньше себя
     */
    public static void removeLower(Pj pj, CopyOnWriteArrayList<Pj> collection) {
        collection = collection.stream().filter(n -> n.compareTo(pj) > -1).collect(Collectors.toCollection(CopyOnWriteArrayList<Pj>::new));
//        PjCollection.pjeys.retainAll(pjeys.stream().filter(n -> n.compareTo(pj) > -1).collect(Collectors.toList()));
//        ArrayList<Pj> pjArrayList = new ArrayList<Pj>();
//
//        for (Pj pjiteratored : pjeys) {
//            if (pj.compareTo(pjiteratored) == -1) {
//                pjArrayList.add(pjiteratored);
//            }
//        }
//        for (Pj pjiteratored : pjArrayList) {
//            pjeys.remove(pjiteratored);
//        }
    }


    /**
     * <p>Удаляет из коллекции все элементы, которые больше заданного</p>
     * <p>Больше данного считаются элементы, параметр id которых большк параметра id данного элемента</p>
     *
     * @param pj Объект класса Pj, требующий удаления из коллекции элементов больше себя
     */
    public static void removeGreater(Pj pj, CopyOnWriteArrayList<Pj> collection) {
        collection = pjeys.stream().filter(n -> n.compareTo(pj) == -1).collect(Collectors.toCollection(CopyOnWriteArrayList<Pj>::new));
//        ArrayList<Pj> pjArrayList = new ArrayList<Pj>();
//
//        for (Pj pjiteratored : pjeys) {
//            if (pj.compareTo(pjiteratored) == 1) {
//                pjArrayList.add(pjiteratored);
//            }
//        }
//        for (Pj pjiteratored : pjArrayList) {
//            pjeys.remove(pjiteratored);
//        }
    }

    /**
     * <p>Удаляет из коллекции все элементы, эквивалентные данному.</p>
     * <p>Эквивалентным данному считается элемент, значение id которого эквивалентно значению id данного</p>
     *
     * @param pj Объект класса Pj, требующий удаления из коллекции
     */
    public static void remove(Pj pj, CopyOnWriteArrayList<Pj> collection) {
        collection = collection.stream().filter(n -> n.compareTo(pj) != 0).collect(Collectors.toCollection(CopyOnWriteArrayList<Pj>::new));
//        pjeys.remove(pj);

    }

    public static void getElemByString(String line) {
        pj_save = Pj.defaultPj;
        Scanner scan_element = new Scanner(line);
        scan_element.useDelimiter("\",");
        String poisk = scan_element.findWithinHorizon("epj\":\"", 100);
        String size = scan_element.next().trim().toUpperCase();
        poisk = scan_element.findWithinHorizon("epjc\":\"", 30);
        String clearance = scan_element.next().trim().toUpperCase();
        poisk = scan_element.findWithinHorizon("name\":\"", 30);
        String name = scan_element.next().trim().toUpperCase();
        poisk = scan_element.findWithinHorizon("loca\":\\{\"name\":\"", 30);
        String location = scan_element.next().trim().toUpperCase();
        poisk = scan_element.findWithinHorizon("color\":\"", 40);
        String color = scan_element.next().trim().toUpperCase();
        poisk = scan_element.findWithinHorizon("dt\":\"", 40);
        String date = scan_element.next().trim().toUpperCase();
        poisk = scan_element.findWithinHorizon("id\":", 40);
        scan_element.useDelimiter("}");
        String id = scan_element.next().trim().toUpperCase();
        scan_element.close();

        pj_save.epj = EPj.valueOf(size.trim().toUpperCase());
        pj_save.epjc = EPjc.valueOf(clearance.trim().toUpperCase());
        pj_save.loca = Location.valueOf(location.trim().toUpperCase());
        pj_save.color = ColorsEnum.valueOf(color.trim().toUpperCase());
        pj_save.id = (Integer.parseInt(id));
        pj_save.name = name;
        pj_save.dt = date;

    }

    protected static String formatOut(Pj pj) {
        StringBuilder stb = new StringBuilder();
        stb.append("Name of Pj - " + pj.name);
        stb.append("size of pijama: ");
        stb.append(pj.epj.toString().toLowerCase() + ". ");
        stb.append("color: ");
        stb.append(pj.color.toString().toLowerCase() + ". ");
        stb.append(pj.epjc.toString().toLowerCase().equals("washed") ? "pijama is clear" :
                "pijama is dirty");
        stb.append("location of pijama is " + pj.loca.toString().toLowerCase() + ". ");
        stb.append("data of creation: " + pj.dt);
//        System.out.println(stb.toString());
        return stb.toString();

    }
}


