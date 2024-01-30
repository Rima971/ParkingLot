package org.example;

import java.util.HashMap;

public class Attendant {
    private HashMap<Integer, ParkingLot> parkingLots = new HashMap<>();
    private int counter = 0;
    public Attendant(ParkingLot[] parkingLots){
        createHashMap(parkingLots);
    }

    private void createHashMap(ParkingLot[] parkingLots){
        for (ParkingLot parkingLot: parkingLots){
            this.parkingLots.put(this.counter++, parkingLot);
        }
    }

    public String park(Vehicle vehicle) throws Exception {
        for (int i=0; i<this.parkingLots.size(); i++){
            ParkingLot parkingLot = this.parkingLots.get(i);
            if (parkingLot.isVehicleParked(vehicle)) throw new Exception("Vehicle already parked");
            if (parkingLot.hasEmptySlot()){
                return i + "-" + parkingLot.park(vehicle);
            }
        }
        throw new Exception("No spots available in any parking lot");
    }

    public void unpark(String token, String registrationNumber) throws Exception {
        String[] split = token.split("-");
        this.parkingLots.get(Integer.parseInt(split[0])).unpark(Integer.parseInt(split[1]), registrationNumber);
    }

    public void assign(ParkingLot parkingLot) {
        this.parkingLots.forEach((id, storedLot)-> {
            if (storedLot == parkingLot) try {
                throw new Exception("Trying to repeat assign a parking lot");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        this.parkingLots.put(counter++, parkingLot);
    }
}