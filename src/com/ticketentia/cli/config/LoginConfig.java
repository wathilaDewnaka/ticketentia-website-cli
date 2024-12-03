package com.ticketentia.cli.config;

public class LoginConfig extends User {
    private String userType;

    public LoginConfig(){
        super();
    }

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
}
