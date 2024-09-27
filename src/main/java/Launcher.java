
import static ui.layout.BasicFunctions.displayTip;
import static ui.layout.BorderWork.drawTripleBorder;
import static ui.layout.ColorWork.displayColorMessage;
import static ui.layout.TextWork.contentAlignment;
import static ui.layout.ThemesWork.displayErrorAscii;
import static ui.layout.ThemesWork.displayLogo;
import static ui.pages.InfoPage.version;
import static ui.pages.MenuPage.displayMainMenuUi;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("\n");
        displayLogo();
        System.out.println("\n");
        drawTripleBorder();
        System.out.println("\n");
        displayColorMessage("--------------------", "white", 58);
        displayColorMessage("Version: " + version, "white", 58);
        displayColorMessage("--------------------", "white", 58);
        displayTip("Enter 'commands'\n"
                + contentAlignment(58) + "to show list of\n"
                + contentAlignment(58) + "commands", 58);
        displayColorMessage("--------------------", "white", 58);
        System.out.println();
            while(true) {
                try {
                    displayMainMenuUi();
                } catch (Exception ex) {
                    displayErrorAscii();
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
    }
