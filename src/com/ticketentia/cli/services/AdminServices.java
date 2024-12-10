package com.ticketentia.cli.services;

import com.google.gson.*;
import com.ticketentia.cli.api.HandleAPI;
import com.ticketentia.cli.api.RealTimeUpdate;
import com.ticketentia.cli.config.*;
import com.ticketentia.cli.enums.AccountType;
import java.util.*;

// Creating a class to perform all the admin services
public class AdminServices {
    // Creating scanner for input and handle api to perform api transactions
    private Scanner input;
    private HandleAPI handleAPI;
    private Storage storage;

    // Create the constructor
    public AdminServices(){
        this.input = new Scanner(System.in);
        this.storage = Storage.getInstance();
        this.handleAPI = new HandleAPI(storage.getJwtToken());
    }

    // Creating a method to print the menu for the user
    public void showMenu(){
        while (true) { // Run a loop
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
            System.out.println("+                             Main Menu                                     +");
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

            // Printing the welcome menu
            System.out.println("Hi " + storage.getUserName() + ", ");
            System.out.println("Logged into the system as Admin\n");

            // Printing the menu
            System.out.println("1. See all vendors");
            System.out.println("2. See all customers");
            System.out.println("3. See all active events");
            System.out.println("4. Add Vendor / Customer");
            System.out.println("5. Logout");
            System.out.println("6. Exit\n");

            try { // Getting the user input
                System.out.print("Enter your choice : ");
                int adminChoice = input.nextInt();
                input.nextLine();

                switch (adminChoice) { // Calling the relevant method based on user input
                    case 1: // If user enter 1 call seeAllVendors() method likewise
                        seeAllVendors();
                        break;
                    case 2:
                        seeAllCustomers();
                        break;
                    case 3:
                        seeAllEvents();
                        break;
                    case 4:
                        addUser();
                        break;
                    case 5:
                        System.out.println("Logged out of the System !\n");
                        return;
                    case 6:
                        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
                        System.out.println("+            Goodbye ! Thank you for using this system !                    +");
                        System.out.println("+               Developed By :- Wathila Karunathilake                       +");
                        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
                        System.exit(0);
                    default:
                        System.out.println("Please enter your choice between 1 - 6\n");

                }
            } catch (InputMismatchException e){ // Incase of invalid input exception give another attempt for user
                input.nextLine();
                System.out.println("Please enter your choice between 1 - 6\n");
            }

        }
    }

    // Creating a method to print all the vendors
    public void seeAllVendors(){
        // Printing the main menu of showing vendors
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                         See All Vendors                                   +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        // Getting the data from the API endpoint
        String allVendors = handleAPI.getAllVendors();
        printData(allVendors); // Printing data using a method created

        // Calling a method for Account Management under Admin
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        manageAccount(AccountType.VENDOR.name());
    }

    // Creating a method to print all the customers
    public void seeAllCustomers(){
        // Printing the main menu of showing customers
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                          See All Customers                                +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        /// Getting the data from API endpoint
        String allCustomers = handleAPI.getAllCustomers();
        printData(allCustomers); // Printing data using a method created

        // Calling a method for Account Management under Admin
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        manageAccount(AccountType.CUSTOMER.name());
    }

    // Creating a method to print all the events
    public void seeAllEvents(){
        // Printing the main menu to show all the events
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                         See All Events                                    +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        // Getting the events using API endpoint
        String jsonEvents = handleAPI.getAllActiveEvents();

        // Running a loop until user exits
        while (true) {
            try {
                printData(jsonEvents); // Print the data using a method created
                System.out.println("1. View live ticket pool");
                System.out.println("2. Stop session");
                System.out.println("3. Back to Main Menu");

                // Getting the choice from user
                System.out.print("Enter your choice : ");
                int adminChoice = input.nextInt();
                input.nextLine();

                // Calling the relevant method based on user choice
                switch (adminChoice) {
                    case 1:
                        liveSession();
                        break;
                    case 2:
                        stopSession();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Please enter between 1 to 3\n");

                }
            } catch (InputMismatchException e){ // In case of input mismatch exception show enter correct input
                input.nextLine();
                System.out.println("Please enter between 1 to 3\n");

            }
        }
    }

