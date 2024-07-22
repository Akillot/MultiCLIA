package Commands_LanguagePackages;

import calculator.Calculator;
import layout.UserInterface;

import java.util.Map;

public class CzechCommands {
    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put("kalkulačka", Calculator::calculator);
        commandMap.put("kal", Calculator::calculator);
        commandMap.put("rfdlhesho'v", Calculator::calculator); // 'kalkulačka' typed in English layout
        commandMap.put("rfd", Calculator::calculator); // 'kal' typed in English layout

        commandMap.put("nastavení", UserInterface::displaySettings);
        commandMap.put("nastaveni", UserInterface::displaySettings);
        commandMap.put("nas", UserInterface::displaySettings);
        commandMap.put("nfcgtdbp", UserInterface::displaySettings); // 'nastavení' typed in English layout
        commandMap.put("nfc", UserInterface::displaySettings); // 'nas' typed in English layout

        commandMap.put("příkazy", UserInterface::displayListOfMenuCommands);
        commandMap.put("prikazy", UserInterface::displayListOfMenuCommands);
        commandMap.put("prik", UserInterface::displayListOfMenuCommands);
        commandMap.put("ohrfkbd", UserInterface::displayListOfMenuCommands); // 'příkazy' typed in English layout

        commandMap.put("čas", UserInterface::displayCurrentTime);
        commandMap.put("cas", UserInterface::displayCurrentTime);
        commandMap.put("chf", UserInterface::displayCurrentTime); // 'cas' typed in English layout

        commandMap.put("informace", UserInterface::displayInfo);

        commandMap.put("konec", UserInterface::exitProgram);
        commandMap.put("kon", UserInterface::exitProgram);
        commandMap.put("lphfd", UserInterface::exitProgram); // 'konec' typed in English layout
    }
}
