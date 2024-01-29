import org.example.Color;
import org.example.Slot;
import org.example.Vehicle;
import org.example.VehicleType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SlotTest {
    @Test
    public void successfully_create_empty_slots(){
        Slot slot = new Slot(1);
        assertEquals(false, slot.isOccupied());
    }

    @Test
    public void successfully_create_filled_slots(){
        Slot slot = new Slot(new Vehicle(VehicleType.CAR, Color.RED, "RJ111"),1);
        assertEquals(true, slot.isOccupied());
    }

    @Test
    public void successfully_occupy_a_slot_with_a_vehicle() throws Exception {
        Slot slot = new Slot(1);
        slot.occupy(new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ111"));
    }

    @Test
    public void successfully_unoccupy_a_slot_with_a_vehicle() throws Exception {
        Vehicle motorcycle = new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ111");
        Slot slot = new Slot(motorcycle,1);
        slot.unoccupy();
        assertFalse(slot.isOccupied());
    }

}
