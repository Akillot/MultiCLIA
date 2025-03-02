package core.ui.extensions.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static core.logic.CommandManager.exitPage;
import static core.logic.CommandManager.mainMenuRerun;
import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.TextConfigs.*;
import static core.ui.essential.pages.EasterEggPage.displayEasterEgg;
import static java.lang.System.out;

public class WeatherPage {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Path currentDirectory = Paths.get("").toAbsolutePath();

    public static void displayWeatherPage() {
        marginBorder(1, 2);
        message("Weather:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase().trim();

            switch (input) {
                case "local weather", "/lw" -> {
                    insertControlChars('n', 1);
                    WeatherService.getWeatherByIP();
                }
                case "direct weather", "/dw" -> {
                    insertControlChars('n', 1);
                    out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter city name: ");
                    String city = scanner.nextLine().trim();
                    if (!city.isEmpty()) {
                        WeatherService.getWeather(city);
                    } else {
                        message("City name cannot be empty!", rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    }
                }
                case "restart", "/rs" -> {
                    insertControlChars('n', 1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list", "/ls" -> displayListOfCommands();
                case "easteregg", "/ee" -> displayEasterEgg();
                case "quit", "/q" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands() {
        insertControlChars('n', 1);
        message("·  Local weather [" + getColor(mainColor) + "/lw" + getColor(layoutColor) + "]",
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("·  Direct weather [" + getColor(mainColor) + "/dw" + getColor(layoutColor) + "]",
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("·  Local weather [" + getColor(mainColor) + "/lw" + getColor(layoutColor) + "]",
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("·  Restart [" + getColor(mainColor) + "/rs" + getColor(layoutColor) + "]",
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("·  Clear terminal [" + getColor(mainColor) + "/cl" + getColor(layoutColor) + "]",
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("·  List [" + getColor(mainColor) + "/ls" + getColor(layoutColor) + "]",
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("·  Quit [" + getColor(mainColor) + "/q" + getColor(layoutColor) + "]",
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static class WeatherService {

        private static final String API_KEY = "1cf3d1f3e8ee40db940c70cfac6379cc";
        private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=" + API_KEY;
        private static final String GEO_IP_URL = "http://ip-api.com/json";
        private static final OkHttpClient client = new OkHttpClient();

        public static void getWeather(String city) {
            String url = String.format(BASE_URL, city);
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    message("Error: Unable to fetch weather data. HTTP Code: " + response.code(),
                            rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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
                message("🌤 Weather in " + city + ":", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("🌡 Temperature: " + temp + "°C", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("💨 Wind Speed: " + windSpeed + " m/s", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("💧 Humidity: " + humidity + "%", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("🔽 Pressure: " + pressure + " hPa", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            } catch (IOException e) {
                insertControlChars('n', 1);
                message("Error fetching weather data: " + e.getMessage(), rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        }

        public static void getWeatherByIP() {
            Request request = new Request.Builder().url(GEO_IP_URL).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    message("Error: Unable to determine location. HTTP Code: " + response.code(),
                            rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }

                assert response.body() != null;
                String jsonData = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonData);

                String city = jsonNode.get("city").asText();
                String country = jsonNode.get("country").asText();

                message("📍 Your location: " + city + ", " + country, layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

                getWeather(city);
            } catch (IOException e) {
                message("Error fetching location data: " + e.getMessage(), rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        }
    }
}
