package core.ui.pages.time;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;
import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;

import static core.configs.TextConfigs.alignment;
import static core.logic.CommandManager.exitPage;
import static core.logic.CommandManager.mainMenuRerun;
import static core.ui.DisplayManager.clearTerminal;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class CalendarPage {

    public static void displayCalendarPage() {
        marginBorder(1, 2);
        message("AI:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(sysLayoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ask chatgpt", "/ac" -> displayCalendar();
                case "rerun", "/rr" -> {
                    insertControlChars('n', 1);
                    mainMenuRerun();
                }
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

    public static void displayListOfCommands()  {
        insertControlChars('n', 1);
        message("·  Calendar [" + getColor(sysMainColor)
                + "/ca" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

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
        YearMonth month = YearMonth.now();

        marginBorder(1,2);
        out.printf(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "📅 %s %d\n\n",
                month.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), month.getYear());

        message("Mo    Tu    We    Th    Fr    Sa    Su",sysLayoutColor,
                getDefaultLogoAlignment() + 10,getDefaultDelay(),out::println);

        int firstDayOfWeek = month.atDay(7).getDayOfWeek().getValue() % 7;
        int daysInMonth = month.lengthOfMonth();

        for (int i = 0; i < firstDayOfWeek; i++) {
            out.print(alignment(getDefaultTextAlignment()) + "   ");
        }

        for (int day = 1; day <= daysInMonth; day++) {
            out.printf(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "%2d ", day);
            if ((day + firstDayOfWeek) % 7 == 0 || day == daysInMonth) insertControlChars('n',1);
        }
        marginBorder(2,1);
    }
}
