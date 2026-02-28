import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FoodServer {

    static List<String> donations = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // DONATE
        server.createContext("/donate", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {

                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                donations.add(body); // store raw text

                String response = "Donation added!";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        // REQUEST
        server.createContext("/request", exchange -> {

            StringBuilder response = new StringBuilder("<h1>Available Food</h1>");

            for (int i = 0; i < donations.size(); i++) {
                response.append("<p>")
                        .append(donations.get(i))
                        .append(" <a href='/claim?id=")
                        .append(i)
                        .append("'>Claim</a></p>");
            }

            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        });

        // CLAIM
        server.createContext("/claim", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            int id = Integer.parseInt(query.split("=")[1]);

            if (id >= 0 && id < donations.size()) {
                donations.remove(id);
            }

            exchange.getResponseHeaders().add("Location", "/request");
            exchange.sendResponseHeaders(302, -1);
        });

        server.start();
        System.out.println("Running at http://localhost:8080/request");
    }
}