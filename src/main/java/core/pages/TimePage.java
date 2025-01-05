package core.pages;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.choice;
import static core.logic.TextConfigs.*;
import static core.logic.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class TimePage {

    static Scanner scanner = new Scanner(System.in);

    public static void displayTimePage() {
            modifyMessage('n', 2);
        displayConfirmation("Enter","to open and","to skip",
                systemAcceptanceColor, systemRejectionColor, systemLayoutColor);

            modifyMessage('n',1);
            choice("Current Time", TimePage::displayTime, systemMainColor, systemLayoutColor, systemRejectionColor);

            modifyMessage('n', 2);
            choice("International Time", TimePage::displayInternationalTimes, systemMainColor, systemLayoutColor, systemRejectionColor);

            modifyMessage('n', 2);
            choice("Timer", TimePage::startTimer, systemMainColor, systemLayoutColor, systemRejectionColor);

            modifyMessage('n', 2);
            choice("Stopwatch", TimePage::startStopwatch, systemMainColor, systemLayoutColor, systemRejectionColor);

            modifyMessage('n', 2);
            choice("Change Time Zone", TimePage::displayCustomTimeZone, systemMainColor, systemLayoutColor, systemRejectionColor);

            marginBorder(2,1);
    }

    //Current Time
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
        message("International Times:", systemLayoutColor, 58, 0, out::print);
        for (String zone : zones) {
            String time = getTimeInZone(zone);
            modifyMessage('n', 1);
            message(zone + ": " + getAnsi256Color(systemMainColor) + time, systemLayoutColor, 58, 0, out::print);
        }
    }

    private static @NotNull String getTimeInZone(String zoneId) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of(zoneId));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return time.format(formatter);
    }

    //Timer
    private static void startTimer() {
        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(systemMainColor) + "Enter time"
                + getAnsi256Color(systemLayoutColor) + ": ");
        double seconds = scanner.nextDouble();

        if(seconds < 0) {
            message("Error", systemRejectionColor, 58, 0, out::print);
        }

        modifyMessage('n', 1);
        message("Timer started for " + seconds + " seconds.", systemAcceptanceColor, 58, 0, out::print);

        try {
            for (double i = seconds; i >= 0; i--) {
                out.print(alignment(58) + getAnsi256Color(systemMainColor) + "\r   Time left: "
                        + getAnsi256Color(systemLayoutColor) + i + " seconds");
                Thread.sleep(1000);
            }
            out.print("\r");
            message("Time is up!", systemLayoutColor, 58, 0, out::print);
        } catch (InterruptedException e) {
            message("Timer interrupted!", systemRejectionColor, 58, 0, out::print);
        }
    }

    //Stopwatch
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

    //Custom Time Zone
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
}