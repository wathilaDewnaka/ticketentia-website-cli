public class LoginConfig extends User{
    private String userType;

    public LoginConfig(String email, String password, String userType) {
        super(email, password);
        this.userType = userType;
    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String toJson() {
        return String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"userType\":\"%s\"}",
                getEmail(), getPassword(), userType
        );
    }
}
