import org.example.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AttendantTest {
    @Test
    public void successfullyCreatesParkingLots(){
        ParkingLot firstLot = new ParkingLot(3);
        ParkingLot secondLot = new ParkingLot(4);
        ParkingLot[] parkingLots = {firstLot,secondLot};

        assertDoesNotThrow(()->new Attendant(parkingLots));
    }

    @Test
    public void ableToParkVehicleAtTheNearestEmptySlot() throws Exception {
        ParkingLot firstLot = new ParkingLot(3);
        ParkingLot secondLot = new ParkingLot(4);
        ParkingLot[] parkingLots = {firstLot,secondLot};
        Attendant attendant = new Attendant(parkingLots);
        Vehicle firstCar = new Vehicle(VehicleType.CAR, Color.WHITE, "123");
        Vehicle secondCar = new Vehicle(VehicleType.CAR, Color.WHITE, "124");
        Vehicle thirdCar = new Vehicle(VehicleType.CAR, Color.WHITE, "125");

        attendant.park(firstCar);
        attendant.park(secondCar);
        attendant.park(thirdCar);

        assertFalse(firstLot.hasEmptySlot());
        assertTrue(firstLot.isVehicleParked(firstCar));
        assertTrue(firstLot.isVehicleParked(secondCar));
        assertTrue(firstLot.isVehicleParked(thirdCar));
    }

    @Test
    public void throwsExceptionWhenAllParkingLotsAreFilled() throws Exception {
        ParkingLot firstLot = new ParkingLot(1);
        ParkingLot secondLot = new ParkingLot(1);
        ParkingLot[] parkingLots = {firstLot,secondLot};
        Attendant attendant = new Attendant(parkingLots);

        attendant.park(new Vehicle(VehicleType.CAR, Color.WHITE, "111"));
        attendant.park(new Vehicle(VehicleType.CAR, Color.WHITE, "112"));

        assertThrows(Exception.class, ()->attendant.park(new Vehicle(VehicleType.CAR, Color.WHITE, "123")));
    }

    @Test
    public void throwsExceptionOnAttemptingToParkSameVehicleInDifferentParkingLots() throws Exception {
        ParkingLot firstLot = new ParkingLot(1);
        ParkingLot secondLot = new ParkingLot(1);
        ParkingLot[] parkingLots = {firstLot,secondLot};
        Attendant attendant = new Attendant(parkingLots);

        attendant.park(new Vehicle(VehicleType.CAR, Color.WHITE, "111"));

        assertThrows(Exception.class, ()->attendant.park(new Vehicle(VehicleType.CAR, Color.WHITE, "111")));
    }
    @Test
    public void ableToUnparkVehicle() throws Exception {
        ParkingLot firstLot = new ParkingLot(3);
        ParkingLot secondLot = new ParkingLot(4);
        ParkingLot[] parkingLots = {firstLot,secondLot};
        Attendant attendant = new Attendant(parkingLots);
        Vehicle firstCar = new Vehicle(VehicleType.CAR, Color.WHITE, "123");
        Vehicle secondCar = new Vehicle(VehicleType.CAR, Color.WHITE, "124");
        Vehicle thirdCar = new Vehicle(VehicleType.CAR, Color.WHITE, "125");

        String token = attendant.park(firstCar);
        attendant.park(secondCar);
        attendant.park(thirdCar);
        attendant.unpark(token, "123");

        assertTrue(firstLot.hasEmptySlot());
        assertFalse(firstLot.isVehicleParked(firstCar));
    }

    @Test
    public void ThrowsExceptionWhenUparkingUsingIncorrectCredentials() throws Exception {
        ParkingLot firstLot = new ParkingLot(3);
        ParkingLot secondLot = new ParkingLot(4);
        ParkingLot[] parkingLots = {firstLot,secondLot};
        Attendant attendant = new Attendant(parkingLots);

        String token = attendant.park(new Vehicle(VehicleType.CAR, Color.WHITE, "123"));

        assertThrows(Exception.class, ()->attendant.unpark(token, "124"));
        assertThrows(Exception.class, ()->attendant.unpark("", "123"));
        assertThrows(Exception.class, ()->attendant.unpark("", "124"));
    }

    @Test
    public void ableToAssignAParkingLot(){
        ParkingLot firstLot = new ParkingLot(3);
        ParkingLot secondLot = new ParkingLot(4);
        ParkingLot thirdLot = new ParkingLot(4);
        ParkingLot[] initialParkingLotCollection = {firstLot};
        Attendant attendant = new Attendant(initialParkingLotCollection);

        assertDoesNotThrow(()->attendant.assign(secondLot));
        assertDoesNotThrow(()->attendant.assign(thirdLot));
    }

    @Test
    public void throwsExceptionOnTryingToAssigningTheSameParkingLotTwice(){
        ParkingLot firstLot = new ParkingLot(3);
        ParkingLot secondLot = new ParkingLot(4);
        ParkingLot thirdLot = new ParkingLot(4);
        ParkingLot[] initialParkingLotCollection = {firstLot};
        Attendant attendant = new Attendant(initialParkingLotCollection);

        attendant.assign(secondLot);
        attendant.assign(thirdLot);

        assertThrows(Exception.class, ()->attendant.assign(firstLot));
        assertThrows(Exception.class, ()->attendant.assign(secondLot));
        assertThrows(Exception.class, ()->attendant.assign(thirdLot));
    }

    @Test
    public void attendantCanParkInSecondLotWhenFirstLotIsFull() throws Exception {
        ParkingLot lot1 = new ParkingLot(1);
        ParkingLot lot2 = new ParkingLot(1);
        ParkingLot[] initialParkingLotCollection = {lot1, lot2};
        Attendant attendant = new Attendant(initialParkingLotCollection);

        assertTrue(lot1.hasEmptySlot());
        assertTrue(lot2.hasEmptySlot());

        attendant.park(new Vehicle(VehicleType.CAR, Color.WHITE, "123"));
        assertFalse(lot1.hasEmptySlot());
        assertTrue(lot2.hasEmptySlot());

        attendant.park(new Vehicle(VehicleType.CAR, Color.WHITE, "124"));
        assertFalse(lot1.hasEmptySlot());
        assertFalse(lot2.hasEmptySlot());
    }

    @Test
    public void twoAttendantsCanParkInSameParkingLot() throws Exception {
        ParkingLot lot = new ParkingLot(2);
        ParkingLot[] initialParkingLotCollection = {lot};
        Attendant firstAttendant = new Attendant(initialParkingLotCollection);
        Attendant secondAttendant = new Attendant(initialParkingLotCollection);
        Vehicle firstCar = new Vehicle(VehicleType.CAR, Color.WHITE, "123");
        Vehicle secondCar = new Vehicle(VehicleType.CAR, Color.WHITE, "122");

        firstAttendant.park(firstCar);
        secondAttendant.park(secondCar);

        assertFalse(lot.hasEmptySlot());
        assertTrue(lot.isVehicleParked(firstCar));
        assertTrue(lot.isVehicleParked(secondCar));
    }

    @Test
    public void twoAttendantsCanUnparkAnyVehicleFromSameParkingLotWithoutConflict() throws Exception {
        ParkingLot lot = new ParkingLot(3);
        ParkingLot[] initialParkingLotCollection = {lot};
        Attendant firstAttendant = new Attendant(initialParkingLotCollection);
        Attendant secondAttendant = new Attendant(initialParkingLotCollection);
        Vehicle firstCar = new Vehicle(VehicleType.CAR, Color.WHITE, "122");
        Vehicle secondCar = new Vehicle(VehicleType.CAR, Color.WHITE, "123");
        Vehicle thirdCar = new Vehicle(VehicleType.CAR, Color.WHITE, "124");
        String firstToken = firstAttendant.park(firstCar);
        String secondToken = secondAttendant.park(secondCar);
        String thirdToken = secondAttendant.park(thirdCar);

        firstAttendant.unpark(firstToken, "122");
        assertTrue(lot.hasEmptySlot());
        assertFalse(lot.isVehicleParked(firstCar));
        assertThrows(Exception.class, ()-> secondAttendant.unpark(firstToken, "122"));

        firstAttendant.unpark(secondToken, "123");
        assertFalse(lot.isVehicleParked(secondCar));
        assertThrows(Exception.class, ()-> secondAttendant.unpark(secondToken, "123"));

        secondAttendant.unpark(thirdToken, "124");
        assertFalse(lot.isVehicleParked(thirdCar));
        assertThrows(Exception.class, ()-> firstAttendant.unpark(thirdToken, "124"));
    }
}
