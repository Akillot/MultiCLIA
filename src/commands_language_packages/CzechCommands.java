package commands_language_packages;

import additional_packages.calculator.Calculator;
import ui.layout.UiLayout;
import ui.pages.GeneralSettingsPage;
import ui.pages.InfoPage;
import ui.pages.TimePage;

import java.util.Map;

public class CzechCommands {
    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put("kalkulačka", Calculator::calculator);
        commandMap.put("kal", Calculator::calculator);
        commandMap.put("rfdlhesho'v", Calculator::calculator); // 'kalkulačka' typed in English layout
        commandMap.put("rfd", Calculator::calculator); // 'kal' typed in English layout

        commandMap.put("nastavení", GeneralSettingsPage::displayGeneralSettings);
        commandMap.put("nastaveni", GeneralSettingsPage::displayGeneralSettings);
        commandMap.put("nas", GeneralSettingsPage::displayGeneralSettings);
        commandMap.put("nfcgtdbp", GeneralSettingsPage::displayGeneralSettings); // 'nastavení' typed in English layout
        commandMap.put("nfc", GeneralSettingsPage::displayGeneralSettings); // 'nas' typed in English layout

        commandMap.put("příkazy", UiLayout::displayListOfMenuCommands);
        commandMap.put("prikazy", UiLayout::displayListOfMenuCommands);
        commandMap.put("prik", UiLayout::displayListOfMenuCommands);
        commandMap.put("ohrfkbd", UiLayout::displayListOfMenuCommands); // 'příkazy' typed in English layout

        commandMap.put("čas", TimePage::displayCurrentTime);
        commandMap.put("cas", TimePage::displayCurrentTime);
        commandMap.put("chf", TimePage::displayCurrentTime); // 'cas' typed in English layout

        commandMap.put("informace", InfoPage::displayInfo);

        commandMap.put("konec", UiLayout::exitProgram);
        commandMap.put("kon", UiLayout::exitProgram);
        commandMap.put("lphfd", UiLayout::exitProgram); // 'konec' typed in English layout
    }
}
