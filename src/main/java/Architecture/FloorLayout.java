package Architecture;

import java.util.ArrayList;

public class FloorLayout {
    private final ArrayList<Floor> floors;

    public FloorLayout(Floor initialFloor) {
        this.floors = new ArrayList<>();
        this.floors.add(initialFloor);
    }

    public FloorLayout(int level) {
        this.floors = new ArrayList<>();
        this.floors.add(new Floor(level));
    }

    public void addAbove(int distance, int level) {
        if(level <= getTopFloor().getLevel()) {
            throw new IllegalArgumentException("New floor's level must be above the current top floor's level");
        }

        Floor newFloor = new Floor(level);
        Shaft newShaft = new Shaft(distance, this.getTopFloor(), newFloor);
        this.getTopFloor().setShaftAbove(newShaft);
        newFloor.setShaftBelow(newShaft);

        this.floors.add(newFloor);
    }

    public void addBelow(int distance, int level) {
        if(level >= getBottomFloor().getLevel()) {
            throw new IllegalArgumentException("New floor's level must be below the current bottom floor's level");
        }

        Floor newFloor = new Floor(level);
        Shaft newShaft = new Shaft(distance, newFloor, this.getBottomFloor());
        this.getBottomFloor().setShaftBelow(newShaft);
        newFloor.setShaftAbove(newShaft);

        this.floors.add(0, newFloor);
    }

    public Floor getTopFloor() {
        // Requires java 21
        return this.floors.getLast();
    }

    public Floor getBottomFloor() {
        // Requires java 21
        return this.floors.getFirst();
    }

    public ArrayList<Floor> getFloors() {
        return floors;
    }
}
