package core.ui.extensions.time;

import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
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
        message("Time:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), acceptanceColor, false,
                    getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "current time", "/ct" -> displayCurrentTime();
                case "calendar", "/c" -> displayCalendar();
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
        message("·  Current Time [" + getColor(mainColor)
                + "/ct" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Calendar [" + getColor(mainColor)
                + "/c" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Timer [" + getColor(mainColor)
                + "/t" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Stopwatch ["  + getColor(mainColor)
                + "/sw" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Change Time Zone [" + getColor(mainColor)
                + "/ctz" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Restart [" + getColor(mainColor)
                + "/rs" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear terminal [" + getColor(mainColor)
                + "/cl" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List [" + getColor(mainColor)
                + "/ls" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Quit [" + getColor(mainColor)
                + "/q" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    // /ct
    private static void displayCurrentTime() {
        insertControlChars('n',1);
        message("Current Time: " + getColor(mainColor)
                + getCurrentTime(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        message("Current Time Zone: " + getColor(mainColor)
                + getCurrentTimeZone(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor)
                    + "Enter time in seconds (or '" + getColor(mainColor)
                    + "exit" + getColor(layoutColor) + "' to quit): ");
            String input = scanner.nextLine();

            if (exitCheck(input)) break;

            try {
                double seconds = Double.parseDouble(input);
                if (seconds < 0) {
                    message("Time cannot be negative.", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    continue;
                }
                if (isTimerRunning) {
                    message("A timer is already running" + getColor(layoutColor) +
                            ". Please wait or stop it.", mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    continue;
                }
                isTimerRunning = true;
                startAsyncTimer(seconds);

            } catch (NumberFormatException e) {
                out.println(alignment(getDefaultTextAlignment()) + getColor(layoutColor)
                        + "Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void startAsyncTimer(double seconds) {
        new Thread(() -> {
            try {
                for (double i = seconds; i >= 0; i--) {
                    if (!isTimerRunning) {
                        message("Timer stopped.", mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                        return;
                    }
                    out.print("\r" + alignment(getDefaultTextAlignment()) + getColor(layoutColor)
                            + "Time left: " + getColor(mainColor)
                            + i + getColor(layoutColor) + " sec");
                    Thread.sleep(1000);
                }
                if (isTimerRunning) {
                    insertControlChars('r', 1);
                    message("Time is up", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Status: "
                            + getColor(acceptanceColor) + " ✓");
                    insertControlChars('n', 1);
                }
                insertControlChars('n',1);
                out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor)
                        + "Enter time in seconds (or '" + getColor(mainColor)
                        + "exit" + getColor(layoutColor) + "' to quit): ");
            } catch (InterruptedException e) {
                message("Timer interrupted" + getColor(layoutColor) + ".", mainColor,
                        getDefaultTextAlignment(), getDefaultDelay(), out::print);
            } finally {
                isTimerRunning = false;
            }
        }).start();
    }

    // /sw command
    private static void runStopwatch() {
        insertControlChars('n', 1);
        message("Press " + getColor(mainColor) + "any key" + getColor(layoutColor)
                        + " to start stopwatch and again to stop:", layoutColor, getDefaultTextAlignment(),
                getDefaultDelay(), out::print);
        scanner.nextLine();
        long startTime = System.currentTimeMillis();
        message("Stopwatch: " + getColor(acceptanceColor) + "started", layoutColor, getDefaultTextAlignment(),
                getDefaultDelay(), out::print);

        scanner.nextLine();
        double elapsedTime = calculateElapsedTime(startTime);
        message("Stopwatch: " + getColor(rejectionColor) + "stopped", layoutColor, getDefaultTextAlignment(),
                getDefaultDelay(), out::println);

        printElapsedTime(elapsedTime);
    }

    private static double calculateElapsedTime(long startTime) {
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }

    private static void printElapsedTime(double elapsedTime) {
        message("Elapsed Time: " + getColor(mainColor) + elapsedTime
                + getColor(layoutColor) + " sec.", layoutColor, getDefaultTextAlignment(),
                getDefaultDelay(), out::println);
    }

    // /ctz command
    private static void displayCustomTimeZone() {
        insertControlChars('n', 1);

        while (true) {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter a time zone [e.g., "
                    + getColor(219) + "Europe" + getColor(layoutColor)
                    + "/" + getColor(mainColor) + "Paris" + getColor(layoutColor) + "]: ");

            String inputZone = scanner.nextLine().trim();

            if (exitCheck(inputZone)) break;

            if (isValidTimeZone(inputZone)) {
                String time = getTimeInZone(inputZone);
                message("Time in " + inputZone + ": " + getColor(mainColor) + time
                        + getColor(layoutColor) + ".", layoutColor, getDefaultTextAlignment(),
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
            message("Terminated correctly" + getColor(layoutColor)
                    + "." + getColor(mainColor) + "You are in time menu"
                    + getColor(layoutColor) + ".", mainColor, getDefaultTextAlignment(),
                    getDefaultDelay(),out::print);

            message("\r   Status: " + getColor(acceptanceColor) + "✓",
                    layoutColor,getDefaultTextAlignment(),
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
        message("Invalid time zone: " + inputZone, layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        List<String> similarZones = ZoneId.getAvailableZoneIds().stream()
                .filter(zone -> zone.toLowerCase().contains(inputZone.toLowerCase()))
                .sorted()
                .limit(5)
                .toList();

        if (similarZones.isEmpty()) {
            message("No similar time zones found. Please check your input.", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } else {
            message("Did you mean:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            similarZones.forEach(zone -> message("- " + zone, layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println));
        }
    }

    private static void displayCalendar() {
        YearMonth currentMonth = YearMonth.now();

        marginBorder(1, 2);
        out.printf(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "📅 %s %d\n\n",
                currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), currentMonth.getYear());

        message("Mo      Tu      We      Th      Fr      Sa      Su", layoutColor,
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
                out.printf(alignment(getDefaultTextAlignment()) + getColor(acceptanceColor) + "%-5d", day);
            } else {
                out.printf(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "%-5d", day);
            }

            int dayOfWeekValue = (firstDayOfWeekValue + day - 1) % 7;
            if (dayOfWeekValue == 0 || day == daysInMonth) {
                insertControlChars('n', 1);
            }
        }
        marginBorder(2, 1);
    }
}