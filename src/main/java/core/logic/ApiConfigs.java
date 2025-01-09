package core.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import static core.logic.AppearanceConfigs.*;
import static core.logic.TextConfigs.alignment;
import static core.logic.TextConfigs.message;
import static java.lang.System.out;

public class ApiConfigs {

    public static @Nullable String httpRequest(String userUri, String requestType, String text, String key) {
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
                message("Error: HTTP " + statusCode, systemRejectionColor, 58, 0, out::print);
                return null;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            try {
                JSONObject jsonResponse = new JSONObject(response.toString());
                String value = jsonResponse.optString(key);

                out.println(alignment(58) + getAnsi256Color(systemLayoutColor) + text + " " + RESET +
                        getAnsi256Color(systemMainColor) + value + RESET);
            } catch (Exception e) {
                message("Error parsing JSON response: " + e.getMessage(), systemRejectionColor,58,0,out::print);
            }

            return response.toString();

        } catch (Exception e) {
            message("Error: " + e.getMessage(), systemRejectionColor,58,0,out::print);
            return null;
        }
    }
}
