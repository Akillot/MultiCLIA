package core.pages;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.choice;
import static core.logic.TextConfigs.*;
import static core.logic.TextConfigs.message;
import static java.lang.System.out;

public class SettingsPage {
    public static void displaySettings() {
        modifyMessage('n', 2);
        message("Memory",15,58,0,out::print);
        displayUsingMemory();

        modifyMessage('n', 2);
        out.print(getAnsi256Color(systemDefaultColor) + BOLD + alignment(58) + "ANSI Colors\n" + RESET);
        choice("Text", textModification());
        marginBorder();
    }

    public static void displayUsingMemory(){
        message("Memory used: " +
                ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                        / (1000 * 1000) + "M"),15,58,0, out::print);
    }

    @Contract(pure = true)
    public static @NotNull Runnable textModification() {
        return () -> {
            modifyMessage('n', 2);
            printColorRange(0, 15, " ");

            modifyMessage('n', 1);
            printColorBlock(16, 231);

            modifyMessage('n', 1);
            printColorRange(232, 255, " ");
        };
    }

    @Contract(pure = true)
    public static void printColorRange(int start, int end, String title) {
        out.println(BOLD + title + RESET);
        for (int i = start; i <= end; i++) {
            out.print(getAnsi256BackgroundColor(i) + tableAlignment(4) + " " + i + " " + RESET);
            if ((i - start + 1) % 8 == 0) modifyMessage('n',1);
        }
        modifyMessage('n', 1);
    }

    @Contract(pure = true)
    public static void printColorBlock(int start, int end) {
        int columns = 6;
        int rows = (end - start + 1) / columns;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int colorCode = start + row + col * rows;
                if (colorCode <= end) {
                    out.print(getAnsi256BackgroundColor(colorCode) + tableAlignment(4) + " " + colorCode + " " + RESET);
                }
            }
            modifyMessage('n', 1);
        }
    }

    @Contract(pure = true)
    public static @NotNull String tableAlignment(int width) {
        return " ".repeat(Math.max(0, width));
    }
}
