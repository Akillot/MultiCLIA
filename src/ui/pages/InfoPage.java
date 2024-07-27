package ui.pages;

import static ui.layout.BorderWork.drawHorizontalBorder;
import static ui.layout.BorderWork.numberOfSymbols;
import static ui.layout.ColorWork.displayColorCommand;
import static ui.layout.TextWork.contentAlignment;

public class InfoPage {
    public static void displayInfo() {
        displayColorCommand(contentAlignment(58) + "Current version:", "white", (byte) 58);
        displayColorCommand("0.4.9", "randomly", (byte) 58);
        displayColorCommand("Author: Nick Zozulia", "white", (byte) 58);
        drawHorizontalBorder(numberOfSymbols);
    }
}
