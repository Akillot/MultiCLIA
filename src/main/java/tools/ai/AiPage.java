package tools.ai;

import core.Page;
import java.util.Scanner;

import static core.CommandManager.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.clearTerminal;
import static core.ui.configs.DisplayManager.scanner;
import static core.ui.configs.TextConfigs.*;
import static tools.ai.ChatGPTClient.*;
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
        message("Powered by OpenAI " + coloredChatGptLogo + RESET,
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);
        message("AI:",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(),
                    getSearchingLineAlignment(),
                    false,
                    getColor(getLayoutColor()) + getSearchingArrow(),
                    "");
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
                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment())
                        + getColor(getLayoutColor()) + "Enter prompt [or "
                        + getColor(getMainColor()) + "q" + getColor(getLayoutColor()) + " to exit]: ");
                String userMessage = scanner.nextLine();

                if (userMessage.equalsIgnoreCase("q") || userMessage.isEmpty()) {
                    insertControlChars('n', 1);
                    break;
                }

                String response = sendMessage(userMessage);

                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment()) + coloredChatGptLogo + getColor(getLayoutColor()) + ": ");

                slowMotionText(20,
                        getDefaultTextAlignment(),
                        false,
                        getColor(getLayoutColor()) + response, "");

                insertControlChars('n', 1);
            }
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), getLayoutColor(), getDefaultTextAlignment(), 0, out::println);
        }
    }

    private static void displayChatGptInfo() {
        try {
            insertControlChars('n', 1);
            message("Model: " + getColor(getMainColor()) + getModel(),
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("Max Tokens: " + getColor(getMainColor()) + getMaxTokens(),
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(), out::print);

            message("Creativity: " + getColor(getMainColor()) + getTemperature(),
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);

        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Model: " + getColor(getMainColor()) + getColor(getRejectionColor()) + "x",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("Max Tokens: " + getColor(getMainColor()) + getColor(getRejectionColor()) + "x",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("Temperature: " + getColor(getMainColor()) + getColor(getRejectionColor()) + "x",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

            message("Error: " + e.getMessage(),
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);
        }
    }

    private static void configureCreativity() {
        insertControlChars('n', 1);
        message("["+ getColor(getMainColor()) + "i"
                        + getColor(getLayoutColor()) + "] The creativity of the AI controls the creativity of responses.\n"
                        + alignment(getDefaultTextAlignment()) + "The higher the creativity, the more diverse and unpredictable the answers.",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        double temperature;
        while (true) {
            out.print(alignment(getDefaultTextAlignment())
                    + getColor(getLayoutColor()) + "Enter a creativity [Choose between "
                    + getColor(getMainColor()) + "0.1"
                    + getColor(getLayoutColor()) + " and "
                    + getColor(getMainColor()) + "1.2" + getColor(getLayoutColor()) + "]: ");
            try {
                temperature = scanner.nextDouble();
                scanner.nextLine();

                if (temperature >= 0.1 && temperature <= 1.2) {
                    setTemperature(temperature);
                    insertControlChars('n', 1);
                    message("Status: " + getColor(getAcceptanceColor()) + "✓",
                            getLayoutColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::print);

                    message("New creativity is: " + getColor(getMainColor())
                                    + getTemperature() + getColor(getLayoutColor()) + ".",
                            getLayoutColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::println);
                    break;
                } else {
                    message("Invalid input! Please enter a value between"
                                    + getColor(getMainColor()) + " 0.1 " + getColor(getLayoutColor())
                                    + "and" + getColor(getMainColor())
                                    + " 1.2" + getColor(getLayoutColor()) + ".",
                            getLayoutColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::println);
                }
            } catch (Exception e) {
                scanner.nextLine();
                message("Error: Invalid input. Please enter a valid number.",
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);
            }
        }
    }

    private static void configureMaxTokens() {
        insertControlChars('n', 1);

        message("["+ getColor(getMainColor()) + "i"
                        + getColor(getLayoutColor())
                        + "] Max tokens define the response length. More tokens allow longer answers.\n"
                        + alignment(getDefaultTextAlignment()) + "Choose wisely based on your needs.",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        int maxTokens;

        while (true) {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                    + "Enter max amount of tokens [Choose a number between " + getColor(getMainColor())
                    + "1" + getColor(getLayoutColor()) + " and " + getColor(getMainColor())
                    + "500"  + getColor(getLayoutColor()) + "]: ");

            try {
                maxTokens = scanner.nextInt();
                scanner.nextLine();

                if (maxTokens > 0 && maxTokens <= 500) {
                    setMaxTokens(maxTokens);
                    insertControlChars('n', 1);
                    message("Status: " + getColor(getAcceptanceColor()) + "✓",
                            getLayoutColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::print);

                    message("New maximum amount of tokens is: "
                                    + getColor(getMainColor())
                                    + getMaxTokens() + getColor(getLayoutColor()) + ".",
                            getLayoutColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::println);
                    break;
                } else {
                    message("Invalid input! Please enter a number between"
                                    + getColor(getMainColor())
                                    + "1" + getColor(getLayoutColor())
                                    + " and " + getColor(getMainColor())
                                    + "500"  + getColor(getLayoutColor())
                                    + ".",
                            getLayoutColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::println);
                }
            } catch (Exception e) {
                scanner.nextLine();
                message("Error: Invalid input. Please enter a valid number.",
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);
            }
        }
    }
}