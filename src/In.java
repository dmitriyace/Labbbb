import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class In {
    public static void getPjeys(String path, String encoding, Heroes hero, HashSet<Pj> pjeys) {

        String checkheroname = "", strSize, strClear, strLocation, strName=hero.name;
        EPj size;
        EPjc clearance;
        Location loca;

        try {
            Scanner inputXML = new Scanner(new File(path));
            inputXML.useDelimiter("</");
            for (int i = 0; i < 4; i++) {//количество циклов фор можно посчитать по количеству
                //пижам. количество пижам считается счетчиком в цикле вайл пока сканнер ищет пижамы
                if (checkheroname.equals(strName)) {

                    checkheroname = inputXML.findWithinHorizon("<pijamasize>", 40);
                    strSize = inputXML.next().trim().toUpperCase();
                    checkheroname = inputXML.findWithinHorizon("<pijamaclearance>", 40);
                    strClear = inputXML.next().trim().toUpperCase();
                    checkheroname = inputXML.findWithinHorizon("<pijamalocation>", 40);
                    strLocation = inputXML.next().trim().toUpperCase();
                    size = EPj.valueOf(strSize);
                    clearance = EPjc.valueOf(strClear);
                    loca = Location.valueOf(strLocation);

                    pjeys.add(new Pj(size,clearance,loca,hero));
                    System.out.println(pjeys.size()+" "+pjeys.hashCode());
                } else {
                    checkheroname = inputXML.findWithinHorizon("<person>", 100000);
                    checkheroname = inputXML.next();
                    System.out.println(checkheroname);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
