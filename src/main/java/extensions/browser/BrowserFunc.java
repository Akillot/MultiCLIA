package extensions.browser;

import static core.logic.BorderFunc.marginBorder;
import static core.logic.ColorFunc.*;
import static core.logic.CommandManager.choice;
import static core.logic.CommandManager.openUri;
import static core.logic.DisplayManager.*;
import static core.logic.TextFunc.alignment;
import static java.lang.System.out;

public class BrowserFunc {
    public static void browser() {
        while (true) {
            messageModifier('n', 1);
            out.print(alignment(58) + BLUE + BOLD + "Enter domain" + RESET
                    + WHITE + BOLD + " (or type 'exit' to quit): ");
            String domainInput = scanner.nextLine().toLowerCase();

            if (domainInput.equals("exit")) {
                message("Exiting browser...", "red", 58,0, out::print);
                marginBorder();
                break;
            }

            String domain = "https://www." + domainInput;
            choice(domainInput, openUri(domain));
        }
    }
    public static String[] browserLogo = {
            "oooooooooo.                                      ",
            "`888'   `Y8b                                     ",
            " 888     888 oooo d8b  .ooooo.  oooo oooo    ooo ",
            " 888oooo888' `888\"\"8P d88' `88b  `88. `88.  .8'",
            " 888    `88b  888     888   888   `88..]88..8'   ",
            " 888    .88P  888     888   888    `888'`888'    ",
            "o888bood8P'  d888b    `Y8bod8P'     `8'  `8'     ",
            "                                                 ",
            " .oooo.o  .ooooo.  oooo d8b ",
            "d88(  \"8 d88' `88b `888\"\"8P",
            "`\"Y88b.  888ooo888  888     ",
            "o.  )88b 888    .o  888     ",
            "8\"\"888P' `Y8bod8P' d888b    ",
            "                                                 "};
}