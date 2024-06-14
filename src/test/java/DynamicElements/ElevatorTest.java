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
        assertEquals(Status.IDLE, elevator.getStatus());
        assertFalse(elevator.isWaiting());
        assertTrue(elevator.getRoadmap().isEmpty());
        assertTrue(elevator.getBacklog().isEmpty());
    }

    @Test
    public void testGeneral1() {
        FloorLayout layout = new FloorLayout(0);
        layout.addAbove(1);
        layout.addAbove(1);

        // Level 0 -> _
        Elevator elevator = new Elevator(1, layout.getBottomFloor());

        // Call 2
        elevator.assignCall(2);
        assertFalse(elevator.getRoadmap().isEmpty());
        assertEquals(0, elevator.getPosition());
        assertEquals(Status.UPWARD, elevator.getStatus());

        // Level 1 -> 2
        elevator.step();
        assertFalse(elevator.getRoadmap().isEmpty());
        assertEquals(1, elevator.getPosition());
        assertEquals(Status.UPWARD, elevator.getStatus());

        // Level 2 -> _, waiting
        elevator.step();
        assertTrue(elevator.getRoadmap().isEmpty());
        assertEquals(2, elevator.getPosition());
        assertEquals(Status.IDLE, elevator.getStatus());
        assertTrue(elevator.isWaiting());

        // Level 2 -> _
        elevator.step();
        assertTrue(elevator.getRoadmap().isEmpty());
        assertEquals(2, elevator.getPosition());
        assertEquals(Status.IDLE, elevator.getStatus());
        assertFalse(elevator.isWaiting());

        // Call 2, 0
        elevator.assignCall(2);
        elevator.assignCall(0);
        assertFalse(elevator.getRoadmap().isEmpty());
        assertEquals(2, elevator.getPosition());
        assertEquals(Status.IDLE, elevator.getStatus());
        assertFalse(elevator.isWaiting());

        // Level 2 -> 0
        elevator.step();
        assertFalse(elevator.getRoadmap().isEmpty());
        assertEquals(2, elevator.getPosition());
        assertEquals(Status.DOWNWARD, elevator.getStatus());
        assertFalse(elevator.isWaiting());

        // Level 1 -> 0
        elevator.step();
        assertFalse(elevator.getRoadmap().isEmpty());
        assertEquals(1, elevator.getPosition());
        assertEquals(Status.DOWNWARD, elevator.getStatus());
        assertFalse(elevator.isWaiting());

        // Call 2
        elevator.assignCall(2);
        assertFalse(elevator.getRoadmap().isEmpty());
        assertFalse(elevator.getBacklog().isEmpty());

        // Level 0 -> 2, waiting
        elevator.step();
        assertFalse(elevator.getRoadmap().isEmpty());
        assertTrue(elevator.getBacklog().isEmpty());
        assertEquals(0, elevator.getPosition());
        assertEquals(Status.UPWARD, elevator.getStatus());
        assertTrue(elevator.isWaiting());
    }

}
