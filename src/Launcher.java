import static layout.Logos.logoInitializer;
import static layout.Stylization.drawFullTripleBorder;
import static layout.UserInterface.displayMainMenuUi;
import static layout.UserInterface.nameOfLogo;

public class Launcher {
    public static void main(String[] args) {
        //Eula.displayEula();
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
