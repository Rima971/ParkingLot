import org.example.Color;
import org.example.Vehicle;
import org.example.VehicleType;
import org.junit.Test;

import static org.junit.Assert.*;

public class VehicleTest {
    @Test
    public void can_create_a_vehicle_of_certain_type(){
        new Vehicle(VehicleType.CAR, Color.RED, "RJ123");
        new Vehicle(VehicleType.MOTORCYCLE, Color.BLUE, "RJ124");
        new Vehicle(VehicleType.SCOOTER, Color.BLACK, "RJ123");
    }

    @Test
    public void detects_correctly_if_two_vehicles_instances_represents_same_vehicle(){
        Vehicle v1 = new Vehicle(VehicleType.MOTORCYCLE, Color.BLUE, "RJ122");
        Vehicle v2 = new Vehicle(VehicleType.MOTORCYCLE, Color.BLUE, "RJ122");
        Vehicle v3 = new Vehicle(VehicleType.MOTORCYCLE, Color.BLUE, "RJ123");
        Vehicle v4 = new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ122");

        assertEquals(v1, v2);
        assertEquals(v2, v1);
        assertEquals(v1, v1);
        assertNotEquals(v1, v3);
        assertNotEquals(v1, v4);
        assertNotEquals(null, v1);
    }
    @Test
    public void detects_correctly_if_two_vehicles_instances_represents_same_registration_number(){
        Vehicle motorcycle = new Vehicle(VehicleType.MOTORCYCLE, Color.BLUE, "RJ122");

        assertTrue(motorcycle.HasRegistrationNo("RJ122"));
        assertFalse(motorcycle.HasRegistrationNo("RJ123"));
    }
}
