package extensions.time.clock;

import org.jetbrains.annotations.Contract;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.choice;
import static core.logic.CommandManager.switchLogo;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class ClockConfigs {

    private static int themeColor_1 = 223;
    private static int themeColor_2 = 187;
    private static int layoutColor = 15;

    private static int acceptanceColor = 46;
    private static int rejectionColor = 196;

    private static String[] clockLogo = {
            "  .oooooo.   oooo                       oooo        ",
            " d8P'  `Y8b  `888                       `888        ",
            "888           888   .ooooo.    .ooooo.   888  oooo  ",
            "888           888  d88' `88b  d88' `\"Y8  888 .8P'   ",
            "888           888  888   888  888        888888.    ",
            "`88b    ooo   888  888   888  888   .o8  888 `88b.  ",
            " `Y8bood8P'  o888o `Y8bod8P'  `Y8bod8P' o888o o888o ",
            " "
    };

    public static void displayClockMenu() {
        modifyMessage('n', 2);
        switchLogo(clockLogo, -2);
        marginBorder();
        modifyMessage('n', 1);
        displayConfirmation("Enter","to open and","to skip", themeColor_1, rejectionColor, layoutColor);
        choice("Clock", ClockConfigs::displayTime, themeColor_1, layoutColor, rejectionColor);
        marginBorder();
    }

    @Contract(pure = true)
    private static void displayTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "HH:mm");
        String formattedTime = localTime.format(myFormatter);
        out.println(alignment(58) + getAnsi256Color(themeColor_2) + "Current time: " + getAnsi256Color(layoutColor) + formattedTime);
    }
}
