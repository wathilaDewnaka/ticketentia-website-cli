import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class VendorServices {
    private Scanner input;
    private HandleAPI handleAPI;

    public VendorServices(){
        this.input = new Scanner(System.in);
        this.handleAPI = new HandleAPI(Storage.getJwtToken());
    }

    public void showMenu() {
        while (true) {
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
            System.out.println("+                             Main Menu                                     +");
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

            System.out.println("Hi " + Storage.getUserName() + ", ");
            System.out.println("Logged into the system as Vendor\n");

            System.out.println("1. Start a session");
            System.out.println("2. See active sessions");
            System.out.println("3. See inactive sessions");
            System.out.println("4. Logout");
            System.out.println("5. Exit\n");

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
                    return;
                case 5:
                    System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
                    System.out.println("+            Goodbye ! Thank you for using this system !                    +");
                    System.out.println("+               Developed By :- Wathila Karunathilake                       +");
                    System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void startSession() {
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                             Start Session                                 +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        System.out.println("Please enter the following details to start a session, \n");

        System.out.print("Event Name : ");
        String eventName = input.nextLine();

        System.out.print("Event Venue : ");
        String eventVenue = input.nextLine();

        System.out.print("Ticket Price : ");
        String ticketPrice = input.nextLine();

        System.out.print("Event Date : ");
        String eventDate = input.nextLine();

        System.out.print("Event Time : ");
        String eventTime = input.nextLine();

        System.out.print("Event Category : ");
        String eventCategory = input.nextLine();

        System.out.print("Event Description : ");
        String eventDescription = input.nextLine();

        System.out.print("Event Type : ");
        String eventType = input.nextLine();

        System.out.print("VIP Discount : ");
        double vipDiscount = input.nextDouble();

        System.out.print("Total Tickets : ");
        int totalTickets = input.nextInt();

        System.out.print("Maximum Ticket Pool Capacity : ");
        int maximumPoolCapacity = input.nextInt();

        System.out.print("Ticket Release Rate (in ms) : ");
        int ticketReleaseRate = input.nextInt();

        System.out.print("Customer Retrieve Rate (in ms) : ");
        int customerRetrievalRate = input.nextInt();
        input.nextLine();

        EventConfig eventConfig = new EventConfig(1, eventName, eventVenue, ticketPrice, eventDate, eventTime, eventCategory, eventDescription, eventType, vipDiscount, totalTickets, maximumPoolCapacity, ticketReleaseRate, customerRetrievalRate);

        try {
            handleAPI.startNewSession(eventConfig.toPayload());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadInactiveSessions(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                          Inactive Sessions                                +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        String sessionJson = handleAPI.getSessionsInActive();
        printData(sessionJson);


    }

    private void loadActiveSessions(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                           Active Sessions                                 +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        String sessionJson = handleAPI.getSessions();

        printData(sessionJson);

        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("1. See Live Updates of Session");
        System.out.println("2. Stop Session");
        System.out.println("3. Back to Main Menu");

        System.out.print("Enter your choice : ");
        int option = input.nextInt();
        input.nextLine();

        switch (option){
            case 1:
                liveSession();
                break;
            case 2:
                stopSession();
                break;
        }
    }

    private void stopSession(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                              Stop Session                                 +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        System.out.print("Enter the Session ID to stop : ");
        String sessionId = input.nextLine();

        handleAPI.stopSession(sessionId);
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
    }

    private void printData(String json){
        json = json.substring(1, json.length() - 1); // Remove the outer square brackets
        String[] objects = json.split("},");  // Split based on object ending


        for (String object : objects) {
            object = object.replaceAll("[{}\"]", "");  // Clean up curly braces and quotes
            String[] fields = object.split(",");  // Split by commas for each key-value pair

            for (String field : fields) {
                String[] keyValue = field.split(":");  // Split key and value by colon
                if(keyValue[0].trim().equals("returnedImage") || keyValue[0].trim().equals("eventImage") || keyValue[0].trim().equals("vendorId")){
                    continue;
                }
                System.out.println(keyValue[0].trim() + " : " + keyValue[1].trim());
            }
            System.out.println();  // Print an empty line to separate objects
        }
    }
}
