package ui.pages;

import commands_language_packages.PackageUniter;

import static ui.layout.AdditionalFunctions.displayTip;
import static ui.layout.AdditionalFunctions.scanner;
import static ui.layout.BorderWork.borderWidth;
import static ui.layout.BorderWork.drawFullTripleBorder;
import static ui.layout.ColorWork.displayColorCommand;
import static ui.layout.TextWork.*;

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
        //drawHorizontalBorder(numberOfSymbols);
        drawFullTripleBorder();

        if (!registry.executeCommand(nameOfFunction)) {
            displayColorCommand("Command not found", "red", (byte) 0);
            drawFullTripleBorder();
            //drawHorizontalBorder(numberOfSymbols);
        }
    }
}
