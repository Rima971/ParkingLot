package org.example;

import java.security.InvalidParameterException;

public class ParkingLot {
    private Slot[] slots;
    public ParkingLot(int slotCount){
        if (slotCount<=0) throw new InvalidParameterException("Slot count cannot be less than or equal to 0");
        this.slots = new Slot[slotCount];
        for (int i=0; i<slotCount; i++){
            this.slots[i] = new Slot((int) (Math.random()*1000));
        }
    }

    public ParkingLot(Slot[] slots){
        isValid(slots);
        this.slots = slots;
    }

    private void isValid(Slot[] slots) throws InvalidParameterException{
        for (Slot s1: slots){
            for (Slot s2: slots){
                if (s1!=s2 && s1.id==s2.id) throw new InvalidParameterException("Dulicate slots present");
            }
        }
    }

    private Slot getEmptySlot() throws Exception {
        for (Slot slot: slots){
            if (!slot.isOccupied()) return slot;
        }
        throw new Exception("No slots empty");
    }

    public Slot findVehicle(Vehicle vehicle) {
        for (Slot slot: slots){
            if (slot.checkVehicleMatch(vehicle)) return slot;
        }
        return null;
    }

    public Slot findSlot(int id) {
        for (Slot slot: slots){
            if (slot.id == id) return slot;
        }
        return null;
    }

    public int park(Vehicle vehicle) throws Exception {
        if (this.findVehicle(vehicle)!=null) throw new Exception("This vehicle is already parked");
        Slot slot = this.getEmptySlot();
        slot.occupy(vehicle);
        return slot.id;
    }

    public void unparkByVehicle(Vehicle vehicle) throws Exception {
        Slot slot = findVehicle(vehicle);
        slot.unoccupy();
    }

    public void unparkBySlotID(int id) throws Exception {
        Slot slot = findSlot(id);
        slot.unoccupy();
    }

    public int getCountOfVehiclesOfColor(Color color){
        return 0;
    }


}
