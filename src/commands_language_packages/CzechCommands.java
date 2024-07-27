package commands_language_packages;

import additional_packages.calculator.Calculator;
import ui.layout.AdditionalFunctions;
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

        commandMap.put("příkazy", AdditionalFunctions::displayListOfMenuCommands);
        commandMap.put("prikazy", AdditionalFunctions::displayListOfMenuCommands);
        commandMap.put("prik", AdditionalFunctions::displayListOfMenuCommands);
        commandMap.put("ohrfkbd", AdditionalFunctions::displayListOfMenuCommands); // 'příkazy' typed in English layout

        commandMap.put("čas", TimePage::displayCurrentTime);
        commandMap.put("cas", TimePage::displayCurrentTime);
        commandMap.put("chf", TimePage::displayCurrentTime); // 'cas' typed in English layout

        commandMap.put("informace", InfoPage::displayInfo);

        commandMap.put("konec", AdditionalFunctions::exitProgram);
        commandMap.put("kon", AdditionalFunctions::exitProgram);
        commandMap.put("lphfd", AdditionalFunctions::exitProgram); // 'konec' typed in English layout
    }
}
