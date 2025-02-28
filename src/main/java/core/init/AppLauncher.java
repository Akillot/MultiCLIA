package core.init;

import core.ui.essential.essential.pages.StartPage;

import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.essential.AppearanceConfigs.progressbarAnimation;
import static core.ui.essential.configs.essential.TextConfigs.insertControlChars;

public class AppLauncher {
    public static void main(String[] args) {
        clearTerminal();
        insertControlChars('n',1);
        progressbarAnimation("Processing");
        StartPage.displayStartPage();
    }
}