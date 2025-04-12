package core.init;

import core.ui.essential.pages.StartPage;

import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.appearance.TextConfigs.insertControlChars;

public class AppLauncher {
    public static void main(String[] args) {
        clearTerminal();
        insertControlChars('n',1);
        StartPage.displayMenu();
    }
}