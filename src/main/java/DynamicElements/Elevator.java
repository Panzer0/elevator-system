package DynamicElements;

import Architecture.Floor;
import Architecture.Shaft;

import java.util.TreeSet;

public class Elevator {

    private int id;
    private Status status;
    private Floor currentFloor;
    private int shaftProgress;
    private boolean waiting;
    private TreeSet<Integer> roadmap;
    private TreeSet<Integer> backlog;

    public Elevator(int id, Floor currentFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.status = Status.IDLE;
        this.shaftProgress = 0;
        this.waiting = false;
        this.roadmap = new TreeSet<>();
        this.backlog = new TreeSet<>();
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public TreeSet<Integer> getRoadmap() {
        return roadmap;
    }

    public TreeSet<Integer> getBacklog() {
        return backlog;
    }

    public float getPosition() {
        float position = (float)this.currentFloor.getLevel();
        switch(this.status) {
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

    public int getShaftProgress() {
        return shaftProgress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCurrentFloor(Floor currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public void setRoadmap(TreeSet<Integer> roadmap) {
        this.roadmap = roadmap;
    }

    public void setBacklog(TreeSet<Integer> backlog) {
        this.backlog = backlog;
    }

    public void setShaftProgress(int shaftProgress) {
        this.shaftProgress = shaftProgress;
    }

    public void assignCall(int level) {
        if(level < this.getCurrentFloor().getBottomFloor().getLevel()
                || level > this.currentFloor.getTopFloor().getLevel()) {
            throw new IllegalArgumentException("Level must be within bounds of existing floors");
        }

        // Already at the requested floor or headed in the right direction
        if((level > this.getPosition() && this.status == Status.UPWARD)
                || (level < this.getPosition() && this.status == Status.DOWNWARD)
                || ((float)level == this.getPosition())) {
            this.roadmap.add(level);
        }
        else if(this.status == Status.IDLE) {
            this.roadmap.add(level);
            this.updateStatus();
        }
        else{
            this.backlog.add(level);
        }
    }

    private void move() {
        if(this.status == Status.IDLE) {
            return;
        }
        this.shaftProgress++;
        switch(this.status) {
            case UPWARD -> {
                Shaft nextShaft = this.currentFloor.getShaftAbove();
                if(this.shaftProgress >= nextShaft.getDistance()) {
                    this.currentFloor = nextShaft.getFloorAbove();
                    this.shaftProgress = 0;
                    this.checkOffStop();
                }
            }
            case DOWNWARD -> {
                Shaft nextShaft = this.currentFloor.getShaftBelow();
                if(this.shaftProgress >= nextShaft.getDistance()) {
                    this.currentFloor = nextShaft.getFloorBelow();
                    this.shaftProgress = 0;
                    this.checkOffStop();
                }
            }
        }
    }

    private void updateStatus() {
        if(this.roadmap.isEmpty() && this.backlog.isEmpty()) {
            this.status = Status.IDLE;
            return;
        }

        if(this.roadmap.isEmpty()) {
            this.rotateRoadmap();
        }

        int closestEnd = this.getClosestEnd(this.roadmap);
        if(closestEnd == this.currentFloor.getLevel() && this.shaftProgress == 0) {
            return;
        }
        this.status = this.getClosestEnd(this.roadmap) < this.currentFloor.getLevel() ? Status.DOWNWARD : Status.UPWARD;
    }

    private void checkOffStop() {
        Integer currentLevel = this.currentFloor.getLevel();
        if(this.roadmap.contains(currentLevel)) {
            this.roadmap.remove(currentLevel);
            this.waiting = true;
        }
        updateStatus();
    }

    private void rotateRoadmap() {
        if(!this.roadmap.isEmpty()) {
            throw new IllegalStateException("Can't rotate roadmap with pending requests");
        }

        this.roadmap = this.backlog;
        this.backlog = new TreeSet<>();
    }

    private int getClosestEnd(TreeSet<Integer> set) {
        int floor = set.getFirst();
        int ceiling = set.getLast();
        int currentLevel = this.currentFloor.getLevel();

        return Math.abs(currentLevel - floor) <= Math.abs(currentLevel - ceiling) ? floor : ceiling;
    }

    public void step() {
        this.checkOffStop();
        if(isWaiting()){
            this.waiting = false;
            return;
        }
        this.move();
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", status=" + status +
                ", currentFloor=" + currentFloor +
                ", shaftProgress=" + shaftProgress +
                ", waiting=" + waiting +
                ", roadmap=" + roadmap +
                ", backlog=" + backlog +
                '}';
    }
}
