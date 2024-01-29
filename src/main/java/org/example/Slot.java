package org.example;

public class Slot {
    private boolean occupied = false;
    public final int id;
    private Vehicle vehicle = null;
    public Slot(int id){
        this.id = id;
    }
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

    public boolean checkVehicleMatch(Vehicle vehicle){
        return vehicle.equals(this.vehicle);
    }

}
