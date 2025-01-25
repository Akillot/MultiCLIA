package core.pages;

import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.ui.DisplayManager.clearTerminal;
import static core.ui.DisplayManager.scanner;
import static core.configs.TextConfigs.*;
import static java.lang.System.out;

public class SettingsPage {

    //Java logo
    private static String[] javaAsciiLogo = {
            getColorText("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡇⠀⠀⠀⠀⠀⠀⠀",196),
            getColorText("⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣾⠟⠀⣀⣠⠄⠀⠀⠀⠀",196),
            getColorText("⠀⠀⠀⠀⠀⠀⢠⣶⣿⠟⠁⢠⣾⠋⠁⠀⠀⠀⠀⠀",196),
            getColorText("⠀⠀⠀⠀⠀⠀⠹⣿⡇⠀⠀⠸⣿⡄⠀⠀⠀⠀⠀⠀",196),
            getColorText("⠀⠀⠀⠀⠀⠀⠀⠙⠷⡀⠀⠀⢹⠗⠀⠀⠀⠀⠀⠀",196),
            getColorText("⠀⠀⢀⣤⣴⡖⠒⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠒⢶⣄",21),
            getColorText("⠀⠀⠈⠙⢛⣻⠿⠿⠿⠟⠛⠛⠛⠋⠉⠀⠀⠀⣸⡿",21),
            getColorText("⠀⠀⠀⠀⠛⠿⣷⣶⣶⣶⣶⣾⠿⠗⠂⠀⢀⠴⠛⠁",21),
            getColorText("⠀⠀⠀⠀⠀⢰⣿⣦⣤⣤⣤⣴⣶⣶⠄⠀⠀⠀⠀⠀",21),
            getColorText("⣀⣤⡤⠄⠀⠀⠈⠉⠉⠉⠉⠉⠀⠀⠀⠀⢀⡀⠀⠀",21),
            getColorText("⠻⣿⣦⣄⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣠⣴⠾⠃⠀⢀",21),
            getColorText("⠀⠀⠈⠉⠛⠛⠛⠛⠛⠛⠛⠛⠋⠉⠁⠀⣀⣤⡶⠋",21),
            getColorText("⠀⠀⠀⠀⠐⠒⠀⠠⠤⠤⠤⠶⠶⠚⠛⠛⠉⠀⠀⠀",21)
    };

    //The main method of displaying the page
    public static void displaySettingsPage() {
        marginBorder(1,2);
        message("Settings:", sysLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false,
                    getColor(sysLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "memory", "/m" -> displayUsingMemory();
                case "cpu", "/c" -> displayCpuLoad();
                case "colors", "/col" -> displayColorTable();
                case "java", "/j" -> displayJavaInfo();
                case "rerun", "/rr" -> mainMenuRerunMargin();
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

    private static void displayListOfCommands(){
        modifyMessage('n',1);
        message("·  Memory [" + getColor(sysMainColor)
                + "/m" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  CPU [" + getColor(sysMainColor)
                + "/c" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Colors ["  + getColor(sysMainColor)
                + "/col" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Java ["  + getColor(sysMainColor)
                + "/j" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands ["  + getColor(sysMainColor)
                + "/lc" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getColor(sysMainColor)
                + "/e" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
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
            String color = i < usedBars ? getColor(160) : getColor(47);
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
                        + getColor(sysMainColor) + String.format("%.2f", cpuLoad) + "%",
                sysLayoutColor, 58, 0, out::print);
        message("Process CPU Load: "
                        + getColor(sysMainColor) + String.format("%.2f", processCpuLoad) + "%",
                sysLayoutColor, 58, 0, out::println);
    }

    private static void displayCpuInfo() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        message("CPU Cores: " + getColor(sysMainColor) + availableProcessors,
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
            out.print(getColor(sysLayoutColor) + getBackColor(i)
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
                out.print(getColor(sysLayoutColor) + getBackColor(colorCode)
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
            out.println(alignment(58) + getColor(sysLayoutColor)
                    + "· " + getBackColor(color) + "  " + RESET);
        }
    }

    //java info
    @Contract(pure = true)
    private static void displayLogo(String @NotNull [] logo){
        for (String s : logo) {
            out.println(alignment(58) + s);
        }
    }

    private static void displayJavaInfo() {
        modifyMessage('n',1);
        displayLogo(javaAsciiLogo);
        message("━━━━━━━━━━━━━━━━━━━━━━",sysLayoutColor, 58, 0, out::println);

        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        String javaHome = System.getProperty("java.home");
        String jvmName = System.getProperty("java.vm.name");
        String jvmVersion = System.getProperty("java.vm.version");

        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        int processors = runtime.availableProcessors();

        message("Java Version: " + getColor(sysMainColor) + javaVersion,
                sysLayoutColor, 58, 0, out::println);

        message("Java Vendor: " + getColor(sysMainColor) + javaVendor,
                sysLayoutColor, 58, 0, out::print);

        message("Java Home: " + getColor(sysMainColor) + javaHome,
                sysLayoutColor, 58, 0, out::println);

        message("JVM Name: " + getColor(sysMainColor) + jvmName,
                sysLayoutColor, 58, 0, out::print);

        message("JVM Version: " + getColor(sysMainColor) + jvmVersion,
                sysLayoutColor, 58, 0, out::println);

        message("Max Memory (JVM): " + getColor(sysMainColor) + maxMemory + " MB",
                sysLayoutColor, 58, 0, out::print);

        message("Total Memory (JVM): " + getColor(sysMainColor) + totalMemory + " MB",
                sysLayoutColor, 58, 0, out::print);

        modifyMessage('n',1);
    }
}