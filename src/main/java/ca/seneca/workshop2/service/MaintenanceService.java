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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.seneca.workshop2.model.MaintenanceRecord;
import ca.seneca.workshop2.model.Vehicle;

/**
 * Stores maintenance records, each list linked to its vehicle.
 */
public class MaintenanceService {

    private final Map<Vehicle, List<MaintenanceRecord>> dataStore = new HashMap<>();

    /** Adds a record to the given vehicle, creating its list if needed. */
    public void addRecord(Vehicle vehicle, MaintenanceRecord record) {
        dataStore.computeIfAbsent(vehicle, key -> new ArrayList<>()).add(record);
    }

    /** @return a copy of the records for this vehicle (empty if none) */
    public List<MaintenanceRecord> getRecords(Vehicle vehicle) {
        return new ArrayList<>(dataStore.getOrDefault(vehicle, new ArrayList<>()));
    }

    /** @return the total maintenance cost for this vehicle */
    public double getTotalCost(Vehicle vehicle) {
        double total = 0;
        for (MaintenanceRecord record : getRecords(vehicle)) {
            total += record.getCost();
        }
        return total;
    }
}
