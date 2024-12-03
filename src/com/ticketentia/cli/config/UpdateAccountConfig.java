package com.ticketentia.cli.config;

public class UpdateAccountConfig extends ManageAccountConfig {
    private String password;

    public UpdateAccountConfig(String email, String userType, String password) {
        super(email, userType);
        this.password = password;
    }

    public UpdateAccountConfig(){
        super();

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
