import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class GetSocMembers {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String SOC_MEMBERS_URL = dotenv.get("UNION_URL");
    private static final String SOC_COMMITTEE_COOKIE = ".ASPXAUTH=" + dotenv.get("UNION_COOKIE");


    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SOC_MEMBERS_URL))
                .header("Cookie", SOC_COMMITTEE_COOKIE)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}

