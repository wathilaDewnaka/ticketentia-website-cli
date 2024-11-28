import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HandleAPI {
    private String jwtToken;

    public HandleAPI(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String login(String jsonData) {
        try {
            PostRequestHandle postRequest = new PostRequestHandle(jwtToken);
            return postRequest.execute("http://localhost:8080/api/auth/login", "POST", jsonData);
        } catch (Exception e) {
            handleError(e);
        }
        return null;
    }

    public void startNewSession(String formPayload) {
        try {
            PostRequestHandle postRequest = new PostRequestHandle(jwtToken);
            postRequest.execute("http://localhost:8080/api/vendor/session/start", "POST-FORM", formPayload);
        } catch (Exception e) {
            handleError(e);
        }
    }

    public void stopSession(String sessionId) {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            getRequest.execute("http://localhost:8080/api/vendor/session/stop/" + sessionId, "GET", null);
        } catch (Exception e) {
            handleError(e);
        }
    }

    public String getSessions() {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/vendor/session/all-sessions/1", "GET", null);
        } catch (Exception e) {
            handleError(e);
        }
        return null;
    }

    public String getSessionsInActive() {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/vendor/session/all-sessions/inactive/1", "GET", null);
        } catch (Exception e) {
            handleError(e);
        }
        return null;
    }

    public String getAllVendors() {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/admin/vendors", "GET", null);
        } catch (Exception e) {
            handleError(e);
        }
        return null;
    }

    public String getAllCustomers() {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/admin/customers", "GET", null);
        } catch (Exception e) {
            handleError(e);
        }
        return null;
    }

    public String addAccount(String jsonData) {
        try {
            PostRequestHandle postRequest = new PostRequestHandle(jwtToken);
            return postRequest.execute("http://localhost:8080/api/admin/add-user", "POST", jsonData);
        } catch (Exception e) {
            handleError(e);
        }
        return null;
    }

    public String deleteAccount(String jsonData) {
        try {
            PostRequestHandle postRequest = new PostRequestHandle(jwtToken);
            return postRequest.execute("http://localhost:8080/api/admin/delete-account", "POST", jsonData);
        } catch (Exception e) {
            handleError(e);
        }
        return null;
    }

    public String updatePassword(String jsonData) {
        try {
            PostRequestHandle postRequest = new PostRequestHandle(jwtToken);
            return postRequest.execute("http://localhost:8080/api/admin/update","POST", jsonData);
        } catch (Exception e) {
            handleError(e);
        }
        return null;
    }

    public String ticketPoolData(String sessionId) {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/vendor/session/ticket-pools/" + sessionId, "GET", null);
        } catch (Exception e) {
            handleError(e);
        }
        return null;
    }

    public String getAllActiveEvents() {
        try {
            GetRequestHandle getRequest = new GetRequestHandle(jwtToken);
            return getRequest.execute("http://localhost:8080/api/admin/events", "GET", null);
        } catch (Exception e) {
            handleError(e);
        }
        return null;
    }

    // A method to handle common error logging
    private void handleError(Exception e) {
        System.out.println("Error occurred: " + e.getMessage());
        e.printStackTrace();
    }
}

