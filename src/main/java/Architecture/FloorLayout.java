package Architecture;

import java.util.ArrayList;

/**
 * The FloorLayout class represents the layout of Floors and Shafts in a building.
 */
public class FloorLayout {
    private final ArrayList<Floor> floors;

    /**
     * Floor layout constructor with a given initial Floor.
     *
     * @param initialFloor the initial Floor of the layout
     */
    public FloorLayout(Floor initialFloor) {
        this.floors = new ArrayList<>();
        this.floors.add(initialFloor);
    }

    /**
     * Floor layout constructor with a given initial Floor level.
     *
     * @param initialLevel the level of the initial Floor
     */
    public FloorLayout(int initialLevel) {
        this.floors = new ArrayList<>();
        this.floors.add(new Floor(initialLevel));
    }

    /**
     * Adds a new Floor above the top floor with the specified distance.
     *
     * @param distance the distance between the top Floor and the new Floor
     */
    public void addAbove(int distance) {
        Floor newFloor = new Floor(getTopFloor().getLevel() + 1);
        Shaft newShaft = new Shaft(distance, this.getTopFloor(), newFloor);
        this.getTopFloor().setShaftAbove(newShaft);
        newFloor.setShaftBelow(newShaft);

        this.floors.add(newFloor);
    }

    /**
     * Adds a specified count of new Floors above the top Floor with the specified distance between each Floor.
     *
     * @param distance the distance between each new Floor
     * @param count    the number of Floors to add above
     */
    public void addAbove(int distance, int count) {
        for(int i = 0; i < count; i++) {
            addAbove(distance);
        }
    }

    /**
     * Adds a new Floor below the bottom Floor with the specified distance.
     *
     * @param distance the distance between the bottom Floor and the new Floor
     */
    public void addBelow(int distance) {
        Floor newFloor = new Floor(getBottomFloor().getLevel() - 1);
        Shaft newShaft = new Shaft(distance, newFloor, this.getBottomFloor());
        this.getBottomFloor().setShaftBelow(newShaft);
        newFloor.setShaftAbove(newShaft);

        this.floors.add(0, newFloor);
    }

    /**
     * Adds a specified count of new Floors below the bottom Floor with the specified distance between each Floor.
     *
     * @param distance the distance between each new Floor
     * @param count    the number of Floors to add below
     */
    public void addBelow(int distance, int count) {
        for(int i = 0; i < count; i++) {
            addBelow(distance);
        }
    }

    /**
     * Returns the top Floor of the layout.
     *
     * @return the top Floor of the layout
     */
    public Floor getTopFloor() {
        return this.floors.getLast();
    }

    /**
     * Returns the bottom Floor of the layout.
     *
     * @return the bottom Floor of the layout
     */
    public Floor getBottomFloor() {
        return this.floors.getFirst();
    }

    /**
     * Returns the list of Floors in the layout.
     *
     * @return the list of Floors in the layout
     */
    public ArrayList<Floor> getFloors() {
        return floors;
    }
}
