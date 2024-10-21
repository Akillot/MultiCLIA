package ui.pages;

import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.DisplayManager.*;
import static ui.layout.TextFunc.alignment;
import static ui.pages.InfoPage.version;
import static ui.pages.MenuPage.displayMainMenuUi;

public class StartPage {
    public static void start() {
        System.out.print("\n\n");
        logoAscii();
        displayMarginBigBorder();

        message("--------------------", "white", 58);
        message("Version: " + version, "white", 58);
        message("--------------------", "white", 58);
        tip("Enter " + "'" + BOLD + PURPLE + "commands" + RESET + "'\n"
                + alignment(58) + "to show list of\n"
                + alignment(58) + "commands", 58);
        message("--------------------", "white", 58);
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
