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

package ca.seneca.workshop2;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the Vehicle Maintenance and Usage Management System.
 */
public class VmumsApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(VmumsApplication.class.getResource("view/MainView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("VMUMS - Vehicle Maintenance & Usage Management");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
