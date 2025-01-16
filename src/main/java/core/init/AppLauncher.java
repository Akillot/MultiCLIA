package core.init;

import core.pages.StartPage;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.modifyMessage;

public class AppLauncher {
    public static void main(String[] args) {
        modifyMessage('n',8);
        progressbarAnimation("Processing");
        StartPage.displayStartPage();
    }
}