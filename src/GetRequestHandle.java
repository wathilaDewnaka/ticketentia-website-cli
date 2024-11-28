import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetRequestHandle extends ApiClient {

    public GetRequestHandle(String token) {
        super(token);
    }

    @Override
    public String execute(String uri, String method, String bodyData) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Authorization", "Bearer " + getToken())
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = sendRequest(request);
        return response.statusCode() == 200 ? response.body() : null;
    }
}
