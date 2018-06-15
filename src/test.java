import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

import Enums.ColorsEnum;
import Enums.EPj;
import org.pushingpixels.trident.*;

import javax.swing.*;


class test {
    public static void main(String[] args) {
        OffsetDateTime odt = OffsetDateTime.now( ZoneId.of("Europe/Moscow") ) ;
        System.out.println(odt);

    }

}