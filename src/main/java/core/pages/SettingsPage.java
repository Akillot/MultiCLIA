package core.pages;

import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.ui.DisplayManager.scanner;
import static core.configs.TextConfigs.*;
import static java.lang.System.out;

public class SettingsPage {

    //The main method of displaying the page
    public static void displaySettingsPage() {
        marginBorder(1,2);
        message("Settings:", sysLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false,
                    getAnsi256Color(sysLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "memory", "/m" -> displayUsingMemory();
                case "cpu", "/c" -> displayCpuLoad();
                case "colors", "/cl" -> displayColorTable();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands(){
        modifyMessage('n',1);
        message("·  Memory [" + getAnsi256Color(sysMainColor)
                + "/m" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  CPU [" + getAnsi256Color(sysMainColor)
                + "/c" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Colors ["  + getAnsi256Color(sysMainColor)
                + "/cl" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands ["  + getAnsi256Color(sysMainColor)
                + "/lc" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getAnsi256Color(sysMainColor)
                + "/e" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);
        modifyMessage('n',1);
    }

    //Memory methods
    @SneakyThrows
    private static void displayUsingMemory(){
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        double usagePercentage = (double) usedMemory / maxMemory * 100;

        modifyMessage('n',1);
        message("Memory used: " + (usedMemory / (1000 * 1000)) + "M", sysLayoutColor, 58, 0, out::print);
        message("Free memory: " + (freeMemory / (1000 * 1000)) + "M", sysLayoutColor, 58, 0, out::print);
        message("Total memory: " + (totalMemory / (1000 * 1000)) + "M", sysLayoutColor, 58, 0, out::print);
        message("Max memory: " + (maxMemory / (1000 * 1000)) + "M", sysLayoutColor, 58, 0, out::print);
        message("Memory usage: " + String.format("%.2f", usagePercentage) + "%", sysLayoutColor, 58, 0, out::print);

        if (usagePercentage > 80) {
            modifyMessage('n',1);
            message("Warning: Memory usage exceeds 80%!", sysRejectionColor, 58, 0, out::print);
            modifyMessage('n',1);
        }
        displayMemoryBar();
    }

    private static void displayMemoryBar() {
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        int barLength = 30;
        int usedBars = (int) ((double) usedMemory / maxMemory * barLength);
        StringBuilder bar = new StringBuilder("Memory: [");

        for (int i = 0; i < barLength; i++) {
            String color = i < usedBars ? getAnsi256Color(160) : getAnsi256Color(47);
            bar.append(color).append(i < usedBars ? "■" : "━").append(RESET);
        }
        bar.append("]");

        message(bar.toString(), sysLayoutColor, 58, 0, out::println);
    }

    //CPU methods
    private static void displayCpuLoad() {
        com.sun.management.OperatingSystemMXBean osBean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getCpuLoad() * 100;
        double processCpuLoad = osBean.getProcessCpuLoad() * 100;

        modifyMessage('n',1);
        displayCpuInfo();
        message("System CPU Load: "
                        + getAnsi256Color(sysMainColor) + String.format("%.2f", cpuLoad) + "%",
                sysLayoutColor, 58, 0, out::print);
        message("Process CPU Load: "
                        + getAnsi256Color(sysMainColor) + String.format("%.2f", processCpuLoad) + "%",
                sysLayoutColor, 58, 0, out::println);
    }

    private static void displayCpuInfo() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        message("CPU Cores: " + getAnsi256Color(sysMainColor) + availableProcessors,
                sysLayoutColor, 58, 0, out::print);
    }

    //Color methods
    @Contract(pure = true)
    private static void displayColorTable() {
        marginBorder(1,1);
        printColorRange(0, sysLayoutColor);
        modifyMessage('n', 1);
        printColorBlock();
        printColorRange(232, 255);
        marginBorder(1,1);
    }

    @Contract(pure = true)
    private static void printColorRange(int start, int end) {
        out.println(RESET);
        for (int i = start; i <= end; i++) {
            out.print(getAnsi256Color(sysLayoutColor) + getAnsi256BackgroundColor(i)
                    + tableAlignment() + " " + i + " " + RESET);
            if ((i - start + 1) % 8 == 0) modifyMessage('n',2);
        }
    }

    @Contract(pure = true)
    private static void printColorBlock() {
        int columns = 6;
        int rows = (231 - 16 + 1) / columns;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int colorCode = 16 + row + col * rows;
                out.print(getAnsi256Color(sysLayoutColor) + getAnsi256BackgroundColor(colorCode)
                        + tableAlignment() + " " + colorCode + " " + RESET);
            }
            if(row == 11){
                modifyMessage('n',1);
            }
            modifyMessage('n', 1);
        }
    }

    @Contract(pure = true)
    private static @NotNull String tableAlignment() {
        return " ".repeat(Math.max(0, 4));
    }

    private static void displaySystemColors(){
        modifyMessage('n',1);
        message("Default Colors", sysLayoutColor, 58, 0, out::println);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(sysMainColor);
        colors.add(sysLayoutColor);
        colors.add(sysAcceptanceColor);
        colors.add(sysRejectionColor);

        for (Integer color : colors) {
            out.println(alignment(58) + getAnsi256Color(sysLayoutColor)
                    + "· " + getAnsi256BackgroundColor(color) + "  " + RESET);
        }
    }
}