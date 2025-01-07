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
        marginBorder(1,2);
        message("Time Page Menu:", systemLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            modifyMessage('n',1);
            slowMotionText(0, 56, false,
                    getAnsi256Color(systemLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "current time", "/ct" -> displayTime();
                case "timer", "/t" -> displayTimer();
                case "stopwatch", "/sw" -> displayStopwatch();
                case "change time zone", "/ctz" -> displayCustomTimeZone();
                case "custom time format", "/ctf" -> displayCustomDateTimeFormat();
                case "list commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    marginBorder(2,2);
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

    private static void displayListOfCommands(){
        modifyMessage('n',1);
        message("·  Current Time [/" + getAnsi256Color(systemMainColor)
                + "ct" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

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
    }

    private static void displayTime() {
        modifyMessage('n',1);
        message("Current Time: " + getAnsi256Color(systemMainColor)
                + getCurrentTime(), systemLayoutColor, 58, 0, out::print);
        message("Current Time Zone: " + getAnsi256Color(systemMainColor)
                + getCurrentTimeZone(), systemLayoutColor, 58, 0, out::print);
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

    private static void displayTimer() {
        while (true) {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(systemLayoutColor)
                    + "Enter time in seconds (or '" + getAnsi256Color(systemRejectionColor)
                    + "exit" + getAnsi256Color(systemLayoutColor) + "' to quit): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                if (isTimerRunning) {
                    isTimerRunning = false;
                    modifyMessage('n', 1);
                    message("Stopping timer...", systemRejectionColor, 58, 0, out::print);
                }
                modifyMessage('n', 1);
                terminate(systemMainColor, systemAcceptanceColor, systemLayoutColor);
                break;
            }

            try {
                double seconds = Double.parseDouble(input);
                if (seconds < 0) {
                    message("Time cannot be negative.", systemRejectionColor, 58, 0, out::print);
                    continue;
                }
                if (isTimerRunning) {
                    message("A timer is already running. Please wait or stop it.", systemRejectionColor, 58, 0, out::print);
                    continue;
                }
                isTimerRunning = true;
                message("Timer started for " + getAnsi256Color(systemMainColor) + seconds
                        + getAnsi256Color(systemLayoutColor) + " sec.", systemLayoutColor, 58, 0, out::print);
                startAsyncTimer(seconds);

            } catch (NumberFormatException e) {
                message("Invalid input. Please enter a valid number.", systemRejectionColor, 58, 0, out::print);
            }
        }
    }

    private static void startAsyncTimer(double seconds) {
        new Thread(() -> {
            try {
                for (double i = seconds; i >= 0; i--) {
                    if (!isTimerRunning) {
                        message("Timer stopped.", systemRejectionColor, 58, 0, out::print);
                        return;
                    }
                    out.print("\r" + alignment(58) + getAnsi256Color(systemLayoutColor)
                            + "Time left: " + getAnsi256Color(systemMainColor)
                            + i + getAnsi256Color(systemLayoutColor) + " seconds");
                    Thread.sleep(1000);
                }
                if (isTimerRunning) {
                    out.print("\r");
                    message("Time is up!", systemMainColor, 58, 0, out::print);
                }
                modifyMessage('n',1);
                out.print(alignment(58) + getAnsi256Color(systemLayoutColor)
                        + "Enter time in seconds (or '" + getAnsi256Color(systemRejectionColor)
                        + "exit" + getAnsi256Color(systemLayoutColor) + "' to quit): ");
            } catch (InterruptedException e) {
                message("Timer interrupted!", systemRejectionColor, 58, 0, out::print);
            } finally {
                isTimerRunning = false;
            }
        }).start();
    }

    // /sw command
    private static void displayStopwatch() {
        modifyMessage('n',1);
        message("Press 'Enter' to start stopwatch and '" + getAnsi256Color(systemMainColor)
                + "Enter" + getAnsi256Color(systemLayoutColor) + "' again to stop.",
                systemLayoutColor, 58, 0, out::print);
        scanner.nextLine(); // Wait for enter
        long startTime = System.currentTimeMillis();

        message("Stopwatch started", systemMainColor, 58, 0, out::print);
        scanner.nextLine(); // Wait for enter again
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        message("Elapsed Time: " + elapsedTime + " seconds.", systemAcceptanceColor, 58, 0, out::print);
    }

    // /ctz command
    private static void displayCustomTimeZone() {
        modifyMessage('n',1);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter a time zone [e.g., "
                + getAnsi256Color(171) + "Europe" + getAnsi256Color(systemLayoutColor)
                + "/" + getAnsi256Color(135) + "Paris" + getAnsi256Color(systemLayoutColor) + "]: ");
        String inputZone = scanner.nextLine();
        try {
            String time = getTimeInZone(inputZone);
            message("Time in " + inputZone + ": " + getAnsi256Color(systemMainColor) + time, systemLayoutColor, 58, 0, out::print);
        } catch (Exception e) {
            message(getAnsi256Color(systemRejectionColor) + "Invalid time zone"
                    + getAnsi256Color(systemLayoutColor) + ". Please try again.",
                    systemLayoutColor, 58, 0, out::print);
        }
    }

    private static @NotNull String getTimeInZone(String zoneId) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of(zoneId));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return time.format(formatter);
    }

    // /ctf command
    private static void displayCustomDateTimeFormat() {
        modifyMessage('n',1);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor)
                + "Enter your custom format [e.g., dd/MM/yyyy HH:mm]: ");
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