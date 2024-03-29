package org.example;

import org.example.exceptions.AlreadyParkedVehicle;
import org.example.exceptions.ParkingLotFull;
import org.example.exceptions.ReassigningParkingLot;

import java.util.HashMap;

public class Attendant {
    private final HashMap<Integer, ParkingLot> parkingLots = new HashMap<>();
    private int counter = 0;
    public Attendant(ParkingLot[] parkingLots){
        createHashMap(parkingLots);
    }

    private void createHashMap(ParkingLot[] parkingLots){
        for (ParkingLot parkingLot: parkingLots){
            this.parkingLots.put(this.counter++, parkingLot);
        }
    }

    public String park(Vehicle vehicle) throws ParkingLotFull, AlreadyParkedVehicle {
        for (int i=0; i<this.parkingLots.size(); i++){
            ParkingLot parkingLot = this.parkingLots.get(i);
            if (parkingLot.isVehicleParked(vehicle)) throw new AlreadyParkedVehicle();
            if (parkingLot.hasEmptySlot()){
                return i + "-" + parkingLot.park(vehicle);
            }
        }
        throw new ParkingLotFull();
    }

    public String park(Vehicle vehicle, boolean reverseStrategy) throws ParkingLotFull, AlreadyParkedVehicle {
        if (reverseStrategy){
            for (int i=this.parkingLots.size()-1; i>=0; i--){
                ParkingLot parkingLot = this.parkingLots.get(i);
                if (parkingLot.isVehicleParked(vehicle)) throw new AlreadyParkedVehicle();
                if (parkingLot.hasEmptySlot()){
                    return i + "-" + parkingLot.park(vehicle, reverseStrategy);
                }
            }
        } else{
            for (int i=0; i<this.parkingLots.size(); i++){
                ParkingLot parkingLot = this.parkingLots.get(i);
                if (parkingLot.isVehicleParked(vehicle)) throw new AlreadyParkedVehicle();
                if (parkingLot.hasEmptySlot()){
                    return i + "-" + parkingLot.park(vehicle, reverseStrategy);
                }
            }
        }

        throw new ParkingLotFull();
    }

    public void unpark(String token, String registrationNumber) {
        String[] split = token.split("-");
        this.parkingLots.get(Integer.parseInt(split[0])).unpark(Integer.parseInt(split[1]), registrationNumber);
    }

    public void assign(ParkingLot parkingLot) throws ReassigningParkingLot {
        this.parkingLots.forEach((id, storedLot)-> {
            if (storedLot == parkingLot) try {
                throw new ReassigningParkingLot();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        this.parkingLots.put(counter++, parkingLot);
    }
}
