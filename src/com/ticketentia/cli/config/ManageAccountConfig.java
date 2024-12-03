package com.ticketentia.cli.config;

import java.util.regex.Pattern;

public class ManageAccountConfig {
    private String email;
    private String userType;

    public ManageAccountConfig(String email, String userType) {
        this.setEmail(email);
        this.setUserType(userType);
    }

    public ManageAccountConfig(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
            return;
        }

        throw new IllegalArgumentException("Enter correct email address");
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
}
