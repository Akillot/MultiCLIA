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
        displayMemorySection();

        modifyMessage('n',1);

        displayColorSection();
        marginBorder();
    }

    //Memory methods
    public static void displayMemorySection(){
        choice("Memory", SettingsPage::displayUsingMemory);
    }

    public static void displayUsingMemory(){
        message("Memory used: " +
                ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                        / (1000 * 1000) + "M"),systemDefaultWhite,58,0, out::print);
    }

    //Color methods
    public static void displayColorSection(){
        choice("Colors", textModification());
    }

    @Contract(pure = true)
    public static @NotNull Runnable textModification() {
        return () -> {
            printColorRange(0, systemDefaultWhite, "");
            modifyMessage('n', 1);
            printColorBlock(16, 231);
            printColorRange(232, 255, "");
        };
    }

    @Contract(pure = true)
    public static void printColorRange(int start, int end, String title) {
        out.println(BOLD + title + RESET);
        for (int i = start; i <= end; i++) {
            out.print(getAnsi256BackgroundColor(i) + tableAlignment(4) + " " + i + " " + RESET);
            if ((i - start + 1) % 8 == 0) modifyMessage('n',1);
        }
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
            if(row == 11){
                modifyMessage('n',1);
            }
            modifyMessage('n', 1);
        }
    }

    @Contract(pure = true)
    public static @NotNull String tableAlignment(int width) {
        return " ".repeat(Math.max(0, width));
    }
}
