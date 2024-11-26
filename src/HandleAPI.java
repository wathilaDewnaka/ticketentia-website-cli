import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HandleAPI {
    public String login(String jsonData){
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/login"))
                    .header("Content-Type", "application/json")  // Set the content type to JSON
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8)) // Send JSON payload
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return (response.statusCode() == 200) ? response.body() : null;
        } catch (Exception e){
            System.out.println("Connection to the server failed !");
            System.out.println("Please try again shortly !!!");
            System.exit(0);
        }

        return null;
    }

    public void startNewSession(String formPayload) {
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create the request with the URI and headers
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/vendor/session/start"))
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .header("Content-Type", "application/x-www-form-urlencoded")  // Set the content type to form payload
                    .POST(HttpRequest.BodyPublishers.ofString(formPayload, StandardCharsets.UTF_8)) // Send form payload
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopSession(String sessionId){
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build the request
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .uri(URI.create("http://localhost:8080/api/vendor/session/stop/" + sessionId)) // Replace with your API URL
                    .GET() // Or .POST(BodyPublishers.ofString("your-body-here")) for POST requests
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSessions(){
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build the request
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .uri(URI.create("http://localhost:8080/api/vendor/session/all-sessions/1")) // Replace with your API URL
                    .GET() // Or .POST(BodyPublishers.ofString("your-body-here")) for POST requests
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getSessionsInActive(){
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build the request
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .uri(URI.create("http://localhost:8080/api/vendor/session/all-sessions/inactive/1")) // Replace with your API URL
                    .GET() // Or .POST(BodyPublishers.ofString("your-body-here")) for POST requests
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getAllVendors(){
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build the request
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .uri(URI.create("http://localhost:8080/api/admin/vendors")) // Replace with your API URL
                    .GET() // Or .POST(BodyPublishers.ofString("your-body-here")) for POST requests
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getAllCustomers(){
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build the request
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .uri(URI.create("http://localhost:8080/api/admin/customers")) // Replace with your API URL
                    .GET() // Or .POST(BodyPublishers.ofString("your-body-here")) for POST requests
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String addAccount(String jsonData){
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create the request with the URI and headers
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .uri(URI.create("http://localhost:8080/api/admin/add-user"))
                    .header("Content-Type", "application/json")  // Set the content type to JSON
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8)) // Send JSON payload
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response Code: " + response.statusCode());
            return response.body();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public String deleteAccount(String jsonData){
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create the request with the URI and headers
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .uri(URI.create("http://localhost:8080/api/admin/delete-account"))
                    .header("Content-Type", "application/json")  // Set the content type to JSON
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8)) // Send JSON payload
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response Code: " + response.statusCode());
            return response.body();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public String updatePassword(String jsonData){
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create the request with the URI and headers
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .uri(URI.create("http://localhost:8080/api/admin/update"))
                    .header("Content-Type", "application/json")  // Set the content type to JSON
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData, StandardCharsets.UTF_8)) // Send JSON payload
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response Code: " + response.statusCode());
            return response.body();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public String ticketPoolData(String sessionId){
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create the request with the URI and headers
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .uri(URI.create("http://localhost:8080/api/vendor/session/ticket-pools/" + sessionId))
                    .header("Content-Type", "application/json")  // Set the content type to JSON
                    .GET() // Send JSON payload
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response Code: " + response.statusCode());
            return response.body();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public String getAllActiveEvents(){
        try {
            // Create the HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create the request with the URI and headers
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Storage.getJwtToken())
                    .uri(URI.create("http://localhost:8080/api/admin/events"))
                    .header("Content-Type", "application/json")  // Set the content type to JSON
                    .GET() // Send JSON payload
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response Code: " + response.statusCode());
            return response.body();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
