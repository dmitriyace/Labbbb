package Enums;

public enum Location {
//change id when update.

    NEAR_BED(1, 45, 116), NEAR_KITCHEN(2, 146, 47), LIVING_ROOM(3, 87, 90), SS(4, 10, 10);
    int y;
    int x;
    int id;

    Location(int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }
}