package Enums;

public enum EPj {
    LONG(1),SHORT(2),OK(3);
    int id;

    EPj(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
// энам для размера пижамы