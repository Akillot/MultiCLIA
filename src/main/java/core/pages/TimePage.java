package core.pages;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.exitPage;
import static core.configs.TextConfigs.*;
import static core.pages.StartPage.mainMenuRerun;
import static core.ui.DisplayManager.clearTerminal;
import static java.lang.System.out;

public class TimePage {

    private static final Scanner scanner = new Scanner(System.in);

    public static void displayTimePage() {
        marginBorder(1,2);
        message("Time:", sysLayoutColor, 58, 0, out::println);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, sysAcceptanceColor, false,
                    getAnsi256Color(sysLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "current time", "/ct" -> displayTime();
                case "timer", "/t" -> runTimer();
                case "stopwatch", "/sw" -> runStopwatch();
                case "change time zone", "/ctz" -> displayCustomTimeZone();
                case "rerun", "/rr" -> mainMenuRerun();
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
        message("·  Current Time [" + getAnsi256Color(sysMainColor)
                + "/ct" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Timer [" + getAnsi256Color(sysMainColor)
                + "/t" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Stopwatch ["  + getAnsi256Color(sysMainColor)
                + "/sw" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Change Time Zone [" + getAnsi256Color(sysMainColor)
                + "/ctz" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getAnsi256Color(sysMainColor)
                + "/lc" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getAnsi256Color(sysMainColor)
                + "/e" + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }

    private static void displayTime() {
        modifyMessage('n',1);
        message("Current Time: " + getAnsi256Color(sysMainColor)
                + getCurrentTime(), sysLayoutColor, 58, 0, out::print);
        message("Current Time Zone: " + getAnsi256Color(sysMainColor)
                + getCurrentTimeZone(), sysLayoutColor, 58, 0, out::println);
    }

    private static @NotNull String getCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return localTime.format(formatter);
    }

    private static String getCurrentTimeZone() {
        return ZoneId.systemDefault().toString();
    }

    // /t command
    private static volatile boolean isTimerRunning = false;

    private static void runTimer() {
        while (true) {
            modifyMessage('n',1);
            out.print(alignment(58) + getAnsi256Color(sysLayoutColor)
                    + "Enter time in seconds (or '" + getAnsi256Color(sysMainColor)
                    + "exit" + getAnsi256Color(sysLayoutColor) + "' to quit): ");
            String input = scanner.nextLine();

            if (exitCheck(input)) break;

            try {
                double seconds = Double.parseDouble(input);
                if (seconds < 0) {
                    message("Time cannot be negative.", sysLayoutColor, 58, 0, out::print);
                    continue;
                }
                if (isTimerRunning) {
                    message("A timer is already running" + getAnsi256Color(sysLayoutColor) +
                            ". Please wait or stop it.", sysMainColor, 58, 0, out::print);
                    continue;
                }
                isTimerRunning = true;
                startAsyncTimer(seconds);

            } catch (NumberFormatException e) {
                out.println(alignment(58) + getAnsi256Color(sysLayoutColor)
                        + "Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void startAsyncTimer(double seconds) {
        new Thread(() -> {
            try {
                for (double i = seconds; i >= 0; i--) {
                    if (!isTimerRunning) {
                        message("Timer stopped.", sysMainColor, 58, 0, out::print);
                        return;
                    }
                    out.print("\r" + alignment(58) + getAnsi256Color(sysLayoutColor)
                            + "Time left: " + getAnsi256Color(sysMainColor)
                            + i + getAnsi256Color(sysLayoutColor) + " sec");
                    Thread.sleep(1000);
                }
                if (isTimerRunning) {
                    modifyMessage('r', 1);
                    message("Time is up", sysLayoutColor, 58, 0, out::print);
                    out.print(alignment(58) + getAnsi256Color(sysLayoutColor) + "Status: "
                            + getAnsi256Color(sysAcceptanceColor) + " ✓");
                    modifyMessage('n', 1);
                }
                modifyMessage('n',1);
                out.print(alignment(58) + getAnsi256Color(sysLayoutColor)
                        + "Enter time in seconds (or '" + getAnsi256Color(sysMainColor)
                        + "exit" + getAnsi256Color(sysLayoutColor) + "' to quit): ");
            } catch (InterruptedException e) {
                message("Timer interrupted" + getAnsi256Color(sysLayoutColor) + ".", sysMainColor,
                        58, 0, out::print);
            } finally {
                isTimerRunning = false;
            }
        }).start();
    }

    // /sw command
    private static void runStopwatch() {
        modifyMessage('n', 1);
        message("Press '" + getAnsi256Color(sysMainColor) + "Anything" + getAnsi256Color(sysLayoutColor)
                        + "' to start stopwatch and again to stop:", sysLayoutColor, 58, 0, out::print);
        scanner.nextLine();
        long startTime = System.currentTimeMillis();
        message("Stopwatch: " + getAnsi256Color(sysAcceptanceColor) + "started", sysLayoutColor, 58, 0, out::print);

        scanner.nextLine();
        double elapsedTime = calculateElapsedTime(startTime);
        message("Stopwatch: " + getAnsi256Color(sysRejectionColor) + "stopped", sysLayoutColor, 58, 0, out::println);

        printElapsedTime(elapsedTime);
    }

    private static double calculateElapsedTime(long startTime) {
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }

    private static void printElapsedTime(double elapsedTime) {
        message("Elapsed Time: " + getAnsi256Color(sysMainColor) + elapsedTime
                + getAnsi256Color(sysLayoutColor) + " sec.", sysLayoutColor, 58, 0, out::println);
    }

    // /ctz command
    private static void displayCustomTimeZone() {
        modifyMessage('n', 1);

        while (true) {
            out.print(alignment(58) + getAnsi256Color(sysLayoutColor) + "Enter a time zone [e.g., "
                    + getAnsi256Color(219) + "Europe" + getAnsi256Color(sysLayoutColor)
                    + "/" + getAnsi256Color(sysMainColor) + "Paris" + getAnsi256Color(sysLayoutColor) + "]: ");

            String inputZone = scanner.nextLine().trim();

            if (exitCheck(inputZone)) break;

            if (isValidTimeZone(inputZone)) {
                String time = getTimeInZone(inputZone);
                message("Time in " + inputZone + ": " + getAnsi256Color(sysMainColor) + time
                        + getAnsi256Color(sysLayoutColor) + ".", sysLayoutColor, 58, 0, out::println);
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
                modifyMessage('n', 1);
            }
            message("Terminated correctly" + getAnsi256Color(sysLayoutColor)
                    + "." + getAnsi256Color(sysMainColor) + "You are in time menu"
                    + getAnsi256Color(sysLayoutColor) + ".", sysMainColor, 58,0,out::print);

            message("\r   Status: " + getAnsi256Color(sysAcceptanceColor) + "✓",
                    sysLayoutColor,58,0,out::println);
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
        message("Invalid time zone: " + inputZone, sysLayoutColor, 58, 0, out::println);

        List<String> similarZones = ZoneId.getAvailableZoneIds().stream()
                .filter(zone -> zone.toLowerCase().contains(inputZone.toLowerCase()))
                .sorted()
                .limit(5)
                .toList();

        if (similarZones.isEmpty()) {
            message("No similar time zones found. Please check your input.", sysLayoutColor, 58, 0, out::println);
        } else {
            message("Did you mean:", sysLayoutColor, 58, 0, out::println);
            similarZones.forEach(zone -> message("- " + zone, sysLayoutColor, 58, 0, out::println));
        }
    }
}