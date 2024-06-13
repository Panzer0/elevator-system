package Architecture;

public class Floor {
    private final int level;
    private Shaft shaftBelow = null;
    private Shaft shaftAbove = null;

    public Floor(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
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
}
