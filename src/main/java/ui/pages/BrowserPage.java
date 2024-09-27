package ui.pages;

import static ui.layout.BasicFunctions.*;
import static ui.layout.BorderWork.drawTripleBorder;
import static ui.layout.ColorWork.*;
import static ui.layout.ColorWork.RESET;
import static ui.layout.TextWork.contentAlignment;
import static ui.layout.ThemesWork.displayErrorAscii;

public class BrowserPage {
    public static void browser() {
        System.out.println("\n");

        while (true) {
            System.out.print(contentAlignment(58) + PURPLE + BOLD + "Enter domain" + RESET
                    + WHITE + BOLD + " (or type 'exit' to quit): " + RESET);
            String domainInput = scanner.nextLine().toLowerCase();

            if (domainInput.equals("exit")) {
                displayColorMessage("Exiting browser...", "red", 58);
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
                        displayColorMessage("Failed to open link: " + e.getMessage(), "red", 0);
                    }
                    break;

                case "-":
                    displayColorMessage("Searching canceled\n", "white", 58);
                    break;

                default:
                    System.out.print("\n");
                    drawTripleBorder();
                    System.out.print("\n");
                    displayErrorAscii();
                    displayColorMessage("Invalid input\n", "red", 0);
                    break;
            }
        }
    }
}