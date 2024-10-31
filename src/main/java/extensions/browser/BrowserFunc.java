package extensions.browser;

import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.ColorFunc.*;
import static core.layout.CommandManager.openUri;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.alignment;
import static java.lang.System.out;

public class BrowserFunc {
    public static void browser() {
        while (true) {
            out.print("\n");
            out.print(alignment(58) + PURPLE + BOLD + "Enter domain" + RESET
                    + WHITE + BOLD + " (or type 'exit' to quit): " + RESET);
            String domainInput = scanner.nextLine().toLowerCase();

            if (domainInput.equals("exit")) {
                message("Exiting browser...", "red", 58);
                marginBigBorder();
                break;
            }

            String domain = "https://www." + domainInput;
            //FIX
            tip("Enter '+' to open, '-' to cancel", 58);
            out.print(alignment(58) + WHITE + BOLD + "Choice: " + RESET);
            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "+":
                    try {
                        openUri(domain);
                    } catch (Exception e) {
                        marginBigBorder();
                        errorAscii();
                        message("Failed to open link", "red", 58);
                    }
                    break;

                case "-":
                    message("Searching canceled\n", "white", 58);
                    break;

                default:
                    marginBigBorder();
                    errorAscii();
                    break;
            }
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