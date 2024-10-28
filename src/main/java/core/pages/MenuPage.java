package core.pages;

import core.command_handling_system.PackageUnifier;

import static core.layout.BorderFunc.*;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.*;

public class MenuPage {
    public static void displayMainMenuUi() {
        PackageUnifier registry = new PackageUnifier();
        displaySlowMotionText(100, 56, false, true, "Search", ": ");
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