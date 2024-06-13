package Architecture;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloorTest {

    @Test
    public void testConstructor() {
        Floor floor = new Floor(5);
        assertEquals(5, floor.getLevel());
    }

    @Test
    public void testAddShaftAbove() {
        Floor floor_1 = new Floor(1);
        Floor floor_2 = new Floor(2);
        Shaft shaft = new Shaft(1, floor_1, floor_2);
        floor_1.setShaftAbove(shaft);
        assertEquals(shaft, floor_1.getShaftAbove());
    }

    @Test
    public void testAddShaftBelow() {
        Floor floor_1 = new Floor(1);
        Floor floor_2 = new Floor(2);
        Shaft shaft = new Shaft(1, floor_1, floor_2);
        floor_2.setShaftBelow(shaft);
        assertEquals(shaft, floor_2.getShaftBelow());
    }

}
