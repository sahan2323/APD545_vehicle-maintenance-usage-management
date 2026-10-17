# Vehicle Maintenance & Usage Management System (VMUMS)

A JavaFX desktop application that helps a fleet management company keep track of its vehicles, their maintenance history and their usage logs.

Built for **APD545 – Application Program Development** at Seneca Polytechnic (Workshop 2, Fall 2026).

## Features

- **Add vehicles** with make, model, year and type (Sedan, SUV, Truck)
- **Add maintenance records** (date, description, cost) linked to a selected vehicle
- **Add usage logs** (start date, end date, kilometers) linked to a selected vehicle
- **Summary window** that opens in a separate stage, with a dropdown to switch between Vehicles, Maintenance and Usage, plus totals for cost and distance
- **Input validation** with JavaFX Alert dialogs for empty fields, non-numeric input, negative cost/kilometers, invalid years, and end dates before start dates

## Architecture

The app follows **MVC with an added Service Layer**:

- **Model:** simple POJOs (`Vehicle`, `MaintenanceRecord`, `UsageLog`) with private fields and getters/setters
- **Service:** the only place data is stored
  - `VehicleService`: list of all vehicles
  - `MaintenanceService`: `Map<Vehicle, List<MaintenanceRecord>>`
  - `UsageService`: `Map<Vehicle, List<UsageLog>>`
- **Controller:** handles input and validation and calls the services; stores no data itself
- **View:** two FXML files built with Scene Builder, styled with CSS

The main window passes its existing service objects to the summary window through `setServices(...)`, so both windows always work with the same data.

## Project Structure

src/main/
├── java/
│ ├── module-info.java
│ └── ca/seneca/workshop2/
│ ├── VmumsApplication.java # Entry point
│ ├── model/
│ │ ├── Vehicle.java
│ │ ├── MaintenanceRecord.java
│ │ └── UsageLog.java
│ ├── service/
│ │ ├── VehicleService.java
│ │ ├── MaintenanceService.java
│ │ └── UsageService.java
│ └── controller/
│ ├── MainController.java
│ └── SummaryController.java
└── resources/ca/seneca/workshop2/
├── view/
│ ├── MainView.fxml
│ └── SummaryView.fxml
└── css/
└── styles.css


## Tech Stack

- Java 27
- JavaFX 21 (controls, FXML)
- Maven
- Scene Builder

## How to Run

**IntelliJ IDEA:** open the project, let Maven load the dependencies, then run `VmumsApplication.java`.

**Terminal** (requires Maven):

```bash
mvn clean javafx:run
```

## Academic Integrity

This project was submitted as graded coursework. If you're a student taking APD545 or a similar course, please use it only as a general reference for how the concepts fit together, and don't copy or submit any part of it as your own. Doing so goes against Seneca's Academic Integrity Policy.

## Author

**Sahan Vimukthi Hewa Gallage**
[GitHub](https://github.com/sahan2323) · [LinkedIn](https://www.linkedin.com/in/sahan-vimukthi-hewa-gallage-bb3a24239)
