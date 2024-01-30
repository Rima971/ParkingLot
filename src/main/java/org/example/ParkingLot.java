package org.example;

import java.security.InvalidParameterException;
import java.util.Stack;

public class ParkingLot {
    private Slot[] slots;
    public ParkingLot(int slotCount){
        if (slotCount<=0) throw new InvalidParameterException("Slot count cannot be less than or equal to 0");
        this.slots = new Slot[slotCount];
        for (int i=0; i<slotCount; i++){
            this.slots[i] = new Slot((int) (Math.random()*1000));
        }
    }

    // remove this constructor
    public ParkingLot(Slot[] slots){
        isValid(slots);
        this.slots = slots;
    }

    private void isValid(Slot[] slots) throws InvalidParameterException{
        for (Slot s1: slots){
            for (Slot s2: slots){
                if (s1!=s2){
                    if (s1.id==s2.id) throw new InvalidParameterException("Dulicate slots present");
                    if (s1.checkRegistrationNumberMatch(s2)) throw new InvalidParameterException("Slots have duplicate vehicles");
                }

            }
        }
    }

    private Slot getEmptySlot() {
        for (Slot slot: slots){
            if (!slot.isOccupied()) return slot;
        }
        return null;
    }

    public boolean hasEmptySlot(){
        if (getEmptySlot() != null) return true;
        return false;
    }

    public Slot findVehicleSlot(String registrationNumber) {
        for (Slot slot: slots){
            if (slot.checkRegistrationNumberMatch(registrationNumber)) return slot;
        }
        return null;
    }

    private boolean isVehicleParked(Vehicle vehicle){
        for (Slot slot: slots){
            if (slot.checkRegistrationNumberMatch(vehicle)) return true;
        }
        return false;
    }

    public Slot findSlot(int id) {
        for (Slot slot: slots){
            if (slot.id == id) return slot;
        }
        return null;
    }

    public int park(Vehicle vehicle) throws Exception {
        if (this.isVehicleParked(vehicle)) throw new Exception("This vehicle is already parked");
        Slot slot = this.getEmptySlot();
        if (slot==null) throw new Exception("No slots are available");
        slot.occupy(vehicle);
        return slot.id;
    }

    public Integer[] park(Vehicle[] vehicles) throws Exception {
        Stack<Integer> slotIDs = new Stack<>();
        for (Vehicle vehicle: vehicles){
            try {
                int id = this.park(vehicle);
                slotIDs.push(id);
            } catch (Exception e){
                return slotIDs.toArray(new Integer[0]);
            }
        }
        return slotIDs.toArray(new Integer[0]);
    }

    public void unpark(int slotID, String registrationNumber) throws Exception {
        Slot slot = findSlot(slotID);
        if (slot==null) throw new Exception("This vehicle wasn't parked here");
        if (!slot.checkRegistrationNumberMatch(registrationNumber)) throw new Exception("Unauthorized action: Incorrect slot ID or vehicle registration number");
        slot.unoccupy();
    }

    public int getCountOfVehiclesOfColor(Color color){
        int count = 0;
        for (Slot slot: slots){
            if (slot.checkVehicleColorMatch(color)) count++;
        }
        return count;
    }


}
