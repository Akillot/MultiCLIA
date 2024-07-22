package Commands_LanguagePackages;

import calculator.Calculator;
import layout.UserInterface;

import java.util.Map;

public class EnglishCommands {
    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put("calculator", Calculator::calculator);
        commandMap.put("cal", Calculator::calculator);

        commandMap.put("settings", UserInterface::displaySettings);
        commandMap.put("set", UserInterface::displaySettings);

        commandMap.put("commands", UserInterface::displayListOfMenuCommands);
        commandMap.put("com", UserInterface::displayListOfMenuCommands);

        commandMap.put("time", UserInterface::displayCurrentTime);

        commandMap.put("info", UserInterface::displayInfo);

        commandMap.put("exit", UserInterface::exitProgram);
    }
}
