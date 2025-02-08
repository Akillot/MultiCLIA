package core.pages;

import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.logic.ChatGPTClient.*;
import static core.logic.CommandManager.exitPage;
import static core.logic.CommandManager.mainMenuRerun;
import static core.ui.DisplayManager.clearTerminal;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class AiPage {

    private static String coloredChatGptLogo =
            getColor(204) + "C" + getColor(110) + "h"
                    + getColor(205) + "a" + getColor(208) + "t"
                    + getColor(161) + "G" + getColor(207) + "P"
                    + getColor(217) + "T";

    public static void displayAiPage() {
        marginBorder(1, 2);
        message("Powered by OpenAI " + coloredChatGptLogo + RESET, sysLayoutColor,
                getDefaultTextAlignment(), 0, out::println);
        message("AI:", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

        displayListOfCommands();

        while (true) {
            slowMotionText(0, getSearchingLineAlignment(), false,
                    getColor(sysLayoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ask chatgpt", "/ac" -> runChatGpt();
                case "personalize chatgpt", "/pc" -> configureTemperature();
                case "info", "/i" -> displayChatGptInfo();
                case "rerun", "/rr" -> {
                    insertControlChars('n', 1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands() {
        insertControlChars('n', 1);
        message("·  Ask ChatGPT [" + getColor(sysMainColor)
                + "/ac" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

        message("·  Personalize ChatGPT [" + getColor(sysMainColor)
                + "/pc" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

        message("·  Info [" + getColor(sysMainColor)
                + "/i" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

        message("·  Rerun [" + getColor(sysMainColor)
                + "/rr" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

        message("·  Clear terminal [" + getColor(sysMainColor)
                + "/cl" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

        message("·  List Of Commands [" + getColor(sysMainColor)
                + "/lc" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

        message("·  Exit [" + getColor(sysMainColor)
                + "/e" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
    }

    private static void runChatGpt() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Enter prompt [or "
                        + getColor(sysMainColor) + "exit" + getColor(sysLayoutColor) + " to quit]: ");
                String userMessage = scanner.nextLine();

                if (userMessage.equalsIgnoreCase("exit")) {
                    insertControlChars('n', 1);
                    break;
                }

                if (userMessage.isEmpty()) {
                    insertControlChars('n', 1);
                    break;
                }
                String response = sendMessage(userMessage);

                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment()) + coloredChatGptLogo + getColor(sysLayoutColor) + ": ");
                slowMotionText(50, getDefaultTextAlignment(), false,
                        getColor(sysLayoutColor) + response, "");
                insertControlChars('n', 1);
            }
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    private static void displayChatGptInfo() {
        insertControlChars('n', 1);
        message("Model: " + getColor(sysMainColor) + getModel(), sysLayoutColor, getDefaultTextAlignment(), 0, out::print);
        message("Max Tokens: " + getColor(sysMainColor) + getMaxTokens(), sysLayoutColor, getDefaultTextAlignment(), 0, out::print);
        message("Temperature: " + getColor(sysMainColor) + getTemperature(), sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
    }

    private static void configureTemperature() {
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Enter a temperature [Choose between 0.1 and 1.2]: ");

        try {
            double temperature = scanner.nextDouble();
            scanner.nextLine();

            if (temperature >= 0.1 && temperature <= 1.2) {
                setTemperature(temperature);
                insertControlChars('n', 1);
                message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);
                message("New temperature is: " + getTemperature(), sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
            } else {
                message("Invalid input! Please enter a value between 0.1 and 1.2.", sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
            }
        } catch (Exception e) {
            scanner.nextLine();
            message("Error: Invalid input. Please enter a number.", sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }
}