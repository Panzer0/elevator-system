import Architecture.Floor;

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

    
}
