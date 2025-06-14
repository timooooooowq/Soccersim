import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

public class DeepSeekApiTest {
    private String teamOneName;
    private String teamTwoName;
    private int teamOneScore;
    private int teamTwoScore;
    private List<Player> teamOnePlayers;
    private List<Player> teamTwoPlayers;
    private double teamonexg;
    private double teamtwoxg;


    public DeepSeekApiTest(String t1Name, int t1Score, List<Player> t1Players,
                           String t2Name, int t2Score, List<Player> t2Players, double teamonexg, double teamtwoxg) {
        this.teamOneName = t1Name;
        this.teamOneScore = t1Score;
        this.teamOnePlayers = t1Players;
        this.teamTwoName = t2Name;
        this.teamTwoScore = t2Score;
        this.teamTwoPlayers = t2Players;
        this.teamonexg = teamonexg;
        this.teamtwoxg = teamtwoxg;
    }
    public String getSummary() {
        String prompt =
                "Then I will give the score of team 2 and the players on team 2. Second, you decide who to score and give the minutes from your estimate. Give a man of the match and do not bold anything or use any asterisks or ##. Make it a bit longer—you can add missed opportunities, give a weather report, and mention a random location and stadium. " +
                        "Team 1 Name: " + teamOneName + ", Goals Scored: " + teamOneScore + ", xG: " +
                        ". Players: GK: " + teamOnePlayers.get(0).getName() +
                        ", DEF: " + teamOnePlayers.get(1).getName() +
                        ", DEF: " + teamOnePlayers.get(2).getName() +
                        ", DEF: " + teamOnePlayers.get(3).getName() +
                        ", DEF: " + teamOnePlayers.get(4).getName() +
                        ", MID: " + teamOnePlayers.get(5).getName() +
                        ", MID: " + teamOnePlayers.get(6).getName() +
                        ", MID: " + teamOnePlayers.get(7).getName() +
                        ", ATK: " + teamOnePlayers.get(8).getName() +
                        ", ATK: " + teamOnePlayers.get(9).getName() +
                        ", ATK: " + teamOnePlayers.get(10).getName() +
                        ". Team Two Name: " + teamTwoName + ", Goals Scored: " + teamTwoScore +
                        ". Players: GK: " + teamTwoPlayers.get(0).getName() +
                        ", DEF: " + teamTwoPlayers.get(1).getName() +
                        ", DEF: " + teamTwoPlayers.get(2).getName() +
                        ", DEF: " + teamTwoPlayers.get(3).getName() +
                        ", DEF: " + teamTwoPlayers.get(4).getName() +
                        ", MID: " + teamTwoPlayers.get(5).getName() +
                        ", MID: " + teamTwoPlayers.get(6).getName() +
                        ", MID: " + teamTwoPlayers.get(7).getName() +
                        ", ATK: " + teamTwoPlayers.get(8).getName() +
                        ", ATK: " + teamTwoPlayers.get(9).getName() +
                        ", ATK: " + teamTwoPlayers.get(10).getName();

        System.out.println(prompt);
        String response = chatWithDeepSeek(prompt);
        System.out.println("Summary: " + response);
        return response;
    }


    public static String chatWithDeepSeek(String userMessage) {
        String url = "https://api.deepseek.com/chat/completions";
        String apiKey = "sk-cdae63cd132b4a8783f6614eb64a1563";
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("Missing DEEPSEEK_API_KEY environment variable.");
        }

        String model = "deepseek-chat";

        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);


            JSONObject bodyJson = new JSONObject();
            bodyJson.put("model", model);

            JSONArray messages = new JSONArray();
            messages.put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant.choese what players to score in this "));
            messages.put(new JSONObject().put("role", "user").put("content", userMessage));
            bodyJson.put("messages", messages);
            bodyJson.put("stream", false);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = bodyJson.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();
            InputStream is = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (code != 200) {
                return "Error from DeepSeek API: " + response.toString();
            }

            // Parse response JSON to extract content
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            return message.getString("content").trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
    }
}
