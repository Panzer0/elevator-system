package Simulation;

import Architecture.Floor;
import Architecture.FloorLayout;
import DynamicElements.Elevator;
import DynamicElements.Status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

/**
 * The Simulation class represents a simulation of an elevator system in a building.
 */
public class Simulation {
    public static final int MAX_ELEVATORS = 16;

    private final FloorLayout layout;
    private final ArrayList<Elevator> elevators;
    private final HashSet<ExternalCall> callBacklog;

    /**
     * Simulation object constructor with a specified floor layout and number of Elevators.
     *
     * @param layout        the floor layout of the building
     * @param elevatorCount the number of elevators in the Simulation
     * @throws IllegalArgumentException if the elevatorCount is not within the bounds of <1, MAX_ELEVATORS>
     */
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

    /**
     * Returns the floor layout of the Simulation.
     *
     * @return the floor layout of the Simulation
     */
    public FloorLayout getLayout() {
        return layout;
    }

    /**
     * Returns the list of Elevators in the Simulation.
     *
     * @return the list of Elevators in the Simulation
     */
    public ArrayList<Elevator> getElevators() {
        return elevators;
    }

    /**
     * Returns the backlog of external Elevator calls in the Simulation.
     *
     * @return the backlog of external Elevator calls in the Simulation
     */
    public HashSet<ExternalCall> getCallBacklog() {
        return callBacklog;
    }

    /**
     * Checks if an elevator suits a given external call.
     *
     * Checks if an elevator suits a given external call. An elevator suits the call if it remains idle or is headed in
     * the direction of the call's floor.
     *
     * @param elevator the elevator to check
     * @param call     the external call to check against
     * @return true if the elevator suits the call, false otherwise
     */
    private boolean suitsCall(Elevator elevator, ExternalCall call){
        return(elevator.getPosition() == call.level()
                || elevator.getStatus() == Status.IDLE
                || (elevator.getStatus() == Status.UPWARD && elevator.getPosition() < call.level())
                || (elevator.getStatus() == Status.DOWNWARD && elevator.getPosition() > call.level()));
    }

    /**
     * Finds the closest suitable elevator for a given external call.
     *
     * @param call      the external call to find a suitable elevator for
     * @return an Optional containing the closest suitable elevator, or an empty Optional if no suitable elevator is
     *          found
     */
    private Optional<Elevator> findClosestSuitableElevator(ExternalCall call) {
        return this.elevators.stream()
                .filter(elevator -> suitsCall(elevator, call))
                .min((e1, e2) -> Float.compare(Math.abs(e1.getPosition() - call.level()),
                        Math.abs(e2.getPosition() - call.level())));
    }

    /**
     * Assigns an external call to the closest suitable elevator.
     *
     * @param call the external call to assign
     * @return true if the call is successfully assigned, false otherwise
     */
    private boolean assignCall(ExternalCall call) {
        Optional<Elevator> closestElevator = findClosestSuitableElevator(call);

        if (closestElevator.isPresent()) {
            Elevator elevator = closestElevator.get();
            elevator.assignCall(call.level());
            return true;
        }
        return false;
    }

    /**
     * Assigns pending external calls to suitable elevators.
     *
     * Attempts to assign as many of the pending external calls to suitable elevators as possible. This might leave
     * some (or even all) of the tasks unassigned, making them remain in the backlog until another attempt is made.
     */
    private void assignCalls() {
        callBacklog.removeIf(this::assignCall);
    }

    /**
     * Performs a single step of the simulation.
     * Assigns pending calls and moves the elevators.
     */
    public void step() {
        assignCalls();
        this.elevators.forEach(Elevator::step);
    }

    /**
     * Performs multiple steps of the simulation.
     *
     * @param count the number of steps to perform
     */
    public void step(int count) {
        for(int i = 0; i < count; i++) {
            step();
        }
    }

    /**
     * Prints the status of all elevators in the simulation.
     */
    public void status() {
        this.elevators.forEach(System.out::println);
    }

    /**
     * Updates the position of an elevator to the specified level, resetting its pending calls in the process.
     *
     * @param id    the ID of the elevator to update
     * @param level the level to relocate the elevator to
     */
    public void update(int id, int level) {
        this.elevators.get(id).relocate(level);
    }

    /**
     * Assigns an internal call to a specific elevator.
     *
     * Assigns an itnernal call to a specific elevator. An internal call represents a call made from inside an elevator,
     * indicating a specific floor to go to. The call will be handled exclusively by the specified elevator.
     *
     * @param id    the ID of the elevator to assign the call to
     * @param level the level of the internal call
     * @throws IllegalArgumentException if the elevator ID is invalid or the level is outside the bounds of existing floors
     */
    public void internalCall(int id, int level){
        if(id < 0 || id >= this.elevators.size()) {
            throw new IllegalArgumentException("id must match an existing elevator");
        }
        if(level < this.layout.getBottomFloor().getLevel() || level > this.layout.getTopFloor().getLevel()) {
            throw new IllegalArgumentException("level must match an existing floor");
        }

        this.elevators.get(id).assignCall(level);
    }

    /**
     * Adds an external call to the call backlog.
     *
     * Adds an external call to the call backlog. An external call represents a call made from a button on a specific
     * floor rather than inside an elevator and indicates the direction the user intends to relocate in. It is given
     * over to the backlog, whence it will be transmitted to an adequate elevator should an opportunity arise.
     *
     * @param level     the level the external call is made on
     * @param direction the direction of the external call
     * @throws IllegalArgumentException if the level is outside the bounds of existing floors
     */
    public void externalCall(int level, Direction direction){
        if(level < this.layout.getBottomFloor().getLevel() || level > this.layout.getTopFloor().getLevel()) {
            throw new IllegalArgumentException("level must match an existing floor");
        }

        this.callBacklog.add(new ExternalCall(level, direction));
    }


}
