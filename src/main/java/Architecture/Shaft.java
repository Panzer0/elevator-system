package Architecture;

public class Shaft {
    private int distance;
    private Floor floorBelow = null;
    private Floor floorAbove = null;

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

    public void setFloorBelow(Floor floorBelow) {
        this.floorBelow = floorBelow;
    }

    public Floor getFloorAbove() {
        return floorAbove;
    }

    public void setFloorAbove(Floor floorAbove) {
        this.floorAbove = floorAbove;
    }
}
