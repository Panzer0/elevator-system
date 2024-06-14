package Architecture;

/**
 * The Floor class represents a floor in a building for an elevator to navigate.
 */
public class Floor implements Comparable<Floor>{
    private final int level;
    private Shaft shaftBelow = null;
    private Shaft shaftAbove = null;

    /**
     * Floor object constructor.
     *
     * @param level the level of the floor
     */
    public Floor(int level) {
        this.level = level;
    }

    /**
     * Returns the level of the floor.
     *
     * @return the level of the floor
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the bottom floor connected to this floor.
     *
     * Returns the bottom floor connected to this floor via Shaft objects by iterating over sequential shafts and
     * floors.
     *
     * @return the bottom floor
     */
    public Floor getBottomFloor() {
        Floor floor = this;
        while(floor.getShaftBelow() != null && floor.getShaftBelow().getFloorBelow() != null) {
            floor = floor.getShaftBelow().getFloorBelow();
        }
        return floor;
    }

    /**
     * Returns the top floor connected to this floor.
     *
     * Returns the top floor connected to this floor via Shaft objects by iterating over sequential shafts and floors.
     *
     * @return the top floor
     */
    public Floor getTopFloor() {
        Floor floor = this;
        while(floor.getShaftAbove() != null && floor.getShaftAbove().getFloorAbove() != null) {
            floor = floor.getShaftAbove().getFloorAbove();
        }
        return floor;
    }

    /**
     * Returns the shaft below this floor.
     *
     * @return the shaft below this floor
     */
    public Shaft getShaftBelow() {
        return shaftBelow;
    }

    /**
     * Sets the shaft below this floor.
     *
     * @param shaftBelow the shaft to set below this floor
     */
    public void setShaftBelow(Shaft shaftBelow) {
        this.shaftBelow = shaftBelow;
    }

    /**
     * Returns the shaft above this floor.
     *
     * @return the shaft above this floor
     */
    public Shaft getShaftAbove() {
        return shaftAbove;
    }

    /**
     * Sets the shaft above this floor.
     *
     * @param shaftAbove the shaft to set above this floor
     */
    public void setShaftAbove(Shaft shaftAbove) {
        this.shaftAbove = shaftAbove;
    }

    /**
     * Compares this floor to another floor based on their levels.
     *
     * @param other the other floor to compare to
     * @return a negative integer, zero, or a positive integer as this floor is less than, equal to, or greater than the
     * other floor
     */
    @Override
    public int compareTo(Floor other) {
        return Integer.compare(this.level, other.getLevel());
    }
}
