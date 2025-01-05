package core.pages;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.terminateSection;
import static core.logic.TextConfigs.*;
import static core.logic.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class TimePage {

    static Scanner scanner = new Scanner(System.in);

    public static void displayTimePage() {
        modifyMessage('n', 1);
        displayTimeMenu();
        marginBorder(2, 1);
    }

    public static void displayTimeMenu() {
        while (true) {
            modifyMessage('n', 1);
            message("Time Page Menu:", systemMainColor, 58, 0, out::print);
            modifyMessage('n', 1);
            message("1. Current Time [/ct]", systemLayoutColor, 58, 0, out::print);
            message("2. International Times [/it]", systemLayoutColor, 58, 0, out::print);
            message("3. Timer [/t]", systemLayoutColor, 58, 0, out::print);
            message("4. Stopwatch [/sw]", systemLayoutColor, 58, 0, out::print);
            message("5. Change Time Zone [/ctz]", systemLayoutColor, 58, 0, out::print);
            message("6. Log Current Time [/lct]", systemLayoutColor, 58, 0, out::print);
            message("7. " + getAnsi256Color(systemRejectionColor) + "Exit"
                    + getAnsi256Color(systemLayoutColor) +  " [/e]", systemLayoutColor, 58, 0, out::print);

            String choice = scanner.nextLine().toLowerCase();
            switch (choice) {
                case "1" -> displayTime();
                case "/ct" -> displayTime();
                case "current time" -> displayTime();

                case "2" -> displayInternationalTimes();
                case "/it" -> displayInternationalTimes();
                case "international times" -> displayInternationalTimes();

                case "3" -> startTimer(10);
                case "/t" -> startTimer(10);
                case "timer" -> startTimer(10);

                case "4" -> startStopwatch();
                case "/sw" -> startStopwatch();
                case "stopwatch" -> startStopwatch();

                case "5" -> displayCustomTimeZone();
                case "/ctz" -> displayCustomTimeZone();
                case "change time zone" -> displayCustomTimeZone();

                case "6" -> logCurrentTime();
                case "/lct" -> logCurrentTime();
                case "log current time" -> logCurrentTime();

                case "7" -> terminateSection(systemMainColor, systemAcceptanceColor, systemLayoutColor);
                default -> message("Invalid choice! Try again.", systemRejectionColor, 58, 0, out::print);
            }
        }
    }

    private static void displayTime() {
        modifyMessage('n', 1);
        message("Current Time: " + getAnsi256Color(systemMainColor) + getCurrentTime(), systemLayoutColor, 58, 0, out::print);
        message("Current Time Zone: " + getAnsi256Color(systemMainColor) + getCurrentTimeZone(), systemLayoutColor, 58, 0, out::print);
    }

    private static @NotNull String getCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return localTime.format(formatter);
    }

    private static String getCurrentTimeZone() {
        return ZoneId.systemDefault().toString();
    }

    private static void displayInternationalTimes() {
        String[] zones = {"UTC", "America/New_York", "Europe/London", "Asia/Tokyo", "Australia/Sydney"};
        modifyMessage('n', 1);
        message("International Times:", systemLayoutColor, 58, 0, out::print);
        for (String zone : zones) {
            String time = getTimeInZone(zone);
            message(zone + ": " + getAnsi256Color(systemMainColor) + time, systemLayoutColor, 58, 0, out::print);
        }
    }

    private static @NotNull String getTimeInZone(String zoneId) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of(zoneId));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return time.format(formatter);
    }

    private static void startTimer(int seconds) {
        modifyMessage('n', 1);
        message("Timer started for " + seconds + " seconds.", systemAcceptanceColor, 58, 0, out::print);

        try {
            for (int i = seconds; i >= 0; i--) {
                out.print("\rTime left: " + i + " seconds");
                Thread.sleep(1000);
            }
            out.print("\r");
            message("Time is up!", systemRejectionColor, 58, 0, out::print);
        } catch (InterruptedException e) {
            message("Timer interrupted!", systemRejectionColor, 58, 0, out::print);
        }
    }

    private static void startStopwatch() {
        modifyMessage('n', 1);
        message("Stopwatch started. Press 'Enter' to stop.", systemAcceptanceColor, 58, 0, out::print);

        long startTime = System.currentTimeMillis();
        try {
            System.in.read();
            long endTime = System.currentTimeMillis();
            long elapsed = (endTime - startTime) / 1000;
            message("Elapsed Time: " + elapsed + " seconds.", systemMainColor, 58, 0, out::print);
        } catch (Exception e) {
            message("Error reading input!", systemRejectionColor, 58, 0, out::print);
        }
    }

    private static void displayTimeFormats() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter[] formats = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
                DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")
        };

        modifyMessage('n', 1);
        message("Available Date and Time Formats:", systemLayoutColor, 58, 0, out::print);
        for (DateTimeFormatter format : formats) {
            message(now.format(format), systemMainColor, 58, 0, out::print);
        }
    }

    private static void displayCustomTimeZone() {
        modifyMessage('n', 1);
        message("Enter a time zone [e.g., Europe/Paris]:", systemLayoutColor, 58, 0, out::print);

        try {
            String inputZone = new java.util.Scanner(System.in).nextLine();
            String time = getTimeInZone(inputZone);
            message("Time in " + inputZone + ": " + getAnsi256Color(systemMainColor) + time, systemLayoutColor, 58, 0, out::print);
        } catch (Exception e) {
            message("Invalid time zone!", systemRejectionColor, 58, 0, out::print);
        }
    }

    private static void logCurrentTime() {
        String logMessage = "Log Time: " + getCurrentTime() + " Time Zone: " + getCurrentTimeZone();
        try (java.io.FileWriter writer = new java.io.FileWriter("time_log.txt", true)) {
            writer.write(logMessage + System.lineSeparator());
            message("Time logged successfully!", systemAcceptanceColor, 58, 0, out::print);
        } catch (Exception e) {
            message("Failed to log time: " + e.getMessage(), systemRejectionColor, 58, 0, out::print);
        }
    }

}