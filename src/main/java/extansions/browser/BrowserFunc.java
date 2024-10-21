package extansions.browser;

import static extansions.browser.IpFunc.generateIpv4;
import static ui.layout.BorderFunc.displayBigBorder;
import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.CommandManager.openUri;
import static ui.layout.DisplayManager.*;
import static ui.layout.TextFunc.alignment;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class BrowserFunc {
    public static void browser() {
        while (true) {
            displayBigBorder();
            System.out.print("\n\n");
            tip("Enter domain or an IP or '" + PURPLE + BOLD + "exit" + RESET + "' to quit", 58);
            System.out.print(alignment(58)
                    + WHITE + BOLD + UNDERLINE + "Search" + RESET + BOLD + WHITE + ": " + RESET);
            String domainInput = scanner.nextLine().toLowerCase().trim();
            System.out.print("\n");

            if (domainInput.equals("exit")) {
                message("Exiting browser...", "red", 58, false);
                System.out.print("\n");
                displayMarginBigBorder();
                break;
            }

            if (domainInput.isEmpty()) {
                continue;
            }

            if(domainInput.equalsIgnoreCase("random") || domainInput.equalsIgnoreCase("rand")) {
                openUri(generateIpv4());
            }

            if (isValidDomain(domainInput) || isValidIPv4(domainInput)) {
                String domain = "https://" + domainInput;

                tip("Enter '" + PURPLE + BOLD + "+" + RESET + "' to open, '"
                        + PURPLE + BOLD + "-" + RESET + "' to cancel", 58);
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
            } else {
                message("Invalid domain or IP address", "red", 58, false);
            }
        }
    }

    private static boolean isValidDomain(String domain) {
        String domainRegex = "^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(domainRegex);
        Matcher matcher = pattern.matcher(domain);
        return matcher.matches();
    }

    private static boolean isValidIPv4(String ip) {
        String ipRegex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(ipRegex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
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
