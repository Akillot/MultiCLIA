import layout.Logos;

import static layout.Stylization.drawFullTripleBorder;
import static layout.UserInterface.displayMainMenuUi;

public class Launcher {
    public static void main(String[] args) {
        drawFullTripleBorder();
        Logos.logoInitializer("default");
        drawFullTripleBorder();

        while (true) {
            try {
                displayMainMenuUi();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}
