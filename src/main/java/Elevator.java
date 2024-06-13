import Architecture.Floor;
import Architecture.Shaft;

import java.util.TreeSet;

public class Elevator {
    enum Status {
        UPWARD,
        DOWNWARD,
        IDLE
    }

    int id;
    Status status;
    Floor currentFloor;
    int shaftProgress;
    boolean waiting;
    TreeSet<Integer> roadmap;
    TreeSet<Integer> backlog;

    public Elevator(int id, Floor currentFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.status = Status.IDLE;
        this.shaftProgress = 0;
        this.waiting = false;
        this.roadmap = new TreeSet<>();
        this.backlog = new TreeSet<>();
    }

    public float getPosition() {
        float position = (float)this.currentFloor.getLevel();
        switch (this.status) {
            case UPWARD -> {
                Shaft activeShaft = this.currentFloor.getShaftAbove();
                if(activeShaft == null) {
                    throw new IllegalStateException("Can't be moving upwards beyond top floor");
                }

                if(this.shaftProgress == 0) {
                    return position;
                }
                return position + (float)this.shaftProgress / activeShaft.getDistance();
            }
            case DOWNWARD -> {
                Shaft activeShaft = this.currentFloor.getShaftBelow();
                if(activeShaft == null) {
                    throw new IllegalStateException("Can't be moving downwards beyond bottom floor");
                }

                if(this.shaftProgress == 0) {
                    return position;
                }
                return position - (float)this.shaftProgress / activeShaft.getDistance();
            }
            default -> {
                return position;
            }
        }
    }
}
