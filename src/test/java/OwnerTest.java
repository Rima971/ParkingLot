import org.example.Owner;
import org.example.ParkingLot;
import org.example.ParkingLots;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class OwnerTest {
    @Test
    public void successfullyCreateOwner(){
        new Owner();
    }

    @Test
    public void ableTopAssignAttendentsToAParkingLotAndThrowsExceptionOnDuplicatedAttempts(){
        ParkingLot p1 = new ParkingLot(3);
        ParkingLot p2 = new ParkingLot(4);
        ParkingLot p3 = new ParkingLot(5);
        ParkingLot[] parkingLotCollection = {p3};
        ParkingLots parkingLots = new ParkingLots(parkingLotCollection);
        Owner owner = new Owner();
        owner.assign(parkingLots, p1);
        owner.assign(parkingLots, p2);
        assertThrows(Exception.class, ()->owner.assign(parkingLots, p1));
        assertThrows(Exception.class, ()->owner.assign(parkingLots, p2));
    }

}
