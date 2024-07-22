import static layout.Stylization.displayDefaultLogo;
import static layout.Stylization.drawFullTripleBorder;
import static layout.UserInterface.displayMenu;

public class Launcher {
    public static void main(String[] args) {
        drawFullTripleBorder();
        displayDefaultLogo();
        drawFullTripleBorder();

        while (true) {
            try {
                displayMenu();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}