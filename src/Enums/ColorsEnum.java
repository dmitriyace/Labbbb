package Enums;

public enum ColorsEnum {


    RED(1), BLUE(2), WHITE(3), GREY(4);
    int id;

    ColorsEnum(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

}
