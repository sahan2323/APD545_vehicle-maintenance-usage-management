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

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import ca.seneca.workshop2.model.MaintenanceRecord;
import ca.seneca.workshop2.model.UsageLog;
import ca.seneca.workshop2.model.Vehicle;
import ca.seneca.workshop2.service.MaintenanceService;
import ca.seneca.workshop2.service.UsageService;
import ca.seneca.workshop2.service.VehicleService;

/**
 * Controller for the summary window. Uses the service objects passed in
 * from the main window and never creates its own.
 */
public class SummaryController {

    private static final String VEHICLES = "Vehicles";
    private static final String MAINTENANCE = "Maintenance";
    private static final String USAGE = "Usage";

    private VehicleService vService;
    private MaintenanceService mService;
    private UsageService uService;

    @FXML private ComboBox<String> dataTypeComboBox;
    @FXML private ComboBox<Vehicle> vehicleComboBox;
    @FXML private Label summaryLabel;

    @FXML private TableView<Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, String> makeColumn;
    @FXML private TableColumn<Vehicle, String> modelColumn;
    @FXML private TableColumn<Vehicle, String> yearColumn;
    @FXML private TableColumn<Vehicle, String> typeColumn;

    @FXML private TableView<MaintenanceRecord> maintenanceTable;
    @FXML private TableColumn<MaintenanceRecord, String> dateColumn;
    @FXML private TableColumn<MaintenanceRecord, String> descriptionColumn;
    @FXML private TableColumn<MaintenanceRecord, String> costColumn;

    @FXML private TableView<UsageLog> usageTable;
    @FXML private TableColumn<UsageLog, String> startDateColumn;
    @FXML private TableColumn<UsageLog, String> endDateColumn;
    @FXML private TableColumn<UsageLog, String> distanceColumn;

    /** Sets up the dropdown options and how each table column gets its value. */
    @FXML
    public void initialize() {
        dataTypeComboBox.getItems().addAll(VEHICLES, MAINTENANCE, USAGE);

        makeColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMake()));
        modelColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getModel()));
        yearColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getYear())));
        typeColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getType()));

        dateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDate().toString()));
        descriptionColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescription()));
        costColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(String.format("$%,.2f", cell.getValue().getCost())));

        startDateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStartDate().toString()));
        endDateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEndDate().toString()));
        distanceColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(String.format("%,.1f km", cell.getValue().getDistance())));
    }

    /**
     * Receives the existing services from the main window.
     */
    public void setServices(VehicleService vService, MaintenanceService mService, UsageService uService) {
        this.vService = vService;
        this.mService = mService;
        this.uService = uService;

        vehicleComboBox.getItems().setAll(vService.getAllVehicles());
        if (!vehicleComboBox.getItems().isEmpty()) {
            vehicleComboBox.getSelectionModel().selectFirst();
        }
        dataTypeComboBox.setValue(VEHICLES);
        refreshView();
    }

    @FXML
    public void onDataTypeChanged() {
        refreshView();
    }

    @FXML
    public void onVehicleChanged() {
        refreshView();
    }

    @FXML
    public void onClose() {
        ((Stage) summaryLabel.getScene().getWindow()).close();
    }

    /** Shows the table for the selected data type and fills it from the services. */
    private void refreshView() {
        String type = dataTypeComboBox.getValue();
        if (vService == null || type == null) {
            return;
        }

        vehicleTable.setVisible(VEHICLES.equals(type));
        maintenanceTable.setVisible(MAINTENANCE.equals(type));
        usageTable.setVisible(USAGE.equals(type));
        vehicleComboBox.setDisable(VEHICLES.equals(type));

        switch (type) {
            case VEHICLES -> showVehicles();
            case MAINTENANCE -> showMaintenance();
            default -> showUsage();
        }
    }

    private void showVehicles() {
        List<Vehicle> vehicles = vService.getAllVehicles();
        vehicleTable.getItems().setAll(vehicles);
        summaryLabel.setText("Total vehicles: " + vehicles.size());
    }

    private void showMaintenance() {
        Vehicle vehicle = vehicleComboBox.getValue();
        if (vehicle == null) {
            maintenanceTable.getItems().clear();
            summaryLabel.setText("Select a vehicle to see its maintenance records");
            return;
        }
        List<MaintenanceRecord> records = mService.getRecords(vehicle);
        maintenanceTable.getItems().setAll(records);
        summaryLabel.setText(String.format("Records: %d   |   Total cost: $%,.2f",
                records.size(), mService.getTotalCost(vehicle)));
    }

    private void showUsage() {
        Vehicle vehicle = vehicleComboBox.getValue();
        if (vehicle == null) {
            usageTable.getItems().clear();
            summaryLabel.setText("Select a vehicle to see its usage logs");
            return;
        }
        List<UsageLog> logs = uService.getLogs(vehicle);
        usageTable.getItems().setAll(logs);
        summaryLabel.setText(String.format("Logs: %d   |   Total distance: %,.1f km",
                logs.size(), uService.getTotalDistance(vehicle)));
    }
}
