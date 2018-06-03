package Enums;

public enum Location {


    NEAR_BED(45, 116), NEAR_KITCHEN(146, 47), LIVING_ROOM(87, 90), SS(10, 10);
    int y;
    int x;

    Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}