package tools.asciiartify;

import org.jetbrains.annotations.NotNull;
import java.util.Scanner;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class AsciiArtGenPage {
    private static Scanner scanner = new Scanner(System.in);

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

    public void displayMenu() {
        marginBorder(1, 2);
        message("ASCIIArtify:",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        generateArt();
    }

    private static void generateArt() {
        Scanner scanner = new Scanner(System.in);
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment())
                + getColor(getLayoutColor()) + "Enter text to convert to ASCII banner: ");
        String text = scanner.nextLine().trim();

        if (text.isEmpty()) out.println(alignment(getDefaultTextAlignment())
                + getColor(getLayoutColor()) +  "Error: Input text cannot be empty.");

        String banner = generateBanner(text);
        insertControlChars('n', 1);

        out.println(alignment(getDefaultTextAlignment())
                + getColor(getLayoutColor()) + "Here is your ASCII banner:");

        insertControlChars('n', 1);
        out.println(banner);
        marginBorder(2, 1);
    }
}
