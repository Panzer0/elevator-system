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

}
