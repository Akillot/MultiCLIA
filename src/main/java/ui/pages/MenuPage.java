package ui.pages;

import commands_language_packages.PackageUnifier;

import static ui.layout.BasicFunctions.*;
import static ui.layout.BorderWork.*;
import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.*;
import static ui.layout.ThemesWork.displayErrorAscii;

public class MenuPage {
    public static String nameOfFunction = "";

    public static void displayMainMenuUi() {
        PackageUnifier registry = new PackageUnifier();
        displaySlowMotionText(100, 58, true, "Search", ": ");
        String nameOfFunction = scanner.nextLine().toLowerCase();
        System.out.print("\n");
        wrapText(nameOfFunction, borderWidth - 2);

        if (!registry.executeCommand(nameOfFunction)) {
            System.out.print("\n");
            drawTripleBorder();
            System.out.print("\n");
            displayErrorAscii();
            displayColorMessage("Command not found\n", "red", 0);
            drawTripleBorder();
            System.out.print("\n");
        }
    }
}
