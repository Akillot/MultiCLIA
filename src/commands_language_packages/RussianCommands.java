package commands_language_packages;

import additional_packages.calculator.Calculator;
import ui.layout.AdditionalFunctions;
import ui.pages.GeneralSettingsPage;
import ui.pages.InfoPage;
import ui.pages.TimePage;

import java.util.Map;

public class RussianCommands {
    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put("калькулятор", Calculator::calculator);
        commandMap.put("кал", Calculator::calculator);
        commandMap.put("сфдсгдфещк", Calculator::calculator); // 'calculator' typed in Russian layout
        commandMap.put("сфд", Calculator::calculator); // 'cal' typed in Russian layout

        commandMap.put("настройки", GeneralSettingsPage::displayGeneralSettings);
        commandMap.put("нас", GeneralSettingsPage::displayGeneralSettings);
        commandMap.put("yfcnjhqr", GeneralSettingsPage::displayGeneralSettings); // 'settings' typed in Russian layout
        commandMap.put("ctn", GeneralSettingsPage::displayGeneralSettings); // 'set' typed in Russian layout

        commandMap.put("команды", AdditionalFunctions::displayListOfMenuCommands);
        commandMap.put("ком", AdditionalFunctions::displayListOfMenuCommands);
        commandMap.put("сщььфтвы", AdditionalFunctions::displayListOfMenuCommands);// 'commands' typed in Russian layout
        commandMap.put("сщь", AdditionalFunctions::displayListOfMenuCommands);
        commandMap.put("rjvfyls", AdditionalFunctions::displayListOfMenuCommands); // 'commands' typed in Russian layout

        commandMap.put("время", TimePage::displayCurrentTime);
        commandMap.put("дрфнз", TimePage::displayCurrentTime); // 'time' typed in Russian layout

        commandMap.put("информация", InfoPage::displayInfo);
        commandMap.put("инф", InfoPage::displayInfo);
        commandMap.put("byajhfrwbz", InfoPage::displayInfo); // 'info' typed in Russian layout

        commandMap.put("выход", AdditionalFunctions::exitProgram);
        commandMap.put("вых", AdditionalFunctions::exitProgram);
        commandMap.put("dscjr", AdditionalFunctions::exitProgram); // 'exit' typed in Russian layout
    }
}
