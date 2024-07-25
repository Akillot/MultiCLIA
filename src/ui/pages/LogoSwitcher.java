package ui.pages;

import ui.layout.Logos;

import static ui.layout.Stylization.*;
import static ui.layout.UiLayout.displayTip;
import static ui.layout.UiLayout.scanner;

public class LogoSwitcher {
    public static void logoSwitcherUi() {
        displayTip("Enter 'logos'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "all logos");

        displaySlowMotionText(200, 18, true, "Search", ": ");

        String nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2);

        Logos.logoInitializer(nameOfFunction);
        drawFullTripleBorder();
    }
}
