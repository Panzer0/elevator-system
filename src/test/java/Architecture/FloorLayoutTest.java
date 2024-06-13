package Architecture;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void testAddAbove() {
        Floor floor = new Floor(5);
        FloorLayout layout = new FloorLayout(floor);

        layout.addAbove(3);
        Floor topFloor = layout.getTopFloor();
        Floor bottomFloor = layout.getBottomFloor();

        assertEquals(5, bottomFloor.getLevel());
        assertEquals(6, topFloor.getLevel());
        assertEquals(bottomFloor.getShaftAbove(), topFloor.getShaftBelow());
        assertEquals(bottomFloor.getShaftAbove().getFloorAbove(), topFloor);
        assertEquals(topFloor.getShaftBelow().getFloorBelow(), bottomFloor);
    }

    @Test
    public void testAddBelow() {
        Floor floor = new Floor(5);
        FloorLayout layout = new FloorLayout(floor);

        layout.addBelow(3);
        Floor topFloor = layout.getTopFloor();
        Floor bottomFloor = layout.getBottomFloor();

        assertEquals(4, bottomFloor.getLevel());
        assertEquals(5, topFloor.getLevel());
        assertEquals(bottomFloor.getShaftAbove(), topFloor.getShaftBelow());
        assertEquals(bottomFloor.getShaftAbove().getFloorAbove(), topFloor);
        assertEquals(topFloor.getShaftBelow().getFloorBelow(), bottomFloor);
    }


}
