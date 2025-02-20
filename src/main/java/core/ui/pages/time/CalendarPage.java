package core.ui.pages.time;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;
import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;

import static core.configs.TextConfigs.alignment;
import static java.lang.System.out;

public class CalendarPage {
    public static void displayCalendarPage() {
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
