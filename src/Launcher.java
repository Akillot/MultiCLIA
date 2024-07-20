import static layout.UserInterface.*;

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