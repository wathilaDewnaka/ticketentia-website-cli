public class AddUserConfig extends User{
    private String firstName;
    private String lastName;
    private String country;
    private String telephone;
    private String address;
    private String brOrNICNumber;
    private String userType;

    public AddUserConfig(String email, String password, String firstName, String lastName, String country, String telephone, String address, String brOrNICNumber, String userType) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.telephone = telephone;
        this.address = address;
        this.brOrNICNumber = brOrNICNumber;
        this.userType = userType;
    }

    public AddUserConfig(){
        super();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrOrNICNumber() {
        return brOrNICNumber;
    }

    public void setBrOrNICNumber(String brOrNICNumber) {
        this.brOrNICNumber = brOrNICNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String toJson() {
        // Manually construct the JSON string with username and password included
        return "{"
                + "\"email\": \"" + getEmail() + "\", "  // Assuming getEmail() in the User class
                + "\"password\": \"" + getPassword() + "\", "  // Assuming getPassword() in the User class
                + "\"firstName\": \"" + firstName + "\", "
                + "\"lastName\": \"" + lastName + "\", "
                + "\"country\": \"" + country + "\", "
                + "\"telephone\": \"" + telephone + "\", "
                + "\"address\": \"" + address + "\", "
                + "\"brOrNICNumber\": \"" + brOrNICNumber + "\", "
                + "\"userType\": \"" + userType + "\""
                + "}";
    }
}
