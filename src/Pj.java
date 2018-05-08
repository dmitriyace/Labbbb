import Enums.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pj extends Over implements P_i, Comparable, Serializable {

    protected EPj epj;
    protected EPjc epjc;

    protected Type t;
    protected Emotional e;
    protected String name;
    protected Enum<Location> loca;
    protected ColorsEnum color;
    protected String h;
    transient SimpleDateFormat ft =
            new SimpleDateFormat("hh:mm:ss:SSS");
    protected  String dt;
    int id;
    public static Pj defaultPj = new Pj("default",EPj.LONG, EPjc.UNWASHED, Location.NEAR_BED, ColorsEnum.WHITE, 44444444);

    public Pj(String name, EPj epj, EPjc epjc, Enum<Location> loca, ColorsEnum color, int num) {
        this.name = name;
        id = num;
        this.epj = epj;
        this.epjc = epjc;
        this.loca = loca;
        this.color = color;
        this.dt = ft.format(new Date());

    }


    public EPj getSize() {
        return epj;
    }

    public EPjc getClearance() {
        return epjc;

    }

    @Override
    public void setSize(EPj epj) {
        this.epj = epj;
    }

    // переопределение методов hashCode, toString и equals
    @Override
    public String toString() {
        return this.getClass().toString() + "";

    }

    @Override
    public int hashCode() {
        int hashcode = id ^ (id >>> 16);
        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Pj pe = (Pj) obj;
        return (this.hashCode() == pe.hashCode());
    }

    @Override
    public int compareTo(Object o) {
        if (this.id == ((Pj) o).id)
            return 0;
        else if (this.id < ((Pj) o).id)
            return 1;
        else
            return -1;

    }
}
