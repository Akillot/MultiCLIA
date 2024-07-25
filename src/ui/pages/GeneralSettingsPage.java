package ui.pages;

import ui.layout.UiLayout;

import java.util.HashMap;

import static ui.layout.Stylization.*;
import static ui.layout.UiLayout.displayTip;
import static ui.layout.UiLayout.scanner;
import static ui.pages.MenuPage.nameOfFunction;

public class GeneralSettingsPage {

    public static void displayGeneralSettings() {
        HashMap<String, Runnable> listOfSettings = new HashMap<>();

        listOfSettings.put("commands", UiLayout::displayListOfSettings);
        listOfSettings.put("settings value", UiLayout::displayListOfMenuCommands);
        listOfSettings.put("logo", LogoSwitcher::logoSwitcherUi);
        listOfSettings.put("border", LogoSwitcher::logoSwitcherUi); //In progress
        listOfSettings.put("delay", LogoSwitcher::logoSwitcherUi); //In progress
        listOfSettings.put("exit", UiLayout::exitBlock);

        displaySlowMotionText(100, 8, false, "Settings\n", "");

        drawFullTripleBorder();
        displayTip("Enter 'commands'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "commands");

        displaySlowMotionText(100, 18, true, "Search", ": ");

        nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2); //Wrapping the command from user
        drawFullTripleBorder();

        if (listOfSettings.containsKey(nameOfFunction)) {
            listOfSettings.get(nameOfFunction).run();
        } else {
            drawFullTripleBorder();
            displayColorCommand("Command not found", "red", (byte) 0);
            drawFullTripleBorder();
        }
    }
}
