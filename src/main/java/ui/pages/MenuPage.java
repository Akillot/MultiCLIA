package ui.pages;

import commands_language_packages.PackageUnifier;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

import static ui.layout.BorderFunc.*;
import static ui.layout.ColorFunc.*;
import static ui.layout.TextFunc.*;
import static ui.layout.ThemesFunc.displayErrorAscii;

public class MenuPage {
    public static String nameOfFunction = "";
    public static double time = 0;
    public static Scanner scanner = new Scanner(System.in);

    public static void displayMainMenuUi() {
        PackageUnifier registry = new PackageUnifier();
        boolean commandFound = false;

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("\n");
                displayContent("Time is up! Please enter a command quickly.", "", 0);
            }
        };

        // Set the timer to trigger after 30 seconds
        timer.schedule(timerTask, 300000);
        while (!commandFound) {
            displaySlowMotionText(100, 58, true, "Search", ": ");
            nameOfFunction = scanner.nextLine().toLowerCase();
            System.out.print("\n");
            wrapText(nameOfFunction, borderWidth - 2);

            if (registry.executeCommand(nameOfFunction)) {
                commandFound = true;
                timer.cancel();
            } else {
                displayMarginBigBorder();
                displayErrorAscii();
                displayContent("Command not found. Please try again.\n", "red", 0);
                displayBigBorder();
                System.out.print("\n");
            }
        }
    }
}
