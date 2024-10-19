package ui.pages;

import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.ColorFunc.RESET;
import static ui.layout.CommandManager.openUri;
import static ui.layout.DisplayManager.*;
import static ui.layout.TextFunc.alignment;


public class BrowserPage {
    public static void browser() {
        System.out.println("\n");
        while (true) {
            System.out.print(alignment(58) + PURPLE + BOLD + "Enter domain" + RESET
                    + WHITE + BOLD + " (or type 'exit' to quit): " + RESET);
            String domainInput = scanner.nextLine().toLowerCase();

            if (domainInput.equals("exit")) {
                message("Exiting browser...", "red", 58);
                displayMarginBigBorder();
                break;
            }

            String domain = "https://www." + domainInput;

            tip("Enter '+' to open, '-' to cancel", 58);
            System.out.print(alignment(58) + WHITE + BOLD + "Choice: " + RESET);
            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "+":
                    try {
                        openUri(domain);
                    } catch (Exception e) {
                        displayMarginBigBorder();
                        errorAscii();
                        message("Failed to open link", "red", 58);
                    }
                    break;

                case "-":
                    message("Searching canceled\n", "white", 58);
                    break;

                default:
                    displayMarginBigBorder();
                    errorAscii();
                    break;
            }
        }
    }
}