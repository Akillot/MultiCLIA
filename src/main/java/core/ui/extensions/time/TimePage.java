package core.ui.extensions.time;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static core.logic.CommandManager.*;
import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.TextConfigs.*;
import static java.lang.System.out;

public class TimePage {

    private static final Scanner scanner = new Scanner(System.in);

    public static void displayTimePage() {
        marginBorder(1,2);
        message("Time:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), sysAcceptanceColor, false,
                    getColor(sysLayoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "current time", "/ct" -> displayCurrentTime();
                case "app runtime", "/ar" -> displayAppRuntime();
                case "timer", "/t" -> runTimer();
                case "stopwatch", "/sw" -> runStopwatch();
                case "change time zone", "/ctz" -> displayCustomTimeZone();
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
        message("·  Current Time [" + getColor(sysMainColor)
                + "/ct" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Timer [" + getColor(sysMainColor)
                + "/t" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Stopwatch ["  + getColor(sysMainColor)
                + "/sw" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Change Time Zone [" + getColor(sysMainColor)
                + "/ctz" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Restart [" + getColor(sysMainColor)
                + "/rs" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear terminal [" + getColor(sysMainColor)
                + "/cl" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List [" + getColor(sysMainColor)
                + "/ls" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Quit [" + getColor(sysMainColor)
                + "/q" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    // /ct
    private static void displayCurrentTime() {
        insertControlChars('n',1);
        message("Current Time: " + getColor(sysMainColor)
                + getCurrentTime(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("Current Time Zone: " + getColor(sysMainColor)
                + getCurrentTimeZone(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static @NotNull String getCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return localTime.format(formatter);
    }

    private static void displayAppRuntime() {
        insertControlChars('n',1);
    }

    private static String getCurrentTimeZone() {
        return ZoneId.systemDefault().toString();
    }

    // /t command
    private static volatile boolean isTimerRunning = false;

    private static void runTimer() {
        while (true) {
            insertControlChars('n',1);
            out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor)
                    + "Enter time in seconds (or '" + getColor(sysMainColor)
                    + "exit" + getColor(sysLayoutColor) + "' to quit): ");
            String input = scanner.nextLine();

            if (exitCheck(input)) break;

            try {
                double seconds = Double.parseDouble(input);
                if (seconds < 0) {
                    message("Time cannot be negative.", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    continue;
                }
                if (isTimerRunning) {
                    message("A timer is already running" + getColor(sysLayoutColor) +
                            ". Please wait or stop it.", sysMainColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    continue;
                }
                isTimerRunning = true;
                startAsyncTimer(seconds);

            } catch (NumberFormatException e) {
                out.println(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor)
                        + "Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void startAsyncTimer(double seconds) {
        new Thread(() -> {
            try {
                for (double i = seconds; i >= 0; i--) {
                    if (!isTimerRunning) {
                        message("Timer stopped.", sysMainColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                        return;
                    }
                    out.print("\r" + alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor)
                            + "Time left: " + getColor(sysMainColor)
                            + i + getColor(sysLayoutColor) + " sec");
                    Thread.sleep(1000);
                }
                if (isTimerRunning) {
                    insertControlChars('r', 1);
                    message("Time is up", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Status: "
                            + getColor(sysAcceptanceColor) + " ✓");
                    insertControlChars('n', 1);
                }
                insertControlChars('n',1);
                out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor)
                        + "Enter time in seconds (or '" + getColor(sysMainColor)
                        + "exit" + getColor(sysLayoutColor) + "' to quit): ");
            } catch (InterruptedException e) {
                message("Timer interrupted" + getColor(sysLayoutColor) + ".", sysMainColor,
                        getDefaultTextAlignment(), getDefaultDelay(), out::print);
            } finally {
                isTimerRunning = false;
            }
        }).start();
    }

    // /sw command
    private static void runStopwatch() {
        insertControlChars('n', 1);
        message("Press " + getColor(sysMainColor) + "any key" + getColor(sysLayoutColor)
                        + " to start stopwatch and again to stop:", sysLayoutColor, getDefaultTextAlignment(),
                getDefaultDelay(), out::print);
        scanner.nextLine();
        long startTime = System.currentTimeMillis();
        message("Stopwatch: " + getColor(sysAcceptanceColor) + "started", sysLayoutColor, getDefaultTextAlignment(),
                getDefaultDelay(), out::print);

        scanner.nextLine();
        double elapsedTime = calculateElapsedTime(startTime);
        message("Stopwatch: " + getColor(sysRejectionColor) + "stopped", sysLayoutColor, getDefaultTextAlignment(),
                getDefaultDelay(), out::println);

        printElapsedTime(elapsedTime);
    }

    private static double calculateElapsedTime(long startTime) {
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }

    private static void printElapsedTime(double elapsedTime) {
        message("Elapsed Time: " + getColor(sysMainColor) + elapsedTime
                + getColor(sysLayoutColor) + " sec.", sysLayoutColor, getDefaultTextAlignment(),
                getDefaultDelay(), out::println);
    }

    // /ctz command
    private static void displayCustomTimeZone() {
        insertControlChars('n', 1);

        while (true) {
            out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Enter a time zone [e.g., "
                    + getColor(219) + "Europe" + getColor(sysLayoutColor)
                    + "/" + getColor(sysMainColor) + "Paris" + getColor(sysLayoutColor) + "]: ");

            String inputZone = scanner.nextLine().trim();

            if (exitCheck(inputZone)) break;

            if (isValidTimeZone(inputZone)) {
                String time = getTimeInZone(inputZone);
                message("Time in " + inputZone + ": " + getColor(sysMainColor) + time
                        + getColor(sysLayoutColor) + ".", sysLayoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::println);
                break;
            } else {
                suggestTimeZones(inputZone);
            }
        }
    }

    private static boolean exitCheck(@NotNull String inputZone) {
        if (inputZone.equalsIgnoreCase("exit")) {
            if (isTimerRunning) {
                isTimerRunning = false;
                insertControlChars('n', 1);
            }
            message("Terminated correctly" + getColor(sysLayoutColor)
                    + "." + getColor(sysMainColor) + "You are in time menu"
                    + getColor(sysLayoutColor) + ".", sysMainColor, getDefaultTextAlignment(),
                    getDefaultDelay(),out::print);

            message("\r   Status: " + getColor(sysAcceptanceColor) + "✓",
                    sysLayoutColor,getDefaultTextAlignment(),
                    getDefaultDelay(),out::println);
            return true;
        }
        return false;
    }

    private static boolean isValidTimeZone(String zoneId) {
        return ZoneId.getAvailableZoneIds().contains(zoneId);
    }

    private static @NotNull String getTimeInZone(String zoneId) {
        ZonedDateTime time = ZonedDateTime.now(ZoneId.of(zoneId));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm 'UTC'XXX");
        return time.format(formatter);
    }

    private static void suggestTimeZones(String inputZone) {
        message("Invalid time zone: " + inputZone, sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        List<String> similarZones = ZoneId.getAvailableZoneIds().stream()
                .filter(zone -> zone.toLowerCase().contains(inputZone.toLowerCase()))
                .sorted()
                .limit(5)
                .toList();

        if (similarZones.isEmpty()) {
            message("No similar time zones found. Please check your input.", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } else {
            message("Did you mean:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            similarZones.forEach(zone -> message("- " + zone, sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println));
        }
    }
}