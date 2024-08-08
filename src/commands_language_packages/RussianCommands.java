package commands_language_packages;

import additional_packages.calculator.Calculator;
import ui.layout.BasicFunctions;
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

        commandMap.put("команды", BasicFunctions::displayListOfMenuCommands);
        commandMap.put("ком", BasicFunctions::displayListOfMenuCommands);
        commandMap.put("сщььфтвы", BasicFunctions::displayListOfMenuCommands);// 'commands' typed in Russian layout
        commandMap.put("сщь", BasicFunctions::displayListOfMenuCommands);
        commandMap.put("rjvfyls", BasicFunctions::displayListOfMenuCommands); // 'commands' typed in Russian layout

        commandMap.put("время", TimePage::displayCurrentTime);
        commandMap.put("дрфнз", TimePage::displayCurrentTime); // 'time' typed in Russian layout

        commandMap.put("информация", InfoPage::displayInfo);
        commandMap.put("инф", InfoPage::displayInfo);
        commandMap.put("byajhfrwbz", InfoPage::displayInfo); // 'info' typed in Russian layout

        commandMap.put("выход", BasicFunctions::exitProgram);
        commandMap.put("вых", BasicFunctions::exitProgram);
        commandMap.put("dscjr", BasicFunctions::exitProgram); // 'exit' typed in Russian layout
    }
}
