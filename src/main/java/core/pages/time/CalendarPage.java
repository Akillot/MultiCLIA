package core.pages.time;

import org.jetbrains.annotations.NotNull;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;
import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;

import static core.configs.TextConfigs.alignment;
import static java.lang.System.out;

public class CalendarPage {

    private static void printCalendar(@NotNull YearMonth month) {
        out.printf(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "📅 %s %d\n",
                month.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), month.getYear());

        message("Mo Tu We Th Fr Sa Su",sysLayoutColor,getDefaultLogoAlignment(),getDefaultDelay(),out::println);

        int firstDayOfWeek = month.atDay(1).getDayOfWeek().getValue() % 7;
        int daysInMonth = month.lengthOfMonth();

        for (int i = 0; i < firstDayOfWeek; i++) {
            out.print(alignment(getDefaultTextAlignment()) + "   ");
        }

        for (int day = 1; day <= daysInMonth; day++) {
            out.printf("%2d ", day);
            if ((day + firstDayOfWeek) % 7 == 0 || day == daysInMonth) insertControlChars('n',1);
        }
    }
}
