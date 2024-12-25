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
        modifyMessage('n', 1);
        displayColorSection();
        modifyMessage('n', 1);
        displayTextStylesSection();
        marginBorder();
    }

    //Memory methods
    private static void displayMemorySection(){
        choice("Memory", SettingsPage::displayUsingMemory, systemFirstColor, systemLayoutColor, systemRejectionColor);
    }

    private static void displayUsingMemory(){
        message("Memory used: " +
                ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                        / (1000 * 1000) + "M"), systemLayoutColor,58,0, out::print);
    }

    //Color methods
    private static void displayColorSection(){
        choice("Colors", textModification(), systemFirstColor, systemLayoutColor, systemRejectionColor);
    }

    @Contract(pure = true)
    private static @NotNull Runnable textModification() {
        return () -> {
            printColorRange(0, systemLayoutColor, "");
            modifyMessage('n', 1);
            printColorBlock(16, 231);
            printColorRange(232, 255, "");
        };
    }

    @Contract(pure = true)
    private static void printColorRange(int start, int end, String title) {
        out.println(title + RESET);
        for (int i = start; i <= end; i++) {
            out.print(getAnsi256BackgroundColor(i) + tableAlignment(4) + " " + i + " " + RESET);
            if ((i - start + 1) % 8 == 0) modifyMessage('n',1);
        }
    }

    @Contract(pure = true)
    private static void printColorBlock(int start, int end) {
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

    private static void changeColor(){
        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter new color code: ");
    }

    //Text styles
    private static void displayTextStylesSection(){
        choice("Text styles", SettingsPage::displayTextStyles, systemFirstColor, systemLayoutColor, systemRejectionColor);
    }

    private static void displayTextStyles(){
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + BOLD + "Bold" + RESET);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + UNDERLINE + "Underline" + RESET);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + REVERSE + "Reverse" + RESET);
        modifyMessage('n', 1);
    }
}
