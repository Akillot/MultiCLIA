package ui.pages;

import static ui.layout.BasicFunctions.displayListOfSetup;
import static ui.layout.BasicFunctions.scanner;
import static ui.layout.BorderWork.drawHorizontalBorder;
import static ui.layout.ColorWork.displayColorCommand;
import static ui.layout.TextWork.contentAlignment;

public class SetUpPage {
    public static void displaySetUpPage() {
      /*
        displayColorCommand("  __  __           _   _     _    _____   _        _____            ", "randomly", (byte)0);
        displayColorCommand(" |  \\/  |         | | | |   (_)  / ____| | |      |_   _|     /\\    ", "randomly", (byte)0);
        displayColorCommand(" | \\  / |  _   _  | | | |_   _  | |      | |        | |      /  \\  ", "randomly", (byte)0);
        displayColorCommand(" | |\\/| | | | | | | | | __| | | | |      | |        | |     / /\\ \\  ", "randomly", (byte)0);
        displayColorCommand(" | |  | | | |_| | | | | |_  | | | |____  | |____   _| |_   / ____ \\ ", "randomly", (byte)0);
        displayColorCommand(" |_|  |_|  \\__,_| |_|  \\__| |_|  \\_____| |______| |_____| /_/    \\_\\", "randomly", (byte)0);
       */


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
