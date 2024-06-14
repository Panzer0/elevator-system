package Simulation;

import Architecture.Floor;
import Architecture.FloorLayout;
import DynamicElements.Elevator;
import DynamicElements.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    public void testConstructor() {
        FloorLayout layout = new FloorLayout(0);
        Simulation simulation = new Simulation(layout, 3);

        assertEquals(layout, simulation.getLayout());
        assertEquals(3, simulation.getElevators().size());
        assertTrue(simulation.getCallBacklog().isEmpty());
    }

    @Test
    public void testInternalCall() {
        FloorLayout layout = new FloorLayout(0);
        layout.addAbove(1, 4);
        Simulation simulation = new Simulation(layout, 3);

        simulation.internalCall(0, 3);
        simulation.internalCall(1, 2);
//        #0 to 3, #1 to 2

        simulation.step();
//        #0 1 -> 3, #1 1 -> 2
        assertEquals(1, simulation.getElevators().get(0).getPosition());
        assertEquals(1, simulation.getElevators().get(1).getPosition());
        assertEquals(0, simulation.getElevators().get(2).getPosition());

        simulation.step();
//        #0 2 -> 3, #1 2 -> _ (waiting)
        simulation.step();
//        #0 3 -> _ (waiting), #1 2 -> _
        assertEquals(3, simulation.getElevators().get(0).getPosition());
        assertEquals(2, simulation.getElevators().get(1).getPosition());
        assertEquals(0, simulation.getElevators().get(2).getPosition());
    }

    @Test
    public void testGeneral() {
        FloorLayout layout = new FloorLayout(0);
        layout.addAbove(1, 9);
        Simulation simulation = new Simulation(layout, 3);

        simulation.internalCall(0, 5);
        simulation.internalCall(1, 2);
//        #0 0 -> 5, #1 0 -> 2

        simulation.step(6);
//        #0 1 -> 5, #1 1 -> 2
//        #0 2 -> 5, #1 2 -> _ (waiting)
//        #0 3 -> 5, #1 2 -> _
//        #0 4 -> 5, #1 2 -> _
//        #0 5 -> _ (waiting), #1 2 -> _
//        #0 5 -> _, #1 2 -> _

        assertEquals(5, simulation.getElevators().get(0).getPosition());
        assertFalse(simulation.getElevators().get(0).isWaiting());

        simulation.externalCall(8, Direction.DOWN);
//        #0 5 -> 8, #1 2 -> _
        simulation.step();
        simulation.step();
//        #0 7 -> 8, #1 2 -> _

        assertEquals(7, simulation.getElevators().get(0).getPosition());

        simulation.externalCall(5, Direction.UP);
        simulation.externalCall(6, Direction.UP);
//        #0 7 -> 8, #1 2 -> 5/6
        simulation.step();
//        #0 8 -> _, #1 3 -> 5/6
        assertEquals(8, simulation.getElevators().get(0).getPosition());
        assertEquals(3, simulation.getElevators().get(1).getPosition());


        simulation.internalCall(1, 2);
//        #0 8 -> _, #1 3 -> 5/6 (2)
        simulation.step(4);
//        #0 8 -> _, #1 4 -> 5/6 (2)
//        #0 8 -> _, #1 5 -> 6 (2) (waiting)
//        #0 8 -> _, #1 5 -> 6 (2)
//        #0 8 -> _, #1 6 -> 2 (waiting)

        assertEquals(6, simulation.getElevators().get(1).getPosition());
        assertEquals(1, simulation.getElevators().get(1).getRoadmap().size());
        assertTrue(simulation.getElevators().get(1).getBacklog().isEmpty());

        simulation.step(5);
//        #0 8 -> _, #1 6 -> 2
//        #0 8 -> _, #1 5 -> 2
//        #0 8 -> _, #1 4 -> 2
//        #0 8 -> _, #1 3 -> 2
//        #0 8 -> _, #1 2 -> _ (waiting)
        assertEquals(2, simulation.getElevators().get(1).getPosition());
        assertTrue(simulation.getElevators().get(1).getRoadmap().isEmpty());
        assertTrue(simulation.getElevators().get(1).getBacklog().isEmpty());
        assertTrue(simulation.getElevators().get(1).isWaiting());
    }

}