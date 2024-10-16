import static ui.layout.BasicFunc.displayContent;
import static ui.layout.BasicFunc.displayTip;
import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.TextFunc.alignmentLogic;
import static ui.layout.ThemesFunc.*;
        import static ui.pages.InfoPage.version;
import static ui.pages.MenuPage.displayMainMenuUi;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("\n");
        displayTheme();
        displayMarginBigBorder();

        displayContent("--------------------", "white", 58);
        displayContent("Version: " + version, "white", 58);
        displayContent("--------------------", "white", 58);
        displayTip("Enter " + "'" + BOLD + PURPLE + "commands" + RESET + "'\n"
                + alignmentLogic(58) + "to show list of\n"
                + alignmentLogic(58) + "commands", 58);
        displayContent("--------------------", "white", 58);
        System.out.println();
        while(true) {
            try {
                displayMainMenuUi();
            } catch (Exception ex) {
                displayMarginBigBorder();
                displayErrorAscii();
                displayContent("Error: " + ex.getMessage(), "red", 0);
            }
        }
    }
}