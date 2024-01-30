package org.example;

import java.util.HashMap;

public class ParkingLots {
    private HashMap<Integer, ParkingLot> parkingLots = new HashMap<>();
    public ParkingLots(ParkingLot[] parkingLots){
        createHashMap(parkingLots);
    }

    private void createHashMap(ParkingLot[] parkingLots){
        int counter = 0;
        for (ParkingLot parkingLot: parkingLots){
            this.parkingLots.put(counter++, parkingLot);
        }
    }

    public String park(Vehicle vehicle) throws Exception {
        int i = 0;
        while(i<this.parkingLots.size()){
            ParkingLot parkingLot = this.parkingLots.get(i);
            if (parkingLot.hasEmptySlot()){
                return i + "-" + parkingLot.park(vehicle);
            }
            i++;
        }
        throw new Exception("No spots available in any parking lot");
    }
}
