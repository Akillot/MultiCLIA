package core.ui.extensions.ai;

import core.ui.essential.pages.Page;

import java.util.Scanner;

import static core.logic.CommandManager.*;
import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.ui.essential.configs.appearance.TextConfigs.*;
import static core.ui.extensions.ai.ChatGPTClient.*;
import static java.lang.System.out;

public class AiPage extends Page {

    private String[][] commands = {
            {"Ask ChatGPT", "ac"},
            {"Modify Creativity", "mc"},
            {"Modify Maximum of Tokens", "mmt"},
            {"Info", "i"},
            {"Restart", "rst"},
            {"Restart clear", "rcl"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    private static String coloredChatGptLogo =
            getColor(204) + "C" + getColor(110) + "h"
                    + getColor(205) + "a" + getColor(208) + "t"
                    + getColor(161) + "G" + getColor(207) + "P"
                    + getColor(217) + "T";

    public void displayMenu() {
        marginBorder(1, 2);
        message("Powered by OpenAI " + coloredChatGptLogo + RESET, layoutColor,
                getDefaultTextAlignment(), getDefaultDelay(), out::println);
        message("AI:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ask chatgpt", "ac" -> runChatGpt();
                case "modify creativity", "mc" -> configureCreativity();
                case "modify maximum of tokens", "mmt" -> configureMaxTokens();
                case "info", "i" -> displayChatGptInfo();
                case "restart", "rst" -> {
                    insertControlChars('n', 1);
                    mainMenuRestart();
                }
                case "restart clear", "rcl" -> mainMenuRestartWithClearing();
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

    private static void runChatGpt() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter prompt [or "
                        + getColor(mainColor) + "q" + getColor(layoutColor) + " to exit]: ");
                String userMessage = scanner.nextLine();

                if (userMessage.equalsIgnoreCase("q") || userMessage.isEmpty()) {
                    insertControlChars('n', 1);
                    break;
                }

                String response = sendMessage(userMessage);

                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment()) + coloredChatGptLogo + getColor(layoutColor) + ": ");
                slowMotionText(20, getDefaultTextAlignment(), false,
                        getColor(layoutColor) + response, "");
                insertControlChars('n', 1);
            }
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    private static void displayChatGptInfo() {
        try {
            insertControlChars('n', 1);
            message("Model: " + getColor(mainColor) + getModel(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Max Tokens: " + getColor(mainColor) + getMaxTokens(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Creativity: " + getColor(mainColor) + getTemperature(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Model: " + getColor(mainColor) + getColor(rejectionColor) + "x", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Max Tokens: " + getColor(mainColor) + getColor(rejectionColor) + "x", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Temperature: " + getColor(mainColor) + getColor(rejectionColor) + "x", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            message("Error: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    private static void configureCreativity() {
        insertControlChars('n', 1);
        message("["+ getColor(mainColor) + "i" + getColor(layoutColor) + "] The creativity of the AI controls the creativity of responses.\n" +
                        alignment(getDefaultTextAlignment()) + "The higher the creativity," +
                        " the more diverse and unpredictable the answers.",
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        double temperature;
        while (true) {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) +
                    "Enter a creativity [Choose between " + getColor(mainColor) + "0.1"
                    + getColor(layoutColor) + " and " + getColor(mainColor) + "1.2" + getColor(layoutColor) + "]: ");
            try {
                temperature = scanner.nextDouble();
                scanner.nextLine();

                if (temperature >= 0.1 && temperature <= 1.2) {
                    setTemperature(temperature);
                    insertControlChars('n', 1);
                    message("Status: " + getColor(acceptanceColor) + "✓", layoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);

                    message("New creativity is: " + getColor(mainColor) + getTemperature()
                                    + getColor(layoutColor) + ".", layoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    break;
                } else {
                    message("Invalid input! Please enter a value between"
                                    + getColor(mainColor) + " 0.1 " + getColor(layoutColor) + "and" + getColor(mainColor)
                                    + " 1.2" + getColor(layoutColor) + ".",
                            layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                }
            } catch (Exception e) {
                scanner.nextLine();
                message("Error: Invalid input. Please enter a valid number.",
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        }
    }

    private static void configureMaxTokens() {
        insertControlChars('n', 1);

        message("["+ getColor(mainColor) + "i" + getColor(layoutColor) + "] Max tokens define the response length. More tokens allow longer answers.\n" +
                alignment(getDefaultTextAlignment()) + "Choose wisely based on your needs.", layoutColor,
                getDefaultTextAlignment(), getDefaultDelay(), out::println);
        int maxTokens;

        while (true) {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) +
                    "Enter max amount of tokens [Choose a number between " + getColor(mainColor)
                    + "1" + getColor(layoutColor) + " and " + getColor(mainColor)
                    + "500"  + getColor(layoutColor) + "]: ");

            try {
                maxTokens = scanner.nextInt();
                scanner.nextLine();

                if (maxTokens > 0 && maxTokens <= 500) {
                    setMaxTokens(maxTokens);
                    insertControlChars('n', 1);
                    message("Status: " + getColor(acceptanceColor) + "✓", layoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);

                    message("New maximum amount of tokens is: " + getColor(mainColor)
                                    + getMaxTokens() + getColor(layoutColor) + ".", layoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    break;
                } else {
                    message("Invalid input! Please enter a number between" + getColor(mainColor)
                                    + "1" + getColor(layoutColor) + " and " + getColor(mainColor)
                                    + "500"  + getColor(layoutColor) + ".", layoutColor, getDefaultTextAlignment(),
                            getDefaultDelay(), out::println);
                }
            } catch (Exception e) {
                scanner.nextLine();
                message("Error: Invalid input. Please enter a valid number.",
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        }
    }
}