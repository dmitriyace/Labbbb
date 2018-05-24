

import Enums.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
        if (name == "")
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
    public void sleep(Heroes h) {
        System.out.println();
        System.out.println(h.getName() + " ��� ����� �� ���");
    }

    @Override
    public void suspicious(Heroes h) {
        System.out.printf("%s ��� ������ � ������� � ������� �������," + " �� �� � ���� �������� �����, �����������"
                + " � ���, ������� �� �������� ������� %n", h.getName());
    }

    public void preparingProcess(ArrayList<Hero_Pj> hero_pjs, int ind) {
        Heroes heroes = hero_pjs.get(ind).heroes;
        Pj pijama = hero_pjs.get(ind).pijama;
        do {
            System.out.println(heroes.getName() + " ��������� ������");
            switch (pijama.getSize()) {
                case LONG: {
                    System.out.printf("� %s ������� � ������ ��������� �������� ��������", heroes.getName());
                    System.out.println();
                    givePjsmall(heroes, pijama);
                    break;

                }
                case SHORT:
                    System.out.printf("%s ����� �� ���� ������� � ������� �������� �� ���� �����,"
                            + " ��, ��� �� ��������, ������ �� ����������.", heroes.getName());
                    System.out.println();
                    givePj(heroes, pijama);
                    break;
                case OK:

                    sleep(heroes);
                    break;
            }
            eating = (int) (Math.random() * 2);
            if (eating == 0)
                eatJam(heroes, pijama);
            if (pijama.getClearance() == EPjc.UNWASHED) {
                System.out.println(heroes.getName() + " �������� ������");
                giveClearPj(heroes, pijama);
            }
        } while (pijama.epj != epj.OK || pijama.epjc != epjc.WASHED);

        thoughtful = (int) (Math.random() * 3);
        if (thoughtful == 1) suspicious(heroes);

        System.out.println();

    }

    @Override
    public void givePj(Heroes h, Pj p) {
        bosse = (int) (Math.random() * 2);

        if (bosse == 0) {
            System.out.printf("� � ��� ���� ������ �����, � ������ %s, " + "�������� � ������� ����� � ����� ������ "
                    + "������� ������. ��� ������� � �� ������ " + "��������, ��� %s.%n", HOST.getName(), getName());

            p.epj = epj.LONG;

        } else {
            System.out.printf("%s ����� ����� ������ ��� %s, ��� ��� �� ������� %n", HOST.getName(), getName());
            p.epj = epj.OK;
        }

    }

    public void eatJam(Heroes h, Pj p) {
        System.out.println(getName() + " ����� ����� ���� ��� �������� ����� � �� ����� ������ ������� \n"
                + "��... �������, ������ ��������... ����� � ������...");
        p.epjc = epjc.UNWASHED;

    }


    public void choosingPj(CopyOnWriteArrayList<Pj> pjcol) {
        System.out.println(this.name + " �������� ������...");
        Scanner scnChoice = new Scanner(System.in);
        int colLength = pjcol.size();
        String[] strSize = new String[colLength];
        String[] strLoca = new String[colLength];
        String[] strColor = new String[colLength];
        String[] strClearance = new String[colLength];
        String dostupLoca = new String();
        String pname;
        boolean gotDressed = false;

        while (!gotDressed) {
            try {
                gotDressed = false;
                dostupLoca = "";

                int i = 0;
                for (Pj pj : pjcol) {
                    strSize[i] = pj.epj.toString();
                    strLoca[i] = pj.loca.toString();
                    strClearance[i] = pj.epjc.toString();
                    strColor[i] = pj.color.toString();
//                    dostupLoca += strLoca[i] + ", ";
                    i++;
                }
                i = 0;
                System.out.println("� ����� ���� ������ �������?");
                for (Pj pj : pjcol) {
                    i++;
                    System.out.println(i + ") �������: " + strLoca[i - 1]);
                    dostupLoca += "\n" + i + ") �������: " + strLoca[i - 1];
                }

//                charPos = dostupLoca.length();
//                dostupLoca = dostupLoca.substring(0, charPos - 2);
//                System.out.println("� ����� ���� ������ " + this.name + "? ��������� ��������: " + dostupLoca);
                console = scnChoice.nextLine();

//                console = "ss";
                int indexOf = dostupLoca.lastIndexOf(console.toUpperCase());
                while (((indexOf == -1) || console.equals(""))) {
                    System.out.println("������� ���������� ���� ��� ��� �����");
                    System.out.println();
                    System.out.println("��������� ��������: " + dostupLoca);
                    console = scnChoice.nextLine();
                    indexOf = dostupLoca.lastIndexOf(console.toUpperCase());
                }
                int count=0;

//                if (isNumeric(console)) {
//                    for (Pj pj : pjcol) {
//                        if (count==(int) Double.parseDouble(console) - 1){
//                            console=pj.loca.toString().toUpperCase();
//                            save.add(new Pj("", EPj.valueOf(strSize[i].toUpperCase()), EPjc.valueOf(strClearance[i].toUpperCase()), Location.valueOf(console), ColorsEnum.valueOf(strColor[i].toUpperCase()), count));
//                        }
//                    }
//                    count++;
//                }

                System.out.println("��������� ������ � ����� " + console + " ��������: ");
                i = 0;
                int listItemNumber = 0;
                String locationHas = "";
                String saveloca=console;
                for (Pj pj : pjcol) {
                    if (pj.loca.equals(Location.valueOf(console.toUpperCase()))) {
                        listItemNumber++;
                        locationHas = locationHas + strColor[i];
                        System.out.println((listItemNumber) + ") ����: " + strColor[i] + ", �������: " + strClearance[i] + ", ������: " + strSize[i]);

                    }
                    i++;
                }
                System.out.println("������ �������� ���� �� ������������ ����� ��� �������� ������� \"exit\"");
                console = scnChoice.nextLine();
                if (!console.equals("exit")) {
                    indexOf = locationHas.lastIndexOf(console.toUpperCase());
                    while ((indexOf == -1) || console.equals("")) {
                        System.out.println("������� ���������� ����");
                        console = scnChoice.nextLine();
                        indexOf = locationHas.lastIndexOf(console.toUpperCase());

                    }
//                    console = "blue";
                    locationHas = "";
                    System.out.println("��������� ������ ����� " + console + " ��������: ");
                    i = 0;
                    listItemNumber = 0;
                    for (Pj pj : pjcol) {
                        if (pj.color.equals(ColorsEnum.valueOf(console.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                            listItemNumber++;
                            locationHas = locationHas + strSize[i];
                            System.out.println((listItemNumber) + ") �������: " + strClearance[i] + ", ������: " + strSize[i]);

                        }
//                    pjcol.stream().filter(n ->n.color.equals(Enums.ColorsEnum.valueOf(console.trim().toUpperCase())) && n.loca.equals(Enums.Location.valueOf(saveloca.trim().toUpperCase()))).
//                            forEach(n -> System.out.println(n.name));
                        i++;
                    }
                    String saveCol = console;
                    System.out.println("������ �������� ������ �� ������������ ����� ��� �������� ������� \"exit\"");
                    console = scnChoice.nextLine();
                    if (!console.equals("exit")) {

//                        console = "long";
                        indexOf = locationHas.lastIndexOf(console.toUpperCase());
                        while ((indexOf == -1) || console.equals("")) {
                            System.out.println("������� ���������� ������");
                            console = scnChoice.nextLine();
                            indexOf = locationHas.lastIndexOf(console.toUpperCase());

                        }

                        System.out.println("��������� ������ ��������: ");

                        i = -1;
                        listItemNumber = 0;
                        String saveEPj = console;
                        locationHas = "";
                        for (Pj pj : pjcol) {

                            i++;
                            if (pj.epj.equals(EPj.valueOf(console.trim().toUpperCase())) && pj.color.equals(ColorsEnum.valueOf(saveCol.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                                locationHas = locationHas + strClearance[i];
                                listItemNumber++;
                                System.out.println((listItemNumber) + ") �������: " + strClearance[i]);

                            }

                        }
                        System.out.println("�������� ������ �� ������������ ��� �������� ������� \"exit\". ��� ����� �������� ������� ������");


                        console = scnChoice.nextLine();
                        if (!console.equals("exit")) {

                            indexOf = locationHas.lastIndexOf(console.toUpperCase());
                            while ((indexOf == -1) || console.equals("")) {
                                System.out.println("������� ���������� �������");
                                console = scnChoice.nextLine();
                                indexOf = locationHas.lastIndexOf(console.toUpperCase());

                            }
                            String saveClearance = console;
                            System.out.println("how would you name Her?");
                            console = scnChoice.nextLine();
                            pname = console;
                            System.out.println(this.name + " ������ ������ �� �����" + pname + " � �����." + saveloca + " " + "����: " + saveCol + ", �������: " + saveClearance + ", ������: " + saveEPj);

                            Pj pj = new Pj(pname, EPj.valueOf(saveEPj.trim().toUpperCase()), EPjc.valueOf(saveClearance.trim().toUpperCase()), Location.valueOf(saveloca.trim().toUpperCase()), ColorsEnum.valueOf(saveCol.trim().toUpperCase()), i);

                            gotDressed = true;
                            Hero_Pj hero_pj = new Hero_Pj(this, pj);
                            Hero_Pj.h_p.add(hero_pj);


                        }
                    }
                }
            } catch (NoSuchElementException nsee) {
                System.out.println("������� �� ������");
            } catch (IllegalArgumentException eae) {
                System.out.println("������� �������� �������");
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
    public void giveClearPj(Heroes h, Pj p) {

        System.out.printf("�������� ����� ������ ������ ��� %s %n", getName());
        p.epjc = epjc.WASHED;
    }

    @Override
    public void givePjsmall(Heroes h, Pj p) {

        hmm = (int) (Math.random() * 2);
        if (hmm == 0) {
            System.out.printf(
                    ", �� %s ��� �� ����� ����� � ������� ����� �� �� �������. %n"
                            + "%s �� ����� � ����� ���������, �� ��, �� ������ "
                            + "������, ���� �� ����� ���������. %n � ����� ������, "
                            + "��������� ��, ������ � ��� �������, ���� ���������, "
                            + "� ��, ��� ��� �������, �� ����� �������� ��� �������: "
                            + "%n ���� ��� ����� ������������ ������� � " + "%s ��������� � ���� ��������!%n ",
                    HOST.getName(), getName(), getName());

        } else {
            System.out.printf("%s ����� ����� ������ ��� %s, ��� ��� �� ������� %n", HOST.getName(), getName());

        }
        p.epj = epj.OK;

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