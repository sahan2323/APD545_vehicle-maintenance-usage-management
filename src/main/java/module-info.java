module ca.seneca.workshop2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens ca.seneca.workshop2.controller to javafx.fxml;
    exports ca.seneca.workshop2;
}
