package DynamicElements;

import Architecture.Floor;
import Architecture.FloorLayout;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorTest {

    @Test
    public void testConstructor() {
        Floor floor_1 = new Floor(1);
        Elevator elevator = new Elevator(2, floor_1);

        assertEquals(2, elevator.getId());
        assertEquals(floor_1, elevator.getCurrentFloor());
        assertEquals(Elevator.Status.IDLE, elevator.getStatus());
        assertFalse(elevator.isWaiting());
        assertTrue(elevator.getRoadmap().isEmpty());
        assertTrue(elevator.getBacklog().isEmpty());
    }

    @Test
    public void testGeneral1() {
        FloorLayout layout = new FloorLayout(0);
        layout.addAbove(1);
        layout.addAbove(1);

        Elevator elevator = new Elevator(1, layout.getBottomFloor());


        elevator.assignCall(2);
        assertFalse(elevator.getRoadmap().isEmpty());

        elevator.step();
        elevator.step();
        elevator.step();
        elevator.step();
        elevator.step();

        assertTrue(elevator.getRoadmap().isEmpty());
    }


}
