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

    public boolean equals(Object o){
      if (o==this) return true;
      if (o==null || o.getClass()!=this.getClass()) return false;
      Vehicle v = (Vehicle) o;
      return Objects.equals(v.registrationNumber, this.registrationNumber);
    };
}
