

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

    String consoleLineForChoosing = "";

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
        System.out.println(h.getName() + " уже готов ко сну");
    }

    @Override
    public void suspicious(Heroes h) {
        System.out.printf("%s уже улегся в кровать и пытался заснуть," + " но то и дело открывал глаза, задумываясь"
                + " о том, сколько же осталось варенья %n", h.getName());
    }

    public void preparingProcess(ArrayList<Hero_Pj> hero_pjs, int ind) throws ExcFall {
        Heroes heroes = hero_pjs.get(ind).heroes;
        Pj pijama = hero_pjs.get(ind).pijama;
        do {

            System.out.println(heroes.getName() + " примеряет пижаму");
            switch (pijama.getSize()) {
                case LONG: {
                    System.out.printf("У %s штанины и рукава оказались черезчур длинными", heroes.getName());
                    System.out.println();
                    givePjsmall(heroes, pijama);

                    break;

                }
                case SHORT:
                    System.out.printf("%s сидел на краю кровати и пытался натянуть на себя штаны,"
                            + " но, как ни старался, ничего не получалось.", heroes.getName());
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
                System.out.println(heroes.getName() + " запачкал пижаму");
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
            System.out.printf("— Я дам тебе пижаму Боссе, — сказал %s, " + "метнулся в комнату брата и принёс оттуда "
                    + "большую пижаму. Она налезла и на такого " + "толстяка, как %s.%n", HOST.getName(), getName());

            p.epj = epj.LONG;

        } else {
            System.out.printf("%s нашел новую пижаму для %s, как раз по размеру %n", HOST.getName(), getName());
            p.epj = epj.OK;
        }

    }

    public void eatJam(Heroes h, Pj p) {
        System.out.println(getName() + " устал ждать пока все улягутся спать и он решил съесть варенье \n"
                + "Ой... Кажется, пижаму запачкал... Опять в стирку...");
        p.epjc = epjc.UNWASHED;

    }


    public void choosingPj(CopyOnWriteArrayList<Pj> pjcol) {
        System.out.println(this.name + " выбирает пижаму...");
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
                System.out.println("В какой шкаф пойдет " + this.name + "? Доступные варианты: " + dostupLoca);
                consoleLineForChoosing = scnChoice.nextLine();

//                consoleLineForChoosing = "ss";

                int indexOf = dostupLoca.lastIndexOf(consoleLineForChoosing.toUpperCase());
                while ((indexOf == -1) || consoleLineForChoosing.equals("")) {
                    System.out.println("Введите корректный шкаф");
                    System.out.println("Доступные варианты: " + dostupLoca);
                    consoleLineForChoosing = scnChoice.nextLine();
                    indexOf = dostupLoca.lastIndexOf(consoleLineForChoosing.toUpperCase());

                }
                System.out.println("Следующие пижамы в шкафу " + consoleLineForChoosing + " доступны: ");
                i = 0;
                int listItemNumber = 0;

                String locationHas = "";
                for (Pj pj : pjcol) {
                    if (pj.loca.equals(Location.valueOf(consoleLineForChoosing.trim().toUpperCase()))) {
                        listItemNumber++;
                        locationHas = locationHas + strColor[i];
                        System.out.println((listItemNumber) + ") Цвет: " + strColor[i] + ", чистота: " + strClearance[i] + ", размер: " + strSize[i]);

                    }
                    i++;
                }
                String saveloca = consoleLineForChoosing;
                System.out.println("Теперь выберите цвет из предложенных пижам или напишите команду \"exit\"");
                consoleLineForChoosing = scnChoice.nextLine();
                if (!consoleLineForChoosing.equals("exit")) {
                    indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());
                    while ((indexOf == -1) || consoleLineForChoosing.equals("")) {
                        System.out.println("Введите корректный цвет");
                        consoleLineForChoosing = scnChoice.nextLine();
                        indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());

                    }
//                consoleLineForChoosing = "blue";
                    locationHas = "";
                    System.out.println("Следующие пижамы цвета " + consoleLineForChoosing + " доступны: ");
                    i = 0;
                    listItemNumber = 0;
                    for (Pj pj : pjcol) {
                        if (pj.color.equals(ColorsEnum.valueOf(consoleLineForChoosing.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                            listItemNumber++;
                            locationHas = locationHas + strSize[i];
                            System.out.println((listItemNumber) + ") Чистота: " + strClearance[i] + ", размер: " + strSize[i]);

                        }
//                    pjcol.stream().filter(n ->n.color.equals(Enums.ColorsEnum.valueOf(consoleLineForChoosing.trim().toUpperCase())) && n.loca.equals(Enums.Location.valueOf(saveloca.trim().toUpperCase()))).
//                            forEach(n -> System.out.println(n.name));
                        i++;
                    }
                    String saveCol = consoleLineForChoosing;
                    System.out.println("Теперь выберите размер из предложенных пижам или напишите команду \"exit\"");
                    consoleLineForChoosing = scnChoice.nextLine();
                    if (!consoleLineForChoosing.equals("exit")) {

//                    consoleLineForChoosing = "long";
                        indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());
                        while ((indexOf == -1) || consoleLineForChoosing.equals("")) {
                            System.out.println("Введите корректный размер");
                            consoleLineForChoosing = scnChoice.nextLine();
                            indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());

                        }

                        System.out.println("Следующие пижамы доступны: ");

                        i = -1;
                        listItemNumber = 0;
                        String saveEPj = consoleLineForChoosing;
                        locationHas = "";
                        for (Pj pj : pjcol) {

                            i++;
                            if (pj.epj.equals(EPj.valueOf(consoleLineForChoosing.trim().toUpperCase())) && pj.color.equals(ColorsEnum.valueOf(saveCol.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                                locationHas = locationHas + strClearance[i];
                                listItemNumber++;
                                System.out.println((listItemNumber) + ") Чистота: " + strClearance[i]);

                            }

                        }
                        System.out.println("выберите пижаму из предложенных или напишите команду \"exit\". Для этого напишите чистоту пижамы");


                        consoleLineForChoosing = scnChoice.nextLine();
                        if (!consoleLineForChoosing.equals("exit")) {

                            indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());
                            while ((indexOf == -1) || consoleLineForChoosing.equals("")) {
                                System.out.println("Введите корректную чистоту");
                                consoleLineForChoosing = scnChoice.nextLine();
                                indexOf = locationHas.lastIndexOf(consoleLineForChoosing.toUpperCase());

                            }
                            String saveClearance = consoleLineForChoosing;
                            System.out.println("how would you name Her?");
                            consoleLineForChoosing = scnChoice.nextLine();
                            pname = consoleLineForChoosing;
                            System.out.println(this.name + " выбрал пижаму по имени" + pname + " в шкафу." + saveloca + " " + "Цвет: " + saveCol + ", чистота: " + saveClearance + ", размер: " + saveEPj);

                            Pj pj = new Pj(pname, EPj.valueOf(saveEPj.trim().toUpperCase()), EPjc.valueOf(saveClearance.trim().toUpperCase()), Location.valueOf(saveloca.trim().toUpperCase()), ColorsEnum.valueOf(saveCol.trim().toUpperCase()), i);

                            gotDressed = true;
                            Hero_Pj hero_pj = new Hero_Pj(this, pj);
                            Hero_Pj.h_p.add(hero_pj);


                        }
                    }
                }
            } catch (NoSuchElementException nsee) {
                System.out.println("элемент не найден");
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

        System.out.printf("Пришлось найти чистую пижаму для %s %n", getName());
        p.epjc = epjc.WASHED;
    }

    @Override
    public void givePjsmall(Heroes h, Pj p) {

        hmm = (int) (Math.random() * 2);
        if (hmm == 0) {
            System.out.printf(
                    ", но %s тут же нашёл выход — недолго думая он их обрезал. %n"
                            + "%s не успел и слова вымолвить, но он, по правде "
                            + "говоря, даже не очень огорчился. %n В конце концов, "
                            + "рассуждал он, пижама — это пустяки, дело житейское, "
                            + "и то, что она погибла, не может омрачить его радости: "
                            + "%n ведь это такое удивительное событие — " + "%s останется у него ночевать!%n ",
                    HOST.getName(), getName(), getName());

        } else {
            System.out.printf("%s нашел новую пижаму для %s, как раз по размеру %n", HOST.getName(), getName());

        }
        p.epj = epj.OK;

    }

}