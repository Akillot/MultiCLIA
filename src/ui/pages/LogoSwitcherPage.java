package ui.pages;

import ui.layout.LogoWork;

import static ui.layout.AdditionalFunctions.displayTip;
import static ui.layout.AdditionalFunctions.scanner;
import static ui.layout.BorderWork.borderWidth;
import static ui.layout.TextWork.*;

public class LogoSwitcherPage {
    public static void logoSwitcherUi() {
        displayTip("Enter 'logos'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "all logos");

        displaySlowMotionText(200, 18, true, "Search", ": ");

        String nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2);
        LogoWork.logoInitializer(nameOfFunction);
    }
}
