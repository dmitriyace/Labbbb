import java.util.*;

public class PjCollection  {

    public static HashSet<Pj> pjeys = new HashSet<>();


    public static void pjeysSrt() {
        TreeSet<Pj> pjeysSort = new TreeSet<Pj>(pjeys);
        pjeysSort.addAll(pjeys);
        System.out.println(pjeysSort);


    }


    public static void show() {

        for(Pj pj:pjeys){
            String whos = pj.h;
            String strSize = pj.epj.toString();
            String strClear = pj.epjc.toString();
            String strLocation = pj.loca.toString();
            System.out.println(whos+" pijama. Size of the pijama is "+strSize.toLowerCase());
            if (strClear.equals("WASHED")){
                System.out.print(" Pijama is clear and fresh. ");

            }else System.out.print("Pijama is dirty. ");
            System.out.printf("Pijama for %s was found %s", whos.replace("'s",""),strLocation.toLowerCase());
            System.out.println();
        }
    }

}
