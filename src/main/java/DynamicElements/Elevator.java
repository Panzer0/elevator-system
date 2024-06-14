package DynamicElements;

import Architecture.Floor;
import Architecture.Shaft;

import java.util.TreeSet;

/**
 * The Elevator class represents an elevator in a building.
 */
public class Elevator {

    private int id;
    private Status status;
    private Floor currentFloor;
    private int shaftProgress;
    private boolean waiting;
    private TreeSet<Integer> roadmap;
    private TreeSet<Integer> backlog;

    /**
     * Elevator object constructor with a specified ID and starting floor.
     *
     * @param id            the ID of the elevator
     * @param startingFloor the starting floor of the elevator
     */
    public Elevator(int id, Floor startingFloor) {
        this.id = id;
        this.currentFloor = startingFloor;
        this.status = Status.IDLE;
        this.shaftProgress = 0;
        this.waiting = false;
        this.roadmap = new TreeSet<>();
        this.backlog = new TreeSet<>();
    }

    /**
     * Returns the ID of the elevator.
     *
     * @return the ID of the elevator
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the current status of the Elevator.
     *
     * @return the current status of the Elevator
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns the current Floor of the Elevator.
     *
     * @return the current Floor of the Elevator
     */
    public Floor getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Checks if the Elevator is currently waiting.
     *
     * @return true if the Elevator is waiting, false otherwise
     */
    public boolean isWaiting() {
        return waiting;
    }

    /**
     * Returns the roadmap of the elevator, representing the floors to visit before stopping.
     *
     * @return the roadmap of the elevator
     */
    public TreeSet<Integer> getRoadmap() {
        return roadmap;
    }

    /**
     * Returns the backlog of the elevator, representing the floors to visit after clearing the roadmap.
     *
     * @return the backlog of the elevator
     */
    public TreeSet<Integer> getBacklog() {
        return backlog;
    }

    /**
     * Returns the current detailed position of the Elevator, accounting for partially traversed Shafts.
     *
     * @return the current detailed position of the Elevator
     * @throws IllegalStateException if the Elevator is moving beyond the top or bottom Floor
     */
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

    /**
     * Returns the current shaft progress of the Elevator.
     *
     * @return the current shaft progress of the Elevator
     */
    public int getShaftProgress() {
        return shaftProgress;
    }

    /**
     * Sets the ID of the Elevator.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the waiting status of the Elevator.
     *
     * @param waiting the waiting status to set
     */
    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    /**
     * Sets the Shaft progress of the elevator.
     *
     * @param shaftProgress the Shaft progress to set
     */
    public void setShaftProgress(int shaftProgress) {
        this.shaftProgress = shaftProgress;
    }

    /**
     * Assigns a call to the Elevator to visit the specified Floor level.
     *
     * @param level the Floor level to visit
     * @throws IllegalArgumentException if the level is outside the bounds of existing Floors
     */
    public void assignCall(int level) {
        if(level < this.getCurrentFloor().getBottomFloor().getLevel()
                || level > this.currentFloor.getTopFloor().getLevel()) {
            throw new IllegalArgumentException("Level must be within bounds of existing floors");
        }

        // Already at the requested floor or headed in the right direction
        if(((float)level == this.getPosition())
                ||(level > this.getPosition() && this.status == Status.UPWARD)
                || (level < this.getPosition() && this.status == Status.DOWNWARD)) {
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

    /**
     * Moves the Elevator based on its current status.
     */
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

    /**
     * Updates the status of the Elevator based on its current roadmap and backlog. Cycles the roadmap if necessary.
     */
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

    /**
     * Checks if the Elevator has fulfilled one of the requested calls and reacts accordingly.
     *
     * Checks if the Elevator has reached one of the Floors listed in the roadmap. If this is the case, it updates the
     * roadmap (cycling in the backlog if necessary) and sets the Elevator to wait.
     */
    private void checkOffStop() {
        Integer currentLevel = this.currentFloor.getLevel();
        if(this.roadmap.contains(currentLevel)) {
            this.roadmap.remove(currentLevel);
            this.waiting = true;
        }
        updateStatus();
    }


    /**
     * Relocates the elevator to the specified floor level.
     *
     * Forcibly relocates the elevator to the specified floor level. Doing this clears the elevator's pending orders.
     *
     * @param level the floor level to relocate to
     * @throws IllegalArgumentException if the level is outside the bounds of existing floor levels
     */
    public void relocate(int level) {
        if(level < this.getCurrentFloor().getBottomFloor().getLevel()
                || level > this.getCurrentFloor().getTopFloor().getLevel()) {
            throw new IllegalArgumentException("level must be within the bounds of existing floor levels");
        }
        if(level == this.currentFloor.getLevel()) {
            return;
        }

        Floor newFloor = this.currentFloor;
        if(level > this.currentFloor.getLevel()) {
            while (newFloor.getLevel() < level
                    && newFloor.getShaftAbove() != null
                    && newFloor.getShaftAbove().getFloorAbove() != null) {
                newFloor = newFloor.getShaftAbove().getFloorAbove();
            }
        }
        else{
            while (newFloor.getLevel() > level
                    && newFloor.getShaftBelow() != null
                    && newFloor.getShaftBelow().getFloorBelow() != null) {
                newFloor = newFloor.getShaftBelow().getFloorBelow();
            }
        }

        this.currentFloor = newFloor;
        this.roadmap.clear();
        this.backlog.clear();
        this.waiting = false;
        this.status = Status.IDLE;
        this.shaftProgress = 0;
    }

    /**
     * Rotates the roadmap by moving the backlog to the roadmap and clearing the backlog.
     *
     * @throws IllegalStateException if the roadmap is not empty
     */
    private void rotateRoadmap() {
        if(!this.roadmap.isEmpty()) {
            throw new IllegalStateException("Can't rotate roadmap with pending requests");
        }

        this.roadmap = this.backlog;
        this.backlog = new TreeSet<>();
    }

    /**
     * Returns the closest Floor from the Floors at extreme positions in the given set.
     *
     * @param set the set of floors to consider
     * @return the closest end from the current position
     */
    private int getClosestEnd(TreeSet<Integer> set) {
        int floor = set.getFirst();
        int ceiling = set.getLast();
        int currentLevel = this.currentFloor.getLevel();

        return Math.abs(currentLevel - floor) <= Math.abs(currentLevel - ceiling) ? floor : ceiling;
    }

    /**
     * Performs a single step of the elevator's movement and updates its status and roadmap accordingly.
     */
    public void step() {
        this.checkOffStop();
        if(isWaiting()){
            this.waiting = false;
            return;
        }
        this.move();
    }

    /**
     * Returns a string representation of the elevator.
     *
     * @return a string representation of the elevator
     */
    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", status=" + status +
                ", level=" + currentFloor.getLevel() +
                ", shaftProgress=" + shaftProgress +
                ", waiting=" + waiting +
                ", roadmap=" + roadmap +
                ", backlog=" + backlog +
                '}';
    }
}
