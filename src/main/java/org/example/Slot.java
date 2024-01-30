package org.example;

import org.example.exceptions.AlreadyOccupiedSlot;
import org.example.exceptions.AlreadyUnoccupiedSlot;

public class Slot {
    private boolean occupied = false;
    public final int id; // keep id in ParkingLot
    private Vehicle vehicle = null;
    public Slot(int id){
        this.id = id;
    }

    public boolean isOccupied(){
        return this.occupied;
    }

    public void occupy(Vehicle vehicle) {
        if (this.occupied || this.vehicle!=null) throw new AlreadyOccupiedSlot();
        this.vehicle = vehicle;
        this.occupied = true;
    }

    public void unoccupy() {
        if (!this.occupied || this.vehicle==null) throw new AlreadyUnoccupiedSlot();
        this.vehicle = null;
        this.occupied = false;
    }

    public boolean checkRegistrationNumberMatch(String registrationNumber){
        return this.vehicle.HasRegistrationNo(registrationNumber);
    }
    public boolean checkRegistrationNumberMatch(Vehicle vehicle){
        return vehicle.HasRegistrationNo(this.vehicle);
    }

    public boolean checkRegistrationNumberMatch(Slot slot){
        if (slot.vehicle == null || this.vehicle == null) return false;
        return slot.vehicle.HasRegistrationNo(slot.vehicle);
    }

    public boolean checkVehicleColorMatch(Color color){
        return vehicle.isOfColor(color);
    }

}
