

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
    static Heroes HOST = new Heroes("Хозяин дома", Emotional.HAPPY, Type.HUMAN);// создали хозяина дома
    //	static Heroes Carlson= new Heroes("Карлсон", Enums.Emotional.HAPPY, Enums.Type.FAIRY);
    int hmm;// переменная, используемая в рассчете вероятности, что герой отрежет рукава у
    // пижамы
    int bosse;// переменная, используемая в рассчете вероятности, что герой получит пижаму
    // Боссе
    static int thoughtful;// переменная, используемая в рассчете вероятности того, что герой будет
    // задумчивым ночью

    int hashcode = 1;// для переопределения хэшкода

    static int eating;// переменная, используемая в рассчете вероятности того, что герой съест варенье

    String console = "";

    Heroes(String name, Emotional e, Type t) {
        this.name = name;
        this.e = e;
        this.t = t;

    }

    private class Nametion extends RuntimeException {

        public Nametion() {
            super("У персонажа нет имени");
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
        s += ("\n" + getName() + " уже готов ко сну");
        return s;
    }

    @Override
    public String suspicious(Heroes h, String s) {
        s += (getName() + " уже улегся в кровать и пытался заснуть," + " но то и дело открывал глаза, задумываясь"
                + " о том, сколько же осталось варенья \n");
        return s;
    }

    public void preparingProcess(ArrayList<Hero_Pj> hero_pjs, int ind, ObjectOutputStream out) {
        Heroes heroes = hero_pjs.get(ind).heroes;
        Pj pijama = hero_pjs.get(ind).pijama;
        String saveStrings = "end";
        do {
            saveStrings += ("\n" + heroes.getName() + " примеряет пижаму\n");
            switch (pijama.getSize()) {
                case LONG: {
                    saveStrings += ("У " + heroes.getName() + " штанины и рукава оказались черезчур длинными\n");

                    saveStrings = givePjsmall(heroes, pijama, saveStrings);
                    break;

                }
                case SHORT:
                    saveStrings += (getName() + " сидел на краю кровати и пытался натянуть на себя штаны,"
                            + " но, как ни старался, ничего не получалось.\n");
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
                saveStrings += ("\n" + heroes.getName() + " запачкал пижаму");
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
            s += ("— Я дам тебе пижаму Боссе, — сказал " + HOST.getName() + ", " + "метнулся в комнату брата и принёс оттуда "
                    + "большую пижаму. Она налезла и на такого " + "толстяка, как " + getName() + ".\n");

            p.epj = epj.LONG;

        } else {
            s += (HOST.getName() + " нашел новую пижаму для " + getName() + ", как раз по размеру \n");
            p.epj = epj.OK;
        }
        return s;
    }

    public String eatJam(Heroes h, Pj p, String s) {
        s += ("\n" + getName() + " устал ждать пока все улягутся спать и он решил съесть варенье \n"
                + "Ой... Кажется, пижаму запачкал... Опять в стирку...");
        p.epjc = epjc.UNWASHED;
        return s;
    }


    public void choosingPj(CopyOnWriteArrayList<Pj> pjcol, ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
//        public void choosingPj(CopyOnWriteArrayList<Pj> saveCollection) {

        ArrayList<Pj> saveCollection = (ArrayList<Pj>) pjcol.stream().collect(Collectors.toList());
        System.out.println(this.name + " выбирает пижаму...");
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
//                    System.out.println(i + ") Локация: " + strLoca[i - 1]);
                    dostupLoca += "\n" + i + ") Локация: " + strLoca[i - 1];
                }
                out.writeObject("\nВ какой шкаф пойдет Герой?\n" + dostupLoca);
                System.out.println("жду шкаф");
//                charPos = dostupLoca.length();
//                dostupLoca = dostupLoca.substring(0, charPos - 2);
//                System.out.println("В какой шкаф пойдет " + this.name + "? Доступные варианты: " + dostupLoca);
//                console = (String) in.readObject();

//                console = "ss";
                int indexOf = dostupLoca.lastIndexOf(console.toUpperCase());
                while (((indexOf == -1) || console.equals(""))) {
                    out.writeObject("cont\nВведите корректный шкаф\nДоступные варианты: \" + dostupLoca");
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
                dostupLoca += ("\nСледующие пижамы в шкафу " + console + " доступны: ");
                i = 0;
                int listItemNumber = 0;
                String locationHas = "";
                String saveloca = console;
                ////////////массив инт для запоминания корреляций между индексом ирл и индексом списка
                int[] index = new int[saveCollection.size()];
                int[] inCollection = new int[pjcol.size()];

                int[][] corerelation = {index, inCollection};
                /////////
                saveCollection = (ArrayList<Pj>) saveCollection.stream().filter(n -> n.loca.equals(Location.valueOf(console.toUpperCase()))).collect(Collectors.toList());
                for (Pj pj : saveCollection) {
                    if (pj.loca.equals(Location.valueOf(console.toUpperCase()))) {
                        listItemNumber++;
                        locationHas = locationHas + strColor[i];
                        dostupLoca += ("\n" + (listItemNumber) + ") Цвет: " + strColor[i] + ", чистота: " + strClearance[i] + ", размер: " + strSize[i]);
                        corerelation[listItemNumber - 1][i] = 1;
                    }
                    i++;
                }
                dostupLoca += ("\nТеперь выберите цвет из предложенных пижам или напишите команду \"exit\" для возвращения к выбору шкафа");
                out.writeObject(dostupLoca);
                console = (String) in.readObject();

                if (!console.equals("exit")) {

                    indexOf = dostupLoca.lastIndexOf(console.toUpperCase());

                    while ((indexOf == -1) || console.equals("")) {
                        out.writeObject("\nВведите корректный цвет");
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
                    dostupLoca += ("\nСледующие пижамы цвета " + console + " доступны: ");
                    i = 0;
                    listItemNumber = 0;
                    for (Pj pj : saveCollection) {
                        if (pj.color.equals(ColorsEnum.valueOf(console.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                            listItemNumber++;
                            locationHas = locationHas + strSize[i];
                            dostupLoca += ("\n" + (listItemNumber) + ") Чистота: " + strClearance[i] + ", размер: " + strSize[i]);

                        }
//                    saveCollection.stream().filter(n ->n.color.equals(Enums.ColorsEnum.valueOf(console.trim().toUpperCase())) && n.loca.equals(Enums.Location.valueOf(saveloca.trim().toUpperCase()))).
//                            forEach(n -> System.out.println(n.name));
                        i++;
                    }
                    String saveCol = console;
                    dostupLoca += ("\nТеперь выберите размер из предложенных пижам или напишите команду \"exit\"");
                    out.writeObject(dostupLoca);
                    console = (String) in.readObject();
                    if (!console.equals("exit")) {

//                        console = "long";
                        indexOf = locationHas.lastIndexOf(console.toUpperCase());
                        while ((indexOf == -1) || console.equals("")) {
                            out.writeObject("\nВведите корректный размер");
                            console = (String) in.readObject();
                            indexOf = locationHas.lastIndexOf(console.toUpperCase());

                        }
                        dostupLoca = "";
                        dostupLoca += ("\nСледующие пижамы доступны: ");

                        i = -1;
                        listItemNumber = 0;
                        String saveEPj = console;
                        locationHas = "";
                        for (Pj pj : saveCollection) {

                            i++;
                            if (pj.epj.equals(EPj.valueOf(console.trim().toUpperCase())) && pj.color.equals(ColorsEnum.valueOf(saveCol.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                                locationHas = locationHas + strClearance[i];
                                listItemNumber++;
                                dostupLoca += ("\n" + (listItemNumber) + ") Чистота: " + strClearance[i]);

                            }

                        }
                        dostupLoca += ("\nвыберите пижаму из предложенных или напишите команду \"exit\". Для этого напишите чистоту пижамы");
                        out.writeObject(dostupLoca);

                        console = (String) in.readObject();
                        if (!console.equals("exit")) {

                            indexOf = locationHas.lastIndexOf(console.toUpperCase());
                            while ((indexOf == -1) || console.equals("")) {
                                out.writeObject("\nВведите корректную чистоту");
                                console = (String) in.readObject();
                                indexOf = locationHas.lastIndexOf(console.toUpperCase());

                            }
                            String saveClearance = console;

                            out.writeObject("\nhow would you name Her?");
                            pname = (String) in.readObject();
                            out.writeObject("\n" + this.name + " выбрал пижаму по имени \"" + pname + "\" в шкафу " + saveloca + ". " + "Цвет: " + saveCol + ", чистота: " + saveClearance + ", размер: " + saveEPj + "\n " +
                                    "Нажмите \"ввод\", если хотите продолжить");

                            Pj pj = new Pj(pname, EPj.valueOf(saveEPj.trim().toUpperCase()), EPjc.valueOf(saveClearance.trim().toUpperCase()), Location.valueOf(saveloca.trim().toUpperCase()), ColorsEnum.valueOf(saveCol.trim().toUpperCase()), i);

                            gotDressed = true;
                            Hero_Pj hero_pj = new Hero_Pj(this, pj);
                            Hero_Pj.h_p.add(hero_pj);


                        }
                    }
                }
            } catch (NoSuchElementException nsee) {
                out.writeObject("элемент не найден");
            } catch (IllegalArgumentException eae) {
                out.writeObject("Введена неверная локация");
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

        s += ("Пришлось найти чистую пижаму для " + getName() + "\n");
        p.epjc = epjc.WASHED;
        return s;
    }

    @Override
    public String givePjsmall(Heroes h, Pj p, String saveStrings) {

        hmm = (int) (Math.random() * 2);
        if (hmm == 0) {
            saveStrings += (
                    ", но" + HOST.getName() + "тут же нашёл выход — недолго думая он их обрезал. \n"
                            + getName() + " не успел и слова вымолвить, но он, по правде "
                            + "говоря, даже не очень огорчился. \n В конце концов, "
                            + "рассуждал он, пижама — это пустяки, дело житейское, "
                            + "и то, что она погибла, не может омрачить его радости: "
                            + "\n ведь это такое удивительное событие — " + getName() + " останется у него ночевать!\n ");

        } else {
            saveStrings += (HOST.getName() + " нашел новую пижаму для " + getName() + ", как раз по размеру %n");

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