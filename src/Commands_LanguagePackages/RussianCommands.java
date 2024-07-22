package Commands_LanguagePackages;

import calculator.Calculator;
import layout.UserInterface;

import java.util.Map;

public class RussianCommands {
    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put("калькулятор", Calculator::calculator);
        commandMap.put("кал", Calculator::calculator);
        commandMap.put("сфдсгдфещк", Calculator::calculator); // 'calculator' typed in Russian layout
        commandMap.put("сфд", Calculator::calculator); // 'cal' typed in Russian layout

        commandMap.put("настройки", UserInterface::displaySettings);
        commandMap.put("нас", UserInterface::displaySettings);
        commandMap.put("yfcnjhqr", UserInterface::displaySettings); // 'settings' typed in Russian layout
        commandMap.put("ctn", UserInterface::displaySettings); // 'set' typed in Russian layout

        commandMap.put("команды", UserInterface::displayListOfMenuCommands);
        commandMap.put("ком", UserInterface::displayListOfMenuCommands);
        commandMap.put("сщььфтвы", UserInterface::displayListOfMenuCommands); // 'commands' typed in Russian layout
        commandMap.put("rjvfyls", UserInterface::displayListOfMenuCommands); // 'commands' typed in Russian layout


        commandMap.put("время", UserInterface::displayCurrentTime);
        commandMap.put("дрфнз", UserInterface::displayCurrentTime); // 'time' typed in Russian layout

        commandMap.put("информация", UserInterface::displayInfo);
        commandMap.put("инф", UserInterface::displayInfo);
        commandMap.put("byajhfrwbz", UserInterface::displayInfo); // 'info' typed in Russian layout

        commandMap.put("выход", UserInterface::exitProgram);
        commandMap.put("вых", UserInterface::exitProgram);
        commandMap.put("dscjr", UserInterface::exitProgram); // 'exit' typed in Russian layout
    }
}
