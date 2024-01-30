package org.example.exceptions;

public class AlreadyUnoccupiedSlot extends RuntimeException {
    public AlreadyUnoccupiedSlot(){
        super("This slot cannot be emptied as it is already empty");
    }
}
