

import Enums.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Heroes implements Moves {

    protected Type t;
    protected Emotional e;
    protected String name;
    protected EPj epj;
    protected EPjc epjc;
    protected Hero_Pj h_p;
    //    protected Pj pj;
    static Heroes HOST = new Heroes("������ ����", Emotional.HAPPY, Type.HUMAN);// ������� ������� ����
    //	static Heroes Carlson= new Heroes("�������", Enums.Emotional.HAPPY, Enums.Type.FAIRY);
    int hmm;// ����������, ������������ � �������� �����������, ��� ����� ������� ������ �
    // ������
    int bosse;// ����������, ������������ � �������� �����������, ��� ����� ������� ������
    // �����
    static int thoughtful;// ����������, ������������ � �������� ����������� ����, ��� ����� �����
    // ���������� �����

    int hashcode = 1;// ��� ��������������� �������

    static int eating;// ����������, ������������ � �������� ����������� ����, ��� ����� ����� �������

    String console = "";

    Heroes(String name, Emotional e, Type t) {
        this.name = name;
        this.e = e;
        this.t = t;

    }

    private class Nametion extends RuntimeException {

        public Nametion() {
            super("� ��������� ��� �����");
        }

        public Nametion(String message) {
            super(message);
        }
    }

    public String getName() {
        if (name.equals(""))
            throw new Nametion();
        return this.name;
    }

    void setType() {
        this.t = t;
    }

    void setEmots() {
        this.e = e;
    }

    @Override
    public String sleep(Heroes h, String s) {
        s += ("\n" + getName() + " ��� ����� �� ���");
        return s;
    }

    @Override
    public String suspicious(Heroes h, String s) {
        s += (getName() + " ��� ������ � ������� � ������� �������," + " �� �� � ���� �������� �����, �����������"
                + " � ���, ������� �� �������� ������� \n");
        return s;
    }

    public void preparingProcess(ArrayList<Hero_Pj> hero_pjs, int ind, ObjectOutputStream out) {
        Heroes heroes = hero_pjs.get(ind).heroes;
        Pj pijama = hero_pjs.get(ind).pijama;
        String saveStrings = "end";
        do {
            saveStrings += ("\n" + heroes.getName() + " ��������� ������\n");
            switch (pijama.getSize()) {
                case LONG: {
                    saveStrings += ("� " + heroes.getName() + " ������� � ������ ��������� �������� ��������\n");

                    saveStrings = givePjsmall(heroes, pijama, saveStrings);
                    break;

                }
                case SHORT:
                    saveStrings += (getName() + " ����� �� ���� ������� � ������� �������� �� ���� �����,"
                            + " ��, ��� �� ��������, ������ �� ����������.\n");
                    saveStrings = givePj(heroes, pijama, saveStrings);
                    break;
                case OK:

                    saveStrings = sleep(heroes, saveStrings);
                    break;
            }
            eating = (int) (Math.random() * 2);
            if (eating == 0)
                saveStrings = eatJam(heroes, pijama, saveStrings);
            if (pijama.getClearance() == EPjc.UNWASHED) {
                saveStrings += ("\n" + heroes.getName() + " �������� ������");
                saveStrings = giveClearPj(heroes, pijama, saveStrings);
            }
        } while (pijama.epj != epj.OK || pijama.epjc != epjc.WASHED);

        thoughtful = (int) (Math.random() * 3);
        if (thoughtful == 1) saveStrings = suspicious(heroes, saveStrings);

        try {
            out.writeObject(saveStrings);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public String givePj(Heroes h, Pj p, String s) {
        bosse = (int) (Math.random() * 2);

        if (bosse == 0) {
            s += ("� � ��� ���� ������ �����, � ������ " + HOST.getName() + ", " + "�������� � ������� ����� � ����� ������ "
                    + "������� ������. ��� ������� � �� ������ " + "��������, ��� " + getName() + ".\n");

            p.epj = epj.LONG;

        } else {
            s += (HOST.getName() + " ����� ����� ������ ��� " + getName() + ", ��� ��� �� ������� \n");
            p.epj = epj.OK;
        }
        return s;
    }

    public String eatJam(Heroes h, Pj p, String s) {
        s += ("\n" + getName() + " ����� ����� ���� ��� �������� ����� � �� ����� ������ ������� \n"
                + "��... �������, ������ ��������... ����� � ������...");
        p.epjc = epjc.UNWASHED;
        return s;
    }


    public void choosingPj(CopyOnWriteArrayList<Pj> pjcol, ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
//        public void choosingPj(CopyOnWriteArrayList<Pj> saveCollection) {

        ArrayList<Pj> saveCollection = (ArrayList<Pj>) pjcol.stream().collect(Collectors.toList());
        System.out.println(this.name + " �������� ������...");
//        Scanner scnChoice = new Scanner(System.in);
        int colLength = saveCollection.size();
        String[] strSize = new String[colLength];
        String[] strLoca = new String[colLength];
        String[] strColor = new String[colLength];
        String[] strClearance = new String[colLength];
        String dostupLoca = "";
        String pname;
        boolean gotDressed = false;

        while (!gotDressed) {
            try {
                gotDressed = false;
                dostupLoca = "";

                int i = 0;
                for (Pj pj : saveCollection) {
                    strSize[i] = pj.epj.toString();
                    strLoca[i] = pj.loca.toString();
                    strClearance[i] = pj.epjc.toString();
                    strColor[i] = pj.color.toString();
                    i++;
                }
                i = 0;

                for (Pj pj : saveCollection) {
                    i++;
//                    System.out.println(i + ") �������: " + strLoca[i - 1]);
                    dostupLoca += "\n" + i + ") �������: " + strLoca[i - 1];
                }
                out.writeObject("\n� ����� ���� ������ �����?\n" + dostupLoca);
                System.out.println("��� ����");
//                charPos = dostupLoca.length();
//                dostupLoca = dostupLoca.substring(0, charPos - 2);
//                System.out.println("� ����� ���� ������ " + this.name + "? ��������� ��������: " + dostupLoca);
//                console = (String) in.readObject();

//                console = "ss";
                int indexOf = dostupLoca.lastIndexOf(console.toUpperCase());
                while (((indexOf == -1) || console.equals(""))) {
                    out.writeObject("cont\n������� ���������� ����\n��������� ��������: \" + dostupLoca");
                    console = (String) in.readObject();
                    indexOf = dostupLoca.lastIndexOf(console.toUpperCase());
                }
                int count = 0;

//                if (isNumeric(console)) {
//                    for (Pj pj : saveCollection) {
//                        if (count==(int) Double.parseDouble(console) - 1){
//                            console=pj.loca.toString().toUpperCase();
//                            save.add(new Pj("", EPj.valueOf(strSize[i].toUpperCase()), EPjc.valueOf(strClearance[i].toUpperCase()), Location.valueOf(console), ColorsEnum.valueOf(strColor[i].toUpperCase()), count));
//                        }
//                    }
//                    count++;
//                }
                dostupLoca = "";
                dostupLoca += ("\n��������� ������ � ����� " + console + " ��������: ");
                i = 0;
                int listItemNumber = 0;
                String locationHas = "";
                String saveloca = console;
                ////////////������ ��� ��� ����������� ���������� ����� �������� ��� � �������� ������
                int[] index = new int[saveCollection.size()];
                int[] inCollection = new int[pjcol.size()];

                int[][] corerelation = {index, inCollection};
                /////////
                saveCollection = (ArrayList<Pj>) saveCollection.stream().filter(n -> n.loca.equals(Location.valueOf(console.toUpperCase()))).collect(Collectors.toList());
                for (Pj pj : saveCollection) {
                    if (pj.loca.equals(Location.valueOf(console.toUpperCase()))) {
                        listItemNumber++;
                        locationHas = locationHas + strColor[i];
                        dostupLoca += ("\n" + (listItemNumber) + ") ����: " + strColor[i] + ", �������: " + strClearance[i] + ", ������: " + strSize[i]);
                        corerelation[listItemNumber - 1][i] = 1;
                    }
                    i++;
                }
                dostupLoca += ("\n������ �������� ���� �� ������������ ����� ��� �������� ������� \"exit\" ��� ����������� � ������ �����");
                out.writeObject(dostupLoca);
                console = (String) in.readObject();

                if (!console.equals("exit")) {

                    indexOf = dostupLoca.lastIndexOf(console.toUpperCase());

                    while ((indexOf == -1) || console.equals("")) {
                        out.writeObject("\n������� ���������� ����");
                        console = (String) in.readObject();
                        indexOf = dostupLoca.lastIndexOf(console.toUpperCase());

                    }
                    int saveIndexes[] = new int[listItemNumber];
                    if (isNumeric(console)) {
                        i = Integer.valueOf(console);
                        for (int a = 0; a < listItemNumber; a++) {
                            for (int b = 0; b < pjcol.size(); b++) {
                                if (corerelation[a][b] == 1) saveIndexes[a - 1] = b;
                            }
                        }
                    }

//                    saveCollection = (ArrayList<Pj>) saveCollection.stream().filter(n -> n.color.equals(ColorsEnum.valueOf())).collect(Collectors.toList());
//                    console = "blue";
                    locationHas = "";
                    dostupLoca = "";
                    console = strColor[i].toString();
                    dostupLoca += ("\n��������� ������ ����� " + console + " ��������: ");
                    i = 0;
                    listItemNumber = 0;
                    for (Pj pj : saveCollection) {
                        if (pj.color.equals(ColorsEnum.valueOf(console.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                            listItemNumber++;
                            locationHas = locationHas + strSize[i];
                            dostupLoca += ("\n" + (listItemNumber) + ") �������: " + strClearance[i] + ", ������: " + strSize[i]);

                        }
//                    saveCollection.stream().filter(n ->n.color.equals(Enums.ColorsEnum.valueOf(console.trim().toUpperCase())) && n.loca.equals(Enums.Location.valueOf(saveloca.trim().toUpperCase()))).
//                            forEach(n -> System.out.println(n.name));
                        i++;
                    }
                    String saveCol = console;
                    dostupLoca += ("\n������ �������� ������ �� ������������ ����� ��� �������� ������� \"exit\"");
                    out.writeObject(dostupLoca);
                    console = (String) in.readObject();
                    if (!console.equals("exit")) {

//                        console = "long";
                        indexOf = locationHas.lastIndexOf(console.toUpperCase());
                        while ((indexOf == -1) || console.equals("")) {
                            out.writeObject("\n������� ���������� ������");
                            console = (String) in.readObject();
                            indexOf = locationHas.lastIndexOf(console.toUpperCase());

                        }
                        dostupLoca = "";
                        dostupLoca += ("\n��������� ������ ��������: ");

                        i = -1;
                        listItemNumber = 0;
                        String saveEPj = console;
                        locationHas = "";
                        for (Pj pj : saveCollection) {

                            i++;
                            if (pj.epj.equals(EPj.valueOf(console.trim().toUpperCase())) && pj.color.equals(ColorsEnum.valueOf(saveCol.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                                locationHas = locationHas + strClearance[i];
                                listItemNumber++;
                                dostupLoca += ("\n" + (listItemNumber) + ") �������: " + strClearance[i]);

                            }

                        }
                        dostupLoca += ("\n�������� ������ �� ������������ ��� �������� ������� \"exit\". ��� ����� �������� ������� ������");
                        out.writeObject(dostupLoca);

                        console = (String) in.readObject();
                        if (!console.equals("exit")) {

                            indexOf = locationHas.lastIndexOf(console.toUpperCase());
                            while ((indexOf == -1) || console.equals("")) {
                                out.writeObject("\n������� ���������� �������");
                                console = (String) in.readObject();
                                indexOf = locationHas.lastIndexOf(console.toUpperCase());

                            }
                            String saveClearance = console;

                            out.writeObject("\nhow would you name Her?");
                            pname = (String) in.readObject();
                            out.writeObject("\n" + this.name + " ������ ������ �� ����� \"" + pname + "\" � ����� " + saveloca + ". " + "����: " + saveCol + ", �������: " + saveClearance + ", ������: " + saveEPj + "\n " +
                                    "������� \"����\", ���� ������ ����������");

                            Pj pj = new Pj(pname, EPj.valueOf(saveEPj.trim().toUpperCase()), EPjc.valueOf(saveClearance.trim().toUpperCase()), Location.valueOf(saveloca.trim().toUpperCase()), ColorsEnum.valueOf(saveCol.trim().toUpperCase()), i);

                            gotDressed = true;
                            Hero_Pj hero_pj = new Hero_Pj(this, pj);
                            Hero_Pj.h_p.add(hero_pj);


                        }
                    }
                }
            } catch (NoSuchElementException nsee) {
                out.writeObject("������� �� ������");
            } catch (IllegalArgumentException eae) {
                out.writeObject("������� �������� �������");
            }
        }


    }


    public int hashCode(Heroes h, Emotional e) {
        if (h.e == e.SAD) {
            hashcode *= 3;
        }
        if (h.e == e.HAPPY) {
            hashcode *= 9;
        }
        if (h.e == e.NOTSOHAPPY) {
            hashcode *= 27;
        }
        return hashcode;
    }


    @Override
    public String giveClearPj(Heroes h, Pj p, String s) {

        s += ("�������� ����� ������ ������ ��� " + getName() + "\n");
        p.epjc = epjc.WASHED;
        return s;
    }

    @Override
    public String givePjsmall(Heroes h, Pj p, String saveStrings) {

        hmm = (int) (Math.random() * 2);
        if (hmm == 0) {
            saveStrings += (
                    ", ��" + HOST.getName() + "��� �� ����� ����� � ������� ����� �� �� �������. \n"
                            + getName() + " �� ����� � ����� ���������, �� ��, �� ������ "
                            + "������, ���� �� ����� ���������. \n � ����� ������, "
                            + "��������� ��, ������ � ��� �������, ���� ���������, "
                            + "� ��, ��� ��� �������, �� ����� �������� ��� �������: "
                            + "\n ���� ��� ����� ������������ ������� � " + getName() + " ��������� � ���� ��������!\n ");

        } else {
            saveStrings += (HOST.getName() + " ����� ����� ������ ��� " + getName() + ", ��� ��� �� ������� %n");

        }
        p.epj = epj.OK;
        return saveStrings;
    }

    static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}