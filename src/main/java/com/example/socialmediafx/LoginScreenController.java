package com.example.socialmediafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreenController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    @FXML
    private TextField usernameField;

    @FXML
    private Label messageLabel;


    @FXML
    private PasswordField passwordField;

    public LoginScreenController(UserManager userManager) {
        this.userManager = userManager;
    }

    private UserManager userManager = new UserManager();


    public LoginScreenController() {
        // Default initialization or leave it empty
    }

    @FXML
    private void login() {
        // Get username and password from your input fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Use the userManager to attempt login
        UserProfile loggedInUser = userManager.login(username, password);

        if (loggedInUser != null) {
            // Login successful, switch to the dashboard scene
            switchToDashboard();
        } else {
            // Login failed, show an error message
            messageLabel.setText("Invalid username or password. Please try again.");
        }
    }

    @FXML
    private void register() {
        // Implement the registration logic here
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Use the userManager to register the user
        boolean registrationSuccessful = userManager.registerUser(username, password, "", "");

        if (registrationSuccessful) {
            // Registration successful, you might want to switch scenes or show a success message
            switchToDashboard();
        } else {
            // Registration failed, show an error message
            messageLabel.setText("Username already exists. Please choose another username.");
        }
    }

    private void switchToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXAPP.class.getResource("/com/example/socialmediafx/Dashboard.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Set the primary stage
            if (primaryStage != null) {
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
