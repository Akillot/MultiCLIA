package tools.generate_art;

import org.jetbrains.annotations.NotNull;
import java.util.Scanner;

import static core.CommandManager.clearAndRestartApp;
import static core.CommandManager.exitPageFormatting;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.clearTerminal;
import static core.ui.configs.DisplayManager.displayLogo;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class AsciiArtGenPage {
    private static Scanner scanner = new Scanner(System.in);

    private static final String[] ART_ASCII_LOGO = {
            "╔══════════════════════════════════════════════════════════════════════╗",
            "║                                                                      ║",
            "║   █████╗ ███████╗ ██████╗██╗██╗            █████╗ ██████╗ ████████╗  ║",
            "║  ██╔══██╗██╔════╝██╔════╝██║██║           ██╔══██╗██╔══██╗╚══██╔══╝  ║",
            "║  ███████║███████╗██║     ██║██║           ███████║██████╔╝   ██║     ║",
            "║  ██╔══██║╚════██║██║     ██║██║           ██╔══██║██╔══██╗   ██║     ║",
            "║  ██║  ██║███████║╚██████╗██║██║           ██║  ██║██║  ██║   ██║     ║",
            "║  ╚═╝  ╚═╝╚══════╝ ╚═════╝╚═╝╚═╝           ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝     ║",
            "║                                                                      ║",
            "╚══════════════════════════════════════════════════════════════════════╝"
    };

    public void displayMenu() {
        marginBorder(1, 2);
        clearTerminal();
        displayLogo(getDefaultTextAlignment(), ART_ASCII_LOGO);
        insertControlChars('n',1);
        generateArt();
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

    private static void generateArt() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            insertControlChars('n', 1);
            out.print(alignment(getDefaultTextAlignment())
                    + getColor(getLayoutColor()) + "Enter text to convert to ASCII banner or click ["
                    + getColor(getMainColor()) + "Enter" + getColor(getLayoutColor()) + "] to quit: ");
            String text = scanner.nextLine().trim();
            String banner = generateBanner(text);

            if(text.isEmpty()){
                exitPageFormatting();
                clearAndRestartApp();
                return;
            }

            insertControlChars('n', 1);
            out.println(banner);
        }
    }
}
