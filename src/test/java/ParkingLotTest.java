import org.example.*;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.sql.Array;

import static org.junit.Assert.*;

public class ParkingLotTest {
    @Test
    public void successfully_create_a_parking_lot_with_initial_set_of_slots(){
        Slot[] slots = {new Slot(1), new Slot(2), new Slot(3), new Slot(4), new Slot(5), new Slot(6)};
        new ParkingLot(slots);
        new ParkingLot(3);
    }

    @Test
    public void throws_error_on_passing_slot_count_less_than_or_equal_to_0(){
        assertThrows(InvalidParameterException.class, ()->new ParkingLot(0));
        assertThrows(InvalidParameterException.class, ()->new ParkingLot(-2));
    }

    @Test
    public void throws_error_on_passing_duplicate_slots(){
        Slot[] slots = {new Slot(1), new Slot(1), new Slot(3), new Slot(4), new Slot(5), new Slot(6)};
        assertThrows(InvalidParameterException.class, ()->new ParkingLot(slots));
    }

    @Test
    public void can_park_a_vehicle_in_an_empty_parking_slot() throws Exception {
        Slot[] slots = {new Slot(1), new Slot(2), new Slot(3), new Slot(4), new Slot(5), new Slot(6)};
        ParkingLot parkingLot = new ParkingLot(slots);
        Vehicle car = new Vehicle(VehicleType.CAR, Color.RED, "RJ111");
        int id = parkingLot.park(car);
        assertEquals(1, id);
    }

    @Test
    public void cannot_park_a_vehicle_if_slots_unavailable() throws Exception {
        Slot[] slots = {new Slot(new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ111"),1), new Slot(new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ111"),2)};
        ParkingLot parkingLot = new ParkingLot(slots);
        Vehicle car = new Vehicle(VehicleType.CAR, Color.RED, "RJ111");
        assertThrows(Exception.class, ()->parkingLot.park(car));
    }

    @Test
    public void cannot_park_the_same_car_twice() throws Exception {
        Vehicle car = new Vehicle(VehicleType.CAR, Color.BLACK, "111");
        Slot[] slots = {new Slot(car, 0), new Slot(1)};
        ParkingLot parkingLot = new ParkingLot(slots);

        Vehicle car1 = new Vehicle(VehicleType.CAR, Color.BLACK, "111");
        assertThrows(Exception.class, ()->parkingLot.park(car1));

        Vehicle car2 = new Vehicle(VehicleType.CAR, Color.RED, "111");
        assertThrows(Exception.class, ()->parkingLot.park(car2));
    }

    @Test
    public void can_unpark_a_vehicle() throws Exception {
        Vehicle car1 = new Vehicle(VehicleType.CAR, Color.BLACK, "111");
        Vehicle car2 = new Vehicle(VehicleType.CAR, Color.BLACK, "112");
        Slot[] slots = {new Slot(car1, 0), new Slot(car2,1), new Slot(3)};
        ParkingLot parkingLot = new ParkingLot(slots);

        parkingLot.unpark(0, "111");
        assertFalse(parkingLot.findSlot(0).isOccupied());

        parkingLot.unpark(1, "112");
        assertFalse(parkingLot.findSlot(1).isOccupied());
    }

    @Test
    public void cannot_unpark_a_vehicle_if_invalid_credentials_passed() throws Exception {
        Vehicle car1 = new Vehicle(VehicleType.CAR, Color.BLACK, "111");
        Vehicle car2 = new Vehicle(VehicleType.CAR, Color.BLACK, "112");
        Slot[] slots = {new Slot(car1, 0), new Slot(car2,1), new Slot(3)};
        ParkingLot parkingLot = new ParkingLot(slots);

        assertThrows(Exception.class, ()->parkingLot.unpark(0, "112"));
        assertThrows(Exception.class, ()->parkingLot.unpark(1, "111"));
        assertThrows(Exception.class, ()->parkingLot.unpark(0, "113"));
        assertThrows(Exception.class, ()->parkingLot.unpark(1, "113"));

    }

    @Test
    public void can_find_where_a_car_is_parked() throws Exception {
        Vehicle car = new Vehicle(VehicleType.CAR, Color.BLACK, "111");
        Slot s1 = new Slot(car, 0);
        Slot s2 = new Slot(1);
        Slot[] slots = {s1, s2};
        ParkingLot parkingLot = new ParkingLot(slots);
        assertEquals(s1, parkingLot.findVehicleSlot("111"));

        parkingLot = new ParkingLot(3);
        car = new Vehicle(VehicleType.SCOOTER, Color.BLACK, "112");
        int id = parkingLot.park(car);
        Slot s3 = parkingLot.findSlot(id);
        assertEquals(s3, parkingLot.findVehicleSlot("112"));
    }

    @Test
    public void correctly_finds_count_of_all_cars_with_a_given_color(){
        Slot[] slots = {new Slot(new Vehicle(VehicleType.CAR, Color.RED, "123"), 0),new Slot(new Vehicle(VehicleType.CAR, Color.GREEN, "124"), 1), new Slot(new Vehicle(VehicleType.CAR, Color.RED, "125"), 2)};
        ParkingLot parkingLot = new ParkingLot(slots);
        int actual = parkingLot.getCountOfVehiclesOfColor(Color.BLUE);
        assertEquals(0, actual);

        actual = parkingLot.getCountOfVehiclesOfColor(Color.RED);
        assertEquals(2, actual);
    }

    @Test
    public void able_to_park_in_multiple_slots() throws Exception {
        Vehicle preParkedCar = new Vehicle(VehicleType.CAR, Color.WHITE, "123");
        Slot[] slots = { new Slot( preParkedCar,0), new Slot(1), new Slot(preParkedCar, 2), new Slot(3), new Slot(4)};
        ParkingLot parkingLot = new ParkingLot(slots);
        Vehicle car1 = new Vehicle(VehicleType.CAR, Color.RED, "124");
        Vehicle car2 = new Vehicle(VehicleType.CAR, Color.RED, "125");
        Vehicle car3 = new Vehicle(VehicleType.CAR, Color.RED, "126");
        Vehicle[] cars = {car1, car2, car3};
        Integer[] slotIDs = parkingLot.park(cars);
        Integer[] expected = {1,3,4};
        assertArrayEquals(slotIDs, expected);
    }

    @Test
    public void returns_slot_ids_of_only_the_cars_that_were_parked() throws Exception {
        Vehicle preParkedCar = new Vehicle(VehicleType.CAR, Color.WHITE, "123");
        Slot[] slots = { new Slot( preParkedCar,0), new Slot(1), new Slot(preParkedCar, 2), new Slot(3), new Slot(preParkedCar,4)};
        ParkingLot parkingLot = new ParkingLot(slots);
        Vehicle car1 = new Vehicle(VehicleType.CAR, Color.RED, "124");
        Vehicle car2 = new Vehicle(VehicleType.CAR, Color.RED, "125");
        Vehicle car3 = new Vehicle(VehicleType.CAR, Color.RED, "126");
        Vehicle[] cars = {car1, car2, car3};
        Integer[] slotIDs = parkingLot.park(cars);
        Integer[] expected = {1,3};
        assertArrayEquals(slotIDs, expected);
    }

}
