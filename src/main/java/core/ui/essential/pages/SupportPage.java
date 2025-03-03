package core.ui.essential.pages;

import static core.logic.CommandManager.choice;
import static core.logic.CommandManager.openUri;
import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.TextConfigs.*;
import static java.lang.System.out;

public class SupportPage {

    public static void displaySupportPage() {
        displaySupportMenu();
    }

    private static void displaySupportMenu() {
        marginBorder(1,2);
        message("Thank you for using MultiCLIA!\n" + alignment(getDefaultTextAlignment()) +
                "Let's keep making great things happen together!", layoutColor,getDefaultTextAlignment(),
                getDefaultDelay(),out::print);

        insertControlChars('n',1);
        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                acceptanceColor, rejectionColor, layoutColor,getDefaultTextAlignment());

        choice(getColor(layoutColor) + "☕" + getColor(mainColor) + " Buy Me A Coffee",openUri("https://buymeacoffee.com/akillot"),
                mainColor, layoutColor, rejectionColor);
        marginBorder(2,1);
    }
}
