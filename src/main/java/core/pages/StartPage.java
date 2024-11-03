package core.pages;

import static com.ibm.icu.text.PluralRules.Operand.n;
import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.ColorFunc.*;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.alignment;
import static core.pages.InfoPage.version;
import static core.pages.MenuPage.displayMainMenuUi;

public class StartPage {
    public static void start() {
        messageModifier('n', 2);
        logoAscii(mainLogoAscii, 48);
        marginBigBorder();

        message("+--------------------+", "white", 58);
        message("Version: " + version, "white", 56);
        message("+--------------------+", "white", 58);

        alert("i", "Enter " + "'" + PURPLE + BOLD + "sys.cmds" + RESET + "'\n"
                + alignment(56) + "to show list of\n"
                + alignment(56) + "commands", 56);

        message("+--------------------+", "white", 58);
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
