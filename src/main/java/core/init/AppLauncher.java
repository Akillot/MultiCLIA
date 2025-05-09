package core.init;

import core.ui.pages.StartPage;

import static core.ui.configs.DisplayManager.clearTerminal;
import static core.ui.configs.TextConfigs.insertControlChars;

public class AppLauncher {
    public static void main(String[] args) {
        clearTerminal();
        insertControlChars('n',1);
        StartPage.displayMenu();
    }
}