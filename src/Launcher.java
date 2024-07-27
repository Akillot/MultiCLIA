import static ui.layout.BorderWork.drawFullTripleBorder;
import static ui.layout.LogoWork.logoInitializer;
import static ui.layout.LogoWork.nameOfLogo;
import static ui.pages.MenuPage.displayMainMenuUi;

public class Launcher {
    public static void main(String[] args) {
        //displaySetUpPage();
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
