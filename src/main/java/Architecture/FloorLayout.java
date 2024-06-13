package Architecture;

import java.util.ArrayList;

public class FloorLayout {
    private final ArrayList<Floor> floors;

    public FloorLayout(Floor initialFloor) {
        this.floors = new ArrayList<>();
        this.floors.add(initialFloor);
    }

    public Floor getTopFloor() {
        // Requires java 21
        return this.floors.getLast();
    }

    public Floor getGroundFloor() {
        // Requires java 21
        return this.floors.getFirst();
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

    public ArrayList<Floor> getFloors() {
        return floors;
    }
}
