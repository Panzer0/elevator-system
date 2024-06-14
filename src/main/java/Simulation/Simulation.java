package Simulation;

import Architecture.Floor;
import Architecture.FloorLayout;
import DynamicElements.Elevator;

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

    private void assignCalls() {
        ;
    }

    public void step() {
        assignCalls();
        this.elevators.forEach(Elevator::step);
    }

}
