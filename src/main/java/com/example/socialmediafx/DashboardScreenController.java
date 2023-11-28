package com.example.socialmediafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DashboardScreenController {

    public TextField exportFileName;
    PostManager postManager = new PostManager();

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Button retrievePostButton;

    @FXML
    private TextArea retrievedPostTextArea;

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
        postManager.readDataFromCSV("posts.csv");
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

        // Create a new Post object
        if (error == false) {
            int postId = postManager.posts.size();
            SocialMediaPost newPost = new SocialMediaPost(postId, content, UserManager.getCurrentUser().getUsername(), likes, shares, dateTime, mainPostId);
            postManager.addPost(newPost, true);
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

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void exportPost(ActionEvent event) {
        try {
            int postId = Integer.parseInt(postIdTextField.getText());

            // Call the exportPostToCSV method from SocialMediaManage
            String fileName = exportFileName.getText();
            PostManager postManager = new PostManager();
            postManager.exportPostToCSV(fileName, postId, (Stage) ((Node) event.getSource()).getScene().getWindow());
        } catch (NumberFormatException e) {
            showAlert("Post not found. Please try a different post ID");
        }
    }

    @FXML
    private void retrievePost() {
        try {
            int postId = Integer.parseInt(postIdTextField.getText());
            try {
            SocialMediaPost post = postManager.posts.get(postId);
                    // Display the retrieved post
                    retrievedPostTextArea.setText(formatPostDetails(post));
                } catch (RuntimeException e) {
                showAlert("Post not found");
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid Post ID.");
        }
    }

    private String formatPostDetails(SocialMediaPost post) {
        // Customize the formatting of the retrieved post details
        return "ID: " + post.getId() +
                "\nAuthor: " + post.getAuthor() +
                "\nContent: " + post.getContent() +
                "\nLikes: " + post.getLikes() +
                "\nShares: " + post.getShares() +
                "\nDate-Time: " + post.getDateTime();
    }

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