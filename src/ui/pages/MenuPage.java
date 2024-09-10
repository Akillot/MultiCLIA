package ui.pages;

import commands_language_packages.PackageUniter;

import static ui.layout.BasicFunctions.displayTip;
import static ui.layout.BasicFunctions.scanner;
import static ui.layout.BorderWork.borderWidth;
import static ui.layout.BorderWork.drawTripleBorder;
import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.*;

public class MenuPage {
    public static String nameOfFunction = "";

    public static void displayMainMenuUi() {
        PackageUniter registry = new PackageUniter();

        displayTip("Enter 'commands'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "commands");

        drawTripleBorder();
        displayColorCommand("\n", "white", (byte) 0);
        displaySlowMotionText(100, 18, true, "Search", ": ");

        String nameOfFunction = scanner.nextLine().toLowerCase();
        displayColorCommand("\n", "white", (byte) 0);

        wrapText(nameOfFunction, borderWidth - 2);

        if (!registry.executeCommand(nameOfFunction)) {
            System.out.print("\n");
            displayColorCommand("Command not found\n", "red", (byte) 0);
            drawTripleBorder();
        }
    }
}
