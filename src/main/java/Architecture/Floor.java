package Architecture;

public class Floor implements Comparable<Floor>{
    private final int level;
    private Shaft shaftBelow = null;
    private Shaft shaftAbove = null;

    public Floor(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public Floor getBottomFloor() {
        Floor floor = this;
        while(floor.getShaftBelow() != null && floor.getShaftBelow().getFloorBelow() != null) {
            floor = floor.getShaftBelow().getFloorBelow();
        }
        return floor;
    }
    public Floor getTopFloor() {
        Floor floor = this;
        while(floor.getShaftAbove() != null && floor.getShaftAbove().getFloorAbove() != null) {
            floor = floor.getShaftAbove().getFloorAbove();
        }
        return floor;
    }

    public Shaft getShaftBelow() {
        return shaftBelow;
    }

    public void setShaftBelow(Shaft shaftBelow) {
        this.shaftBelow = shaftBelow;
    }

    public Shaft getShaftAbove() {
        return shaftAbove;
    }

    public void setShaftAbove(Shaft shaftAbove) {
        this.shaftAbove = shaftAbove;
    }

    @Override
    public int compareTo(Floor other) {
        return Integer.compare(this.level, other.getLevel());
    }
}
