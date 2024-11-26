public class Storage {
    private static String jwtToken;
    private static String userName;
    private static String userRole;

    public static String getJwtToken() {
        return jwtToken;
    }

    public static void setJwtToken(String jwtToken) {
        Storage.jwtToken = jwtToken;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Storage.userName = userName;
    }

    public static String getUserRole() {
        return userRole;
    }

    public static void setUserRole(String userRole) {
        Storage.userRole = userRole;
    }
}
