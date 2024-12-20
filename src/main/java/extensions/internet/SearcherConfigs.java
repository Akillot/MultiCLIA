package extensions.internet;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class SearcherConfigs {
    public static void browser() {
        modifyMessage('n', 1);
        switchLogo(browserLogo,32);
        marginBorder();
        modifyMessage('n', 1);
        alert("Example",getAnsi256Color(systemDefaultWhite) + ": '"
                + getAnsi256Color(systemDefaultColor) + "github.com" + getAnsi256Color(systemDefaultWhite) + "'",58);
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
            " .oooooo..o                                        oooo                           ",
            "d8P'    `Y8                                        `888                           ",
            "Y88bo.       .ooooo.   .oooo.   oooo d8b  .ooooo.   888 .oo.    .ooooo.  oooo d8b ",
            " `\"Y8888o.  d88' `88b `P  )88b  `888\"\"8P d88' `\"Y8  888P\"Y88b  d88' `88b `888\"\"8P ",
            "     `\"Y88b 888ooo888  .oP\"888   888     888        888   888  888ooo888  888    ",
            "oo     .d8P 888    .o d8(  888   888     888   .o8  888   888  888    .o  888     ",
            "8\"\"88888P'  `Y8bod8P' `Y888\"\"8o d888b    `Y8bod8P' o888o o888o `Y8bod8P' d888b    ",
            " "
    };
}