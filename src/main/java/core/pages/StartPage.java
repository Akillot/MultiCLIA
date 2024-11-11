package core.pages;

import static core.logic.BorderFunc.marginBigBorder;
import static core.logic.ColorFunc.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextFunc.alignment;
import static core.pages.InfoPage.version;
import static core.pages.MenuPage.displayMainMenuUi;
import static java.lang.System.out;

public class StartPage {
    public static void start() {
        messageModifier('n', 2);
        logoAscii(mainLogoAscii,48);
        marginBigBorder();

        message("+--------------------+", "white", 58,0,out::print);
        message("Version: " + version, "white", 56,0, out::print);
        message("+--------------------+", "white", 58,0, out::print);

        alert("i", "Enter " + "'" + BLUE + BOLD + "sys.cmds" + RESET + "'\n"
                + alignment(56) + "to show list of\n"
                + alignment(56) + "commands", 56);

        message("+--------------------+", "white", 58,0, out::print);
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
