package ui.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static ui.layout.BasicFunctions.displayTip;
import static ui.layout.BasicFunctions.openSite;
import static ui.layout.BorderWork.drawTripleBorder;
import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.contentAlignment;
import static ui.layout.ThemesWork.displayErrorAscii;

public  class InfoPage {
    public static String version = "A-0.6.2";

    public static void displayInfo() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern(" dd-MM-yyyy" + " HH:mm");
        String formattedTime = localTime.format(myFormatter);

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
                    System.out.print("\n");
                    drawTripleBorder();
                    System.out.print("\n");
                    displayErrorAscii();
                    displayContent("Failed to open link: " + e.getMessage(), "red", 0);
                }
                break;

            case "-":
                displayContent("Alright, next time", "white", 58);
                break;

            default:
                System.out.print("\n");
                drawTripleBorder();
                System.out.print("\n");
                displayErrorAscii();
                displayContent("Invalid input", "red", 0);
                break;
        }

        System.out.print("\n");
        drawTripleBorder();
        System.out.print("\n");
    }
}