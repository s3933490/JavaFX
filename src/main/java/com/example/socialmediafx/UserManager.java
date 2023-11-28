package com.example.socialmediafx;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private final List<UserProfile> users;
    private static final String USERS_FILE_PATH = "users.csv";
    private static UserProfile currentUser;

    public UserManager() {
        this.users = loadUsersFromFile();
    }

    private List<UserProfile> loadUsersFromFile() {
        List<UserProfile> loadedUsers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String password = parts[1];
                String firstName = parts[2];
                String lastName = parts[3];

                UserProfile user = new UserProfile(username, password, firstName, lastName);
                loadedUsers.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedUsers;
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH))) {
            for (UserProfile user : users) {
                String line = user.getUsername() + "," + user.getPassword() + "," +
                        user.getFirstName() + "," + user.getLastName();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password, String firstName, String lastName) {
        if (getUserByUsername(username) == null) {
            UserProfile newUser = new UserProfile(username, password, firstName, lastName);
            users.add(newUser);
            saveUsersToFile();
            return true;
        } else {
            System.out.println("Username already exists. Please choose another username.");
            return false;
        }
    }

    public static UserProfile getCurrentUser() {
        return currentUser;
    }

    public UserProfile login(String username, String password) {
        UserProfile user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful. Welcome, " + user.getFirstName() + "!");
            setCurrentUser(user);
            return user;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return null;
        }
    }

    private void setCurrentUser(UserProfile user) {
        currentUser = user;
    }

    public void editUserProfile(UserProfile user, String newFirstName, String newLastName, String newPassword) {
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        user.setPassword(newPassword);
        saveUsersToFile();
        System.out.println("Profile updated successfully.");
    }

    private UserProfile getUserByUsername(String username) {
        for (UserProfile user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
