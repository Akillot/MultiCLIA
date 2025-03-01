package core.ui.essential.pages;

import com.sun.management.OperatingSystemMXBean;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;

import static core.logic.CommandManager.*;
import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.ui.essential.configs.TextConfigs.*;
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

    public static void displaySettingsPage() {
        marginBorder(1,2);
        message("Settings:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, getSearchingLineAlignment(), false,
                    getColor(sysLayoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "memory", "/m" -> displayMemoryInfo();
                case "cpu", "/c" -> displayCpuLoad();
                case "colors", "/col" -> displayColorTable();
                case "java", "/j" -> displayJavaInfo();
                case "restart", "/rs" -> {
                    insertControlChars('n',1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list", "/ls" -> displayListOfCommands();
                case "quit", "/q" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands(){
        insertControlChars('n',1);
        message("·  Memory [" + getColor(sysMainColor)
                + "/m" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  CPU [" + getColor(sysMainColor)
                + "/c" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Colors ["  + getColor(sysMainColor)
                + "/col" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Java ["  + getColor(sysMainColor)
                + "/j" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Restart [" + getColor(sysMainColor)
                + "/rs" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear terminal [" + getColor(sysMainColor)
                + "/cl" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List [" + getColor(sysMainColor)
                + "/ls" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Quit [" + getColor(sysMainColor)
                + "/q" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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

        insertControlChars('n', 1);
        message("System Memory Info:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        // Display JVM memory stats
        message("JVM Memory Statistics", sysMainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        message("Used JVM Memory: " + formatMemory(usedJvmMemory), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("Free JVM Memory: " + formatMemory(freeJvmMemory), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("Total JVM Memory: " + formatMemory(totalJvmMemory), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        // Display ROM stats
        insertControlChars('n', 1);
        message("ROM Info", sysMainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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
        message("System CPU Statistics:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        displayCpuInfo();
        message("System CPU Load: "
                        + getColor(sysMainColor) + String.format("%.2f", cpuLoad) + "%",
                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("Process CPU Load: "
                        + getColor(sysMainColor) + String.format("%.2f", processCpuLoad) + "%",
                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void displayCpuInfo() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        message("CPU Cores: " + getColor(sysMainColor) + availableProcessors,
                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
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
        message("━━━━━━━━━━━━━━━━━━━━━━",sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        String javaHome = System.getProperty("java.home");
        String jvmName = System.getProperty("java.vm.name");
        String jvmVersion = System.getProperty("java.vm.version");

        message("Java Version: " + getColor(sysMainColor) + javaVersion,
                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        message("Java Vendor: " + getColor(sysMainColor) + javaVendor,
                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("Java Home: " + getColor(sysMainColor) + javaHome,
                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        message("JVM Name: " + getColor(sysMainColor) + jvmName,
                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("JVM Version: " + getColor(sysMainColor) + jvmVersion,
                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }
}