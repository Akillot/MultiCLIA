package core.ui.extensions.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.ui.essential.pages.Page;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static core.logic.CommandManager.*;
import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.appearance.TextConfigs.*;
import static java.lang.System.out;

public class WeatherPage extends Page {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Path currentDirectory = Paths.get("").toAbsolutePath();
    private static final String API_KEY;
    private String[][] commands = {
            {"Local weather", "lw"},
            {"Direct weather", "dw"},
            {"Restart", "rst"},
            {"Restart clear", "rcl"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    static {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("OPEN_WEATHER_API_KEY");

        if(API_KEY == null || API_KEY.isEmpty()) {
            insertControlChars('n',1);
            message("Weather is unavailable. Check your API Key.", layoutColor,getDefaultTextAlignment(),getDefaultDelay(),out::println);
        }
    }

    public void displayMenu() {
        marginBorder(1, 2);
        message("Weather:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase().trim();

            switch (input) {
                case "local weather", "lw" -> {
                    insertControlChars('n', 1);
                    WeatherService.getWeatherByIP();
                }
                case "direct weather", "dw" -> {
                    insertControlChars('n', 1);
                    out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter city name: ");
                    String city = scanner.nextLine().trim();
                    if (!city.isEmpty()) {
                        WeatherService.getWeather(city);
                    } else {
                        message("City name cannot be empty!", rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    }
                }
                case "restart", "rst" -> {
                    insertControlChars('n', 1);
                    mainMenuRestart();
                }
                case "restart clear", "rсl" -> mainMenuRestartWithClearing();
                case "clear", "cl" -> clearTerminal();
                case "help", "h" -> displayListOfCommands(commands);
                case "quit", "q", "exit", "e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    @Override
    protected void displayListOfCommands(String[][] commands) {
        super.displayListOfCommands(commands);
    }

    private static class WeatherService {

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
                message("🌤  Weather in " + city + ":", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("🌡  Temperature: " + temp + "°C", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("💨  Wind Speed: " + windSpeed + " m/s", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("💧  Humidity: " + humidity + "%", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("🔽  Pressure: " + pressure + " hPa", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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
