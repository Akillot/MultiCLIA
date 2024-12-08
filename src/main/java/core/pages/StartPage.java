package core.pages;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.searchCommands;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;
import static core.pages.InfoPage.version;
import static java.lang.System.out;

public class StartPage {
    public static void displayStart() {
        modifyMessage('n',2);
        switcherLogoAscii(mainLogoAscii, 48);
        marginBorder();

        String displayVersion = (version != null) ? version : "Unknown Version";
        message("━━━━━━━━━━━━━━━━━━━━━━",systemDefaultWhite,58,0, out::print);
        message("Version: " + displayVersion,systemDefaultWhite,56,0, out::print);
        message("━━━━━━━━━━━━━━━━━━━━━━",systemDefaultWhite,58,0, out::print);

        alert("i", "Enter " + "'" + getAnsi256Color(systemDefaultColor) + BOLD + "sys.cmds" + RESET
                + getAnsi256Color(systemDefaultWhite) +  BOLD + "'\n"
                + alignment(56) + "to show list of\n"
                + alignment(56) + "commands" + RESET, 56);

        message("━━━━━━━━━━━━━━━━━━━━━━",systemDefaultWhite,58,0,out::print);
        modifyMessage('n', 1);

        while (true) {
            try {
                searchCommands();
            } catch (Exception ex) {
                marginBorder();
                errorAscii();
                String errorMessage = (ex.getMessage() != null) ? ex.getMessage() : "Unknown error occurred";
                message(errorMessage, systemDefaultRed, 58, 0, out::print);
            }
        }
    }

    public static String[] mainLogoAscii = {
            "ooo        ooooo             oooo      .    o8o  ",
            "`88.       .888'             `888    .o8    `\"'  ",
            " 888b     d'888  oooo  oooo   888  .o888oo oooo  ",
            " 8 Y88. .P  888  `888  `888   888    888   `888  ",
            " 8  `888'   888   888   888   888    888    888  ",
            " 8    Y     888   888   888   888    888    888  ",
            " 8          888   888   888   888    888 .  888  ",
            "o8o        o888o  `V88V\"V8P' o888o   \"888\" o888o ",
            "                                                    ",
            "                                                    ",
            "    .oooooo.   ooooo        ooooo       .o.       ",
            "   d8P'  `Y8b  `888'        `888'      .888.      ",
            "  888           888          888      .8\"888.     ",
            "  888           888          888     .8' `888.    ",
            "  888           888          888    .88ooo8888.   ",
            "  `88b    ooo   888       o  888   .8'     `888.  ",
            "   `Y8bood8P'  o888ooooood8 o888o o88o     o8888o "
    };

    public static void switcherLogoAscii(String[] logo, int alignment) {
        String[] colors;
        int indexOfLogo = rand.nextInt(3);

        switch (indexOfLogo) {
            case 0 -> colors = new String[]{
                    getAnsi256Color(systemDefaultColor), getAnsi256Color(56),
                    getAnsi256Color(165), getAnsi256Color(99),
                    getAnsi256Color(63), getAnsi256Color(99)};

            case 1 -> colors = new String[]{
                    getAnsi256Color(250), getAnsi256Color(251),
                    getAnsi256Color(252), getAnsi256Color(253),
                    getAnsi256Color(254), getAnsi256Color(255)};

            case 2 -> colors = new String[]{
                    getAnsi256Color(52), getAnsi256Color(53),
                    getAnsi256Color(54), getAnsi256Color(55),
                    getAnsi256Color(56), getAnsi256Color(57)};

            default -> colors = new String[]{
                    getAnsi256Color(systemDefaultWhite), getAnsi256Color(systemDefaultWhite),
                    getAnsi256Color(systemDefaultWhite), getAnsi256Color(systemDefaultWhite),
                    getAnsi256Color(systemDefaultWhite), getAnsi256Color(systemDefaultWhite)};
        }

        for (int i = 0; i < logo.length; i++) {
            String coloredText = colors[i % colors.length] + logo[i] + RESET;
            message(coloredText, i % colors.length, alignment, 0, System.out::print);
        }
    }
}
