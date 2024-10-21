package ui.pages;

import commands_language_packages.PackageUnifier;

import static ui.layout.BorderFunc.*;
import static ui.layout.DisplayManager.*;
import static ui.layout.TextFunc.*;


public class MenuPage {
    public static void displayMainMenuUi() {
        PackageUnifier registry = new PackageUnifier();
        displaySlowMotionText(100, 58, true, "Search", ": ");
        String nameOfFunction = scanner.nextLine().toLowerCase();
        System.out.print("\n");
        wrapText(nameOfFunction, borderWidth - 2);

        if (!registry.executeCommand(nameOfFunction)) {
            System.out.print("\n\n");
            errorAscii();
            displayMarginBigBorder();
        }
    }
}