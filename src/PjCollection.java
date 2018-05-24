import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;
import Enums.Location;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

class PjCollection implements Serializable {

    public static CopyOnWriteArrayList<Pj> pjeys = new CopyOnWriteArrayList<>();
    static Pj pj_save;


    protected static String commands(String command) {
        String[] commands = {"sort", "show", "start", "size", "remove_lower", "remove_greater",
                "remove_by_value", "out", "help", "q", "in"};
        String kekery = null;
        int i;
        for (i = 0; i < commands.length; i++) if (command.startsWith(commands[i])) break;
        switch (i) {
            case 0:
                PjCollection.pjeysSrt(pjeys);
                break;
            case 1:
                PjCollection.show(new CopyOnWriteArrayList<Pj>());
                break;
            case 2:
                Scenary.starting();
                break;
            case 3:
                System.out.println(PjCollection.pjeys.size());
                break;
            case 4:
                PjCollection.getElemByString(command);
                PjCollection.removeLower(PjCollection.pj_save);
                break;
            case 5:
                PjCollection.getElemByString(command);
                PjCollection.removeGreater(PjCollection.pj_save);
                break;
            case 6:
                PjCollection.getElemByString(command);
                PjCollection.remove(PjCollection.pj_save);
                break;
            case 7:
                String path_save = "C:\\Users\\chist\\Documents\\itmo\\proga\\Labbbb\\src\\Output.txt";
//                String path_save = "D:\\0����\\����������������(���)\\6\\Labbbb\\src\\Output.txt";
                Output.save(path_save, PjCollection.pjeys);
                break;
            case 8:
                Scenary.help();
                break;
            case 9:
                System.exit(0);
                break;
            case 10:
                String path = "C:\\Users\\chist\\Documents\\itmo\\proga\\Labbbb\\src\\form.xml";
//                String path = "D:\\0����\\����������������(���)\\6\\Labbbb\\src\\form.xml";
                In.getPjeys(path, PjCollection.pjeys);
                break;
            default:
                System.out.println("command is Illegal");
        }
        return kekery;

    }

    protected static void show(CopyOnWriteArrayList<Pj> collectionName) {
        collectionName.forEach(n -> System.out.println(formatOut(n)));
    }


    public static CopyOnWriteArrayList<Pj> pjeysSrt(CopyOnWriteArrayList<Pj> pjs) {
        Collections.sort(pjs, (Pj p1, Pj p2) -> p1.name.compareTo(p2.name));
        return pjs;
    }


    /**
     * <p>������� �� ��������� ��� ��������, ������� ������ ���������</p>
     * <p>������ ������� ��������� ��������, �������� id ������� ������ ��������� id ������� ��������</p>
     *
     * @param pj ������ ������ Pj, ��������� �������� �� ��������� ��������� ������ ����
     */
    public static void removeLower(Pj pj) {
//        pjeys = (CopyOnWriteArrayList<Pj>) pjeys.stream().filter(n -> n.compareTo(pj) > -1).collect(Collectors.toList());
//        PjCollection.pjeys.retainAll(pjeys.stream().filter(n -> n.compareTo(pj) > -1).collect(Collectors.toList()));
        ArrayList<Pj> pjArrayList = new ArrayList<Pj>();

        for (Pj pjiteratored : pjeys) {
            if (pj.compareTo(pjiteratored) == -1) {
                pjArrayList.add(pjiteratored);
            }
        }
        for (Pj pjiteratored : pjArrayList) {
            pjeys.remove(pjiteratored);
        }
    }


    /**
     * <p>������� �� ��������� ��� ��������, ������� ������ ���������</p>
     * <p>������ ������� ��������� ��������, �������� id ������� ������ ��������� id ������� ��������</p>
     *
     * @param pj ������ ������ Pj, ��������� �������� �� ��������� ��������� ������ ����
     */
    public static void removeGreater(Pj pj) {
//        pjeys = (CopyOnWriteArrayList<Pj>) pjeys.stream().filter(n -> n.compareTo(pj) == -1).collect(Collectors.toList());
        ArrayList<Pj> pjArrayList = new ArrayList<Pj>();

        for (Pj pjiteratored : pjeys) {
            if (pj.compareTo(pjiteratored) == 1) {
                pjArrayList.add(pjiteratored);
            }
        }
        for (Pj pjiteratored : pjArrayList) {
            pjeys.remove(pjiteratored);
        }
    }

    /**
     * <p>������� �� ��������� ��� ��������, ������������� �������.</p>
     * <p>������������� ������� ��������� �������, �������� id �������� ������������ �������� id �������</p>
     *
     * @param pj ������ ������ Pj, ��������� �������� �� ���������
     */
    public static void remove(Pj pj) {
//        pjeys = (CopyOnWriteArrayList<Pj>) pjeys.stream().filter(n -> n.compareTo(pj) != 0).collect(Collectors.toList());
        pjeys.remove(pj);

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
        return stb.toString();
    }
}


