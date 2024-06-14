package Architecture;

import java.util.ArrayList;

public class FloorLayout {
    private final ArrayList<Floor> floors;

    public FloorLayout(Floor initialFloor) {
        this.floors = new ArrayList<>();
        this.floors.add(initialFloor);
    }

    public FloorLayout(int initialLevel) {
        this.floors = new ArrayList<>();
        this.floors.add(new Floor(initialLevel));
    }

    public void addAbove(int distance) {
        Floor newFloor = new Floor(getTopFloor().getLevel() + 1);
        Shaft newShaft = new Shaft(distance, this.getTopFloor(), newFloor);
        this.getTopFloor().setShaftAbove(newShaft);
        newFloor.setShaftBelow(newShaft);

        this.floors.add(newFloor);
    }

    public void addAbove(int distance, int count) {
        for(int i = 0; i < count; i++) {
            addAbove(distance);
        }
    }

    public void addBelow(int distance) {
        Floor newFloor = new Floor(getBottomFloor().getLevel() - 1);
        Shaft newShaft = new Shaft(distance, newFloor, this.getBottomFloor());
        this.getBottomFloor().setShaftBelow(newShaft);
        newFloor.setShaftAbove(newShaft);

        this.floors.add(0, newFloor);
    }

    public void addBelow(int distance, int count) {
        for(int i = 0; i < count; i++) {
            addBelow(distance);
        }
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
