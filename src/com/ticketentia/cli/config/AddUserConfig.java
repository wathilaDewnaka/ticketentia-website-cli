package com.ticketentia.cli.config;

import com.ticketentia.cli.enums.AccountType;
import java.util.Objects;

public class AddUserConfig extends User {
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
        if (firstName.length() < 4){
            throw new IllegalArgumentException();
        }
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
        if (country.length() <= 0){
            throw new IllegalArgumentException();
        }
        this.country = country;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        if (telephone.length() < 9){
            throw new IllegalArgumentException();
        }

        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address.length() < 5){
            throw new IllegalArgumentException();
        }
        this.address = address;
    }

    public String getBrOrNICNumber() {
        return brOrNICNumber;
    }

    public void setBrOrNICNumber(String brOrNICNumber) {
        if (brOrNICNumber.length() < 5 || brOrNICNumber.length() > 12){
            throw new IllegalArgumentException();
        }
        this.brOrNICNumber = brOrNICNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        if (Objects.equals(userType, AccountType.CUSTOMER.name()) || Objects.equals(userType, AccountType.VENDOR.name())){
            this.userType = userType;
            return;
        }
        throw new IllegalArgumentException();

    }
}
