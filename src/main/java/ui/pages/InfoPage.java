package ui.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static ui.layout.BasicFunc.displayTip;
import static ui.layout.BasicFunc.openSite;
import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.TextFunc.contentAlignment;
import static ui.layout.ThemesFunc.displayErrorAscii;

public  class InfoPage {
    public static String version = "A-0.6.4";

    public static void displayInfo() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern(" dd-MM-yyyy" + " HH:mm");
        String formattedTime = localTime.format(myFormatter);
        System.out.println("\n");
        displayContent("Current version:", "white", 58);
        displayContent(version, "purple", 58);
        displayContent("Author: Nick Zozulia", "white", 58);
        displayContent("Current time: " + formattedTime, "white", 58);
        displayContent("Memory used: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1000 * 1000) + "M"), "white", 58);

        System.out.print("\n");
        displayTip("Enter '+' to open and '-' to skip", 58);
        System.out.print(contentAlignment(58) + WHITE + BOLD + "Github: " + RESET);
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "+":
                try {
                    openSite("https://github.com/Akillot/MultiCLIA");
                } catch (Exception e) {
                    displayMarginBigBorder();
                    displayErrorAscii();
                    displayContent("Failed to open link: " + e.getMessage(), "red", 0);
                }
                break;

            case "-":
                displayContent("Alright, next time", "white", 58);
                break;

            default:
                displayMarginBigBorder();
                displayErrorAscii();
                displayContent("Invalid input", "red", 0);
                break;
        }
        displayMarginBigBorder();
    }
}