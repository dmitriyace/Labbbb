import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import Enums.ColorsEnum;
import Enums.EPj;
import Enums.Location;
import org.pushingpixels.trident.*;

import javax.swing.*;

import  static java.lang.System.out;
class test {
    public static void main(String[] args) {
//        OffsetDateTime odt = OffsetDateTime.now( ZoneId.of("Europe/Moscow") ) ;
//        System.out.println(odt);
        Class c = Location.class;
        for (Location l: (Location[]) c.getEnumConstants()){
            out.format("%n%s", l.toString());
        }



    }

}