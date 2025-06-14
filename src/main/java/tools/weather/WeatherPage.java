package tools.weather;

import core.Page;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static core.CommandManager.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.clearTerminal;
import static core.ui.configs.DisplayManager.displayLogo;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;
import static tools.weather.WeatherService.getWeather;
import static tools.weather.WeatherService.getWeatherByIP;

public class WeatherPage extends Page {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Path currentDirectory = Paths.get("").toAbsolutePath();
    private String[][] commands = {
            {"Local weather", "lw"},
            {"Direct weather", "dw"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    private static final String[] WEATHER_ASCII_LOGO = {
            "╔═══════════════════════════════════════════════════════════════╗",
            "║                                                               ║",
            "║  ██╗    ██╗███████╗ █████╗ ████████╗██╗  ██╗███████╗██████╗   ║",
            "║  ██║    ██║██╔════╝██╔══██╗╚══██╔══╝██║  ██║██╔════╝██╔══██╗  ║",
            "║  ██║ █╗ ██║█████╗  ███████║   ██║   ███████║█████╗  ██████╔╝  ║",
            "║  ██║███╗██║██╔══╝  ██╔══██║   ██║   ██╔══██║██╔══╝  ██╔══██╗  ║",
            "║  ╚███╔███╔╝███████╗██║  ██║   ██║   ██║  ██║███████╗██║  ██║  ║",
            "║   ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝  ║",
            "║                                                               ║",
            "╚═══════════════════════════════════════════════════════════════╝"
    };

    public void displayMenu() {
        marginBorder(1, 2);
        clearTerminal();
        displayLogo(getDefaultTextAlignment(), WEATHER_ASCII_LOGO);
        insertControlChars('n', 1);

        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(),
                    getSearchingLineAlignment(),
                    false,
                    getColor(getLayoutColor()) + getSearchingArrow(),
                    " ");

            String input = scanner.nextLine().toLowerCase().trim();

            switch (input) {
                case "local weather", "lw" -> {
                    insertControlChars('n', 1);
                    getWeatherByIP();
                }
                case "direct weather", "dw" -> {
                    insertControlChars('n', 1);
                    out.print(alignment(getDefaultTextAlignment())
                            + getColor(getLayoutColor()) + "Enter city name: ");

                    String city = scanner.nextLine().trim();
                    if (!city.isEmpty()) {
                        getWeather(city);
                    } else {
                        message("City name cannot be empty!",
                                getRejectionColor(),
                                getDefaultTextAlignment(),
                                getDefaultDelay(),
                                out::println);
                    }
                }
                case "clear", "cl" -> clearTerminal();
                case "help", "h" -> displayListOfCommands(commands);
                case "quit", "q", "exit", "e" -> {
                    exitPageFormatting();
                    clearAndRestartApp();
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
}