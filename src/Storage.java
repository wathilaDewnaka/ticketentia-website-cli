public class Storage {
    private static String jwtToken;
    private static String userName;
    private static String userRole;

    public static String getJwtToken() {
        return jwtToken;
    }

    public static void setJwtToken(String jsonData) {
        Storage.jwtToken = getValue(jsonData, "token");
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String jsonData) {
        Storage.userName = getValue(jsonData, "name");
    }

    public static String getUserRole() {
        return userRole;
    }

    public static void setUserRole(String jsonData) {
        Storage.userRole= getValue(jsonData, "role");
    }

    private static String getValue(String json, String key) {
        int startIndex = json.indexOf("\"" + key + "\":\"") + key.length() + 4; // Find the starting point of the value
        int endIndex = json.indexOf("\"", startIndex); // Find the end of the value
        if (startIndex != -1 && endIndex != -1) {
            return json.substring(startIndex, endIndex); // Extract the value
        }
        return null; // Return null if the key is not found
    }
}
