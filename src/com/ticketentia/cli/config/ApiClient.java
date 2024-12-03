package com.ticketentia.cli.config;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class ApiClient {
    private HttpClient client;
    private String token;

    public ApiClient(String token) {
        this.client = HttpClient.newHttpClient();
        this.token = token;
    }

    public HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String getToken() {
        return token;
    }

    // Abstract method for different API calls
    public abstract String execute(String uri, String method, String bodyData) throws IOException, InterruptedException;
}
