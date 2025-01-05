package core.pages;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static core.logic.AppearanceConfigs.*;
import static core.logic.TextConfigs.*;
import static core.logic.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class TimePage {

    public static void displayTimePage() {
        modifyMessage('n', 1);
        displayTime();
        marginBorder(2, 1);
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
}
