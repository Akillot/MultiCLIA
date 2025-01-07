package core.pages;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.terminate;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class TimePage {

    private static final Scanner scanner = new Scanner(System.in);

    public static void displayTimePage() {

        modifyMessage('n', 2);
        message("Time Page Menu:", systemLayoutColor, 58, 0, out::print);
        displayListOfCommands(1);

        while (true) {
            slowMotionText(50, 56, false,
                    getAnsi256Color(systemLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "current time", "/ct" -> displayTime();
                case "international time", "/it" -> displayInternationalTimes();
                case "timer", "/t" -> displayTimer();
                case "stopwatch", "/sw" -> displayStopwatch();
                case "change time zone", "/ctz" -> displayCustomTimeZone();
                case "custom time format", "/ctf" -> displayCustomDateTimeFormat();
                case "list commands", "/lc" -> displayListOfCommands(2);
                case "exit", "/e" -> {
                    message("\r   Status: " + getAnsi256Color(systemAcceptanceColor) + "✓", systemLayoutColor,58,0,out::print);
                    message("Terminated correctly", systemMainColor,
                            58,0,out::print);
                    marginBorder(2,1);
                    return;
                }

                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands(int distance){
        modifyMessage('n',distance);
        message("·  Current Time [/" + getAnsi256Color(systemMainColor)
                + "ct" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  International Time [/" + getAnsi256Color(systemMainColor)
                + "it" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  Timer [/" + getAnsi256Color(systemMainColor)
                + "t" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  Stopwatch [/"  + getAnsi256Color(systemMainColor)
                + "sw" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  Change Time Zone [/" + getAnsi256Color(systemMainColor)
                + "ctz" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  Custom Time Format [/" + getAnsi256Color(systemMainColor)
                + "ctf" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  List Commands [/" + getAnsi256Color(systemMainColor)
                + "lc" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  Exit [/" + getAnsi256Color(systemRejectionColor)
                + "e" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        modifyMessage('n', 1);
    }

    private static void displayTime() {
        marginBorder(2,2);
        message("Current Time: " + getAnsi256Color(systemMainColor)
                + getCurrentTime(), systemLayoutColor, 58, 0, out::print);
        message("Current Time Zone: " + getAnsi256Color(systemMainColor)
                + getCurrentTimeZone(), systemLayoutColor, 58, 0, out::print);
        modifyMessage('n', 1);
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
        modifyMessage('n', 1);
        String[] zones = {"UTC", "America/New_York", "Europe/London", "Asia/Tokyo", "Australia/Sydney"};
        for (String zone : zones) {
            try {
                String time = getTimeInZone(zone);
                message(zone + ": " + getAnsi256Color(systemMainColor) + time, systemLayoutColor, 58, 0, out::print);
            } catch (Exception e) {
                message("Error fetching time for " + zone, systemRejectionColor, 58, 0, out::print);
            }
        }
    }

    private static @NotNull String getTimeInZone(String zoneId) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of(zoneId));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return time.format(formatter);
    }

    private static void displayTimer() {
        while (true) {
            out.print(alignment(58) + "Enter time in seconds (or 'exit' to quit): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                terminate(systemMainColor, systemAcceptanceColor, systemRejectionColor);
                break;
            }

            try {
                double seconds = Double.parseDouble(input);
                if (seconds < 0) {
                    message("Time cannot be negative!", systemRejectionColor,58, 0, out::print);
                    continue;
                }

                message("Timer started for " + seconds + " seconds.", systemAcceptanceColor, 58, 0, out::print);
                startAsyncTimer(seconds);
            } catch (NumberFormatException e) {
                message("Invalid input! Please enter a valid number.", systemRejectionColor,58, 0, out::print);
            }
        }
    }

    private static void startAsyncTimer(double seconds) {
        new Thread(() -> {
            try {
                for (double i = seconds; i >= 0; i--) {
                    out.print("\r" + alignment(58) + "Time left: " + i + " seconds");
                    Thread.sleep(1000);
                }
                out.print("\r");
                message("Time is up!", systemLayoutColor, 58, 0, out::print);
            } catch (InterruptedException e) {
                message("Timer interrupted!", systemRejectionColor, 58, 0, out::print);
            }
        }).start();
    }

    private static void displayStopwatch() {
        modifyMessage('n',1);
        message("Press 'Enter' to start stopwatch and 'Enter' again to stop.", systemLayoutColor, 58, 0, out::print);
        scanner.nextLine(); // Wait for enter
        long startTime = System.currentTimeMillis();

        message("Stopwatch started...", systemMainColor, 58, 0, out::print);
        scanner.nextLine(); // Wait for enter again
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        message("Elapsed Time: " + elapsedTime + " seconds.", systemAcceptanceColor, 58, 0, out::print);
    }

    private static void displayCustomTimeZone() {
        out.print(alignment(58) + "Enter a time zone [e.g., Europe/Paris]: ");
        String inputZone = scanner.nextLine();
        try {
            String time = getTimeInZone(inputZone);
            message("Time in " + inputZone + ": " + getAnsi256Color(systemMainColor) + time, systemLayoutColor, 58, 0, out::print);
        } catch (Exception e) {
            message("Invalid time zone! Please try again.", systemRejectionColor, 58, 0, out::print);
        }
    }

    private static void displayCustomDateTimeFormat() {
        out.print(alignment(58) + "Enter your custom format [e.g., dd/MM/yyyy HH:mm]: ");
        String format = scanner.nextLine();
        try {
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern(format);
            String formattedTime = LocalDateTime.now().format(customFormatter);
            message("Formatted Time: " + getAnsi256Color(systemMainColor) + formattedTime, systemLayoutColor, 58, 0, out::print);
        } catch (Exception e) {
            message("Invalid format! Please try again.", systemRejectionColor,58, 0, out::print);
        }
    }
}