package core.pages;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.choice;
import static core.logic.CommandManager.openUri;
import static core.configs.TextConfigs.*;
import static java.lang.System.out;

public class SupportPage {

    public static void displaySupportPage() {
        displaySupportMenu();
    }

    private static void displaySupportMenu() {
        marginBorder(1,2);
        message("I'm thrilled that you're using MultiCLIA! \uD83C\uDF89\uD83C\uDF88 " +
                "Let's keep making great things happen together! \uD83D\uDE80✨",sysLayoutColor,getDefaultTextAlignment(),
                getDefaultDelay(),out::print);

        insertControlChars('n',1);
        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                sysAcceptanceColor, sysRejectionColor, sysLayoutColor,getDefaultTextAlignment());

        choice(getColor(sysLayoutColor) + "☕" + getColor(sysMainColor) + " Buy Me A Coffee",openUri("https://buymeacoffee.com/akillot"),
                sysMainColor,sysLayoutColor,sysRejectionColor);
        marginBorder(2,1);
    }
}
