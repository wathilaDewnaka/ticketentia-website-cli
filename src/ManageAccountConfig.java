public class ManageAccountConfig {
    private String email;
    private String userType;

    public ManageAccountConfig(String email, String userType) {
        this.email = email;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String toJson() {
        return "{" +
                "\"email\":\"" + email + "\"," +
                "\"userType\":\"" + userType + "\"" +
                "}";
    }
}
