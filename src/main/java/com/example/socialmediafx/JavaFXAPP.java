package com.example.socialmediafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class JavaFXAPP extends Application {

    private static Stage primaryStage;

    public JavaFXAPP() {
        // Default constructor
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        JavaFXAPP.primaryStage = primaryStage;

        // Load the login screen initially
        loadLoginScreen();
    }

    public static void loadLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL fxmlLocation = JavaFXAPP.class.getResource("/com/example/socialmediafx/LoginScreen.fxml");
            System.out.println(fxmlLocation);
            loader.setLocation(fxmlLocation);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Get the controller for the login screen if needed
            LoginScreenController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDashboardScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXAPP.class.getResource("DashboardScreen.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Get the controller for the dashboard screen if needed
            DashboardScreenController dashboardController = loader.getController();
//            PostManager postManager = new PostManager();
//            postManager.readDataFromCSV("posts.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add other methods as needed
}