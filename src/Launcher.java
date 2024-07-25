import static ui.layout.Logos.logoInitializer;
import static ui.layout.Stylization.drawFullTripleBorder;
import static ui.layout.UiLayout.nameOfLogo;
import static ui.pages.MenuPage.displayMainMenuUi;

public class Launcher {
    public static void main(String[] args) {
        drawFullTripleBorder();
        logoInitializer(nameOfLogo);
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
