package Architecture;

public class Shaft {
    private int distance;
    private final Floor floorBelow;
    private final Floor floorAbove;

    public Shaft(int distance, Floor floorBelow, Floor floorAbove) {
        this.distance = distance;
        this.floorBelow = floorBelow;
        this.floorAbove = floorAbove;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Floor getFloorBelow() {
        return floorBelow;
    }

    public Floor getFloorAbove() {
        return floorAbove;
    }

}
