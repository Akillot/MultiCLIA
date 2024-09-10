import static ui.pages.InfoPage.version;
import static ui.layout.BorderWork.drawTripleBorder;
import static ui.layout.ThemesWork.*;
import static ui.layout.ColorWork.displayColorCommand;
import static ui.pages.MenuPage.displayMainMenuUi;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("\n");
        displayColorCommand("Version: " + version, "white", (byte) 50);
        drawTripleBorder();
        displayLogo();
        drawTripleBorder();

        while (true) {
            try {
                displayMainMenuUi();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}
