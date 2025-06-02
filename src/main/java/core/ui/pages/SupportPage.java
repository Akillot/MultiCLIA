package core.ui.pages;

import static core.CommandManager.choice;
import static core.CommandManager.openUri;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class SupportPage {

    public static void displaySupportPage() {
        marginBorder(1,2);

        message("Thank you for using MultiCLIA!\n" + alignment(getDefaultTextAlignment()) +
                "Let's keep making great things happen together!", getLayoutColor(), getDefaultTextAlignment(),
                getDefaultDelay(),out::print);

        insertControlChars('n',1);
        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                getAcceptanceColor(), getRejectionColor(), getLayoutColor(),getDefaultTextAlignment());

        choice(getColor(getLayoutColor()) + "☕" + getColor(getMainColor()) + " Buy Me A Coffee",openUri("https://buymeacoffee.com/akillot"),
                getMainColor(), getLayoutColor(), getRejectionColor());
        marginBorder(2,1);
    }
}
