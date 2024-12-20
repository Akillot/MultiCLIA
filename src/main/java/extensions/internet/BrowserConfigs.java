package extensions.internet;

import static core.logic.BorderConfigs.border;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.alignment;
import static core.logic.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class BrowserConfigs {
    public static void browser() {
        modifyMessage('n', 2);
        switchLogo(browserLogo,48);
        border();
        while (true) {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(systemDefaultColor) + "Enter domain"
                    + getAnsi256Color(systemDefaultWhite) + " (or type '" + getAnsi256Color(systemDefaultRed) + "exit"
                    + getAnsi256Color(systemDefaultWhite) + "' to quit): " + RESET);
            String domainInput = scanner.nextLine().toLowerCase();

            if (domainInput.equals("exit")) {
                terminateExtension();
                modifyMessage('n', 1);
                break;
            }

            String domain = "https://" + domainInput;
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