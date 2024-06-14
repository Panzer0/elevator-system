package Simulation;
package DynamicElements;

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

}