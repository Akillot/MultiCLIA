package extensions.time.clock;

import org.jetbrains.annotations.Contract;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.choice;
import static core.logic.CommandManager.switchLogoRandomly;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class ClockConfigs {

    private static int themeColor_1 = 177;
    private static int themeColor_2 = 213;
    private static int layoutColor = 15;

    private static int acceptanceColor = 46;
    private static int rejectionColor = 196;

    private static double version = 1.0;

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
        switchLogoRandomly(clockLogo, -2);
        marginBorder();

        modifyMessage('n', 1);
        displayConfirmation("Enter","to open and","to skip", acceptanceColor, rejectionColor, layoutColor);
        choice("Clock", ClockConfigs::displayTime, themeColor_1, rejectionColor, layoutColor);

        marginBorder();

        modifyMessage('n', 1);
        choice("Info", ClockConfigs::displayInfo, themeColor_1, rejectionColor, layoutColor);
        marginBorder();
    }

    @Contract(pure = true)
    private static void displayTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "HH:mm");
        String formattedTime = localTime.format(myFormatter);
        modifyMessage('n',1);
        out.println(alignment(58) + getAnsi256Color(themeColor_2) + "Current Time: " + getAnsi256Color(layoutColor) + formattedTime);
    }

    private static void displayInfo(){
        modifyMessage('n',1);
        message("Name: " + getAnsi256Color(themeColor_1) + "Clock", layoutColor,58,0,out::print);
        message("Type: " + getAnsi256Color(themeColor_1) + "Default extension", layoutColor,58,0,out::print);
        message("Version: " + getAnsi256Color(themeColor_1) + version, layoutColor,58,0,out::print);
        message("Author: " + getAnsi256Color(themeColor_1) + "Nick Zozulia", layoutColor,58,0,out::print);
    }
}
