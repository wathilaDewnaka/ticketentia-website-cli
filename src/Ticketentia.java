import java.net.ConnectException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Ticketentia {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
            System.out.println("+                        Welcome to Ticketentia                             +");
            System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
            System.out.println("Please enter details to login\n");

            System.out.print("Email : ");
            String email = input.nextLine();

            while (!isValidEmail(email)){
                System.out.println("Please enter valid email address : \n");

                System.out.print("Email : ");
                email = input.nextLine();
            }

            System.out.print("Password : ");
            String password = input.nextLine();

            try {
                User user = new LoginConfig(email, password, (email.startsWith("admin") ? "ADMIN" : "VENDOR"));
                HandleAPI handleAPI = new HandleAPI();
                String loginJson = handleAPI.login(((LoginConfig) user).toJson());

                if (loginJson == null) {
                    System.out.println("\nInvalid Credentials !");
                    System.out.println("Please Try Again !");
                    continue;
                }

                Storage.setJwtToken(getValue(loginJson, "token"));
                Storage.setUserName(getValue(loginJson, "name"));
                Storage.setUserRole(getValue(loginJson, "role"));

                if (Storage.getUserRole().equals("ADMIN")) {
                    AdminServices adminServices = new AdminServices();
                    adminServices.showMenu();
                } else if (Storage.getUserRole().equals("VENDOR")) {
                    VendorServices vendorServices = new VendorServices();
                    vendorServices.showMenu();
                }
            } catch (IllegalArgumentException e){
                System.out.println("Please enter valid email address");
            }
        }
    }

    public static String getValue(String json, String key) {
        int startIndex = json.indexOf("\"" + key + "\":\"") + key.length() + 4; // Find the starting point of the value
        int endIndex = json.indexOf("\"", startIndex); // Find the end of the value
        if (startIndex != -1 && endIndex != -1) {
            return json.substring(startIndex, endIndex); // Extract the value
        }
        return null; // Return null if the key is not found
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
}