

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
//        public void choosingPj(CopyOnWriteArrayList<Pj> pjcol) {


        System.out.println(this.name + " выбирает пижаму...");
//        Scanner scnChoice = new Scanner(System.in);
        int colLength = pjcol.size();
        String[] strSize = new String[colLength];
        String[] strLoca = new String[colLength];
        String[] strColor = new String[colLength];
        String[] strClearance = new String[colLength];
        String dostupLoca = "";
        String pname;
        boolean gotDressed = false;
        String console = "";
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
                    i++;
                }
                i = 0;

                for (Pj pj : pjcol) {
                    i++;
//                    System.out.println(i + ") Локация: " + strLoca[i - 1]);
                    dostupLoca += "\n" + i + ") Локация: " + strLoca[i - 1];
                }
                out.writeObject("\nВ какой шкаф пойдет Герой?\n" + dostupLoca);
                System.out.println("жду шкаф");
//                charPos = dostupLoca.length();
//                dostupLoca = dostupLoca.substring(0, charPos - 2);
//                System.out.println("В какой шкаф пойдет " + this.name + "? Доступные варианты: " + dostupLoca);
                console = (String) in.readObject();

//                console = "ss";
                int indexOf = dostupLoca.lastIndexOf(console.toUpperCase());
                while (((indexOf == -1) || console.equals(""))) {
                    out.writeObject("cont\nВведите корректный шкаф\nДоступные варианты: \n" + dostupLoca);
                    console = (String) in.readObject();
                    indexOf = dostupLoca.lastIndexOf(console.toUpperCase());
                }
                int count = 0;

