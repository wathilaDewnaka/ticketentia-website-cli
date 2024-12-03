package com.ticketentia.cli.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Objects;

public class Storage {
    private static Storage instance; // Singleton instance

    private String jwtToken;
    private String userName;
    private String userRole;
    private String userId;

    // Private constructor to prevent instantiation
    private Storage() {}

    // Public method to provide access to the singleton instance
    public static synchronized Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    // Getter and Setter methods for jwtToken
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jsonData) {
        this.jwtToken = getValue(jsonData, "token");
    }

    // Getter and Setter methods for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String jsonData) {
        this.userId = getValue(jsonData, "userId");
    }

    // Getter and Setter methods for userName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String jsonData) {
        this.userName = getValue(jsonData, "name");
    }

    // Getter and Setter methods for userRole
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String jsonData) {
        this.userRole = Objects.requireNonNull(getValue(jsonData, "role")).toUpperCase();
    }

    private String getValue(String json, String key) {
        try {
            // Parse the JSON string into a JsonObject
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            // Check if the key exists
            if (jsonObject.has(key)) {
                return jsonObject.get(key).getAsString();
            } else {
                return null; // Key not found
            }
        } catch (Exception e) {
            return null;
        }
    }
}
