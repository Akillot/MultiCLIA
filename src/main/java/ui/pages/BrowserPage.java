package ui.pages;

import static ui.layout.BasicFunc.*;
import static ui.layout.BorderFunc.drawTripleBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.ColorFunc.RESET;
import static ui.layout.TextFunc.contentAlignment;
import static ui.layout.ThemesFunc.displayErrorAscii;

public class BrowserPage {
    public static void browser() {
        System.out.println("\n");

        while (true) {
            System.out.print(contentAlignment(58) + PURPLE + BOLD + "Enter domain" + RESET
                    + WHITE + BOLD + " (or type 'exit' to quit): " + RESET);
            String domainInput = scanner.nextLine().toLowerCase();

            if (domainInput.equals("exit")) {
                displayContent("Exiting browser...", "red", 58);
                System.out.print("\n");
                drawTripleBorder();
                System.out.print("\n");
                break;
            }

            String domain = "https://www." + domainInput;

            displayTip("Enter '+' to open, '-' to cancel", 58);
            System.out.print(contentAlignment(58) + WHITE + BOLD + "Choice: " + RESET);
            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "+":
                    try {
                        openSite(domain);
                    } catch (Exception e) {
                        System.out.print("\n");
                        drawTripleBorder();
                        System.out.print("\n");
                        displayErrorAscii();
                        displayContent("Failed to open link: " + e.getMessage(), "red", 0);
                    }
                    break;

                case "-":
                    displayContent("Searching canceled\n", "white", 58);
                    break;

                default:
                    System.out.print("\n");
                    drawTripleBorder();
                    System.out.print("\n");
                    displayErrorAscii();
                    displayContent("Invalid input\n", "red", 0);
                    break;
            }
        }
    }
}