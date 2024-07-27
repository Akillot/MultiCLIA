package ui.pages;

import static ui.layout.BorderWork.drawFullTripleBorder;
import static ui.layout.ColorWork.displayColorCommand;
import static ui.layout.TextWork.contentAlignment;

public class SetUpPage {
    public static void displaySetUpPage() {
        drawFullTripleBorder();
        displayColorCommand("\n" + contentAlignment(20)
                        + "Welcome to universal\n" + contentAlignment(27)
                        + "multifunctional application\n",
                "white", (byte) 20);
        displayColorCommand("First of all let`s do first setup.", "white", (byte) 58);
        displayColorCommand("\n" + contentAlignment(58) + "First Setup:", "white", (byte) 58);
    }
}
