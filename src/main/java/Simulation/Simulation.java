package Simulation;

import Architecture.Floor;
import Architecture.FloorLayout;
import DynamicElements.Elevator;
import DynamicElements.Status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;


public class Simulation {
    public static final int MAX_ELEVATORS = 16;

    private final FloorLayout layout;
    private final ArrayList<Elevator> elevators;
    private final HashSet<ExternalCall> callBacklog;

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

    public FloorLayout getLayout() {
        return layout;
    }

    public ArrayList<Elevator> getElevators() {
        return elevators;
    }

    public HashSet<ExternalCall> getCallBacklog() {
        return callBacklog;
    }

    private boolean suitsCall(Elevator elevator, ExternalCall call){
        return(elevator.getPosition() == call.level()
                || elevator.getStatus() == Status.IDLE
                || (elevator.getStatus() == Status.UPWARD && elevator.getPosition() < call.level())
                || (elevator.getStatus() == Status.DOWNWARD && elevator.getPosition() > call.level()));
    }

    private Optional<Elevator> findClosestSuitableElevator(ArrayList<Elevator> elevators, ExternalCall call) {
        return elevators.stream()
                .filter(elevator -> suitsCall(elevator, call))
                .min((e1, e2) -> Float.compare(Math.abs(e1.getPosition() - call.level()),
                        Math.abs(e2.getPosition() - call.level())));
    }

    private void assignCall(ExternalCall call) {
        Optional<Elevator> closestElevator = findClosestSuitableElevator(this.elevators,call);

        if(closestElevator.isPresent()) {
            Elevator elevator = closestElevator.get();
            elevator.assignCall(call.level());
            this.callBacklog.remove(call);
        }
    }

    private void assignCalls() {
        for (ExternalCall call: callBacklog) {
            assignCall(call);
        }
    }

    public void step() {
        assignCalls();
        this.elevators.forEach(Elevator::step);
    }

    public void status() {
        this.elevators.forEach(System.out::println);
    }

    public void update(int id, int level) {
        this.elevators.get(id).relocate(level);
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

    public void externalCall(int level, Direction direction){
        if(level < this.layout.getBottomFloor().getLevel() || level > this.layout.getTopFloor().getLevel()) {
            throw new IllegalArgumentException("level must match an existing floor");
        }

        this.callBacklog.add(new ExternalCall(level, direction));
    }


}
