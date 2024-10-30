package core.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static core.layout.BorderFunc.displayMarginBigBorder;
import static core.layout.ColorFunc.*;
import static core.layout.CommandManager.openUri;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.alignment;
import static java.lang.System.out;

public  class InfoPage {
    public static String version = "A-0.6.5";

    public static void displayInfo() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern(" dd-MM-yyyy" + " HH:mm");
        String formattedTime = localTime.format(myFormatter);
        out.println("\n");
        message("Current version:", "white", 58);
        message(version, "purple", 58);
        message("Author: Nick Zozulia", "white", 58);
        message("Current time: " + formattedTime, "white", 58);
        message("Memory used: " +
                ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                / (1000 * 1000) + "M"), "white", 58);

        out.print("\n");
        tip("Enter '+' to open and '-' to skip", 58);
        out.print(alignment(58) + WHITE + BOLD + "Github: " + RESET);
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
                message("Alright, next time", "white", 58);
                out.print("\n");
                break;

            default:
                displayMarginBigBorder();
                errorAscii();
                break;
        }
        displayMarginBigBorder();
    }
}