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

import ca.seneca.workshop2.model.UsageLog;
import ca.seneca.workshop2.model.Vehicle;

/**
 * Stores usage logs, each list linked to its vehicle.
 */
public class UsageService {

    private final Map<Vehicle, List<UsageLog>> dataStore = new HashMap<>();

    /** Adds a log to the given vehicle, creating its list if needed. */
    public void addLog(Vehicle vehicle, UsageLog log) {
        dataStore.computeIfAbsent(vehicle, key -> new ArrayList<>()).add(log);
    }

    /** @return a copy of the logs for this vehicle (empty if none) */
    public List<UsageLog> getLogs(Vehicle vehicle) {
        return new ArrayList<>(dataStore.getOrDefault(vehicle, new ArrayList<>()));
    }

    /** @return the total kilometers driven by this vehicle */
    public double getTotalDistance(Vehicle vehicle) {
        double total = 0;
        for (UsageLog log : getLogs(vehicle)) {
            total += log.getDistance();
        }
        return total;
    }
}
