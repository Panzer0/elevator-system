package Architecture;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FloorLayoutTest {

    @Test
    public void testFloorConstructor() {
        Floor floor = new Floor(5);
        FloorLayout layout = new FloorLayout(floor);

        assertEquals(1, layout.getFloors().size());
        assertEquals(floor, layout.getTopFloor());
        assertEquals(floor, layout.getBottomFloor());
    }

    @Test
    public void testIntConstructor() {
        FloorLayout layout = new FloorLayout(5);

        assertEquals(1, layout.getFloors().size());
        assertEquals(5, layout.getBottomFloor().getLevel());
        assertEquals(5, layout.getTopFloor().getLevel());
    }

    @Test
    public void testAddAboveSuccessful() {
        Floor floor = new Floor(5);
        FloorLayout layout = new FloorLayout(floor);

        layout.addAbove(3, 7);
        Floor topFloor = layout.getTopFloor();
        Floor bottomFloor = layout.getBottomFloor();

        assertEquals(5, bottomFloor.getLevel());
        assertEquals(7, topFloor.getLevel());
        assertEquals(bottomFloor.getShaftAbove(), topFloor.getShaftBelow());
        assertEquals(bottomFloor.getShaftAbove().getFloorAbove(), topFloor);
        assertEquals(topFloor.getShaftBelow().getFloorBelow(), bottomFloor);
    }

    @Test
    public void testAddAboveIllegalLevel() {
        Floor floor = new Floor(5);
        FloorLayout layout = new FloorLayout(floor);


        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> layout.addAbove(3, 5));
        assertEquals("New floor's level must be above the current top floor's level", exception.getMessage());
    }
    @Test
    public void testAddBelowSuccessful() {
        Floor floor = new Floor(5);
        FloorLayout layout = new FloorLayout(floor);

        layout.addBelow(3, 3);
        Floor topFloor = layout.getTopFloor();
        Floor bottomFloor = layout.getBottomFloor();

        assertEquals(3, bottomFloor.getLevel());
        assertEquals(5, topFloor.getLevel());
        assertEquals(bottomFloor.getShaftAbove(), topFloor.getShaftBelow());
        assertEquals(bottomFloor.getShaftAbove().getFloorAbove(), topFloor);
        assertEquals(topFloor.getShaftBelow().getFloorBelow(), bottomFloor);
    }

    @Test
    public void testAddBelowIllegalLevel() {
        Floor floor = new Floor(5);
        FloorLayout layout = new FloorLayout(floor);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> layout.addBelow(3, 5));
        assertEquals("New floor's level must be below the current bottom floor's level", exception.getMessage());
    }

}
