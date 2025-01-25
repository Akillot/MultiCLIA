package core.pages;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.choice;
import static core.logic.CommandManager.openUri;
import static core.configs.TextConfigs.*;

public class SupportPage {

    public static void displaySupportPage() {
        displaySupportMenu();
    }

    private static void displaySupportMenu() {
        modifyMessage('n',1);
        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                sysAcceptanceColor, sysRejectionColor, sysLayoutColor,58);

        choice("Buy-Me-A-Coffee",openUri("https://buymeacoffee.com/akillot"),
                sysMainColor,sysLayoutColor,sysRejectionColor);
        modifyMessage('n',1);
    }
}
