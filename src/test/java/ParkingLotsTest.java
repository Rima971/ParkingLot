import org.example.*;
import org.junit.Test;

import java.sql.Array;

import static org.junit.Assert.assertThrows;

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
}
