package com.ticketentia.cli.api;

import com.ticketentia.cli.config.Storage;

public class HandleAPI {
    private final String jwtToken;
    private final Storage storage;

    public HandleAPI(String jwtToken) {
        this.jwtToken = jwtToken;
        this.storage = Storage.getInstance();
    }

    public String login(String jsonData) {
        try {
            PostRequestHandle postRequest = new PostRequestHandle(jwtToken);
            return postRequest.execute("http://localhost:8080/api/auth/login", "POST", jsonData);
        } catch (Exception e) {
            return null;
        }
    }

    public String startNewSession(String formPayload) {
        try {
            PostRequestHandle postRequest = new PostRequestHandle(jwtToken);
            return postRequest.execute("http://localhost:8080/api/vendor/session/start", "POST-FORM", formPayload);
        } catch (Exception e) {
            System.out.println("Internal server error !\n");
        }
        return null;
    }

    public String  stopSession(String sessionId) {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/vendor/session/stop/" + sessionId, "GET", null);
        } catch (Exception e) {
            System.out.println("Internal server error !\n");
        }
        return null;
    }

    public String getSessions() {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/vendor/session/all-sessions/" + storage.getUserId(), "GET", null);
        } catch (Exception e) {
            return null;
        }
    }

    public String getSessionsInActive() {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/vendor/session/all-sessions/inactive/" + storage.getUserId(), "GET", null);
        } catch (Exception e) {
            return null;
        }
    }

    public String getAllVendors() {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/admin/vendors", "GET", null);
        } catch (Exception e) {
            return null;
        }
    }

    public String getAllCustomers() {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/admin/customers", "GET", null);
        } catch (Exception e) {
            return null;
        }
    }

    public String addAccount(String jsonData) {
        try {
            PostRequestHandle postRequest = new PostRequestHandle(jwtToken);
            return postRequest.execute("http://localhost:8080/api/admin/add-user", "POST", jsonData);
        } catch (Exception e) {
            return null;
        }
    }

    public String deleteAccount(String jsonData) {
        try {
            PostRequestHandle postRequest = new PostRequestHandle(jwtToken);
            return postRequest.execute("http://localhost:8080/api/admin/delete-account", "POST", jsonData);
        } catch (Exception e) {
            return null;
        }
    }

    public String updatePassword(String jsonData) {
        try {
            PostRequestHandle postRequest = new PostRequestHandle(jwtToken);
            return postRequest.execute("http://localhost:8080/api/admin/update","POST", jsonData);
        } catch (Exception e) {
            return null;
        }
    }

    public String ticketPoolData(String sessionId) {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/vendor/session/ticket-pool/" + sessionId, "GET", null);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public String getAllActiveEvents() {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/admin/events", "GET", null);
        } catch (Exception e) {
            return null;
        }
    }
}

