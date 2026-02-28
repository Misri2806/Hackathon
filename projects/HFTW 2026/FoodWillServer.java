import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class FoodWillServer {

    private static final String DB_URL =
        "jdbc:postgresql://YOUR_SUPABASE_HOST:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "YOUR_PASSWORD";

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/request", (HttpExchange exchange) -> {

            if ("POST".equals(exchange.getRequestMethod())) {

                InputStream input = exchange.getRequestBody();
                String body = new String(input.readAllBytes(), StandardCharsets.UTF_8);

                String item = getValue(body, "item");
                String location = getValue(body, "location");

                String response = searchDatabase(item, location);

                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, response.length());

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.start();
        System.out.println("Backend running on http://localhost:8080");
    }

    private static String getValue(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search) + search.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private static String searchDatabase(String item, String location) {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql =
                "SELECT donor_name, item_name FROM donations " +
                "WHERE item_name ILIKE ? AND location = ? LIMIT 1";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + item + "%");
            stmt.setString(2, location);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "Match found: " +
                       rs.getString("donor_name") +
                       " has " +
                       rs.getString("item_name");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "No donations found.";
    }
}