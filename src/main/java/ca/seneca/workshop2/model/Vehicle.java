/**********************************************
Workshop # 2
Course: APD545 - Fall 2026
Last Name: Hewa Gallage
First Name: Sahan Vimukthi
ID: 178061230
Section: NBB
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature: Sahan Vimukthi Hewa Gallage
Date: October 16, 2026
**********************************************/

package ca.seneca.workshop2.model;

/**
 * Stores the details of a single fleet vehicle.
 */
public class Vehicle {

    private String make;
    private String model;
    private int year;
    private String type;

    public Vehicle(String make, String model, int year, String type) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /** @return a readable label used in the vehicle list and dropdowns */
    @Override
    public String toString() {
        return year + " " + make + " " + model + " (" + type + ")";
    }
}