//                if (isNumeric(console)) {
//                    for (Pj pj : pjcol) {
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

                for (Pj pj : pjcol) {
                    if (pj.loca.equals(Location.valueOf(console.toUpperCase()))) {
                        listItemNumber++;
                        locationHas = locationHas + strColor[i];
                        dostupLoca += ("\n" + (listItemNumber) + ") Цвет: " + strColor[i] + ", чистота: " + strClearance[i] + ", размер: " + strSize[i]);

                    }
                    i++;
                }
                dostupLoca += ("\nТеперь выберите цвет из предложенных пижам или напишите команду \"exit\"");
                out.writeObject(dostupLoca);
                console = (String) in.readObject();
                if (!console.equals("exit")) {
                    indexOf = locationHas.lastIndexOf(console.toUpperCase());
                    while ((indexOf == -1) || console.equals("")) {
                        out.writeObject("\nВведите корректный цвет");
                        console = (String) in.readObject();
                        indexOf = locationHas.lastIndexOf(console.toUpperCase());

                    }
//                    console = "blue";
                    locationHas = "";
                    dostupLoca = "";
                    dostupLoca += ("\nСледующие пижамы цвета " + console + " доступны: ");
                    i = 0;
                    listItemNumber = 0;
                    for (Pj pj : pjcol) {
                        if (pj.color.equals(ColorsEnum.valueOf(console.trim().toUpperCase())) && pj.loca.equals(Location.valueOf(saveloca.trim().toUpperCase()))) {
                            listItemNumber++;
                            locationHas = locationHas + strSize[i];
                            dostupLoca += ("\n" + (listItemNumber) + ") Чистота: " + strClearance[i] + ", размер: " + strSize[i]);

                        }
//                    pjcol.stream().filter(n ->n.color.equals(Enums.ColorsEnum.valueOf(console.trim().toUpperCase())) && n.loca.equals(Enums.Location.valueOf(saveloca.trim().toUpperCase()))).
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
                        for (Pj pj : pjcol) {

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

    public void testChoosing(CopyOnWriteArrayList<Pj> pjcol, ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
        String[] strSize = new String[pjcol.size()];
        String[] strLoca = new String[pjcol.size()];
        String[] strColor = new String[pjcol.size()];
        String[] strClearance = new String[pjcol.size()];
        String tempRes = "";
        String answer = "";
        int numAnswer;
        ArrayList<HandleNumbers> handleNum = new ArrayList<>();
        int i;
        int lastIndex;
        boolean getDressed = false;
        ArrayList<Pj> saveC;
        while (!getDressed) {
            saveC = (ArrayList<Pj>) pjcol.stream().collect(Collectors.toList());
            getDressed = false;
            i = 0;
            for (Pj pj : saveC) {
                strSize[i] = pj.epj.toString();
                strLoca[i] = pj.loca.toString();
                strClearance[i] = pj.epjc.toString();
                strColor[i] = pj.color.toString();
                i++;
            }

            i = 0;
            for (Pj pj : saveC) {
                i++;
                tempRes += "\n" + i + ") Локация: " + strLoca[i - 1];
                handleNum.add(new HandleNumbers(i, i - 1));
            }
            out.writeObject(tempRes);
            answer = (String) in.readObject();
            answer = figureAnswer(tempRes, answer, in, out);
//            lastIndex = tempRes.lastIndexOf(answer.toUpperCase());
//            while ((lastIndex == -1) || (answer.equals(""))) {
//                //out.writeObject("cont\nВведите корректный шкаф\nДоступные варианты: " + tempRes);
//                //answer = (String)read...
//                lastIndex = tempRes.lastIndexOf(answer.toUpperCase());
//            }
            if (isNumeric(answer)) {
                numAnswer = Integer.valueOf(answer);
                i = 0;
                for (Pj pj : saveC) {
                    if (numAnswer - 1 == i) {
                        answer = strLoca[i];
                        break;
                    } else i++;
                }
            }
            //if answer is still numeric, (check it), then you should again call figureAnswer while it isn't ok
            String saveLocation = answer;
            saveC = (ArrayList<Pj>) saveC.stream().filter(n -> n.loca.equals(Location.valueOf(saveLocation.toUpperCase()))).collect(Collectors.toList());
            tempRes = ("\nСледующие пижамы в шкафу " + saveLocation + " доступны: ");
            i = 0;
            for (Pj pj : saveC) {
                strSize[i] = pj.epj.toString();
                strClearance[i] = pj.epjc.toString();
                strColor[i] = pj.color.toString();
                tempRes += ("\n" + (i + 1) + ") Цвет: " + strColor[i] + ", чистота: " + strClearance[i] + ", размер: " + strSize[i]);
                i++;
            }
            tempRes += "Выберете цвет из предложенных пижам: ";
            out.writeObject(tempRes);
            answer = (String) in.readObject();
            figureAnswer(tempRes, answer, in, out);
            if (isNumeric(answer)) {
                numAnswer = Integer.valueOf(answer);
                i = 0;
                for (Pj pj : saveC) {
                    if (numAnswer - 1 == i) {
                        answer = strColor[i];
                        break;
                    } else i++;
                }
            }
            String saveColor = answer;
            saveC = (ArrayList<Pj>) saveC.stream().filter(n -> n.color.equals(ColorsEnum.valueOf(saveColor.toUpperCase()))).collect(Collectors.toList());
            tempRes = ("\nСледующие пижамы в шкафу " + saveLocation + " цвета " + saveColor + " доступны:");
            i = 0;
            for (Pj pj : saveC) {
                strSize[i] = pj.epj.toString();
                strClearance[i] = pj.epjc.toString();
                tempRes += ("\n" + (i + 1) + ") Чистота: " + strClearance[i] + ", размер: " + strSize[i]);
                i++;
            }
            tempRes += "Выберете размер из предложенных";
            out.writeObject(tempRes);
            answer = (String) in.readObject();
            figureAnswer(tempRes, answer, in, out);
            if (isNumeric(answer)) {
                numAnswer = Integer.valueOf(answer);
                i = 0;
                for (Pj pj : saveC) {
                    if (numAnswer - 1 == i) {
                        answer = strSize[i];
                        break;
                    } else i++;
                }
            }
            String saveSize = answer;
            saveC = (ArrayList<Pj>) saveC.stream().filter(n -> n.epj.equals(EPj.valueOf(saveSize.toUpperCase()))).collect(Collectors.toList());
            tempRes += ("\nСледующие пижамы в шкафу " + saveLocation + " цвета " + saveColor + " размера " + saveSize + " доступны:");
            i = 0;
            for (Pj pj : saveC) {
                strClearance[i] = pj.epjc.toString();
                tempRes += ("\n" + (i + 1) + ") Чистота: " + strClearance[i]);
                i++;
            }
            tempRes += "Выберете чистоту из предложенных";
            out.writeObject(tempRes);
            answer = (String) in.readObject();
            figureAnswer(tempRes, answer, in, out);
            if (isNumeric(answer)) {
                numAnswer = Integer.valueOf(answer);
                i = 0;
                for (Pj pj : saveC) {
                    if (numAnswer - 1 == i) {
                        answer = strClearance[i];
                        break;
                    } else i++;
                }
            }
            String saveClear = answer;
            out.writeObject("\nWhat is her name?");
            String pname = (String) in.readObject();
            out.writeObject("\n" + this.name + " выбрал пижаму по имени \"" + pname + "\" в шкафу " + saveLocation + ". " + "Цвет: " + saveColor + ", чистота: " + saveClear + ", размер: " + saveSize + "\n " +
                    "Нажмите \"ввод\", если хотите продолжить");

            Pj pj = new Pj(pname, EPj.valueOf(saveSize.trim().toUpperCase()), EPjc.valueOf(saveClear.trim().toUpperCase()), Location.valueOf(saveLocation.trim().toUpperCase()), ColorsEnum.valueOf(saveColor.trim().toUpperCase()), i);

            getDressed = true;
            Hero_Pj hero_pj = new Hero_Pj(this, pj);
//            Hero_Pj.h_p.add()

        }


    }

    static String figureAnswer(String tempRes, String answer, ObjectInputStream in, ObjectOutputStream out) {
        int lastIndex = tempRes.lastIndexOf(answer.toUpperCase());
        while ((lastIndex == -1) || (answer.equals(""))) {
            try {
                out.writeObject("\nВведите корректный шкаф\nДоступные варианты: " + tempRes);
                answer = (String) in.readObject();
            } catch (IOException e1) {
                e1.printStackTrace();
                System.err.println("клиент отвалился");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            lastIndex = tempRes.lastIndexOf(answer.toUpperCase());
        }
        return answer;
    }

}