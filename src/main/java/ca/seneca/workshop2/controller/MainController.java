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

package ca.seneca.workshop2.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import ca.seneca.workshop2.VmumsApplication;
import ca.seneca.workshop2.model.MaintenanceRecord;
import ca.seneca.workshop2.model.UsageLog;
import ca.seneca.workshop2.model.Vehicle;
import ca.seneca.workshop2.service.MaintenanceService;
import ca.seneca.workshop2.service.UsageService;
import ca.seneca.workshop2.service.VehicleService;

/**
 * Controller for the main window. Reads user input, validates it,
 * and passes data to the service layer. Stores no data itself.
 */
public class MainController {

    private static final int MIN_YEAR = 1900;

    private final VehicleService vService = new VehicleService();
    private final MaintenanceService mService = new MaintenanceService();
    private final UsageService uService = new UsageService();

    @FXML private ComboBox<Vehicle> vehicleComboBox;

    @FXML private TextField makeField;
    @FXML private TextField modelField;
    @FXML private TextField yearField;
    @FXML private ComboBox<String> typeComboBox;

    @FXML private DatePicker maintenanceDatePicker;
    @FXML private TextArea descriptionArea;
    @FXML private TextField costField;

    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextField kilometersField;

    @FXML
    public void initialize() {
        // Dates must be picked from the calendar, which avoids invalid typed dates
        maintenanceDatePicker.setEditable(false);
        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);
    }

    // ---------- Add Vehicle ----------

    @FXML
    public void onSaveVehicle() {
        String make = makeField.getText().trim();
        String model = modelField.getText().trim();
        String yearText = yearField.getText().trim();
        String type = typeComboBox.getValue();

        if (make.isEmpty() || model.isEmpty() || yearText.isEmpty() || type == null) {
            showError("Missing information", "Please fill in Make, Model, Year and Type.");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            showError("Invalid year", "Year must be a whole number, e.g. 2021.");
            return;
        }

        int maxYear = Year.now().getValue() + 1;
        if (year < MIN_YEAR || year > maxYear) {
            showError("Invalid year", "Year must be between " + MIN_YEAR + " and " + maxYear + ".");
            return;
        }

        Vehicle vehicle = new Vehicle(make, model, year, type);
        vService.addVehicle(vehicle);
        vehicleComboBox.getItems().setAll(vService.getAllVehicles());
        vehicleComboBox.setValue(vehicle);
        clearVehicleForm();
        showInfo("Vehicle saved", vehicle + " was added to the fleet.");
    }

    // ---------- Add Maintenance ----------

    @FXML
    public void onSaveMaintenance() {
        Vehicle vehicle = getSelectedVehicle();
        if (vehicle == null) {
            return;
        }

        LocalDate date = maintenanceDatePicker.getValue();
        String description = descriptionArea.getText().trim();
        String costText = costField.getText().trim();

        if (date == null || description.isEmpty() || costText.isEmpty()) {
            showError("Missing information", "Please fill in Date, Description and Cost.");
            return;
        }

        Double cost = parseNonNegative(costText, "Cost");
        if (cost == null) {
            return;
        }

        mService.addRecord(vehicle, new MaintenanceRecord(date, description, cost));
        clearMaintenanceForm();
        showInfo("Record saved", "Maintenance record added to " + vehicle + ".");
    }

    // ---------- Add Usage ----------

    @FXML
    public void onSaveUsage() {
        Vehicle vehicle = getSelectedVehicle();
        if (vehicle == null) {
            return;
        }

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String kilometersText = kilometersField.getText().trim();

        if (startDate == null || endDate == null || kilometersText.isEmpty()) {
            showError("Missing information", "Please fill in Start Date, End Date and Kilometers.");
            return;
        }

        if (endDate.isBefore(startDate)) {
            showError("Invalid dates", "End date cannot be before the start date.");
            return;
        }

        Double distance = parseNonNegative(kilometersText, "Kilometers");
        if (distance == null) {
            return;
        }

        uService.addLog(vehicle, new UsageLog(startDate, endDate, distance));
        clearUsageForm();
        showInfo("Record saved", "Usage log added to " + vehicle + ".");
    }

    // ---------- Summary window ----------

    /**
     * Opens the summary in a new stage, the same size and position as the
     * main window, and passes it the existing service objects.
     */
    @FXML
    public void onOpenSummary() {
        try {
            FXMLLoader loader = new FXMLLoader(VmumsApplication.class.getResource("view/SummaryView.fxml"));
            Scene scene = new Scene(loader.load());
            SummaryController summaryController = loader.getController();
            summaryController.setServices(vService, mService, uService);

            Window mainWindow = vehicleComboBox.getScene().getWindow();
            Stage summaryStage = new Stage();
            summaryStage.setTitle("VMUMS - Summary");
            summaryStage.setScene(scene);
            summaryStage.initOwner(mainWindow);
            summaryStage.initModality(Modality.WINDOW_MODAL);
            summaryStage.setX(mainWindow.getX());
            summaryStage.setY(mainWindow.getY());
            summaryStage.setWidth(mainWindow.getWidth());
            summaryStage.setHeight(mainWindow.getHeight());
            summaryStage.show();
        } catch (IOException e) {
            showError("Error", "Could not open the summary window.");
        }
    }

    // ---------- Helpers ----------

    /** @return the chosen vehicle, or null after showing an error if none is chosen */
    private Vehicle getSelectedVehicle() {
        Vehicle vehicle = vehicleComboBox.getValue();
        if (vehicle == null) {
            showError("No vehicle selected", "Please choose a vehicle from the dropdown at the top first.");
        }
        return vehicle;
    }

    /**
     * Parses a number that must be 0 or greater.
     * @return the value, or null after showing an error if the input is invalid
     */
    private Double parseNonNegative(String text, String fieldName) {
        double value;
        try {
            value = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            showError("Invalid " + fieldName.toLowerCase(), fieldName + " must be a number, e.g. 120.50.");
            return null;
        }
        if (!Double.isFinite(value) || value < 0) {
            showError("Invalid " + fieldName.toLowerCase(), fieldName + " cannot be negative.");
            return null;
        }
        return value;
    }

    private void clearVehicleForm() {
        makeField.clear();
        modelField.clear();
        yearField.clear();
        typeComboBox.setValue(null);
    }

    private void clearMaintenanceForm() {
        maintenanceDatePicker.setValue(null);
        descriptionArea.clear();
        costField.clear();
    }

    private void clearUsageForm() {
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        kilometersField.clear();
    }

    private void showError(String title, String message) {
        showAlert(Alert.AlertType.ERROR, title, message);
    }

    private void showInfo(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
