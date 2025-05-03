package core.ui.essential.pages;

import com.sun.management.OperatingSystemMXBean;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;

import static core.logic.CommandManager.*;
import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.ui.essential.configs.appearance.TextConfigs.*;
import static java.lang.System.out;

public class ConfigPage extends Page {

    private String[][] commands = {
            {"Memory", "m"},
            {"CPU", "c"},
            {"Color table", "coltab"},
            {"Change Color", "ccol"},
            {"Java", "j"},
            {"Restart", "rst"},
            {"Restart clear", "rcl"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    //Java logo
    private static String[] JAVA_ASCII_LOGO = {
            getColorText("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡇⠀⠀⠀⠀⠀⠀⠀",196),
            getColorText("⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣾⠟⠀⣀⣠⠄⠀⠀⠀⠀",196),
            getColorText("⠀⠀⠀⠀⠀⠀⢠⣶⣿⠟⠁⢠⣾⠋⠁⠀⠀⠀⠀⠀",196),
            getColorText("⠀⠀⠀⠀⠀⠀⠹⣿⡇⠀⠀⠸⣿⡄⠀⠀⠀⠀⠀⠀",196),
            getColorText("⠀⠀⠀⠀⠀⠀⠀⠙⠷⡀⠀⠀⢹⠗⠀⠀⠀⠀⠀⠀",196),
            getColorText("⠀⠀⢀⣤⣴⡖⠒⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠒⢶⣄",33),
            getColorText("⠀⠀⠈⠙⢛⣻⠿⠿⠿⠟⠛⠛⠛⠋⠉⠀⠀⠀⣸⡿",33),
            getColorText("⠀⠀⠀⠀⠛⠿⣷⣶⣶⣶⣶⣾⠿⠗⠂⠀⢀⠴⠛⠁",33),
            getColorText("⠀⠀⠀⠀⠀⢰⣿⣦⣤⣤⣤⣴⣶⣶⠄⠀⠀⠀⠀⠀",33),
            getColorText("⣀⣤⡤⠄⠀⠀⠈⠉⠉⠉⠉⠉⠀⠀⠀⠀⢀⡀⠀⠀",33),
            getColorText("⠻⣿⣦⣄⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣠⣴⠾⠃⠀⢀",33),
            getColorText("⠀⠀⠈⠉⠛⠛⠛⠛⠛⠛⠛⠛⠋⠉⠁⠀⣀⣤⡶⠋",33),
            getColorText("⠀⠀⠀⠀⠐⠒⠀⠠⠤⠤⠤⠶⠶⠚⠛⠛⠉⠀⠀⠀",33)
    };

    public void displayMenu() {
        marginBorder(1,2);
        message("Configs:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(0, getSearchingLineAlignment(), false,
                    getColor(getLayoutColor()) + getSearchingArrow(), "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "memory", "m" -> displayMemoryInfo();
                case "cpu", "c" -> displayCpuLoad();
                case "color table", "coltab" -> displayColorTable();
                case "change color", "ccol" -> changeColor();
                case "java", "j" -> displayJavaInfo();
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

    // Display memory information and other
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

        insertControlChars('n', 1);
        message("System Memory Info:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

        // Display JVM memory stats
        message("JVM Memory Statistics", getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        message("Used JVM Memory: " + formatMemory(usedJvmMemory), getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("Free JVM Memory: " + formatMemory(freeJvmMemory), getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("Total JVM Memory: " + formatMemory(totalJvmMemory), getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

        // Display ROM stats
        insertControlChars('n', 1);
        message("ROM Info", getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        executeTerminalCommand("df -h");
    }

    @Contract(pure = true)
    private static @NotNull String formatMemory(long memoryInBytes) {
        return String.format("%.2f Gi", memoryInBytes / (1024.0 * 1024 * 1024));
    }

    //CPU methods
    private static void displayCpuLoad() {
        com.sun.management.OperatingSystemMXBean osBean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getCpuLoad() * 100;
        double processCpuLoad = osBean.getProcessCpuLoad() * 100;

        insertControlChars('n',1);
        message("System CPU Statistics:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        displayCpuInfo();
        message("System CPU Load: "
                        + getColor(getMainColor()) + String.format("%.2f", cpuLoad) + "%",
                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("Process CPU Load: "
                        + getColor(getMainColor()) + String.format("%.2f", processCpuLoad) + "%",
                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void displayCpuInfo() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        message("CPU Cores: " + getColor(getMainColor()) + availableProcessors,
                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
    }

    //java info
    @Contract(pure = true)
    private static void displayLogo(String @NotNull [] logo){
        for (String s : logo) {
            out.println(alignment(getDefaultTextAlignment()) + s);
        }
    }

    private static void displayJavaInfo() {
        insertControlChars('n',1);
        displayLogo(JAVA_ASCII_LOGO);
        //message("━━━━━━━━━━━━━━━━━━━━━━", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        String javaHome = System.getProperty("java.home");
        String jvmName = System.getProperty("java.vm.name");
        String jvmVersion = System.getProperty("java.vm.version");

        message("Java Version: " + getColor(getMainColor()) + javaVersion,
                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

        message("Java Vendor: " + getColor(getMainColor()) + javaVendor,
                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("Java Home: " + getColor(getMainColor()) + javaHome,
                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

        message("JVM Name: " + getColor(getMainColor()) + jvmName,
                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("JVM Version: " + getColor(getMainColor()) + jvmVersion,
                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void changeColor(){
        insertControlChars('n',1);

    }
}