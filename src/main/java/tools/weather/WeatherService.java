package tools.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static core.ui.configs.AppearanceConfigs.getDefaultDelay;
import static core.ui.configs.AppearanceConfigs.getDefaultTextAlignment;
import static core.ui.configs.AppearanceConfigs.getLayoutColor;
import static core.ui.configs.AppearanceConfigs.getRejectionColor;
import static core.ui.configs.TextConfigs.insertControlChars;
import static core.ui.configs.TextConfigs.message;
import static java.lang.System.out;


public class WeatherService {
    private static final String API_KEY;

    static {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("OPEN_WEATHER_API_KEY");
    }

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=" + API_KEY;
    private static final String GEO_IP_URL = "http://ip-api.com/json";
    private static OkHttpClient client = new OkHttpClient();

    static void getWeather(String city) {
        String url = String.format(BASE_URL, city);
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                message("Error: Unable to fetch weather data. HTTP Code: " + response.code(),
                        getRejectionColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);
                return;
            }

            assert response.body() != null;
            String jsonData = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);

            String weather = jsonNode.get("weather").get(0).get("description").asText();

            double temp = jsonNode.get("main").get("temp").asDouble();
            double windSpeed = jsonNode.get("wind").get("speed").asDouble();

            int humidity = jsonNode.get("main").get("humidity").asInt();
            double pressure = jsonNode.get("main").get("pressure").asDouble();

            insertControlChars('n', 1);
            message("🌤  Weather in " + city + ":",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("🌡  Temperature: " + temp + "°C",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("💨  Wind Speed: " + windSpeed + " m/s",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("💧  Humidity: " + humidity + "%",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("🔽  Pressure: " + pressure + " hPa",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

        } catch (IOException e) {
            insertControlChars('n', 1);
            message("Error fetching weather data: " + e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        }
    }

    static void getWeatherByIP() {
        Request request = new Request.Builder().url(GEO_IP_URL).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                message("Error: Unable to determine location. HTTP Code: " + response.code(),
                        getRejectionColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);
                return;
            }

            assert response.body() != null;
            String jsonData = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);

            String city = jsonNode.get("city").asText();
            String country = jsonNode.get("country").asText();

            message("📍 Your location: " + city + ", " + country,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            getWeather(city);
        } catch (IOException e) {
            message("Error fetching location data: " + e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        }
    }
}
