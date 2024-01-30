package org.example.exceptions;

public class AlreadyOccupiedSlot extends RuntimeException {
    public AlreadyOccupiedSlot(){
        super("This slot cannot be filled as it is already occupied");
    }
}
