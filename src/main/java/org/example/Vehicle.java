package org.example;

import java.util.Objects;

public class Vehicle {
    private VehicleType type;
    private Color color;
    private String registrationNumber;
    public Vehicle(VehicleType type, Color color, String registrationNumber){
        this.type = type;
        this.color = color;
        this.registrationNumber = registrationNumber;
    }

    @Override
    public boolean equals(Object o){
      if (o==this) return true;
      if (o==null || o.getClass()!=this.getClass()) return false;
      Vehicle v = (Vehicle) o;
      return v.type == this.type && v.color == this.color && Objects.equals(v.registrationNumber, this.registrationNumber);
    };

    public boolean HasRegistrationNo(String registrationNumber){
        return Objects.equals(this.registrationNumber, registrationNumber);
    }

    public boolean HasRegistrationNo(Vehicle vehicle){
        if (vehicle==this) return true;
        if (vehicle==null) return false;
        return Objects.equals(this.registrationNumber, vehicle.registrationNumber);
    }

    public boolean isOfColor(Color color){
        return this.color == color;
    }
}
