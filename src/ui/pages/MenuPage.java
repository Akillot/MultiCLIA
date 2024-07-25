package ui.pages;

import commands_language_packages.PackageUniter;

import static ui.layout.Stylization.*;
import static ui.layout.UiLayout.displayTip;
import static ui.layout.UiLayout.scanner;

public class MenuPage {
    public static String nameOfFunction = "";

    public static void displayMainMenuUi() {
        PackageUniter registry = new PackageUniter();

        displayTip("Enter 'commands'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "commands");

        displaySlowMotionText(100, 18, true, "Search", ": ");

        String nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2);
        drawFullTripleBorder();

        if (!registry.executeCommand(nameOfFunction)) {
            displayColorCommand("Command not found", "red", (byte) 0);
            drawFullTripleBorder();
        }
    }
}
