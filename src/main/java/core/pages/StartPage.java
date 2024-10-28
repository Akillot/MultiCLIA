package core.pages;

import static core.layout.BorderFunc.displayMarginBigBorder;
import static core.layout.ColorFunc.*;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.alignment;
import static core.pages.InfoPage.version;
import static core.pages.MenuPage.displayMainMenuUi;

public class StartPage {
    public static void start() {
        System.out.print("\n\n");
        logoAscii(mainLogoAscii, 48);
        displayMarginBigBorder();

        message("+--------------------+", "white", 58);
        message("Version: " + version, "white", 56);
        message("+--------------------+", "white", 58);

        tip("Enter " + "'" + PURPLE + BOLD + "sys.cmds" + RESET + "'\n"
                + alignment(56) + "to show list of\n"
                + alignment(56) + "commands", 56);

        message("+--------------------+", "white", 58);
        System.out.print("\n");
        while(true) {
            try {
                displayMainMenuUi();
            } catch (Exception ex) {
                displayMarginBigBorder();
                errorAscii();
            }
        }
    }
}
