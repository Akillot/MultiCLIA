package core.starter;

import core.pages.StartPage;

import static core.logic.TextConfigs.modifyMessage;

public class Launcher {
    public static void main(String[] args) {
        modifyMessage('n',8);
        StartPage.displayStartPage();
    }
}