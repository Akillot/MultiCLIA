package commands_language_packages;

import calculator.Calculator;
import layout.UserInterface;

import java.util.Map;

public class EnglishCommands {
    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put("calculator", Calculator::calculator);
        commandMap.put("cal", Calculator::calculator);

        commandMap.put("settings", UserInterface::displaySettingsUi);
        commandMap.put("set", UserInterface::displaySettingsUi);

        commandMap.put("commands", UserInterface::displayListOfMenuCommands);
        commandMap.put("com", UserInterface::displayListOfMenuCommands);

        commandMap.put("time", UserInterface::displayCurrentTime);

        commandMap.put("info", UserInterface::displayInfo);

        commandMap.put("exit", UserInterface::exitProgram);
    }
}
