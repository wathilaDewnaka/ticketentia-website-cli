public class UpdateAccountConfig extends ManageAccountConfig {
    private String password;

    public UpdateAccountConfig(String email, String userType, String password) {
        super(email, userType);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toJson() {
        return "{" +
                "\"email\":\"" + getEmail() + "\"," +
                "\"userType\":\"" + getUserType() + "\"," +
                "\"password\":\"" + password + "\"" +
                "}";
    }
}
