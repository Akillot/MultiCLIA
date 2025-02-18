package core.ui.pages;

import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.logic.ChatGPTClient.*;
import static core.logic.CommandManager.exitPage;
import static core.logic.CommandManager.mainMenuRerun;
import static core.ui.DisplayManager.clearTerminal;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class AiPage{

    public static String coloredChatGptLogo =
            getColor(204) + "C" + getColor(110) + "h"
                    + getColor(205) + "a" + getColor(208) + "t"
                    + getColor(161) + "G" + getColor(207) + "P"
                    + getColor(217) + "T";

    public static void displayPage() {
        marginBorder(1, 2);
        message("Powered by OpenAI " + coloredChatGptLogo + RESET, sysLayoutColor,
                getDefaultTextAlignment(), getDefaultDelay(), out::println);
        message("AI:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(sysLayoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ask chatgpt", "/ac" -> runChatGpt();
                case "modify temperature", "/mt" -> configureTemperature();
                case "modify maximum of tokens", "/mmt" -> configureMaxTokens();
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

    public static void displayListOfCommands()  {
        insertControlChars('n', 1);
        message("·  Ask ChatGPT [" + getColor(sysMainColor)
                + "/ac" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Modify Temperature [" + getColor(sysMainColor)
                + "/mt" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Modify Maximum of Tokens [" + getColor(sysMainColor)
                + "/mmt" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Info [" + getColor(sysMainColor)
                + "/i" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Rerun [" + getColor(sysMainColor)
                + "/rr" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear terminal [" + getColor(sysMainColor)
                + "/cl" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List Of Commands [" + getColor(sysMainColor)
                + "/lc" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Exit [" + getColor(sysMainColor)
                + "/e" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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
                slowMotionText(20, getDefaultTextAlignment(), false,
                        getColor(sysLayoutColor) + response, "");
                insertControlChars('n', 1);
            }
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    private static void displayChatGptInfo() {
        try {
            insertControlChars('n', 1);
            message("Model: " + getColor(sysMainColor) + getModel(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Max Tokens: " + getColor(sysMainColor) + getMaxTokens(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Temperature: " + getColor(sysMainColor) + getTemperature(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Model: " + getColor(sysMainColor) + getColor(sysRejectionColor) + "x", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Max Tokens: " + getColor(sysMainColor) + getColor(sysRejectionColor) + "x", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Temperature: " + getColor(sysMainColor) + getColor(sysRejectionColor) + "x", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            message("Error: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    private static void configureTemperature() {
        insertControlChars('n', 1);
        message("["+ getColor(sysMainColor) + "i" + getColor(sysLayoutColor) + "] The temperature of the AI controls the creativity of responses.\n" +
                        alignment(getDefaultTextAlignment()) + "The higher the temperature," +
                        " the more diverse and unpredictable the answers.",
                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        double temperature;
        while (true) {
            out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) +
                    "Enter a temperature [Choose between " + getColor(sysMainColor) + "0.1"
                    + getColor(sysLayoutColor) + " and " + getColor(sysMainColor) + "1.2" + getColor(sysLayoutColor) + "]: ");
            try {
                temperature = scanner.nextDouble();
                scanner.nextLine();

                if (temperature >= 0.1 && temperature <= 1.2) {
                    setTemperature(temperature);
                    insertControlChars('n', 1);
                    message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);

                    message("New temperature is: " + getColor(sysMainColor) + getTemperature()
                                    + getColor(sysLayoutColor) + ".", sysLayoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    break;
                } else {
                    message("Invalid input! Please enter a value between"
                                    + getColor(sysMainColor) + " 0.1 " + getColor(sysLayoutColor) + "and" + getColor(sysMainColor)
                                    + " 1.2" + getColor(sysLayoutColor) + ".",
                            sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                }
            } catch (Exception e) {
                scanner.nextLine();
                message("Error: Invalid input. Please enter a valid number.",
                        sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        }
    }

    private static void configureMaxTokens() {
        insertControlChars('n', 1);

        message("Max tokens define the response length. More tokens allow longer answers.\n" +
                alignment(getDefaultTextAlignment()) + "Choose wisely based on your needs.", sysLayoutColor,
                getDefaultTextAlignment(), getDefaultDelay(), out::println);
        int maxTokens;

        while (true) {
            out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) +
                    "Enter max amount of tokens [Choose a number between " + getColor(sysMainColor)
                    + "1" + getColor(sysLayoutColor) + " and " + getColor(sysMainColor)
                    + "500"  + getColor(sysLayoutColor) + "]: ");

            try {
                maxTokens = scanner.nextInt();
                scanner.nextLine();

                if (maxTokens > 0 && maxTokens <= 500) {
                    setMaxTokens(maxTokens);
                    insertControlChars('n', 1);
                    message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);

                    message("New maximum amount of tokens is: " + getColor(sysMainColor)
                                    + getMaxTokens() + getColor(sysLayoutColor) + ".", sysLayoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    break;
                } else {
                    message("Invalid input! Please enter a number between" + getColor(sysMainColor)
                                    + "1" + getColor(sysLayoutColor) + " and " + getColor(sysMainColor)
                                    + "500"  + getColor(sysLayoutColor) + ".", sysLayoutColor, getDefaultTextAlignment(),
                            getDefaultDelay(), out::println);
                }
            } catch (Exception e) {
                scanner.nextLine();
                message("Error: Invalid input. Please enter a valid number.",
                        sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        }
    }
}