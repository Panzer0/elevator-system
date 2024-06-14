package Simulation;

import Architecture.Floor;
import Architecture.FloorLayout;
import DynamicElements.Elevator;
import DynamicElements.Status;

import java.util.ArrayList;
import java.util.HashSet;



public class Simulation {
    public static final int MAX_ELEVATORS = 16;

    private FloorLayout layout;
    private ArrayList<Elevator> elevators;
    private HashSet<ExternalCall> callBacklog;

    public Simulation(FloorLayout layout, int elevatorCount) {
        if(elevatorCount <= 0 || elevatorCount > MAX_ELEVATORS) {
            throw new IllegalArgumentException("elevatorCount must be within the bounds of <1, " + MAX_ELEVATORS + ">");
        }

        this.layout = layout;
        this.callBacklog = new HashSet<>();

        this.elevators = new ArrayList<>();
        Floor bottomFloor = layout.getBottomFloor();
        for(int i = 0; i < elevatorCount; i++) {
            Elevator newElevator = new Elevator(i, bottomFloor);
            this.elevators.add(newElevator);
        }
    }

    public boolean suitsCall(Elevator elevator, ExternalCall call){
        return(elevator.getPosition() == call.level()
                || elevator.getStatus() == Status.IDLE
                || (elevator.getStatus() == Status.UPWARD && elevator.getPosition() < call.level())
                || (elevator.getStatus() == Status.DOWNWARD && elevator.getPosition() > call.level()));
    }

    public void assignCall() {
        ;
    }

    private void assignCalls() {
        ;
    }

    public void step() {
        assignCalls();
        this.elevators.forEach(Elevator::step);
    }

    public void internalCall(int id, int level){
        if(id < 0 || id >= this.elevators.size()) {
            throw new IllegalArgumentException("id must match an existing elevator");
        }
        if(level < this.layout.getBottomFloor().getLevel() || level > this.layout.getTopFloor().getLevel()) {
            throw new IllegalArgumentException("level must match an existing floor");
        }

        this.elevators.get(id).assignCall(level);
    }


}
