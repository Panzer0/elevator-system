package Architecture;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShaftTest {

    @Test
    public void testConstructor() {
        Floor floor_1 = new Floor(1);
        Floor floor_2 = new Floor(2);
        Shaft shaft = new Shaft(1, floor_1, floor_2);

        assertEquals(1, shaft.getDistance());
        assertEquals(floor_1, shaft.getFloorBelow());
        assertEquals(floor_2, shaft.getFloorAbove());
    }

    @Test
    public void testConstructorIllegalDistance() {
        Floor floor_1 = new Floor(1);
        Floor floor_2 = new Floor(2);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Shaft(0, floor_1, floor_2));
        assertEquals("distance must be greater than 0", exception.getMessage());
    }

    @Test
    public void testConstructorDuplicateNeighbour() {
        Floor floor = new Floor(1);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Shaft(1, floor, floor));
        assertEquals("floorBelow and floorAbove must not be the same", exception.getMessage());
    }

    @Test
    public void testConstructorMisalignedNeighbours() {
        Floor floor_1 = new Floor(1);
        Floor floor_2 = new Floor(2);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Shaft(1, floor_2, floor_1));
        assertEquals("floorBelow's level must be lesser than floorAbove's level", exception.getMessage());
    }

    @Test
    public void testSetDifference() {
        Floor floor_1 = new Floor(1);
        Floor floor_2 = new Floor(2);
        Shaft shaft = new Shaft(1, floor_1, floor_2);

        shaft.setDistance(5);
        assertEquals(5, shaft.getDistance());
    }

}
