package com.ticketentia.cli.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ticketentia.cli.api.HandleAPI;
import com.ticketentia.cli.api.RealTimeUpdate;
import com.ticketentia.cli.config.EventConfig;
import com.ticketentia.cli.config.Storage;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Creating a class to handle vendor services
public class VendorServices {
    // Creating instance variables
    private final Scanner input;
    private final HandleAPI handleAPI;
    private final Storage storage;

    public VendorServices() {
        // Initializing the instance variables
        this.input = new Scanner(System.in);
        this.storage = Storage.getInstance();
        this.handleAPI = new HandleAPI(storage.getJwtToken());
    }

    // Creating a method to print the menu to the user
    public void showMenu() {
        // Running a continuous loop until user exits
        while (true) {
            // Showing the main menu
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
            System.out.println("+                             Main Menu                                     +");
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

            // Print the name by config.Storage
            System.out.println("Hi " + storage.getUserName() + ", ");
            System.out.println("Logged into the system as Vendor\n");

            System.out.println("1. Start a session");
            System.out.println("2. See active sessions");
            System.out.println("3. See inactive sessions");
            System.out.println("4. Logout");
            System.out.println("5. Exit\n");

            try {
                System.out.print("Enter your choice: ");
                int choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1:
                        startSession();
                        break;
                    case 2:
                        loadActiveSessions();
                        break;
                    case 3:
                        loadInactiveSessions();
                        break;
                    case 4:
                        System.out.println("Logged out of the system\n");
                        return;
                    case 5:
                        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
                        System.out.println("+            Goodbye ! Thank you for using this system !                    +");
                        System.out.println("+               Developed By :- Wathila Karunathilake                       +");
                        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
                        System.exit(0);
                    default:
                        System.out.println("Please enter your choice between 1 - 5\n");

                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Please enter your choice between 1 - 5\n");
            }
        }
    }

    private void startSession() {
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                             Start Session                                 +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        System.out.println("Please enter the following details to start a session, \n");

        EventConfig eventConfig = new EventConfig();

        while (true) {
            try {
                System.out.print("Event Name : ");
                String eventName = input.nextLine();
                eventConfig.setEventName(eventName);
                break;
            } catch (Exception e){
                System.out.println("Invalid input. Please enter event name with 4 characters\n");
            }
        }

        while (true) {
            try {
                System.out.print("Event Venue : ");
                String eventVenue = input.nextLine();
                eventConfig.setEventVenue(eventVenue);
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter event venue with 4 characters\n");
            }
        }

        while (true) {
            try {
                System.out.print("Ticket Price : ");
                double ticketPrice = input.nextDouble();
                input.nextLine();
                eventConfig.setTicketPrice(ticketPrice);
                break;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Invalid input. Please enter ticket price above Rs. 100/=\n");
            }

        }

        while (true) {
            try {
                System.out.print("Event Date : ");
                String eventDate = input.nextLine();
                eventConfig.setEventDate(eventDate);
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter date as YYYY-MM-DD and greater than today\n");
            }
        }

        while (true) {
            try {
                System.out.print("Event Time : ");
                String eventTime = input.nextLine();
                eventConfig.setEventTime(eventTime);
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter proper time like 22:00 \n");
            }
        }

        while (true) {
            try {
                System.out.print("Event Category (Webinar, Live Concert) : ");
                String eventCategory = input.nextLine().toUpperCase().strip();
                eventConfig.setEventCategory(eventCategory);
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter event category");
                System.out.println("Only Webinar, Live Concert, Musical Concert, Concert, Workshop\n");
            }
        }

        while (true) {
            try {
                System.out.print("Event Description : ");
                String eventDescription = input.nextLine();
                eventConfig.setEventDescription(eventDescription);
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter event description with 20 characters\n");
            }
        }

        while (true) {
            try {
                System.out.print("Event Type : ");
                String eventType = input.nextLine().toUpperCase().strip();
                eventConfig.setEventType(eventType);
                break;
            } catch (Exception e) {
                System.out.println("Invalid Event Type. Regular or VIP is only allowed\n");
            }
        }

        while (true) {
            try {
                System.out.print("VIP Discount : ");
                double vipDiscount = input.nextDouble();
                eventConfig.setVipDiscount(vipDiscount);
                break;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Invalid input. VIP discount allowed between 0 to 60%.\n");
            }
        }

        while (true) {
            try {
                System.out.print("Total Tickets : ");
                int totalTickets = input.nextInt();
                eventConfig.setTotalTickets(totalTickets);
                break;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Invalid input. Enter total tickets above 10.\n");
            }
        }

        while (true) {
            try {
                System.out.print("Ticket Release Rate (in S) : ");
                int ticketReleaseRate = input.nextInt();
                eventConfig.setTicketReleaseRate(ticketReleaseRate);
                break;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Invalid input. Enter the release rate between 1 to 10\n");
            }
        }

        while (true) {
            try {
                System.out.print("Customer Retrieve Rate (in S) : ");
                int customerRetrievalRate = input.nextInt();
                eventConfig.setCustomerRetrievalRate(customerRetrievalRate);
                break;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Invalid input. Enter retrievel rate 1 to 10\n");
            }
        }

        while (true) {
            try {
                System.out.print("Maximum Ticket Pool Capacity : ");
                int maximumPoolCapacity = input.nextInt();
                eventConfig.setMaximumPoolCapacity(maximumPoolCapacity);
                break;
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Invalid input. This should be less than total tickets\n");

            }
        }

        input.nextLine();

        try {
            String response = handleAPI.startNewSession(eventConfig.toPayload());
            if (response == null){
                return;
            }
            System.out.println("Session added successfully !\n");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInactiveSessions () {
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                          Inactive Sessions                                +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        String sessionJson = handleAPI.getSessionsInActive();
        printData(sessionJson);
    }

    private void loadActiveSessions () {
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                           Active Sessions                                 +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        String sessionJson = handleAPI.getSessions();
        printData(sessionJson);

        while (true) {
            try {
                System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
                System.out.println("1. See Live Updates of Session");
                System.out.println("2. Stop Session");
                System.out.println("3. Back to Main Menu");

                System.out.print("Enter your choice : ");
                int option = input.nextInt();
                input.nextLine();

                switch (option) {
                    case 1:
                        liveSession();
                        break;
                    case 2:
                        stopSession();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid choice please enter between 1 - 3\n");
                }
            } catch (Exception e){
                input.nextLine();
                System.out.println("Invalid choice please enter between 1 - 3\n");
            }
        }
    }

        private void stopSession () {
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
            System.out.println("+                              Stop Session                                 +");
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

            System.out.print("Enter the Session ID to stop : ");
            String sessionId = input.nextLine();

            handleAPI.stopSession(sessionId);
        }

        private void liveSession () {
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
