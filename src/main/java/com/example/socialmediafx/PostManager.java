package com.example.socialmediafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class PostManager {
    private ObservableList<SocialMediaPost> posts;
    private static final String POSTS_FILE_PATH = "posts.csv";

    public PostManager() {
        this.posts = FXCollections.observableArrayList(new ArrayList<>());
    }



    public void exportPostToCSV(int postId, Stage primaryStage) {
        readDataFromCSV("posts.csv");
        SocialMediaPost postToExport = posts.get(postId);
        System.out.println(postId);
        System.out.println(postToExport);

        if (postToExport != null) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(primaryStage);

            if (selectedDirectory != null) {
                String folderPath = selectedDirectory.getAbsolutePath();
                String fileName = "exported_post";

                String filePath = folderPath + "/" + fileName + ".csv";

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    // Write header
                    writer.write("ID,content,author,likes,shares,date-time,main_post_id");
                    writer.newLine();

                    // Write the post
                    writer.write(postToExport.toCSVString());
                    writer.newLine();

                    System.out.println("Post exported to file successfully: " + filePath);
                } catch (IOException e) {
                    System.err.println("Error exporting post to file: " + e.getMessage());
                }
            } else {
                System.out.println("Export canceled by user.");
            }
        } else {
            System.out.println("Post not found.");
        }
    }

    private void savePostsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(POSTS_FILE_PATH))) {
            // Write header
            writer.write("ID,content,author,likes,shares,date-time,main_post_id");
            writer.newLine();

            // Write each post
            for (SocialMediaPost post : posts) {
                writer.write(post.toCSVString()); // Assuming Post class has a method to convert post data to CSV format
                writer.newLine();
            }

            System.out.println("Posts saved to file successfully.");
        } catch (IOException e) {
            System.err.println("Error saving posts to file: " + e.getMessage());
        }
    }
    public ObservableList<SocialMediaPost> getPosts() {
        return FXCollections.unmodifiableObservableList(posts);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    public void addPost(SocialMediaPost post) {
        posts.add(post);
        savePostsToFile();
        showAlert("Post added successfully with ID " + post.getId() + "!" );
    }


    public void retrieveTopLikedPosts() {
        int topN = collectNumberOfTopPosts();
        List<SocialMediaPost> topLikedPosts = getTopLikedPosts(topN);

        if (topLikedPosts.isEmpty()) {
            showAlert("No posts found.");
        } else {
            showAlert("Top " + topN + " Liked Posts:\n" + formatPosts(topLikedPosts));
        }
    }

    private String formatPosts(List<SocialMediaPost> posts) {
        StringBuilder result = new StringBuilder();
        for (SocialMediaPost post : posts) {
            result.append(post.toString()).append("\n\n");
        }
        return result.toString();
    }


    // Retrieve the top N posts and replies with most shares
    public void retrieveTopSharedPosts() {
        int topN = collectNumberOfTopPosts();
        List<SocialMediaPost> topSharedPosts = getTopSharedPosts(topN);

        if (topSharedPosts.isEmpty()) {
            showAlert("No posts found.");
        } else {
            showAlert("Top " + topN + " Shared Posts:\n" + formatPosts(topSharedPosts));
        }
    }

    public List<SocialMediaPost> getTopSharedPosts(int n) {
        List<SocialMediaPost> sortedPosts = new ArrayList<>(posts);
        sortedPosts.sort(Comparator.comparingInt(SocialMediaPost::getShares).reversed());
        return sortedPosts.subList(0, Math.min(n, sortedPosts.size()));
    }

    public int collectNumberOfTopPosts() {
        System.out.print("Enter the number of top posts to retrieve: ");
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.nextLine(); // Consume the invalid input
        }

        int topN = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return topN;
    }

    public void addSocialMediaPost() {
        // Create a new Post object and add it to the collection in SocialMediaManager
        SocialMediaPost newPost = createNewPost();
        addPost(newPost);
    }





    // Retrieve a social media post
    public void retrieveSocialMediaPost(int postID) {
        SocialMediaPost retrievedPost = posts.get(postID);

        if (retrievedPost != null) {
            // Display the retrieved post
            printSocialMediaPost(retrievedPost);
        } else {
            System.out.println("Post not found.");
        }
    }


    // Retrieve all replies of a particular social media post
    public void retrieveReplies() {
        int postIdToRetrieveRepliesFor = collectPostId("Get Replies for");
        List<SocialMediaPost> replies = getReplies(postIdToRetrieveRepliesFor);

        if (replies.isEmpty()) {
            System.out.println("No replies found for the specified post.");
        } else {
            System.out.println("Replies for Post ID " + postIdToRetrieveRepliesFor + ":");
            for (SocialMediaPost reply : replies) {
                // Display each reply
                printSocialMediaPost(reply);
            }
        }
    }

    public List<SocialMediaPost> getReplies(int postId) {
        List<SocialMediaPost> replies = new ArrayList<>();
        for (SocialMediaPost post : posts) {
            if (post.getMainPostId() == postId) {
                replies.add(post);
            }
        }
        return replies;
    }

    public int collectPostId(String action) {
        System.out.print("Enter the Post ID to " + action + ": ");
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid Post ID: ");
            scanner.nextLine(); // Consume the invalid input
        }

        int postId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return postId;
    }


    public SocialMediaPost createNewPost() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the content of the post: ");
        String content = scanner.nextLine();

        System.out.print("Enter the author's anonymous ID: ");
        String author = scanner.nextLine();

        int likes = -1;
        while (likes < 0) {
            System.out.print("Enter the number of likes (non-negative integer): ");
            if (scanner.hasNextInt()) {
                likes = scanner.nextInt();
                if (likes < 0) {
                    System.out.println("Likes must be a non-negative integer.");
                }
            } else {
                System.out.println("Please enter a valid non-negative integer for likes.");
                scanner.next(); // Consume the invalid input
            }
        }

        int shares = -1;
        while (shares < 0) {
            System.out.print("Enter the number of shares (non-negative integer): ");
            if (scanner.hasNextInt()) {
                shares = scanner.nextInt();
                if (shares < 0) {
                    System.out.println("Shares must be a non-negative integer.");
                }
            } else {
                System.out.println("Please enter a valid non-negative integer for shares.");
                scanner.next(); // Consume the invalid input
            }
        }

        scanner.nextLine(); // Consume the newline character

        String dateTime = "";
        Pattern dateTimePattern = Pattern.compile("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$");
        while (dateTime.isEmpty() || !dateTimePattern.matcher(dateTime).matches() || !isValidDateTime(dateTime)) {
            System.out.print("Enter the date and time (DD/MM/YYYY HH:MM): ");
            dateTime = scanner.nextLine();
            if (!dateTimePattern.matcher(dateTime).matches() || !isValidDateTime(dateTime)) {
                System.out.println("Invalid date and time format or values. Please use DD/MM/YYYY HH:MM.");
            }
        }

        int mainPostId = -1;
        while (mainPostId < 0) {
            System.out.print("Enter the main post ID (0 if not a reply): ");
            if (scanner.hasNextInt()) {
                mainPostId = scanner.nextInt();
                if (mainPostId < 0) {
                    System.out.println("Main Post ID must be a non-negative integer.");
                }
            } else {
                System.out.println("Please enter a valid non-negative integer for Main Post ID.");
                scanner.next(); // Consume the invalid input
            }
        }

        // Create a new Post object
        SocialMediaPost newPost = new SocialMediaPost(0, content, author, likes, shares, dateTime, mainPostId);

        return newPost;
    }

    public boolean isValidDateTime(String dateTime) {
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

    // Retrieve the top N posts (and replies) with the most likes
    public List<SocialMediaPost> getTopLikedPosts(int n) {
        posts.sort((post1, post2) -> Integer.compare(post2.getLikes(), post1.getLikes()));
        return posts.subList(0, Math.min(n, posts.size()));
    }

    public void printSocialMediaPost(SocialMediaPost post) {
        System.out.println("Retrieved Social Media Post:");
        System.out.println("ID: " + post.getId());
        System.out.println("Author: " + post.getAuthor());
        System.out.println("Content: " + post.getContent());
        System.out.println("Likes: " + post.getLikes());
        System.out.println("Shares: " + post.getShares());
        System.out.println("Date-Time: " + post.getDateTime());
    }

    public void readDataFromCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("posts.csv"))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the first line
                }

                String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (parts.length >= 7) {
                    int id = Integer.parseInt(parts[0].trim());
                    String content = parts[1].trim();
                    String author = parts[2].trim();
                    int likes = Integer.parseInt(parts[3].trim());
                    int shares = Integer.parseInt(parts[4].trim());
                    String dateTime = parts[5].trim();
                    int mainPostId = Integer.parseInt(parts[6].trim());

                    // Create a Post object and add it to your collection
                    SocialMediaPost post = new SocialMediaPost(id, content, author, likes, shares, dateTime, mainPostId);
                    addPost(post);
                } else {
                    // Handle incomplete or malformed lines
                    System.err.println("Malformed line in CSV: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
