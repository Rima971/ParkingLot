package org.example.exceptions;

public class ParkingLotFull extends RuntimeException {
    public ParkingLotFull(){
        super("No parking space are available");
    }
}
