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

import java.time.LocalDate;

/**
 * Stores a single usage log (trip period and distance) for a vehicle.
 */
public class UsageLog {

    private LocalDate startDate;
    private LocalDate endDate;
    private double distance;

    public UsageLog(LocalDate startDate, LocalDate endDate, double distance) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /** @return kilometers driven during this period */
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
