package org.example;

import java.util.HashMap;

public class Owner {
    private HashMap<Integer, ParkingLots> parkingLotsRecord = new HashMap<>();
    public Owner(){
    }

    public void assign(ParkingLots attendant, ParkingLot lot){
        attendant.assign(lot);
    }


}
