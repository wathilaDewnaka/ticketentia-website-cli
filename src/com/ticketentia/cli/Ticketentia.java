package com.ticketentia.cli;

import com.ticketentia.cli.api.HandleAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ticketentia.cli.services.AdminServices;
import com.ticketentia.cli.config.LoginConfig;
import com.ticketentia.cli.config.Storage;
import com.ticketentia.cli.config.User;
import com.ticketentia.cli.enums.AccountType;
import com.ticketentia.cli.services.VendorServices;

import java.util.Scanner;

// Create and declare class with main method
public class Ticketentia {
    // Create the Scanner on global level
    private static final Scanner input = new Scanner(System.in);

    // Creating the main method to do main program execution
    public static void main(String[] args) {
        User user = new LoginConfig(); // Creating the Login Configuration object

        // Running a loop until user wills to exit
        while (true) {
            // Printing the welcome message
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
            System.out.println("+                        Welcome to Ticketentia                             +");
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
            System.out.println("Please enter details to login\n");

            // Calling the method to login
            boolean loginStatus = loginUser(user);
            Storage storage = Storage.getInstance();

            // Check login is successful and role is Admin
            if (loginStatus && storage.getUserRole().equals(AccountType.ADMIN.name())) {
                AdminServices adminServices = new AdminServices(); // Creating object of Admin Service class
                adminServices.showMenu(); // Calling method to show Admin menu
            } else if (loginStatus && storage.getUserRole().equals(AccountType.VENDOR.name())) { // Check login is successful and role is Vendor
                VendorServices vendorServices = new VendorServices(); // Creating object of Vendor Service class
                vendorServices.showMenu(); // Calling method to show Vendor menu
            }
        }
    }

    // Creating the method to check login status
    private static boolean loginUser(User user) {
        while (true) { // Running a loop until correct email is entered
            try {
                // Getting email as input from user
                System.out.print("Email : ");
                String email = input.nextLine().toLowerCase().strip();
                // Setting the email to user object, if invalid exception is thrown
                user.setEmail(email);

                // If email start with admin set type as ADMIN
                if (email.startsWith("admin")){
                    ((LoginConfig) user).setUserType(AccountType.ADMIN.name());
                    break;
                }
                // set type as VENDOR default
                ((LoginConfig) user).setUserType(AccountType.VENDOR.name());

                break; // Exit out of the loop
            } catch (IllegalArgumentException e) { // Handling the exception, printing user enter correct email and run loop again
                System.out.println("Please enter valid email address !\n");
            }
        }

        while (true) { // Running a loop until correct password criteria is entered
            try {
                // Getting password as input from user
                System.out.print("Password : ");
                String password = input.nextLine().strip();
                // Setting the password to user object, if invalid exception is thrown
                user.setPassword(password);
                break; // Exit out of the loop
            } catch (IllegalArgumentException e){ // Handling the exception, printing user enter correct password and run loop again
                System.out.println("Please enter valid password !\n");
            }
        }
        // Creating the api.HandleAPI object to deal with API operations
        HandleAPI handleAPI = new HandleAPI(null);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Call the login method to call login API and get response
        String loginJson = handleAPI.login(gson.toJson(user));

        // If no login response received print invalid credentials
        if (loginJson == null || loginJson.isEmpty()) {
            System.out.println("Invalid credentials or server error. Please try again.\n");
            return false;
        }

        // Setting the details (Using Singleton design pattern)
        Storage storage = Storage.getInstance();

        // Setting the data to the class
        storage.setUserId(loginJson);
        storage.setJwtToken(loginJson);
        storage.setUserName(loginJson);
        storage.setUserRole(loginJson);
        return true;
    }

}