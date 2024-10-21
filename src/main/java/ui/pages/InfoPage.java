package ui.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.CommandManager.openUri;
import static ui.layout.DisplayManager.*;
import static ui.layout.TextFunc.alignment;

public  class InfoPage {
    public static String version = "A-0.6.5";

    public static void displayInfo() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern(" dd-MM-yyyy" + " HH:mm");
        String formattedTime = localTime.format(myFormatter);
        System.out.println("\n");
        message("Current version:", "white", 58, false);
        message(version, "purple", 58, false);
        message("Author: Nick Zozulia", "white", 58, false);
        message("Current time: " + formattedTime, "white", 58, false);
        message("Memory used: " +
                ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                / (1000 * 1000) + "M"), "white", 58, false);

        System.out.print("\n");
        tip("Enter '+' to open and '-' to skip", 58);
        System.out.print(alignment(58) + WHITE + BOLD + "Github: " + RESET);
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "+":
                try {
                    openUri("https://github.com/Akillot/MultiCLIA");
                } catch (Exception e) {
                    displayMarginBigBorder();
                    errorAscii();
                }
                break;

            case "-":
                message("Alright, next time", "white", 58, false);
                System.out.print("\n");
                break;

            default:
                displayMarginBigBorder();
                errorAscii();
                break;
        }
        displayMarginBigBorder();
    }
}