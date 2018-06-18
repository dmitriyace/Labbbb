import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;
import Enums.Location;

public class ReflectEnum {
    static DataBaseWork db;
    static Class c;
    static int id;

    ReflectEnum(DataBaseWork db) {
        this.db = db;
    }

    public static void reflectEnums() {
        db.deleteFromEnumTables("location");
        db.deleteFromEnumTables("colors");
        db.deleteFromEnumTables("clearance");
        db.deleteFromEnumTables("size");

        c = Location.class;
        id = 0;
        for (Location loc : (Location[]) c.getEnumConstants()) {
            id++;
            db.fillEnumTable(loc, "location", id);

        }
        c = ColorsEnum.class;
        id = 0;
        for (ColorsEnum ce : (ColorsEnum[]) c.getEnumConstants()) {
            id++;
            db.fillEnumTable(ce, "colors", id);
        }
        c = EPj.class;
        id = 0;
        for (EPj size : (EPj[]) c.getEnumConstants()) {
            id++;
            db.fillEnumTable(size, "size", id);
        }
        c = EPjc.class;
        id = 0;
        for (EPjc clear : (EPjc[]) c.getEnumConstants()) {
            id++;
            db.fillEnumTable(clear, "clearance", id);
        }
    }

}
