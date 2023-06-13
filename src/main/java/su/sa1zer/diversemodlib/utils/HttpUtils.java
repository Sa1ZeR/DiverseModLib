package su.sa1zer.diversemodlib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class HttpUtils {

    private static final Gson GSON = new GsonBuilder().serializeNulls().create();

    public static HttpRequest createGetRequest(String url) {
        return createGetRequest(url, new String[] {});
    }

    public static HttpRequest createGetRequest(String url, String... params) {
        if(params.length > 1)
            url = url + buildParams(params);

        return HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();
    }

    public static HttpRequest createPostRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
    }

    public static HttpRequest createPostRequest(String url, Object object) {
        return createGetRequest(url, GSON.toJson(object));
    }

    public static HttpRequest createPostRequest(String url, String json) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/json;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    public static HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return sendRequest(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    public static <T> HttpResponse<T> sendRequest(HttpRequest request, HttpResponse.BodyHandler<T> bodyHandlers) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2).build();

        return httpClient.send(request, bodyHandlers);
    }

    //todo async
    private static String buildParams(String... params) {
        if(params.length % 2 != 0)
            throw new IllegalArgumentException("Incorrect get params");

        List<String> args = new ArrayList<>();

        for(int i = 0; i < params.length; i+=2) {
            args.add(params[i] + "=" + params[i+1]);
        }

        return "?" + String.join("&", args);
    }
}
