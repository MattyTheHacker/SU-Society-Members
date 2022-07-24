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
    private static final String MEMBER_TABLE_ID = dotenv.get("MEMBER_TABLE_ID");


    public static void main(String[] args) throws IOException, InterruptedException {
        HttpResponse<String> membersPage = getSocMembersPage();
        System.out.println(membersPage.body());
    }

    public static HttpResponse<String> getSocMembersPage() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(SOC_MEMBERS_URL)).header("Cookie", SOC_COMMITTEE_COOKIE).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}