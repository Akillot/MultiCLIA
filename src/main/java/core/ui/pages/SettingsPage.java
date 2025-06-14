package core.ui.pages;

import com.sun.management.OperatingSystemMXBean;
import core.Page;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;
import java.util.Objects;

import static core.CommandManager.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.*;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class SettingsPage extends Page {

    private static final String API_KEY;

    static {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("OPEN_WEATHER_API_KEY");
    }

    private final String[][] commands = {
            {"Memory", "m"},
            {"CPU", "c"},
            {"Color table", "coltab"},
            {"Design info", "di"},
            {"Java", "j"},
            {"API", "a"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    private static final String[] SETTINGS_ASCII_LOGO = {
            "╔════════════════════════════════════════════════════════════════════╗",
            "║                                                                    ║",
            "║  ███████╗███████╗████████╗████████╗██╗███╗   ██╗ ██████╗ ███████╗  ║",
            "║  ██╔════╝██╔════╝╚══██╔══╝╚══██╔══╝██║████╗  ██║██╔════╝ ██╔════╝  ║",
            "║  ███████╗█████╗     ██║      ██║   ██║██╔██╗ ██║██║  ███╗███████╗  ║",
            "║  ╚════██║██╔══╝     ██║      ██║   ██║██║╚██╗██║██║   ██║╚════██║  ║",
            "║  ███████║███████╗   ██║      ██║   ██║██║ ╚████║╚██████╔╝███████║  ║",
            "║  ╚══════╝╚══════╝   ╚═╝      ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝  ║",
            "║                                                                    ║",
            "╚════════════════════════════════════════════════════════════════════╝"
    };

    private final static String[] JAVA_ASCII_LOGO = {
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
        clearTerminal();
        displayLogo(getDefaultTextAlignment(), SETTINGS_ASCII_LOGO);
        insertControlChars('n',1);

        displayListOfCommands(commands);

        while (true) {
            slowMotionText(0, getSearchingLineAlignment(), false,
                    getColor(getLayoutColor()) + getSearchingArrow(), "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "memory", "m" -> displayMemoryInfo();
                case "cpu", "c" -> displayCpuLoad();
                case "color table", "coltab" -> displayColorTable();
                case "design info", "di" -> displayDesignInfo();
                case "java", "j" -> displayJavaInfo();
                case "api", "a" -> {
                    insertControlChars('n',1);
                    displayApiSettings();
                    insertControlChars('n',1);
                }
                case "clear", "cl" -> clearTerminal();
                case "help", "h" -> displayListOfCommands(commands);
                case "quit", "q", "exit", "e" -> {
                    exitPageFormatting();
                    clearAndRestartApp();
                    return;

                }
                default -> out.print("");
            }
        }
    }

    private static boolean isApiKeyValid(String apiKey) {
        return apiKey != null && !apiKey.isBlank();
    }

    private void displayApiSettings() {
        Dotenv dotenv = Dotenv.load();

        java.util.Map<String, String> keys = java.util.Map.of(
                "OPEN_WEATHER_API_KEY", Objects.requireNonNull(dotenv.get("OPEN_WEATHER_API_KEY")),
                "DEEPL_API_KEY", Objects.requireNonNull(dotenv.get("DEEPL_API_KEY")),
                "OPENAI_API_KEY", Objects.requireNonNull(dotenv.get("OPENAI_API_KEY"))
        );

        keys.forEach((name, value) -> {
            if (isApiKeyValid(value)) {
                out.println(alignment(getDefaultTextAlignment())
                        + getColor(getLayoutColor()) + name + ": "
                        + getColor(getMainColor()) + value);
            } else {
                out.println(alignment(getDefaultTextAlignment())
                        + getColor(getLayoutColor()) + name + " is missing or invalid.");
            }
        });

        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Do you want to change an API key? ["
                + getColor(getAcceptanceColor()) + "yes" + getColor(getLayoutColor()) + "|" + getColor(getRejectionColor())
                + "no" + getColor(getLayoutColor()) + "]: ");

        String input = scanner.nextLine().trim().toLowerCase();

        if (input.equals("yes") || input.equals("y")) {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                            + "Enter key name [OPEN_WEATHER_API_KEY | DEEPL_API_KEY | OPENAI_API_KEY]: ");

            String keyName = scanner.nextLine().trim();

            if (!keys.containsKey(keyName)) {
                insertControlChars('n', 1);
                message("Unknown key name" + getColor(getLayoutColor()) + ". Update cancelled.",
                        getRejectionColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);
                return;
            }

            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter new API key: ");
            String newKey = scanner.nextLine().trim();

            if (isApiKeyValid(newKey)) {
                replaceApiKey(keyName, newKey);
                insertControlChars('n', 1);
                message("API key updated" + getColor(getLayoutColor()) + ". Please restart the app to apply changes.",
                        getAcceptanceColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);
            } else {
                insertControlChars('n', 1);
                message("Invalid API key" + getColor(getLayoutColor()) + ". Update cancelled.",
                        getRejectionColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);
            }
        }
    }

    private static void replaceApiKey(String keyName, String apiKey) {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(".env");
            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(keyName + "=")) {
                    lines.set(i, keyName + "=" + apiKey);
                    found = true;
                    break;
                }
            }
            if (!found) lines.add(keyName + "=" + apiKey);

            java.nio.file.Files.write(path, lines);
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error" + getColor(getLayoutColor()) + " while saving API key: " + e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        }
    }



    @Override
    protected void displayListOfCommands(String[][] commands) {
        super.displayListOfCommands(commands);
    }

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
        message("System Memory Info:",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        message("JVM Memory Statistics",
                getMainColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        message("Used JVM Memory: " + formatMemory(usedJvmMemory),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("Free JVM Memory: " + formatMemory(freeJvmMemory),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("Total JVM Memory: " + formatMemory(totalJvmMemory),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        insertControlChars('n', 1);

        message("ROM Info",
                getMainColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        executeTerminalCommand("df -h");
    }

    @Contract(pure = true)
    private static @NotNull String formatMemory(long memoryInBytes) {
        return String.format("%.2f Gi", memoryInBytes / (1024.0 * 1024 * 1024));
    }

    private static void displayCpuLoad() {
        com.sun.management.OperatingSystemMXBean osBean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getCpuLoad() * 100;
        double processCpuLoad = osBean.getProcessCpuLoad() * 100;

        insertControlChars('n',1);
        message("System CPU Statistics:", getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        displayCpuInfo();
        message("System CPU Load: "
                        + getColor(getMainColor()) + String.format("%.2f", cpuLoad) + "%",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("Process CPU Load: "
                        + getColor(getMainColor()) + String.format("%.2f", processCpuLoad) + "%",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);
    }

    private static void displayCpuInfo() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        message("CPU Cores: " + getColor(getMainColor()) + availableProcessors,
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);
    }

    private static void displayJavaInfo() {
        insertControlChars('n',1);
        displayLogo(getDefaultTextAlignment(), JAVA_ASCII_LOGO);
        insertControlChars('n',1);

        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        String javaHome = System.getProperty("java.home");
        String jvmName = System.getProperty("java.vm.name");
        String jvmVersion = System.getProperty("java.vm.version");

        message("Java Version: " + getColor(getMainColor()) + javaVersion,
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        message("Java Vendor: " + getColor(getMainColor()) + javaVendor,
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("Java Home: " + getColor(getMainColor()) + javaHome,
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        message("JVM Name: " + getColor(getMainColor()) + jvmName,
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("JVM Version: " + getColor(getMainColor()) + jvmVersion,
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);
    }

    private static void displayDesignInfo(){
        insertControlChars('n',1);
        message("·  Main color code: " + getColor(getMainColor()) + getMainColor(),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("·  Layout color code: " + getLayoutColor(),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("·  Acceptance color code: " + getColor(getAcceptanceColor()) + getAcceptanceColor(),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("·  Rejection color code: " + getColor(getRejectionColor()) + getRejectionColor(),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        message("·  Searching arrow: " + getSearchingArrow(),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("·  Delay: " + getDefaultDelay(),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        message("·  Default logo alignment: " + getDefaultLogoAlignment(),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("·  Default text alignment: " + getDefaultTextAlignment(),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("·  Searching line alignment: " + getSearchingLineAlignment(),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);
    }

    private static void modifyDesign(){
        insertControlChars('n',1);
        // In progress
    }
}