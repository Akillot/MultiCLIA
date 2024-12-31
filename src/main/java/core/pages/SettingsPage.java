package core.pages;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.TextConfigs.*;
import static core.logic.TextConfigs.message;
import static core.pages.StartPage.mainLogoAscii;
import static java.lang.System.out;

public class SettingsPage {
    public static void displaySettings() {
        modifyMessage('n', 2);
        displayMemorySection();

        border();
        modifyMessage('n',2);
        displayColorSection();

        marginBorder();
        modifyMessage('n',1);
        displayLogoSection();

        marginBorder();
    }

    //Memory methods
    private static void displayMemorySection(){
        choice("Memory", SettingsPage::displayUsingMemory, systemFirstColor, systemLayoutColor, systemRejectionColor);
    }

    private static void displayUsingMemory(){
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        double usagePercentage = (double) usedMemory / maxMemory * 100;

        message("Memory used: " + (usedMemory / (1000 * 1000)) + "M", systemLayoutColor, 58, 0, out::print);
        message("Free memory: " + (freeMemory / (1000 * 1000)) + "M", systemLayoutColor, 58, 0, out::print);
        message("Total memory: " + (totalMemory / (1000 * 1000)) + "M", systemLayoutColor, 58, 0, out::print);
        message("Max memory: " + (maxMemory / (1000 * 1000)) + "M", systemLayoutColor, 58, 0, out::print);
        message("Memory usage: " + String.format("%.2f", usagePercentage) + "%", systemLayoutColor, 58, 0, out::print);

        if (usagePercentage > 80) {
            modifyMessage('n',1);
            message("Warning: Memory usage exceeds 80%!", systemRejectionColor, 58, 0, out::print);
            modifyMessage('n',1);
        }
        displayMemoryBar();
        modifyMessage('n',1);
    }

    private static void displayMemoryBar() {
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        int barLength = 30;
        int usedBars = (int) ((double) usedMemory / maxMemory * barLength);
        StringBuilder bar = new StringBuilder("Memory: [");

        for (int i = 0; i < barLength; i++) {
            bar.append(i < usedBars ? "#" : "-");
        }
        bar.append("]");

        message(bar.toString(), systemLayoutColor, 58, 0, out::print);
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
            if ((i - start + 1) % 8 == 0) modifyMessage('n',2);
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
    private static @NotNull String tableAlignment(int width) {
        return " ".repeat(Math.max(0, width));
    }

    /*private static void changeColor(){
        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter new color code: ");
    }
     */

    //Logo
    public static int colorVariationOfLogo = 3;
    private static void displayLogoSection(){
        choice("Logo", SettingsPage::displayAllLogos, systemFirstColor, systemRejectionColor, systemLayoutColor);
    }

    private static void displayAllLogos() {
        try {
            modifyMessage('n', 1);
            for (int i = 0; i < colorVariationOfLogo; i++) {
                switchLogoManualy(mainLogoAscii, i % 3, 48);
                Thread.sleep(500);
                modifyMessage('n', 1);
            }
        } catch (Exception ex) {
            message("Error: " + ex.getMessage(), systemRejectionColor, 58, 0, out::print);
        }
    }
}
