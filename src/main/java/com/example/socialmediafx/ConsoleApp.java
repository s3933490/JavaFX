package com.example.socialmediafx;

import java.util.Scanner;
public class ConsoleApp {

    private static UserProfile loggedInUser = null;

//    public static void main(String[] args) {
//        UserManager userManager = new UserManager();
//        PostManager postManager = new PostManager();
//        Scanner scanner = new Scanner(System.in);
//
//
//        while (true) {
//
//            if (loggedInUser == null) {
//                // Display options for not logged in users
//                System.out.println("1. Register\n2. Login\n3. Exit");
//            } else {
//                System.out.printf("   %s%n", "1) Add a social media post");
//                System.out.printf("   %s%n", "2) Delete an existing social media post");
//                System.out.printf("   %s%n", "3) Retrieve a social media post");
//                System.out.printf("   %s%n", "4) Retrieve all replies of a particular social media post");
//                System.out.printf("   %s%n", "5) Retrieve the top N posts and replies with most likes");
//                System.out.printf("   %s%n", "6) Retrieve the top N posts and replies with most shares");
//                System.out.printf("   %s%n", "7)  Exit");
////		System.out.printf("   %s%n", "8)  Print all social media contents"); // Added menu option for printing all contents
//                System.out.print("Please select: ");
//            }
//
//            System.out.print("Choose an option: ");
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume the newline character
//
//
//            switch (choice) {
//                case 1:
//                    if (loggedInUser == null) {
//                        registerUser(userManager, scanner);
//                    } else {
//                        postManager.addSocialMediaPost();
//                    }
//                    break;
//
//                case 2:
//                    if (loggedInUser == null) {
//                        loginUser(userManager, scanner);
//                    } else {
////                        socialMediaManager.deleteSocialMediaPost();
//                    }
//                    break;
//
//                case 3:
//                    if (loggedInUser == null) {
//                        System.out.println("Case 4");
//                        System.out.println("Exiting the application. Goodbye!");
//                        scanner.close();
//                        System.exit(0);
//                    } else {
//                        if (loggedInUser != null) {
//                            int postId = postManager.collectPostId("Retrieve");
//                            postManager.retrieveSocialMediaPost(postId);
//                        }
//                    }
//                    break;
//
//                case 4:
//                    if (loggedInUser != null) {
//                        System.out.println("Logging out...");
//                        loggedInUser = null;
//                    } else {
//                        postManager.retrieveReplies();
//                    }
//                    break;
//
//                case 5:
//                    if (loggedInUser != null) {
//                        postManager.retrieveTopLikedPosts();
//                    }
//
//                case 6:
//                    if (loggedInUser != null) {
//                        postManager.retrieveTopSharedPosts();
//                    }
//
//                case 7:
//                    if (loggedInUser != null) {
//                        System.out.println("Exiting the application. Goodbye!");
//                        scanner.close();
//                        System.exit(0);
//                    }
//
//                default:
//                    System.out.println("Invalid option. Please try again.");
//            }
//        }
//    }

    private static void registerUser(UserManager userManager, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        boolean registrationSuccessful = userManager.registerUser(username, password, firstName, lastName);

        if (registrationSuccessful) {
            System.out.println("Registration successful!");
        }
    }

    private static void loginUser(UserManager userManager, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        loggedInUser = userManager.login(username, password);
    }
}
