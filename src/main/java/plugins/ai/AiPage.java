package plugins.ai;

import core.ui.configs.AppearanceConfigs;
import core.ui.configs.TextConfigs;
import core.Page;

import java.util.Scanner;

import static core.CommandManager.*;
import static core.ui.configs.DisplayManager.clearTerminal;
import static core.ui.configs.DisplayManager.scanner;
import static plugins.ai.ChatGPTClient.*;
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
            AppearanceConfigs.getColor(204) + "C" + AppearanceConfigs.getColor(110) + "h"
                    + AppearanceConfigs.getColor(205) + "a" + AppearanceConfigs.getColor(208) + "t"
                    + AppearanceConfigs.getColor(161) + "G" + AppearanceConfigs.getColor(207) + "P"
                    + AppearanceConfigs.getColor(217) + "T";

    public void displayMenu() {
        AppearanceConfigs.marginBorder(1, 2);
        TextConfigs.message("Powered by OpenAI " + coloredChatGptLogo + AppearanceConfigs.RESET, AppearanceConfigs.getLayoutColor(),
                AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::println);
        TextConfigs.message("AI:", AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::print);

        displayListOfCommands(commands);

        while (true) {
            TextConfigs.slowMotionText(AppearanceConfigs.getDefaultDelay(), AppearanceConfigs.getSearchingLineAlignment(), false,
                    AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + AppearanceConfigs.getSearchingArrow(), "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ask chatgpt", "ac" -> runChatGpt();
                case "modify creativity", "mc" -> configureCreativity();
                case "modify maximum of tokens", "mmt" -> configureMaxTokens();
                case "info", "i" -> displayChatGptInfo();
                case "restart", "rst" -> {
                    TextConfigs.insertControlChars('n', 1);
                    mainMenuRestart();
                }
                case "restart clear", "rcl" -> mainMenuRestartWithClearing();
                case "clear", "cl" -> clearTerminal();
                case "help", "h" -> displayListOfCommands(commands);
                case "quit", "q", "exit", "e" -> {
                    exitPage("You are in main menu");
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
                TextConfigs.insertControlChars('n', 1);
                out.print(TextConfigs.alignment(AppearanceConfigs.getDefaultTextAlignment()) + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + "Enter prompt [or "
                        + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + "q" + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + " to exit]: ");
                String userMessage = scanner.nextLine();

                if (userMessage.equalsIgnoreCase("q") || userMessage.isEmpty()) {
                    TextConfigs.insertControlChars('n', 1);
                    break;
                }

                String response = sendMessage(userMessage);

                TextConfigs.insertControlChars('n', 1);
                out.print(TextConfigs.alignment(AppearanceConfigs.getDefaultTextAlignment()) + coloredChatGptLogo + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + ": ");
                TextConfigs.slowMotionText(20, AppearanceConfigs.getDefaultTextAlignment(), false,
                        AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + response, "");
                TextConfigs.insertControlChars('n', 1);
            }
        } catch (Exception e) {
            TextConfigs.insertControlChars('n', 1);
            TextConfigs.message("Error: " + e.getMessage(), AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), 0, out::println);
        }
    }

    private static void displayChatGptInfo() {
        try {
            TextConfigs.insertControlChars('n', 1);
            TextConfigs.message("Model: " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + getModel(), AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::print);
            TextConfigs.message("Max Tokens: " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + getMaxTokens(), AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::print);
            TextConfigs.message("Creativity: " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + getTemperature(), AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::println);
        } catch (Exception e) {
            TextConfigs.insertControlChars('n', 1);
            TextConfigs.message("Model: " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + AppearanceConfigs.getColor(AppearanceConfigs.getRejectionColor()) + "x", AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::print);
            TextConfigs.message("Max Tokens: " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + AppearanceConfigs.getColor(AppearanceConfigs.getRejectionColor()) + "x", AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::print);
            TextConfigs.message("Temperature: " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + AppearanceConfigs.getColor(AppearanceConfigs.getRejectionColor()) + "x", AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::println);
            TextConfigs.message("Error: " + e.getMessage(), AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), 0, out::println);
        }
    }

    private static void configureCreativity() {
        TextConfigs.insertControlChars('n', 1);
        TextConfigs.message("["+ AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + "i" + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + "] The creativity of the AI controls the creativity of responses.\n" +
                        TextConfigs.alignment(AppearanceConfigs.getDefaultTextAlignment()) + "The higher the creativity," +
                        " the more diverse and unpredictable the answers.",
                AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::println);

        double temperature;
        while (true) {
            out.print(TextConfigs.alignment(AppearanceConfigs.getDefaultTextAlignment()) + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) +
                    "Enter a creativity [Choose between " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + "0.1"
                    + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + " and " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + "1.2" + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + "]: ");
            try {
                temperature = scanner.nextDouble();
                scanner.nextLine();

                if (temperature >= 0.1 && temperature <= 1.2) {
                    setTemperature(temperature);
                    TextConfigs.insertControlChars('n', 1);
                    TextConfigs.message("Status: " + AppearanceConfigs.getColor(AppearanceConfigs.getAcceptanceColor()) + "✓", AppearanceConfigs.getLayoutColor(),
                            AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::print);

                    TextConfigs.message("New creativity is: " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + getTemperature()
                                    + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + ".", AppearanceConfigs.getLayoutColor(),
                            AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::println);
                    break;
                } else {
                    TextConfigs.message("Invalid input! Please enter a value between"
                                    + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + " 0.1 " + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + "and" + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor())
                                    + " 1.2" + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + ".",
                            AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::println);
                }
            } catch (Exception e) {
                scanner.nextLine();
                TextConfigs.message("Error: Invalid input. Please enter a valid number.",
                        AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::println);
            }
        }
    }

    private static void configureMaxTokens() {
        TextConfigs.insertControlChars('n', 1);

        TextConfigs.message("["+ AppearanceConfigs.getColor(AppearanceConfigs.getMainColor()) + "i" + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + "] Max tokens define the response length. More tokens allow longer answers.\n" +
                TextConfigs.alignment(AppearanceConfigs.getDefaultTextAlignment()) + "Choose wisely based on your needs.", AppearanceConfigs.getLayoutColor(),
                AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::println);
        int maxTokens;

        while (true) {
            out.print(TextConfigs.alignment(AppearanceConfigs.getDefaultTextAlignment()) + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) +
                    "Enter max amount of tokens [Choose a number between " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor())
                    + "1" + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + " and " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor())
                    + "500"  + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + "]: ");

            try {
                maxTokens = scanner.nextInt();
                scanner.nextLine();

                if (maxTokens > 0 && maxTokens <= 500) {
                    setMaxTokens(maxTokens);
                    TextConfigs.insertControlChars('n', 1);
                    TextConfigs.message("Status: " + AppearanceConfigs.getColor(AppearanceConfigs.getAcceptanceColor()) + "✓", AppearanceConfigs.getLayoutColor(),
                            AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::print);

                    TextConfigs.message("New maximum amount of tokens is: " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor())
                                    + getMaxTokens() + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + ".", AppearanceConfigs.getLayoutColor(),
                            AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::println);
                    break;
                } else {
                    TextConfigs.message("Invalid input! Please enter a number between" + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor())
                                    + "1" + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + " and " + AppearanceConfigs.getColor(AppearanceConfigs.getMainColor())
                                    + "500"  + AppearanceConfigs.getColor(AppearanceConfigs.getLayoutColor()) + ".", AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(),
                            AppearanceConfigs.getDefaultDelay(), out::println);
                }
            } catch (Exception e) {
                scanner.nextLine();
                TextConfigs.message("Error: Invalid input. Please enter a valid number.",
                        AppearanceConfigs.getLayoutColor(), AppearanceConfigs.getDefaultTextAlignment(), AppearanceConfigs.getDefaultDelay(), out::println);
            }
        }
    }
}