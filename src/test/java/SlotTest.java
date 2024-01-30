import org.example.enums.Color;
import org.example.Slot;
import org.example.Vehicle;
import org.example.enums.VehicleType;
import org.example.exceptions.AlreadyOccupiedSlot;
import org.example.exceptions.AlreadyUnoccupiedSlot;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SlotTest {
    @Test
    public void successfullyCreateEmptySlots(){
        assertDoesNotThrow(()->new Slot(1));

        Slot slot = new Slot(1);
        assertEquals(false, slot.isOccupied());
    }

    @Test
    public void successfullyOccupyASlotWithAVehicle() {
        Slot slot = new Slot(1);
        Vehicle motorcycle = new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ111");
        slot.occupy(motorcycle);
        assertTrue(slot.isOccupied());
        assertTrue(slot.checkRegistrationNumberMatch("RJ111"));
    }

    @Test
    public void successfullyUnoccupyASlotWithAVehicle() throws Exception {
        Vehicle motorcycle = new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ111");
        Slot slot = new Slot(1);
        slot.occupy(motorcycle);
        slot.unoccupy();
        assertFalse(slot.isOccupied());
    }

    @Test
    public void throwsExceptionOnFillingAnAlreadyOccupiedSlot(){
        Vehicle motorcycle = new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ111");
        Slot slot = new Slot(1);
        slot.occupy(motorcycle);
        assertThrows(AlreadyOccupiedSlot.class, ()->slot.occupy(motorcycle));
    }

    @Test
    public void throwsExceptionOnEmptyingAnAlreadyUnoccupiedSlot(){
        Slot slot = new Slot(1);
        assertThrows(AlreadyUnoccupiedSlot.class, slot::unoccupy);
    }


    @Test
    public void correctlyMatchesVehiclesByRegistrationNumber(){
        Slot slot = new Slot(1);
        Vehicle car = new Vehicle(VehicleType.CAR, Color.WHITE, "123");
        slot.occupy(car);
        assertTrue(slot.checkRegistrationNumberMatch("123"));
        assertFalse(slot.checkRegistrationNumberMatch("124"));
        assertTrue(slot.checkRegistrationNumberMatch(car));
    }

    @Test
    public void correctlyMatchesVehicleColor(){
        Slot slot = new Slot(1);
        slot.occupy(new Vehicle(VehicleType.CAR, Color.WHITE, "123"));

        assertTrue(slot.checkVehicleColorMatch(Color.WHITE));
        assertFalse(slot.checkVehicleColorMatch(Color.RED));

        slot.unoccupy();
        slot.occupy(new Vehicle(VehicleType.CAR, Color.GREEN, "123"));

        assertTrue(slot.checkVehicleColorMatch(Color.GREEN));
        assertFalse(slot.checkVehicleColorMatch(Color.BLUE));
    }
}
