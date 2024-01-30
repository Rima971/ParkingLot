package org.example;

import org.example.enums.Color;
import org.example.exceptions.AlreadyParkedVehicle;
import org.example.exceptions.ParkingLotFull;
import org.example.exceptions.UnauthorizedAction;

import java.security.InvalidParameterException;
import java.util.Stack;

public class ParkingLot {
    private Slot[] slots;
    public ParkingLot(int slotCount){
        if (slotCount<=0) throw new InvalidParameterException("Slot count cannot be less than or equal to 0");
        this.slots = new Slot[slotCount];
        for (int i=0; i<slotCount; i++){
            this.slots[i] = new Slot(i);
        }
    }

    private Slot getEmptySlot(boolean reverseStrategy) {
        if (reverseStrategy){
            for (int i=slots.length-1; i>=0; i--){
                if (!this.slots[i].isOccupied()) return this.slots[i];
            }
        } else{
            for (Slot slot: this.slots){
                if (!slot.isOccupied()) return slot;
            }
        }

        return null;
    }

    public boolean hasEmptySlot(){
        if (getEmptySlot(false) != null) return true;
        return false;
    }

    public Slot findVehicleSlot(String registrationNumber) {
        for (Slot slot: slots){
            if (slot.checkRegistrationNumberMatch(registrationNumber)) return slot;
        }
        return null;
    }

    public boolean isVehicleParked(Vehicle vehicle){
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

    public int park(Vehicle vehicle) throws AlreadyParkedVehicle, ParkingLotFull {
        if (this.isVehicleParked(vehicle)) throw new AlreadyParkedVehicle();
        Slot slot = this.getEmptySlot(false);
        if (slot==null) throw new ParkingLotFull();
        slot.occupy(vehicle);
        return slot.id;
    }

    public int park(Vehicle vehicle, boolean reverseStrategy) throws AlreadyParkedVehicle, ParkingLotFull {
        if (this.isVehicleParked(vehicle)) throw new AlreadyParkedVehicle();
        Slot slot = this.getEmptySlot(reverseStrategy);
        if (slot==null) throw new ParkingLotFull();
        slot.occupy(vehicle);
        return slot.id;
    }

    public Integer[] park(Vehicle[] vehicles) {
        Stack<Integer> slotIDs = new Stack<>();
        for (Vehicle vehicle: vehicles){
            try {
                int id = this.park(vehicle);
                slotIDs.push(id);
            } catch (Exception e){
                if (e instanceof ParkingLotFull){
                    return slotIDs.toArray(new Integer[0]);
                }
                throw e;
            }
        }
        return slotIDs.toArray(new Integer[0]);
    }

    public void unpark(int slotID, String registrationNumber) throws UnauthorizedAction {
        Slot slot = findSlot(slotID);
        if (slot==null) throw new UnauthorizedAction("No slots exist with the given ID in this parking lot");
        if (!slot.checkRegistrationNumberMatch(registrationNumber)) throw new UnauthorizedAction("Incorrect slot ID or vehicle registration number provided while attempting to unpark");
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
