package ui.pages;

import static ui.layout.BasicFunctions.displayListOfSetup;
import static ui.layout.BasicFunctions.scanner;
import static ui.layout.BorderWork.drawHorizontalBorder;
import static ui.layout.ColorWork.displayColorCommand;
import static ui.layout.TextWork.contentAlignment;

public class SetupPage {
    public static void displaySetUpPage() {

        drawHorizontalBorder(6);
        System.out.print("\n");
        drawHorizontalBorder(6);

        displayColorCommand("Welcome to universal\n" + contentAlignment(27)
                        + "multifunctional application",
                "white", (byte) 20);
        drawHorizontalBorder(6);
        displayColorCommand("First of all let`s do first setup.", "white", (byte) 58);
        displayListOfSetup();
        System.out.print("\n");
        displayColorCommand("Enter: ", "white", (byte) 58);
        String command = scanner.nextLine().toLowerCase();
    }
}
