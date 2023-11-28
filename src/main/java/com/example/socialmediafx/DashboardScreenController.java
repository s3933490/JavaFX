package com.example.socialmediafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DashboardScreenController {

    PostManager postManager = new PostManager();

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField postIdTextField;

    public TextField getContentTextField() {
        return contentTextField;
    }

    @FXML
    private TextField contentTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField likesTextField;

    @FXML
    private TextField sharesTextField;

    @FXML
    private TextField dateTimeTextField;

    @FXML
    private TextField mainPostIdTextField;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button addPostButton;

    @FXML
    private Button retrievePostButton;

    @FXML
    private Button retrieveRepliesButton;

    @FXML
    private Button removePostButton;

    @FXML
    private TextField topNPostsTextField;

    @FXML
    private Button retrieveTopNPostsButton;

    @FXML
    private Button exportPostButton;

    @FXML
    private Button logoutButton;

    // TODO: Add more FXML elements for other UI components

    @FXML
    private void initialize() {
        UserManager userManager = new UserManager();
        String username = userManager.getCurrentUser().getUsername();
        welcomeLabel.setText("Welcome " + username);
    }

    @FXML
    private void editProfile() {
        // TODO: Implement edit profile logic
    }



    public SocialMediaPost createNewPost() {
        String content = contentTextField.getText();
        boolean error = false;

        Integer likes = getIntegerInput(likesTextField, "Likes must be a non-negative integer.");
        if (likes == -1) {
            error = true; // Likes input is invalid
        }

        // Validate shares input
        Integer shares = getIntegerInput(sharesTextField, "Shares must be a non-negative integer.");
        if (shares == -1) {
            error = true; // Shares input is invalid
        }

        // Validate date and time input
        String dateTime = getDateTimeInput(dateTimeTextField, "Invalid date and time format or values. Please use DD/MM/YYYY HH:MM.");
        if (dateTime == null) {
            error = true; // Date and time input is invalid
        }

        // Validate main post ID input
        Integer mainPostId = getIntegerInput(mainPostIdTextField, "Main Post ID must be a non-negative integer.");
        if (mainPostId == -1) {
            error = true; // Main Post ID input is invalid
        }

        // Check for blank fields
        if (content.isBlank()) {
            error = true; // Content or author is blank
        }

        // Automatically assign a unique ID based on the position in the array
        int postId = postManager.getPosts().size();

        // Create a new Post object
        if (error == false) {
            SocialMediaPost newPost = new SocialMediaPost(postId, content, UserManager.getCurrentUser().getUsername(), likes, shares, dateTime, mainPostId);
            postManager.addPost(newPost);
            return newPost;
        } else {
            return null;
        }
    }


    private int getIntegerInput(TextField textField, String errorMessage) {
        try {
            int value = Integer.parseInt(textField.getText());
            if (value < 0) {
                showAlert(errorMessage);
                return -1;
            }
            return value;
        } catch (NumberFormatException e) {
            showAlert(errorMessage);
            return -1;
        }
    }

    private String getDateTimeInput(TextField textField, String errorMessage) {
        String dateTime = textField.getText();
        Pattern dateTimePattern = Pattern.compile("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$");
        if (dateTime.isEmpty() || !dateTimePattern.matcher(dateTime).matches() || !isValidDateTime(dateTime)) {
            showAlert(errorMessage);
            return null;
        }
        return dateTime;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void exportPost(ActionEvent event) {
        // Get the selected post ID (you need to implement this part)
//        int postId = getSelectedPostId();

        // Call the exportPostToCSV method from SocialMediaManager
        PostManager postManager = new PostManager();
        postManager.exportPostToCSV(0, (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

//    // You need to implement a method to get the selected post ID
//    private int getSelectedPostId() {
//        // Implement this method based on your UI, e.g., retrieve from a TableView or other UI components
//        return 1; // Replace with the actual selected post ID
//    }


    private boolean isValidDateTime(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf.setLenient(false); // Disallow lenient parsing
        try {
            Date date = sdf.parse(dateTime);
            // Check if the parsed date is not null and is valid
            return date != null;
        } catch (ParseException e) {
            // Parsing error, not a valid date and time
            return false;
        }
    }

    public void logout(){
        System.exit(0);
    }
}