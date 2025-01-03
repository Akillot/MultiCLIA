package extensions.time.clock;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.TextConfigs.*;
import static extensions.time.clock.ClockConfigs.*;
import static java.lang.System.out;

public class ClockUI {
    public static void displayTime() {
        modifyMessage('n', 1);
        message("Current Time: " + getAnsi256Color(themeColor_2) + ClockService.getCurrentTime(), layoutColor, 58, 0, out::print);
        message("Current Time Zone: " + getAnsi256Color(themeColor_2) + ClockService.getCurrentTimeZone(), layoutColor, 58, 0, out::print);
    }

    public static void displayClockMenu() {
        modifyMessage('n', 2);
        switchLogoRandomly(ClockConfigs.getClockLogo(), -2);
        marginBorder(2, 1);

        modifyMessage('n', 1);
        displayConfirmation("Enter", "to open and", "to skip", acceptanceColor, rejectionColor, layoutColor);
        modifyMessage('n', 1);
        choice("Clock", ClockUI::displayTime, themeColor_1, rejectionColor, layoutColor);

        modifyMessage('n', 2);
        choice("Info", ClockUI::displayInfo, themeColor_1, rejectionColor, layoutColor);
        marginBorder(2, 1);
    }

    public static void displayInfo() {
        modifyMessage('n', 1);
        message("Name: " + getAnsi256Color(themeColor_2) + "Clock", layoutColor, 58, 0, out::print);
        message("Type: " + getAnsi256Color(themeColor_2) + "Default extension", layoutColor, 58, 0, out::print);
        message("Version: " +  getAnsi256Color(themeColor_2) + ClockConfigs.getVersion(), layoutColor, 58, 0, out::print);
        message("Author: " + getAnsi256Color(themeColor_2) + "Nick Zozulia", layoutColor, 58, 0, out::print);
    }
}
