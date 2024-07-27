package ui.pages;

import static ui.layout.AdditionalFunctions.displayListOfSetup;
import static ui.layout.AdditionalFunctions.scanner;
import static ui.layout.ColorWork.displayColorCommand;
import static ui.layout.TextWork.contentAlignment;

public class SetUpPage {
    public static void displaySetUpPage() {
        displayColorCommand("Welcome to universal\n" + contentAlignment(27)
                        + "multifunctional application\n",
                "white", (byte) 20);
        displayColorCommand("First of all let`s do first setup.", "white", (byte) 58);
        displayListOfSetup();
        System.out.print("\n");
        displayColorCommand("Enter: ", "white", (byte) 58);
        String command = scanner.nextLine().toLowerCase();
    }
}
