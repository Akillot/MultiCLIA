package core.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static core.logic.ColorConfigs.*;
import static core.logic.DisplayManager.message;
import static core.logic.TextConfigs.alignment;
import static java.lang.System.out;

public class ApiConfigs {
    public static void getHttpRequest(String userUri, String requestType, String text) {
        StringBuilder response = new StringBuilder();
        try {
            URI uri = new URI(userUri);
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestType);

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                message("Error: HTTP " + statusCode, "red", 58, 0, out::print);
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            out.println(alignment(58) + WHITE + BOLD + text + " " + RESET + BLUE + response + RESET);

        } catch (Exception e) {
            message("Error: " + e.getMessage(), "red", 58, 0, out::print);
        }
    }
}
