import org.example.*;
import org.example.enums.Color;
import org.example.enums.VehicleType;
import org.example.exceptions.AlreadyParkedVehicle;
import org.example.exceptions.ParkingLotFull;
import org.junit.Test;

import java.security.InvalidParameterException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParkingLotTest {
    @Test
    public void successfullyCreateAParkingLotWithInitialSetOfSlots(){
        assertDoesNotThrow(()->new ParkingLot(3));
    }

    @Test
    public void throwsErrorOnPassingSlotCountLessThanOrEqualTo0(){
        assertThrows(InvalidParameterException.class, ()->new ParkingLot(0));
        assertThrows(InvalidParameterException.class, ()->new ParkingLot(-2));
    }

    @Test
    public void canParkAVehicleInAnEmptyParkingSlot() {
        ParkingLot parkingLot = new ParkingLot(6);
        Vehicle car = new Vehicle(VehicleType.CAR, Color.RED, "RJ111");

        assertDoesNotThrow(()->{
            int id = parkingLot.park(car);
            assertEquals(0, id);
            assertTrue(parkingLot.findSlot(id).checkRegistrationNumberMatch(car));
        });
    }

    @Test
    public void cannotParkAVehicleIfSlotsUnavailable() {
        Vehicle firstCar = new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ111");
        Vehicle secondCar = new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ112");
        Vehicle thirdCar = new Vehicle(VehicleType.MOTORCYCLE, Color.RED, "RJ113");
        ParkingLot parkingLot = new ParkingLot(2);

        parkingLot.park(firstCar);
        parkingLot.park(secondCar);

        assertThrows(ParkingLotFull.class, ()->parkingLot.park(thirdCar));
    }

    @Test
    public void cannotParkTheSameCarTwice() {
        Vehicle firstCar = new Vehicle(VehicleType.CAR, Color.BLACK, "111");
        Vehicle secondCar = new Vehicle(VehicleType.CAR, Color.BLACK, "111");
        Vehicle thirdCar = new Vehicle(VehicleType.CAR, Color.RED, "111");

        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.park(firstCar);

        assertThrows(AlreadyParkedVehicle.class, ()->parkingLot.park(secondCar));
        assertThrows(AlreadyParkedVehicle.class, ()->parkingLot.park(thirdCar));
    }

    @Test
    public void canUnparkAVehicle() {
        Vehicle firstCar = new Vehicle(VehicleType.CAR, Color.BLACK, "111");
        Vehicle secondCar = new Vehicle(VehicleType.CAR, Color.BLACK, "112");
        ParkingLot parkingLot = new ParkingLot(3);
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);

        parkingLot.unpark(0, "111");
        assertFalse(parkingLot.findSlot(0).isOccupied());

        parkingLot.unpark(1, "112");
        assertFalse(parkingLot.findSlot(1).isOccupied());
    }

    @Test
    public void cannotUnparkAVehicleIfIncorrectCredentialsArePassed() {
        Vehicle firstCar = new Vehicle(VehicleType.CAR, Color.BLACK, "111");
        Vehicle secondCar = new Vehicle(VehicleType.CAR, Color.BLACK, "112");
        ParkingLot parkingLot = new ParkingLot(3);
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);

        assertThrows(Exception.class, ()->parkingLot.unpark(0, "112"));
        assertThrows(Exception.class, ()->parkingLot.unpark(1, "111"));
        assertThrows(Exception.class, ()->parkingLot.unpark(0, "113"));
        assertThrows(Exception.class, ()->parkingLot.unpark(1, "113"));

    }

    @Test
    public void canFIneWhereACarIsParked() {
        Vehicle car = new Vehicle(VehicleType.CAR, Color.BLACK, "111");
        ParkingLot parkingLot = new ParkingLot(2);
        int slotID = parkingLot.park(car);

        assertTrue(parkingLot.findSlot(slotID).isOccupied());
        assertTrue(parkingLot.findSlot(slotID).checkRegistrationNumberMatch(car));
        assertEquals(parkingLot.findSlot(slotID), parkingLot.findVehicleSlot("111"));

        parkingLot = new ParkingLot(3);
        car = new Vehicle(VehicleType.SCOOTER, Color.BLACK, "112");
        int id = parkingLot.park(car);

        assertTrue(parkingLot.findSlot(slotID).isOccupied());
        assertTrue(parkingLot.findSlot(slotID).checkRegistrationNumberMatch(car));
        assertEquals(parkingLot.findSlot(id), parkingLot.findVehicleSlot("112"));
    }

    @Test
    public void ableToParkVehicleInMultipleSlots() {
        Vehicle[] preParkedCars = {
                new Vehicle(VehicleType.CAR, Color.WHITE, "123"),
                new Vehicle(VehicleType.CAR, Color.WHITE, "124"),
                new Vehicle(VehicleType.CAR, Color.WHITE, "125")
        };
        ParkingLot parkingLot = new ParkingLot(5);
        Integer[] preParkedCarsSlotIDs = parkingLot.park(preParkedCars);
        parkingLot.unpark(preParkedCarsSlotIDs[1], "124"); // now second slot is empty now

        Vehicle[] cars = {
                new Vehicle(VehicleType.CAR, Color.RED, "124"),
                new Vehicle(VehicleType.CAR, Color.RED, "126"),
                new Vehicle(VehicleType.CAR, Color.RED, "127")
        };
        Integer[] slotIDs = parkingLot.park(cars);

        Integer[] expected = {1,3,4};
        assertArrayEquals(slotIDs, expected);
    }

    @Test
    public void ReturnsSlotIDsOfOnlyTheCarThatWereParked() {
        Vehicle[] preParkedCars = {
                new Vehicle(VehicleType.CAR, Color.WHITE, "123"),
                new Vehicle(VehicleType.CAR, Color.WHITE, "124"),
                new Vehicle(VehicleType.CAR, Color.WHITE, "125"),
                new Vehicle(VehicleType.CAR, Color.WHITE, "126"),
                new Vehicle(VehicleType.CAR, Color.WHITE, "127"),
        };
        ParkingLot parkingLot = new ParkingLot(5);
        parkingLot.park(preParkedCars);
        parkingLot.unpark(1, "124");
        parkingLot.unpark(3, "126");
        // now slot 1 and 3 are empty

        Vehicle[] cars = {
                new Vehicle(VehicleType.CAR, Color.RED, "124"),
                new Vehicle(VehicleType.CAR, Color.RED, "126"),
                new Vehicle(VehicleType.CAR, Color.RED, "128")
        };
        Integer[] slotIDs = parkingLot.park(cars);

        Integer[] expected = {1,3};
        assertArrayEquals(slotIDs, expected);
    }

    @Test
    public void correctlyFindsCountOfAllVehiclesWithGivenColor(){
        Vehicle[] cars = {
                new Vehicle(VehicleType.CAR, Color.RED, "123"),
                new Vehicle(VehicleType.CAR, Color.GREEN, "124"),
                new Vehicle(VehicleType.CAR, Color.RED, "125")
        };
        ParkingLot parkingLot = new ParkingLot(3);
        parkingLot.park(cars);

        assertEquals(0, parkingLot.getCountOfVehiclesOfColor(Color.BLUE));
        assertEquals(2, parkingLot.getCountOfVehiclesOfColor(Color.RED));
    }

    @Test
    public void parksAtTheFarthestSlotOnReversingStrategy(){
        ParkingLot parkingLot = new ParkingLot(6);
        Vehicle car = new Vehicle(VehicleType.CAR, Color.RED, "RJ111");

        assertDoesNotThrow(()->{
            int id = parkingLot.park(car, true);
            assertEquals(5, id);
            assertTrue(parkingLot.findSlot(id).checkRegistrationNumberMatch(car));
        });
    }

}
