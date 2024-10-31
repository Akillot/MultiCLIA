package core.pages;

import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.ColorFunc.*;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.alignment;
import static core.pages.InfoPage.version;
import static core.pages.MenuPage.displayMainMenuUi;
import static java.lang.System.out;

public class StartPage {
    public static void start() {
        out.print("\n\n");
        logoAscii(mainLogoAscii, 48);
        marginBigBorder();

        message("+--------------------+", "white", 58);
        message("Version: " + version, "white", 56);
        message("+--------------------+", "white", 58);

        tip("Enter " + "'" + PURPLE + BOLD + "sys.cmds" + RESET + "'\n"
                + alignment(56) + "to show list of\n"
                + alignment(56) + "commands", 56);

        message("+--------------------+", "white", 58);
        out.print("\n");
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
