package core.pages;

import static core.logic.BorderFunc.marginBigBorder;
import static core.logic.ColorFunc.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextFunc.alignment;
import static core.pages.InfoPage.version;
import static core.pages.MenuPage.displayMainMenuUi;

public class StartPage {
    public static void start() {
        messageModifier('n', 2);
        logoAscii(mainLogoAscii,48);
        marginBigBorder();

        message("+--------------------+", "white", 58,0);
        message("Version: " + version, "white", 56,0);
        message("+--------------------+", "white", 58,0);

        alert("i", "Enter " + "'" + PURPLE + BOLD + "sys.cmds" + RESET + "'\n"
                + alignment(56) + "to show list of\n"
                + alignment(56) + "commands", 56);

        message("+--------------------+", "white", 58,0);
        messageModifier('n', 1);
        while(true) {
            try {
                displayMainMenuUi();
            } catch (Exception ex) {
                marginBigBorder();
                errorAscii();
            }
        }
    }
}
