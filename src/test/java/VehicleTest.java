import org.example.Color;
import org.example.Vehicle;
import org.example.VehicleType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));
        assertTrue(v1.equals(v1));
        assertFalse(v1.equals(v3));
        assertFalse(v1.equals(v4));
        assertFalse(v1.equals(null));
    }
    @Test
    public void detects_correctly_if_two_vehicles_instances_represents_same_registration_number(){
        Vehicle motorcycle = new Vehicle(VehicleType.MOTORCYCLE, Color.BLUE, "RJ122");

        assertTrue(motorcycle.HasRegistrationNo("RJ122"));
        assertFalse(motorcycle.HasRegistrationNo("RJ123"));
    }
}
