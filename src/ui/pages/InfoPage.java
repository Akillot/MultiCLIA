package ui.pages;

import static ui.layout.BorderWork.drawHorizontalBorder;
import static ui.layout.BorderWork.numberOfSymbols;
import static ui.layout.ColorWork.displayColorCommand;
import static ui.layout.TextWork.contentAlignment;

public class InfoPage {
    public static void displayInfo() {
        displayColorCommand(contentAlignment(18) + "Current version:", "white", (byte) 18);
        displayColorCommand("0.4.9", "randomly", (byte) 18);
        displayColorCommand("Author: Nick Zozulia", "white", (byte) 18);
        drawHorizontalBorder(numberOfSymbols);
    }
}
