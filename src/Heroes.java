

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

    String consoleLineForChoosing = "";

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

    public void preparingProcess(ArrayList<Hero_Pj> hero_pjs, int ind) throws ExcFall {
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
                    dostupLoca = dostupLoca + strLoca[i] + ", ";
                    i++;
                }
                int charPos = dostupLoca.length();
                dostupLoca = dostupLoca.substring(0, charPos - 2);
                System.out.println("� ����� ���� ������ " + this.name + "? ��������� ��������: " + dostupLoca);
                consoleLineForChoosing = scnChoice.nextLine();

//                consoleLineForChoosing = "ss";

                int indexOf = dostupLoca.lastIndexOf(consoleLineForChoosing.toUpperCase());
                while ((indexOf == -1) || consoleLineForChoosing.equals("")) {
                    System.out.println("������� ���������� ����");
                    System.out.println("��������� ��������: " + dostupLoca);
                    consoleLineForChoosing = scnChoice.nextLine();
                    indexOf = dostupLoca.lastIndexOf(consoleLineForChoosing.toUpperCase());

                }
                System.out.println("��������� ������ � ����� " + consoleLineForChoosing + " ��������: ");
                i = 0;
                int listItemNumber = 0;

                String locationHas = "";
                for (Pj pj : pjcol) {
                    if (pj.loca.equals(Location.valueOf(consoleLineForChoosing.trim().toUpperCase()))) {
                        listItemNumber++;
                        locationHas = locationHas + strColor[i];
                        System.out.println((listItemNumber) + ") ����: " + strColor[i] + ", �������: " + strClearance[i] + ", ������: " + strSize[i]);

                    }
                    i++;
                }
                String saveloca = consoleLineForChoosing;
                System.out.println("������ �������� ���� �� ������������ ����� ��� �������� ������� \"exit\"");
                consoleLineForChoosing = scnChoice.nextLine();
                if (!consoleLineForChoosing.equals("exit")) {
                    indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());
                    while ((indexOf == -1) || consoleLineForChoosing.equals("")) {
                        System.out.println("������� ���������� ����");
                        consoleLineForChoosing = scnChoice.nextLine();
                        indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());

                    }
//                consoleLineForChoosing = "blue";
                    locationHas = "";
                    System.out.println("��������� ������ ����� " + consoleLineForChoosing + " ��������: ");
                    i = 0;
                    listItemNumber = 0;
                    for (Pj pj : pjcol) {
                        if (pj.color.equals(ColorsEnum.valueOf(consoleLineForChoosing.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                            listItemNumber++;
                            locationHas = locationHas + strSize[i];
                            System.out.println((listItemNumber) + ") �������: " + strClearance[i] + ", ������: " + strSize[i]);

                        }
//                    pjcol.stream().filter(n ->n.color.equals(Enums.ColorsEnum.valueOf(consoleLineForChoosing.trim().toUpperCase())) && n.loca.equals(Enums.Location.valueOf(saveloca.trim().toUpperCase()))).
//                            forEach(n -> System.out.println(n.name));
                        i++;
                    }
                    String saveCol = consoleLineForChoosing;
                    System.out.println("������ �������� ������ �� ������������ ����� ��� �������� ������� \"exit\"");
                    consoleLineForChoosing = scnChoice.nextLine();
                    if (!consoleLineForChoosing.equals("exit")) {

//                    consoleLineForChoosing = "long";
                        indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());
                        while ((indexOf == -1) || consoleLineForChoosing.equals("")) {
                            System.out.println("������� ���������� ������");
                            consoleLineForChoosing = scnChoice.nextLine();
                            indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());

                        }

                        System.out.println("��������� ������ ��������: ");

                        i = -1;
                        listItemNumber = 0;
                        String saveEPj = consoleLineForChoosing;
                        locationHas = "";
                        for (Pj pj : pjcol) {

                            i++;
                            if (pj.epj.equals(EPj.valueOf(consoleLineForChoosing.trim().toUpperCase())) && pj.color.equals(ColorsEnum.valueOf(saveCol.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                                locationHas = locationHas + strClearance[i];
                                listItemNumber++;
                                System.out.println((listItemNumber) + ") �������: " + strClearance[i]);

                            }

                        }
                        System.out.println("�������� ������ �� ������������ ��� �������� ������� \"exit\". ��� ����� �������� ������� ������");


                        consoleLineForChoosing = scnChoice.nextLine();
                        if (!consoleLineForChoosing.equals("exit")) {

                            indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());
                            while ((indexOf == -1) || consoleLineForChoosing.equals("")) {
                                System.out.println("������� ���������� �������");
                                consoleLineForChoosing = scnChoice.nextLine();
                                indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());

                            }
                            String saveClearance = consoleLineForChoosing;
                            System.out.println("how would you name Her?");
                            consoleLineForChoosing = scnChoice.nextLine();
                            pname = consoleLineForChoosing;
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
                System.out.println("illegal argument caught");
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

}