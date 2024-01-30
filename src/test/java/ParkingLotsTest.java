import org.example.*;
import org.junit.Test;

import java.sql.Array;

import static org.junit.Assert.*;

public class ParkingLotsTest {
    @Test
    public void successfullyCreatesParkingLots(){
        ParkingLot p1 = new ParkingLot(3);
        ParkingLot p2 = new ParkingLot(4);
        ParkingLot[] parkingLots = {p1,p2};
        new ParkingLots(parkingLots);
    }

    @Test
    public void ableToParkVehicleAtTheNearestEmptySlot() throws Exception {
        ParkingLot p1 = new ParkingLot(3);
        ParkingLot p2 = new ParkingLot(4);
        ParkingLot[] parkingLots = {p1,p2};
        ParkingLots manager = new ParkingLots(parkingLots);
        manager.park(new Vehicle(VehicleType.CAR, Color.WHITE, "123"));
        manager.park(new Vehicle(VehicleType.CAR, Color.WHITE, "124"));
        manager.park(new Vehicle(VehicleType.CAR, Color.WHITE, "125"));
        assertFalse(p1.hasEmptySlot());
    }

    @Test
    public void throwsExceptionWhenAllParkingLotsAreFilled() throws Exception {
        ParkingLot p1 = new ParkingLot(1);
        ParkingLot p2 = new ParkingLot(1);
        ParkingLot[] parkingLots = {p1,p2};
        ParkingLots manager = new ParkingLots(parkingLots);
        p1.park(new Vehicle(VehicleType.CAR, Color.WHITE, "111"));
        p2.park(new Vehicle(VehicleType.CAR, Color.WHITE, "111"));
        assertThrows(Exception.class, ()->manager.park(new Vehicle(VehicleType.CAR, Color.WHITE, "123")));
    }

    @Test
    public void ableToUnparkVehicleAtTheNearestEmptySlot() throws Exception {
        ParkingLot p1 = new ParkingLot(3);
        ParkingLot p2 = new ParkingLot(4);
        ParkingLot[] parkingLots = {p1,p2};
        ParkingLots manager = new ParkingLots(parkingLots);
        String token1 = manager.park(new Vehicle(VehicleType.CAR, Color.WHITE, "123"));
        manager.park(new Vehicle(VehicleType.CAR, Color.WHITE, "124"));
        manager.park(new Vehicle(VehicleType.CAR, Color.WHITE, "125"));
        manager.unpark(token1, "123");
        assertTrue(p1.hasEmptySlot());
    }

    @Test
    public void ThrowsExceptionWhenUparkingUsingIncorrectCredentials() throws Exception {
        ParkingLot p1 = new ParkingLot(3);
        ParkingLot p2 = new ParkingLot(4);
        ParkingLot[] parkingLots = {p1,p2};
        ParkingLots manager = new ParkingLots(parkingLots);
        String token1 = manager.park(new Vehicle(VehicleType.CAR, Color.WHITE, "123"));
        assertThrows(Exception.class, ()->manager.unpark(token1, "124"));
        assertThrows(Exception.class, ()->manager.unpark("", "123"));
        assertThrows(Exception.class, ()->manager.unpark("", "124"));
    }

    @Test
    public void ableToAssignAParkingLot(){
        ParkingLot p1 = new ParkingLot(3);
        ParkingLot p2 = new ParkingLot(4);
        ParkingLot p3 = new ParkingLot(4);
        ParkingLot[] initialParkingLotCollection = {p1};
        ParkingLots attendant = new ParkingLots(initialParkingLotCollection);
        attendant.assign(p2);
        attendant.assign(p3);
    }

    @Test
    public void throwsExceptionOnTryingToAssigningTheSameParkingLotTwice(){
        ParkingLot p1 = new ParkingLot(3);
        ParkingLot p2 = new ParkingLot(4);
        ParkingLot p3 = new ParkingLot(4);
        ParkingLot[] initialParkingLotCollection = {p1};
        ParkingLots attendant = new ParkingLots(initialParkingLotCollection);
        attendant.assign(p2);
        attendant.assign(p3);
        assertThrows(Exception.class, ()->attendant.assign(p1));
        assertThrows(Exception.class, ()->attendant.assign(p2));
        assertThrows(Exception.class, ()->attendant.assign(p3));
    }
}
