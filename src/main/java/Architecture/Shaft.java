package Architecture;

/**
 * The Shaft class represents a shaft connecting two Floors in a building.
 */
public class Shaft {
    private int distance;
    private final Floor floorBelow;
    private final Floor floorAbove;

    /**
     * Shaft object constructor with a specified distance and neighbouring Floors.
     *
     * @param distance   the distance between the Floors
     * @param floorBelow the Floor below the Shaft
     * @param floorAbove the Floor above the Shaft
     * @throws IllegalArgumentException if the distance is not positive or if floorBelow and floorAbove are the same
     */
    public Shaft(int distance, Floor floorBelow, Floor floorAbove) {
        if(distance <= 0) {
            throw new IllegalArgumentException("distance must be greater than 0");
        }
        if(floorAbove == floorBelow) {
            throw new IllegalArgumentException("floorBelow and floorAbove must not be the same");
        }
        this.distance = distance;
        this.floorBelow = floorBelow;
        this.floorAbove = floorAbove;
    }
    /**
     * Returns the distance between the Floors connected by the Shaft.
     *
     * @return the distance between the Floors
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Sets the distance between the Floors connected by the Shaft.
     *
     * @param distance the distance to set
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Returns the Floor below the Shaft.
     *
     * @return the Floor below the Shaft
     */
    public Floor getFloorBelow() {
        return floorBelow;
    }

    /**
     * Returns the Floor above the Shaft.
     *
     * @return the Floor above the Shaft
     */
    public Floor getFloorAbove() {
        return floorAbove;
    }

}
