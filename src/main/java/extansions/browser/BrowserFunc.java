package extansions.browser;

import static ui.layout.BorderFunc.displayBigBorder;
import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.CommandManager.openUri;
import static ui.layout.DisplayManager.*;
import static ui.layout.TextFunc.alignment;

public class BrowserFunc {
    public static void browser() {
        while (true) {
            displayBigBorder();
            System.out.print("\n\n");
            tip("Enter domain or '" + PURPLE + BOLD + "exit" + RESET + "' to quit", 58);
            System.out.print(alignment(58)
                    + WHITE + BOLD  + UNDERLINE + "Search" + RESET + BOLD + WHITE + ": " + RESET);
            String domainInput = scanner.nextLine().toLowerCase();
            System.out.print("\n");

            if (domainInput.equals("exit")) {
                message("Exiting browser...", "red", 58, false);
                System.out.print("\n");
                displayMarginBigBorder();
                break;
            }

            String domain = "https://www." + domainInput;

            tip("Enter '" + PURPLE + BOLD + "+" + RESET + "' to open, '"
                    + PURPLE + BOLD +  "-" + RESET + "' to cancel", 58);
            System.out.print(alignment(58) + WHITE + BOLD + "Choice: " + RESET);
            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "+":
                    try {
                        openUri(domain);
                    } catch (Exception e) {
                        displayMarginBigBorder();
                        errorAscii();
                        message("Failed to open link", "red", 58, false);
                    }
                    break;

                case "-":
                    message("Searching canceled\n", "white", 58, false);
                    break;

                default:
                    displayMarginBigBorder();
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
