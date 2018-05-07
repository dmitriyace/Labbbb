package Enums;

public enum Location {


    NEAR_BED(45,116),NEAR_KITCHEN(146,47),LIVING_ROOM(87,90),SS(10,10);
    private int[] xy = new int[2];

    Location(int x,int y) {
        this.xy[0] = x;this.xy[1]=y;
    }
}