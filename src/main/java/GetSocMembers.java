import io.github.cdimascio.dotenv.Dotenv;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;


public class GetSocMembers {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String SOC_MEMBERS_URL = dotenv.get("UNION_URL");
    private static final String SOC_COMMITTEE_COOKIE = ".ASPXAUTH=" + dotenv.get("UNION_COOKIE");
    private static final String MEMBER_TABLE_ID = dotenv.get("MEMBER_TABLE_ID");

    private static final ArrayList<Member> allMembers = new ArrayList<>();

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public static void main(String[] args) throws IOException, InterruptedException {
        getMembersFromPage(getSocMembersPage());
        System.out.println("Found " + allMembers.size() + " members");
    }

    public static void getMembersFromPage(HttpResponse<String> membersPage) {
        Document doc = Jsoup.parse(membersPage.body());
        Elements members = Objects.requireNonNull(doc.getElementById(MEMBER_TABLE_ID)).getElementsByTag("tr");
        for (int i = 1; i < members.size(); i++) {
            Elements member = members.get(i).getElementsByTag("td");
            try {
                allMembers.add(new Member(member.get(0).text(), Integer.parseInt(member.get(1).text()), LocalDateTime.parse(member.get(2).text(), dtf), LocalDateTime.parse(member.get(3).text(), dtf)));
            } catch (NumberFormatException e) {
                if (member.get(1).text().equals("")) {
                    allMembers.add(new Member(member.get(0).text(), 0, LocalDateTime.parse(member.get(2).text(), dtf), LocalDateTime.parse(member.get(3).text(), dtf)));
                } else {
                    System.out.println("Error parsing member " + member.get(0).text() + " with id " + member.get(1).text());
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error parsing member date: " + member.get(2).text());
            }
        }
    }

    public static HttpResponse<String> getSocMembersPage() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(SOC_MEMBERS_URL)).header("Cookie", SOC_COMMITTEE_COOKIE).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response;
        } else {
            throw new IOException("Failed to get members page");
        }
    }
}