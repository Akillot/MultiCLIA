package core.ui.pages.time;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;
import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;

import static core.configs.TextConfigs.alignment;
import static core.logic.CommandManager.*;
import static core.ui.DisplayManager.clearTerminal;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class CalendarPage {

    private static int qrCodeColor = sysAcceptanceColor;

    public static void displayCalendarPage() {
        marginBorder(1, 2);
        message("Calendar:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(sysLayoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "calendar", "/c" -> displayCalendar();
                case "secret", "/scrt" -> secretCommand();
                case "rerun", "/rr" -> {
                    insertControlChars('n', 1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list", "/ls" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    public static void displayListOfCommands()  {
        insertControlChars('n', 1);
        message("·  Calendar [" + getColor(sysMainColor)
                + "/c" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Rerun [" + getColor(sysMainColor)
                + "/rr" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear terminal [" + getColor(sysMainColor)
                + "/cl" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List Of Commands [" + getColor(sysMainColor)
                + "/lc" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Exit [" + getColor(sysMainColor)
                + "/e" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void displayCalendar() {
        YearMonth currentMonth = YearMonth.now();

        marginBorder(1, 2);
        out.printf(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "📅 %s %d\n\n",
                currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), currentMonth.getYear());

        message("Mo      Tu      We      Th      Fr      Sa      Su",sysLayoutColor,
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
                out.printf(alignment(getDefaultTextAlignment()) + getColor(qrCodeColor) + "%-5d", day);
            } else {
                out.printf(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "%-5d", day);
            }

            int dayOfWeekValue = (firstDayOfWeekValue + day - 1) % 7;
            if (dayOfWeekValue == 0 || day == daysInMonth) {
                insertControlChars('n', 1);
            }
        }
        marginBorder(2, 1);
    }
}