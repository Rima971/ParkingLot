package org.example.exceptions;

public class ReassigningParkingLot extends RuntimeException {
    public ReassigningParkingLot(){
        super("Attempting to reassign a parking lot to the same attendant");
    }
}
