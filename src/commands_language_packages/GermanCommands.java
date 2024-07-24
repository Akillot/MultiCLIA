package commands_language_packages;

import calculator.Calculator;
import layout.UserInterface;

import java.util.Map;

public class GermanCommands {
    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put("rechner", Calculator::calculator);
        commandMap.put("rech", Calculator::calculator);
        commandMap.put("rfxnher", Calculator::calculator); // 'rechner' typed in English layout
        commandMap.put("rfxh", Calculator::calculator); // 'rech' typed in English layout

        commandMap.put("einstellungen", UserInterface::displaySettingsUi);
        commandMap.put("ein", UserInterface::displaySettingsUi);
        commandMap.put("dpfmdmbhfyftufohfo", UserInterface::displaySettingsUi); // 'einstellungen' typed in English layout

        commandMap.put("befehle", UserInterface::displayListOfMenuCommands);
        commandMap.put("bef", UserInterface::displayListOfMenuCommands);
        commandMap.put("whfidh", UserInterface::displayListOfMenuCommands); // 'befehle' typed in English layout

        commandMap.put("zeit", UserInterface::displayCurrentTime);
        commandMap.put("ze", UserInterface::displayCurrentTime);
        commandMap.put("tdbv", UserInterface::displayCurrentTime); // 'zeit' typed in English layout

        commandMap.put("informationen", UserInterface::displayInfo);
        commandMap.put("info", UserInterface::displayInfo);
        commandMap.put("hofpsnbujpofo", UserInterface::displayInfo); // 'info' typed in English layout

        commandMap.put("ausgang", UserInterface::exitProgram);
        commandMap.put("aus", UserInterface::exitProgram);
        commandMap.put("tbgboh", UserInterface::exitProgram); // 'ausgang' typed in English layout
    }
}