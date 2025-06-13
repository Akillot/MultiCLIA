package tools.ai;

import core.Page;

import java.util.Random;
import java.util.Scanner;

import static core.CommandManager.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.*;
import static core.ui.configs.TextConfigs.*;
import static tools.ai.ChatGPTClient.*;
import static java.lang.System.out;

public class AiPage extends Page {

    private String[][] commands = {
            {"Ask", "a"},
            {"Info", "i"},
            {"Restart", "r"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    private static final String[] AI_ASCII_LOGO = {
            "╔═══════════════╗",
            "║               ║",
            "║   █████╗ ██╗  ║",
            "║  ██╔══██╗██║  ║",
            "║  ███████║██║  ║",
            "║  ██╔══██║██║  ║",
            "║  ██║  ██║██║  ║",
            "║  ╚═╝  ╚═╝╚═╝  ║",
            "║               ║",
            "╚═══════════════╝"
    };

    public void displayMenu() {
        marginBorder(1, 0);
        clearTerminal();
        insertControlChars('n', 2);

        displayLogo(getDefaultTextAlignment(), AI_ASCII_LOGO);
        insertControlChars('n', 1);

        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(),
                    getSearchingLineAlignment(),
                    false,
                    getColor(getLayoutColor()) + getSearchingArrow(),
                    "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ask", "a" -> runChatGpt();
                case "info", "i" -> displayChatGptInfo();
                case "restart", "r" -> clearAndRestartApp();
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

    private void runChatGpt() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                clearTerminal();
                drawEllipse();
                insertControlChars('n', 2);

                out.print(alignment(getDefaultTextAlignment())
                        + getColor(getLayoutColor()) + "Enter prompt [or "
                        + getColor(getMainColor()) + "Enter" + getColor(getLayoutColor()) + " to quit]: ");

                String userMessage = scanner.nextLine();

                if (userMessage.isEmpty()) {
                    displayMenu();
                    break;
                }

                String response = sendMessage(userMessage);

                insertControlChars('n', 2);
                slowMotionText(20,
                        getDefaultTextAlignment(),
                        false,
                        getColor(getLayoutColor()) + response, "");

                insertControlChars('n', 1);
                scanner.nextLine();
                clearTerminal();
            }
        } catch (Exception e) {
            insertControlChars('n', 2);
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
            insertControlChars('n', 2);
        }
    }

    private static void drawEllipse() {
        double centerY = 10 / 2.0;
        double centerX = 10 / 2.0;
        double radiusY = 10 / 2.0 - 0.5;
        double radiusX = 10 / 2.0 - 0.5;

        Random rand = new Random();

        int[] colorStartNumber = new int[]{17,53,89,125,161,197};
        int pickedColorIndex = rand.nextInt(colorStartNumber.length);
        int pickedColor = colorStartNumber[pickedColorIndex];

        for (int i = 0; i < 10; i++) {
            out.print(alignment(getDefaultTextAlignment() + 4));
            for (int j = 0; j < 10; j++) {
                double normY = (i - centerY) / radiusY;
                double normX = (j - centerX) / radiusX;
                if (normX * normX + normY * normY <= 1.0) {
                    out.print(getRangedRandomBackColor(pickedColor, pickedColor + 35) + "  " + RESET);
                } else out.print("  ");
            }
            insertControlChars('n', 1);
        }
    }
}