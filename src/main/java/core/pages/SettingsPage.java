package core.pages;

import com.sun.management.OperatingSystemMXBean;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.configs.TextConfigs.*;
import static core.ui.DisplayManager.*;
import static java.lang.System.out;

public class SettingsPage {

    //Java logo
    private static String[] JAVA_ASCII_LOGO = {
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
                case "memory", "/m" -> displayMemoryInfo();
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

    // Display memory information
    public static void displayMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        long totalJvmMemory = runtime.totalMemory();
        long freeJvmMemory = runtime.freeMemory();
        long usedJvmMemory = totalJvmMemory - freeJvmMemory;

        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalOsMemory = osBean.getTotalMemorySize();
        long freeOsMemory = osBean.getFreeMemorySize();
        long usedOsMemory = totalOsMemory - freeOsMemory;

        long maxMemory = runtime.maxMemory() / (1024 * 1024 * 1024);
        double usagePercentage = (double) usedOsMemory / totalOsMemory * 100;

        modifyMessage('n', 1);
        message("System Memory Info:", sysLayoutColor, 58, 0, out::println);

        // Display JVM memory stats
        message("JVM Memory Statistics", sysMainColor, 58, 0, out::println);
        message("Used JVM Memory: " + formatMemory(usedJvmMemory), sysLayoutColor, 58, 0, out::print);
        message("Free JVM Memory: " + formatMemory(freeJvmMemory), sysLayoutColor, 58, 0, out::print);
        message("Total JVM Memory: " + formatMemory(totalJvmMemory), sysLayoutColor, 58, 0, out::println);

        // Display ROM stats
        modifyMessage('n', 1);
        message("ROM Info", sysMainColor, 58, 0, out::println);
        executeTerminalCommand("df -h");
    }

    @Contract(pure = true)
    private static @NotNull String formatMemory(long memoryInBytes) {
        return String.format("%.2f GB", memoryInBytes / (1024.0 * 1024 * 1024));
    }

    //CPU methods
    private static void displayCpuLoad() {
        com.sun.management.OperatingSystemMXBean osBean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getCpuLoad() * 100;
        double processCpuLoad = osBean.getProcessCpuLoad() * 100;

        modifyMessage('n',1);
        message("System CPU Statistics:", sysLayoutColor, 58, 0, out::println);
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
        modifyMessage('n',1);
        message("Color Table:", sysLayoutColor, 58, 0, out::print);
        printColorRange(0, sysLayoutColor);
        printColorBlock();
        printColorRange(232, 255);
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

    //java info
    @Contract(pure = true)
    private static void displayLogo(String @NotNull [] logo){
        for (String s : logo) {
            out.println(alignment(58) + s);
        }
    }

    private static void displayJavaInfo() {
        modifyMessage('n',1);
        displayLogo(JAVA_ASCII_LOGO);
        message("━━━━━━━━━━━━━━━━━━━━━━",sysLayoutColor, 58, 0, out::println);

        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        String javaHome = System.getProperty("java.home");
        String jvmName = System.getProperty("java.vm.name");
        String jvmVersion = System.getProperty("java.vm.version");

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
    }
}