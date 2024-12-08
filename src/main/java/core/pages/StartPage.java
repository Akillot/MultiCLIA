package core.pages;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.searchCommands;
import static core.logic.CommandManager.switchLogo;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;
import static core.pages.InfoPage.version;
import static java.lang.System.out;

public class StartPage {
    public static void displayStart() {
        modifyMessage('n',2);
        switchLogo(mainLogoAscii, 48);
        marginBorder();

        String displayVersion = (version != null) ? version : "Unknown Version";
        message("━━━━━━━━━━━━━━━━━━━━━━",systemDefaultWhite,58,0, out::print);
        message("Version: " + displayVersion,systemDefaultWhite,56,0, out::print);
        message("━━━━━━━━━━━━━━━━━━━━━━",systemDefaultWhite,58,0, out::print);

        alert("i", "Enter " + "'" + getAnsi256Color(systemDefaultColor) + "--cmds" + RESET
                + getAnsi256Color(systemDefaultWhite) + "'\n"
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

    private static String[] mainLogoAscii = {
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
}
