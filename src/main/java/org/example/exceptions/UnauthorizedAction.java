package org.example.exceptions;

public class UnauthorizedAction extends RuntimeException {
    public UnauthorizedAction(String message){
        super("Unauthorized Action: "+message);
    }
}