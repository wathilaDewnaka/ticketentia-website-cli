import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AdminServices {
    private final Scanner input = new Scanner(System.in);

    public void showMenu(){
        while (true) {
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
            System.out.println("+                             Main Menu                                     +");
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

            System.out.println("Hi " + Storage.getUserName() + ", ");
            System.out.println("Logged into the system as Admin\n");

            System.out.println("1. See all vendors");
            System.out.println("2. See all customers");
            System.out.println("3. See all active events");
            System.out.println("4. Add Vendor / Customer");
            System.out.println("5. Logout");
            System.out.println("6. Exit\n");

            System.out.print("Enter your choice : ");
            int adminChoice = input.nextInt();
            input.nextLine();

            switch (adminChoice) {
                case 1:
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
            }
        }
    }

    public void seeAllVendors(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                         See All Vendors                                   +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        HandleAPI handleAPI = new HandleAPI();
        String allVendors = handleAPI.getAllVendors();

        printData(allVendors);

        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        manageAccount("VENDOR");
    }

    public void seeAllCustomers(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                          See All Customers                                +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        HandleAPI handleAPI = new HandleAPI();
        String allCustomers = handleAPI.getAllCustomers();

        printData(allCustomers);

        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        manageAccount("CUSTOMER");
    }

    public void seeAllEvents(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                         See All Events                                    +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        HandleAPI handleAPI = new HandleAPI();
        String jsonEvents = handleAPI.getAllActiveEvents();

        printData(jsonEvents);
        System.out.println("1. View live ticket pool");
        System.out.println("2. Stop session");
        System.out.println("3. Back to Main Menu");

        System.out.print("Enter your choice : ");
        int adminChoice = input.nextInt();
        input.nextLine();

        switch (adminChoice){
            case 1:
                liveSession();
                break;
            case 2:
                stopSession();
                break;
        }
    }

    public void addUser(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                            Add User                                       +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        System.out.print("Enter User Type (Vendor/Customer): ");
        String userType = input.nextLine().toUpperCase();

        // Prompt for common details
        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();

        System.out.print("Enter Email: ");
        String email = input.nextLine();

        System.out.print("Enter Password: ");
        String password = input.nextLine();

        System.out.print("Enter Country: ");
        String country = input.nextLine();

        System.out.print("Enter Telephone: ");
        String telephone = input.nextLine();

        System.out.print("Enter Address: ");
        String address = input.nextLine();

        String brOrNICNumber = null;

        if (userType.equalsIgnoreCase("VENDOR")) {
            System.out.print("Enter Business Registration Number: ");
            brOrNICNumber = input.nextLine();
        } else if (userType.equalsIgnoreCase("CUSTOMER")) {
            System.out.print("Enter NIC Number: ");
            brOrNICNumber = input.nextLine();
        } else {
            System.out.println("Invalid User Type entered!");
        }

        User user = new AddUserConfig(email, password, firstName, lastName, country, telephone, address, brOrNICNumber, userType);
        HandleAPI handleAPI = new HandleAPI();
        String response = handleAPI.addAccount(((AddUserConfig) user).toJson());

        System.out.println(response);
    }

    public void updatePassword(String userRole){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                          Update Password                                  +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        System.out.print("Enter the email to update password : ");
        String email = input.nextLine();

        System.out.print("Enter the password wish to update : ");
        String password = input.nextLine();

        ManageAccountConfig manageAccountConfig = new UpdateAccountConfig(email, userRole, password);
        HandleAPI handleAPI = new HandleAPI();
        handleAPI.updatePassword(manageAccountConfig.toJson());
    }

    public void deleteAccount(String userRole){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                         Delete Account                                    +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        System.out.print("Enter the email to delete account : ");
        String email = input.nextLine();

        ManageAccountConfig manageAccountConfig = new ManageAccountConfig(email, userRole);
        HandleAPI handleAPI = new HandleAPI();
        handleAPI.deleteAccount(manageAccountConfig.toJson());
    }

    public void manageAccount(String userRole){
        System.out.println("1. Update Credentials");
        System.out.println("2. Delete Account");
        System.out.println("3. Back to Main Menu");

        System.out.print("Enter your choice : ");
        int adminChoice = input.nextInt();
        input.nextLine();

        switch (adminChoice){
            case 1:
                updatePassword(userRole);
                break;
            case 2:
                deleteAccount(userRole);
                break;
        }
    }

    private void stopSession(){
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("+                              Stop Session                                 +");
        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");

        System.out.print("Enter the Session ID to stop : ");
        String sessionId = input.nextLine();

        HandleAPI handleAPI = new HandleAPI();
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
                List<String> excludedKeys = Arrays.asList("returnedImage", "eventImage", "enabled", "username", "authorities", "credentialsNonExpired", "accountNonExpired", "accountNonLocked", "password");
                if (excludedKeys.contains(keyValue[0].trim())) {
                    continue;
                }
                System.out.println(keyValue[0].trim() + " : " + keyValue[1].trim());
            }
            System.out.println();  // Print an empty line to separate objects
        }
    }

}
