package core.ui.extensions.asciiartify;

import core.ui.essential.pages.Page;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

import static core.logic.CommandManager.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.appearance.AppearanceConfigs.searchingArrow;
import static core.ui.essential.configs.appearance.TextConfigs.*;
import static java.lang.System.out;

public class AsciiArtifyPage extends Page {
    private static Scanner scanner = new Scanner(System.in);
    private String[][] commands = {
            {"Generate Art", "ga"},
            {"info", "i"},
            {"Restart", "rst"},
            {"Restart clear", "rcl"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    public void displayMenu() {
        marginBorder(1, 2);
        message("Network:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "generate art", "ga" -> generateArt();
                case "restart", "rst" -> {
                    insertControlChars('n',1);
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

    private static void generateArt(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            insertControlChars('n',1);
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter text to convert to ASCII banner, type [quit/q] to exit: ");
            String text = scanner.nextLine().trim();
            if (text.equalsIgnoreCase("quit") || text.equalsIgnoreCase("q")) {
                displayConfirmation(
                        "Enter", "y", "+", "to exit MultiCLIA and",
                        "n", "-", "to stay in",
                        acceptanceColor, rejectionColor, layoutColor, getDefaultTextAlignment()
                );
                String answer = scanner.nextLine().trim();
                if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                    out.println(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Bye");
                    marginBorder(2, 1);

                    break;
                }
            }

            if (text.isEmpty()) out.println("Error: Input text cannot be empty.");
            String banner = generateBanner(text);
            out.println("\nHere is your ASCII banner:\n");
            out.println(banner);
        }
    }

    private static @NotNull String generateBanner(String text) {
        text = text.toUpperCase();
        int height = LettersDictionary.BLOCK_LETTERS.get('A').length;

        int totalWidth = text.chars()
                .mapToObj(c -> (char) c)
                .mapToInt(c -> LettersDictionary.BLOCK_LETTERS.getOrDefault(c,
                        LettersDictionary.BLOCK_LETTERS.get(' '))[0].length()).sum();

        int borderWidth = totalWidth + 2;
        String borderLine = "╔" + "═".repeat(borderWidth) + "╗";
        String emptyLine = "║" + " ".repeat(borderWidth) + "║";

        StringBuilder banner = new StringBuilder();

        banner.append(borderLine).append("\n");
        banner.append(emptyLine).append("\n");

        for (int line = 0; line < height; line++) {
            banner.append("║ ");
            for (char c : text.toCharArray()) {
                String[] letterLines = LettersDictionary.BLOCK_LETTERS
                        .getOrDefault(c, LettersDictionary.BLOCK_LETTERS.get(' '));
                banner.append(letterLines[line]);
            }
            banner.append(" ║\n");
        }

        banner.append(emptyLine).append("\n");
        banner.append("╚").append("═".repeat(borderWidth)).append("╝");

        return banner.toString();
    }
}
