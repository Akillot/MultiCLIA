
import static ui.layout.BasicFunc.displayTip;
import static ui.layout.BorderFunc.drawTripleBorder;
import static ui.layout.ColorFunc.displayContent;
import static ui.layout.TextFunc.contentAlignment;
import static ui.layout.ThemesFunc.displayErrorAscii;
import static ui.layout.ThemesFunc.displayLogo;
import static ui.pages.InfoPage.version;
import static ui.pages.MenuPage.displayMainMenuUi;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("\n");
        displayLogo();
        System.out.println("\n");
        drawTripleBorder();
        System.out.println("\n");
        displayContent("--------------------", "white", 58);
        displayContent("Version: " + version, "white", 58);
        displayContent("--------------------", "white", 58);
        displayTip("Enter 'commands'\n"
                + contentAlignment(58) + "to show list of\n"
                + contentAlignment(58) + "commands", 58);
        displayContent("--------------------", "white", 58);
        System.out.println();
            while(true) {
                try {
                    displayMainMenuUi();
                } catch (Exception ex) {
                    displayErrorAscii();
                    displayContent("Error: " + ex.getMessage(), "red", 0);
                }
            }
        }
    }
