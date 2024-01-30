package org.example.exceptions;

public class ParkingLotFull extends RuntimeException {
    public ParkingLotFull(){
        super("No slots are available in this parking lot");
    }
}
