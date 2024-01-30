package org.example;

public class Slot {
    private boolean occupied = false;
    public final int id;
    private Vehicle vehicle = null;
    public Slot(int id){
        this.id = id;
    }
    // remove this constructor
    public Slot(Vehicle vehicle, int id){
        this.occupied = true;
        this.id = id;
        this.vehicle = vehicle;
    }

    public boolean isOccupied(){
        return this.occupied;
    }

    public void occupy(Vehicle vehicle) throws Exception {
        if (this.occupied || this.vehicle!=null) throw new Exception("This slot is already occupied");
        this.vehicle = vehicle;
        this.occupied = true;
    }

    public void unoccupy() throws Exception {
        if (!this.occupied || this.vehicle==null) throw new Exception("This slot is already empty");
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
