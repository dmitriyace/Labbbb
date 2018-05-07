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
    static int first_elem_hash;
    static Pj pj_save;//������ ��� �������������� ����������


    protected static String commands(String command) throws ExcFall {
        String kekery = null;
        switch (command) {
            case "sort":
                PjCollection.pjeysSrt(pjeys);
                break;
            case "show":
                 PjCollection.show();
                break;
            case "start":
                Scenary.starting();
                break;

            case "size":
                System.out.println(PjCollection.pjeys.size());
                break;
            case "remove_lower":
                PjCollection.getElemByString(command);
                PjCollection.removeLower(PjCollection.pj_save);
                break;
            case "remove_greater":
                PjCollection.getElemByString(command);
                PjCollection.removeGreater(PjCollection.pj_save);
                break;
            case "remove_by_value":
                Scanner scan_element = new Scanner(command);
                PjCollection.getElemByString(command);
                PjCollection.remove(PjCollection.pj_save);
                break;
            case "out":
                String path_save = "C:\\Users\\chist\\Documents\\itmo\\proga\\Lab3\\src\\Output.txt";
                Output.save(path_save, PjCollection.pjeys);
                break;
            case "help":
                Scenary.help();
                break;
            case "q":
                System.exit(0);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return kekery;

    }

    protected static void show() {
        int counter = 0;
        String result = "";
//        for (Pj pj : pjeys) {
//
//
//            String strSize = pj.epj.toString();
//            String strClear = pj.epjc.toString();
//            String strLocation = pj.loca.toString();
//            String strColor = pj.color.toString();
//            result = result + "\n"  + ") Size of the pijama is " + strSize.toLowerCase() + ", and it's color is " + strColor.toLowerCase();
//
//            if (strClear.equals("WASHED")) result += ". Pijama is clear and fresh.";
//            else result += ". Pijama is dirty.";
//            result += "It was found in \"" + strLocation.toLowerCase() + "\n";
//
//        }System.out.println(result);
        pjeys.forEach(n -> System.out.println(formatOut(n)));

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

    public static CopyOnWriteArrayList<Pj> pjeysSrt(CopyOnWriteArrayList<Pj> pjs) {
        Collections.sort(pjs, (Pj p1, Pj p2) -> p1.name.compareTo(p2.name));
        return pjs;
//        TreeSet<Pj> pjList = new TreeSet<>();
//
//        for (Pj pj : pjeys) {
//            pjList.add(new Pj(pj.name,pj.epj, pj.epjc, pj.loca, pj.color, pj.id));
//        }

    }

    public static void removeFirst() {
        for (Pj pj : pjeys) {
            if (pj.hashCode() == 1) {
                pj_save = pj;

            }
        }
        first_elem_hash++;
        pjeys.remove(pj_save);
        System.out.println(pjeys.size() + " - ������ ���������");

    }

    /**
     * <p>������� �� ��������� ��� ��������, ������� ������ ���������</p>
     * <p>������ ������� ��������� ��������, �������� id ������� ������ ��������� id ������� ��������</p>
     *
     * @param pj ������ ������ Pj, ��������� �������� �� ��������� ��������� ������ ����
     */
    public static void removeLower(Pj pj) {
        ArrayList<Pj> pjArrayList = new ArrayList<Pj>();

        for (Pj pjiteratored : pjeys) {
            if (pj.compareTo(pjiteratored) == -1) {
                pjArrayList.add(pjiteratored);
            }
        }
        for (Pj pjiteratored : pjArrayList) {
            pjeys.remove(pjiteratored);
        }
       pjeys = (CopyOnWriteArrayList<Pj>) pjeys.stream().filter(n -> n.compareTo(pj) >-1).collect(Collectors.toList());


    }


    /**
     * <p>������� �� ��������� ��� ��������, ������� ������ ���������</p>
     * <p>������ ������� ��������� ��������, �������� id ������� ������ ��������� id ������� ��������</p>
     *
     * @param pj ������ ������ Pj, ��������� �������� �� ��������� ��������� ������ ����
     */
    public static void removeGreater(Pj pj) {
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
        pjeys.remove(pj);
    }

    public static void getElemByString(String line) {
        pj_save = Pj.d_f;
        Scanner scan_element = new Scanner(line);
        scan_element.useDelimiter("\",");
        String poisk = scan_element.findWithinHorizon("epj\":\"", 30);
        String size = scan_element.next().trim().toUpperCase();
        poisk = scan_element.findWithinHorizon("epjc\":\"", 30);
        String clearance = scan_element.next().trim().toUpperCase();
        poisk = scan_element.findWithinHorizon("name\":\"", 30);
        String location = scan_element.next().trim().toUpperCase();
        poisk = scan_element.findWithinHorizon("color\":\"", 40);
        String color = scan_element.next().trim().toUpperCase();
        poisk = scan_element.findWithinHorizon("id\":", 40);
        scan_element.useDelimiter("}");
        String id = scan_element.next().trim().toUpperCase();
        scan_element.close();

        pj_save.epj = EPj.valueOf(size.trim().toUpperCase());
        pj_save.epjc = EPjc.valueOf(clearance.trim().toUpperCase());
        pj_save.loca = Location.valueOf(location.trim().toUpperCase());
        pj_save.color = ColorsEnum.valueOf(color.trim().toUpperCase());
        pj_save.id = (Integer.parseInt(id));

    }


}


