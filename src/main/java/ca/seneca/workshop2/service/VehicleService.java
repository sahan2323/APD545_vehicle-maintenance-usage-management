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

package ca.seneca.workshop2.service;

import java.util.ArrayList;
import java.util.List;

import ca.seneca.workshop2.model.Vehicle;

/**
 * Stores and manages all vehicles in the fleet.
 */
public class VehicleService {

    private final List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    /** @return a copy of the vehicle list so callers cannot change it directly */
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }
}
