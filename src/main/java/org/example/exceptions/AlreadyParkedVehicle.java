package org.example.exceptions;

public class AlreadyParkedVehicle extends RuntimeException {
    public AlreadyParkedVehicle(){
        super("Invalid request to park a vehicle that is already parked");
    }
}
