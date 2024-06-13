package Architecture;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void testSetDifference() {
        Floor floor_1 = new Floor(1);
        Floor floor_2 = new Floor(2);
        Shaft shaft = new Shaft(1, floor_1, floor_2);

        shaft.setDistance(5);
        assertEquals(5, shaft.getDistance());
    }
}
