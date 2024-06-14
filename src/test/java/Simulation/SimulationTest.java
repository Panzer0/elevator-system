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
//        #0 3 -> 3 (waiting), #1 2 -> _
        assertEquals(3, simulation.getElevators().get(0).getPosition());
        assertEquals(2, simulation.getElevators().get(1).getPosition());
        assertEquals(0, simulation.getElevators().get(2).getPosition());
    }
    

}