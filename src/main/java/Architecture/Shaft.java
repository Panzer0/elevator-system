package Architecture;

public class Shaft {
    private int distance;
    private final Floor floorBelow;
    private final Floor floorAbove;

    public Shaft(int distance, Floor floorBelow, Floor floorAbove) {
        if(distance <= 0) {
            throw new IllegalArgumentException("distance must be greater than 0");
        }
        if(floorAbove == floorBelow) {
            throw new IllegalArgumentException("floorBelow and floorAbove must not be the same");
        }
        if(floorBelow.getLevel() >= floorAbove.getLevel()) {
            throw new IllegalArgumentException("floorBelow's level must be lesser than floorAbove's level");
        }
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
