import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class PostRequestHandle extends ApiClient {
    public PostRequestHandle(String token) {
        super(token);
    }

    @Override
    public String execute(String uri, String method, String bodyData) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(bodyData, StandardCharsets.UTF_8));

        String token = getToken();
        if (token != null) {
            requestBuilder.header("Authorization", "Bearer " + token);
        }

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = sendRequest(request);
        return response.statusCode() == 200 ? response.body() : null;
    }
}
