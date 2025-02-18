package core.init;

import core.ui.pages.StartPage;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.insertControlChars;
import static core.ui.DisplayManager.clearTerminal;

public class AppLauncher {
    public static void main(String[] args) {
        clearTerminal();
        insertControlChars('n',1);
        progressbarAnimation("Processing");
        StartPage.displayStartPage();
    }
}