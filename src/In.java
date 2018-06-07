import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;
import Enums.Location;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class In {

    public static void getPjeys(String path, CopyOnWriteArrayList<Pj> collection) {
        String checkheroname = "", strSize, strClear, strLocation, strColor;
        EPj size;
        EPjc clearance;
        Location loca;
        int id = 0;
        int i = 0;
        String name;
        ColorsEnum color;
        SimpleDateFormat sdt = new SimpleDateFormat("hh:mm:ss:SSS");
        try (Scanner inputXML = new Scanner(new File(path))) {
            inputXML.useDelimiter("</");
            while (!(inputXML.findWithinHorizon("</pijamacollection>", 60) instanceof String)) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                checkheroname = inputXML.findWithinHorizon("<pijamasize>", 100000);
                strSize = inputXML.next().trim().toUpperCase();
                checkheroname = inputXML.findWithinHorizon("<pijamaclearance>", 60);
                strClear = inputXML.next().trim().toUpperCase();
                checkheroname = inputXML.findWithinHorizon("<pijamalocation>", 60);
                strLocation = inputXML.next().trim().toUpperCase();
                checkheroname = inputXML.findWithinHorizon("<pijamacolor>", 60);
                strColor = inputXML.next().trim().toUpperCase();
                checkheroname = inputXML.findWithinHorizon("<pijamaname>", 60);
                name = inputXML.next().trim().toUpperCase();

                size = EPj.valueOf(strSize);
                clearance = EPjc.valueOf(strClear);
                loca = Location.valueOf(strLocation);
                color = ColorsEnum.valueOf(strColor);

                id++;
                try {
                    Thread.sleep(2);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                collection.add(new Pj(name, size, clearance, loca, color, id));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException io) {
            System.out.println("Input exception");
        }
    }


}
