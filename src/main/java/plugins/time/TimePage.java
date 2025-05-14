package plugins.time;

import core.ui.pages.Page;
import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static core.CommandManager.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.clearTerminal;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class TimePage extends Page {

    private static final Scanner scanner = new Scanner(System.in);
    private String[][] commands = {
            {"Current time", "ct"},
            {"Calendar", "c"},
            {"Timer", "t"},
            {"Stopwatch", "sw"},
            {"Time zone", "tz"},
            {"Restart", "rst"},
            {"Restart clear", "rcl"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    public void displayMenu() {
        marginBorder(1,2);
        message("Time:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(), getAcceptanceColor(), false,
                    getColor(getLayoutColor()) + getSearchingArrow(), "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "current time", "ct" -> displayCurrentTime();
                case "calendar", "c" -> displayCalendar();
                case "app runtime", "ar" -> displayAppRuntime();
                case "timer", "t" -> runTimer();
                case "stopwatch", "sw" -> runStopwatch();
                case "time zone", "tz" -> displayCustomTimeZone();
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

    private static void displayCurrentTime() {
        insertControlChars('n',1);
        message("Current Time: " + getColor(getMainColor())
                + getCurrentTime(), getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("Current Time Zone: " + getColor(getMainColor())
                + getCurrentTimeZone(), getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
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

    private static volatile boolean isTimerRunning = false;

    private static void runTimer() {
        while (true) {
            insertControlChars('n',1);
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                    + "Enter time in seconds (or '" + getColor(getMainColor())
                    + "quit" + getColor(getLayoutColor()) + "' to close the timer): ");
            String input = scanner.nextLine();

            if (exitCheck(input)) break;

            try {
                double seconds = Double.parseDouble(input);
                if (seconds < 0) {
                    message("Time cannot be negative.", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    continue;
                }
                if (isTimerRunning) {
                    message("A timer is already running" + getColor(getLayoutColor()) +
                            ". Please wait or stop it.", getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    continue;
                }
                if(input.equalsIgnoreCase("quit")) {
                    isTimerRunning = false;

                }
                isTimerRunning = true;
                startAsyncTimer(seconds);

            } catch (NumberFormatException e) {
                out.println(alignment(getDefaultTextAlignment()) + getColor(getRejectionColor())
                        + "Invalid input" + getColor(getLayoutColor()) + ". Please enter a valid number.");
            }
        }
    }

    private static void startAsyncTimer(double seconds) {
        new Thread(() -> {
            try {
                for (double i = seconds; i >= 0; i--) {
                    if (!isTimerRunning) {
                        message("Timer stopped.", getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                        return;
                    }
                    out.print("\r" + alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                            + "Time left: " + getColor(getMainColor())
                            + i + getColor(getLayoutColor()) + " sec");
                    Thread.sleep(1000);
                }
                if (isTimerRunning) {
                    insertControlChars('r', 1);
                    message("Time is up", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Status: "
                            + getColor(getAcceptanceColor()) + " ✓");
                    insertControlChars('n', 1);
                }
                insertControlChars('n',1);
                out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                        + "Enter time in seconds (or '" + getColor(getMainColor())
                        + "q" + getColor(getLayoutColor()) + "' to close): ");
            } catch (InterruptedException e) {
                message("Timer interrupted" + getColor(getLayoutColor()) + ".", getRejectionColor(),
                        getDefaultTextAlignment(), getDefaultDelay(), out::print);
            } finally {
                isTimerRunning = false;
            }
        }).start();
    }

    private static void runStopwatch() {
        insertControlChars('n', 1);
        message("Press " + getColor(getMainColor()) + "'Enter'" + getColor(getLayoutColor())
                        + " to start stopwatch and again to stop:", getLayoutColor(), getDefaultTextAlignment(),
                getDefaultDelay(), out::print);
        scanner.nextLine();
        long startTime = System.currentTimeMillis();
        message("Stopwatch: " + getColor(getAcceptanceColor()) + "started", getLayoutColor(), getDefaultTextAlignment(),
                getDefaultDelay(), out::print);

        scanner.nextLine();
        double elapsedTime = calculateElapsedTime(startTime);
        message("Stopwatch: " + getColor(getRejectionColor()) + "stopped", getLayoutColor(), getDefaultTextAlignment(),
                getDefaultDelay(), out::println);

        printElapsedTime(elapsedTime);
    }

    private static double calculateElapsedTime(long startTime) {
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }

    private static void printElapsedTime(double elapsedTime) {
        message("Elapsed Time: " + getColor(getMainColor()) + elapsedTime
                + getColor(getLayoutColor()) + " sec.", getLayoutColor(), getDefaultTextAlignment(),
                getDefaultDelay(), out::println);
    }

    private static void displayCustomTimeZone() {
        insertControlChars('n', 1);

        while (true) {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter a time zone [e.g., "
                    + getColor(getMainColor()) + "Europe" + getColor(getLayoutColor())
                    + "/" + getColor(getMainColor()) + "Paris" + getColor(getLayoutColor()) + "]: ");

            String inputZone = scanner.nextLine().trim();

            if (exitCheck(inputZone)) break;

            if (isValidTimeZone(inputZone)) {
                String time = getTimeInZone(inputZone);
                message("Time in " + inputZone + ": " + getColor(getMainColor()) + time
                        + getColor(getLayoutColor()) + ".", getLayoutColor(), getDefaultTextAlignment(),
                        getDefaultDelay(), out::println);
                break;
            } else {
                suggestTimeZones(inputZone);
            }
        }
    }

    private static boolean exitCheck(@NotNull String inputZone) {
        if (inputZone.equalsIgnoreCase("q")) {
            if (isTimerRunning) {
                isTimerRunning = false;
                insertControlChars('n', 1);
            }
            message("Terminated correctly" + getColor(getLayoutColor())
                    + ". " + getColor(getMainColor()) + "You are in time menu"
                    + getColor(getLayoutColor()) + ".", getMainColor(), getDefaultTextAlignment(),
                    getDefaultDelay(),out::print);

            message("\r   Status: " + getColor(getAcceptanceColor()) + "✓",
                    getLayoutColor(),getDefaultTextAlignment(),
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
        message("Invalid time zone: " + inputZone, getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

        List<String> similarZones = ZoneId.getAvailableZoneIds().stream()
                .filter(zone -> zone.toLowerCase().contains(inputZone.toLowerCase()))
                .sorted()
                .limit(5)
                .toList();

        if (similarZones.isEmpty()) {
            message("No similar time zones found. Please check your input.", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } else {
            message("Did you mean:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
            similarZones.forEach(zone -> message("- " + zone, getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println));
        }
    }

    private static void displayCalendar() {
        YearMonth currentMonth = YearMonth.now();

        marginBorder(1, 2);
        out.printf(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "📅 %s %d\n\n",
                currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), currentMonth.getYear());

        message("Mo      Tu      We      Th      Fr      Sa      Su", getLayoutColor(),
                getDefaultLogoAlignment() + 10,getDefaultDelay(),out::println);

        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        int firstDayOfWeekValue = firstDayOfMonth.getDayOfWeek().getValue();


        int initialSpacing = firstDayOfWeekValue - 1;
        int daysInMonth = currentMonth.lengthOfMonth();

        for (int i = 0; i < initialSpacing; i++) {
            out.print(alignment(getDefaultTextAlignment()) + "     ");
        }

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentMonth.atDay(day);
            boolean isToday = date.equals(LocalDate.now());

            if (isToday) {
                out.printf(alignment(getDefaultTextAlignment()) + getColor(getAcceptanceColor()) + "%-5d", day);
            } else {
                out.printf(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "%-5d", day);
            }

            int dayOfWeekValue = (firstDayOfWeekValue + day - 1) % 7;
            if (dayOfWeekValue == 0 || day == daysInMonth) {
                insertControlChars('n', 1);
            }
        }
        marginBorder(2, 1);
    }
}