    public void addUser(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                            Add User                                       +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        User user = new AddUserConfig();
        String userType;

        while (true) {
            try {
                System.out.print("Enter User Type (Vendor/Customer): ");
                userType = input.nextLine().toUpperCase().trim();
                ((AddUserConfig) user).setUserType(userType);
                break;
            } catch (Exception e){
                System.out.println("Invalid user type please enter VENDOR or CUSTOMER !\n");
            }
        }

        while (true) {
            try {
                System.out.print("Enter First Name: ");
                String firstName = input.nextLine();
                ((AddUserConfig) user).setFirstName(firstName);
                break;
            } catch (Exception e){
                System.out.println("Please enter first name with at least 4 characters\n");
            }
        }

        while (true) {
            try {
                System.out.print("Enter Last Name: ");
                String lastName = input.nextLine();
                ((AddUserConfig) user).setLastName(lastName);
                break;
            } catch (Exception e){
                System.out.println("Please enter last name\n");
            }
        }

        while (true) {
            try {
                System.out.print("Enter Email: ");
                String email = input.nextLine();
                user.setEmail(email);
                break;
            } catch (Exception e){
                System.out.println("Please enter valid email address\n");
            }
        }

        while (true) {
            try {
                System.out.print("Enter Password: ");
                String password = input.nextLine();
                user.setPassword(password);
                break;
            } catch (Exception e){
                System.out.println("Please enter valid password !\n");
            }
        }

        while (true) {
            try {
                System.out.print("Enter Country: ");
                String country = input.nextLine().trim();
                ((AddUserConfig) user).setCountry(country);
                break;
            } catch (Exception e){
                System.out.println("Please enter country name !\n");
            }
        }

        while (true) {
            try {
                System.out.print("Enter Telephone: ");
                String telephone = input.nextLine();
                ((AddUserConfig) user).setTelephone(telephone);
                break;
            } catch (Exception e){
                System.out.println("Please enter valid phone number !\n");
            }
        }

        while (true) {
            try {
                System.out.print("Enter Address: ");
                String address = input.nextLine();
                ((AddUserConfig) user).setAddress(address);
                break;
            } catch (Exception e){
                System.out.println("Please enter valid address ! \n");
            }
        }

        while (true){
            try {
                String brOrNICNumber = null;

                if (userType.equalsIgnoreCase(AccountType.VENDOR.name())) {
                    System.out.print("Enter Business Registration Number: ");
                    brOrNICNumber = input.nextLine();
                } else if (userType.equalsIgnoreCase(AccountType.CUSTOMER.name())) {
                    System.out.print("Enter NIC Number: ");
                    brOrNICNumber = input.nextLine();
                } else {
                    System.out.println("Invalid config.User Type entered!");
                }

                ((AddUserConfig) user).setBrOrNICNumber(brOrNICNumber);
                break;
            } catch (Exception e){
                System.out.println("Enter valid input !\n");
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String response = handleAPI.addAccount(gson.toJson(user));

        System.out.println(response);
    }

    public void updatePassword(String userRole){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                          Update Password                                  +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        ManageAccountConfig manageAccountConfig = new UpdateAccountConfig();

        while (true) {
            try {
                System.out.print("Enter the email to update password : ");
                String email = input.nextLine();
                manageAccountConfig.setEmail(email);
                break;
            } catch (Exception e){
                System.out.println("Invalid email entered !\n");
            }
        }

        manageAccountConfig.setUserType(userRole);

        while (true) {
            try {
                System.out.print("Enter the password wish to update : ");
                String password = input.nextLine();
                ((UpdateAccountConfig) manageAccountConfig).setPassword(password);
                break;
            } catch (Exception e){
                System.out.println("Invalid password entered !\n");
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String response = handleAPI.updatePassword(gson.toJson(manageAccountConfig));

        System.out.println(response != null ? "User updated successfully !\n" : "Internal server error !\n");
    }

    public void deleteAccount(String userRole){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                         Delete Account                                    +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        ManageAccountConfig manageAccountConfig = new ManageAccountConfig();

        while (true) {
            try {
                System.out.print("Enter the email to update password : ");
                String email = input.nextLine();
                manageAccountConfig.setEmail(email);
                manageAccountConfig.setUserType(userRole);
                break;
            } catch (Exception e){
                System.out.println("Invalid email entered !\n");
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String response = handleAPI.deleteAccount(gson.toJson(manageAccountConfig));

        System.out.println(response != null ? response : "Internal server error !\n");
    }

    public void manageAccount(String userRole){
        System.out.println("1. Update Credentials");
        System.out.println("2. Delete Account");
        System.out.println("3. Back to Main Menu");

        while (true) {
            try {
                System.out.print("Enter your choice : ");
                int adminChoice = input.nextInt();
                input.nextLine();

                switch (adminChoice) {
                    case 1:
                        updatePassword(userRole);
                        break;
                    case 2:
                        deleteAccount(userRole);
                        break;
                    case 3:
                        break;
                    default:
                        throw new InputMismatchException();

                }

                break;
            } catch (IllegalFormatException e) {
                input.nextLine();
                System.out.println("Enter valid input between 1 - 3\n");
            }
        }
    }

    private void stopSession(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                              Stop Session                                 +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        System.out.print("Enter the Session ID to stop : ");
        String sessionId = input.nextLine();

        String response = handleAPI.stopSession(sessionId);
        System.out.println(response);
    }

    private void liveSession(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                          See Live Updates                                 +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        System.out.print("Enter the Session ID to get update : ");
        String sessionId = input.nextLine();

        System.out.println("You will recieve updates from ticket pool !");
        System.out.println("If you wish to terminate press Enter !\n");

        RealTimeUpdate realTimeUpdate = new RealTimeUpdate(sessionId);
        Thread thread = new Thread(realTimeUpdate, "Periodic-Polling Thread Pool");
        thread.start();

        input.nextLine();
        thread.interrupt();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void printData(String json) {
        try {
            Gson gson = new Gson();
            List<String> excludedKeys = Arrays.asList(
                    "returnedImage", "eventImage", "enabled", "username",
                    "authorities", "credentialsNonExpired", "accountNonExpired",
                    "accountNonLocked", "password"
            );

            // Parse the JSON string into a JsonArray
            JsonArray jsonArray = gson.fromJson(json, JsonArray.class);

            // Iterate over each JsonObject in the array
            for (JsonElement element : jsonArray) {
                if (element.isJsonObject()) {
                    JsonObject jsonObject = element.getAsJsonObject();

                    // Iterate over the key-value pairs
                    for (String key : jsonObject.keySet()) {
                        if (!excludedKeys.contains(key)) {
                            System.out.println(key + " : " + jsonObject.get(key).getAsString());
                        }
                    }
                    System.out.println(); // Print an empty line to separate objects
                }
            }
        } catch (NullPointerException e){
            System.out.println("Something went wrong !\n");
        }
    }

}
