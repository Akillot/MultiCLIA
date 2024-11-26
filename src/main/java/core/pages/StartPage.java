package core.pages;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.alignment;
import static core.pages.InfoPage.version;
import static core.pages.MenuPage.displayMainMenuUi;
import static java.lang.System.out;

public class StartPage {
    public static void start() {
        messageModifier('n',2);
        switchLogoAscii(mainLogoAscii, 48);
        marginBorder();

        String displayVersion = (version != null) ? version : "Unknown Version";
        message("━━━━━━━━━━━━━━━━━━━━━━", "white", 58, 0, out::print);
        message("Version: " + displayVersion, "white", 56, 0, out::print);
        message("━━━━━━━━━━━━━━━━━━━━━━", "white", 58, 0, out::print);

        alert("i", "Enter " + "'" + BLUE + BOLD + "sys.cmds" + RESET + "'\n"
                + alignment(56) + "to show list of\n"
                + alignment(56) + "commands", 56);

        message("━━━━━━━━━━━━━━━━━━━━━━", "white", 58, 0, out::print);
        messageModifier('n', 1);

        while (true) {
            try {
                displayMainMenuUi();
            } catch (Exception ex) {
                marginBorder();
                errorAscii();
                String errorMessage = (ex.getMessage() != null) ? ex.getMessage() : "Unknown error occurred";
                message(errorMessage, "red", 58, 0, out::print);
            }
        }
    }
}
