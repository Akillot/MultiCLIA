package Commands_LanguagePackages;

import java.util.HashMap;
import java.util.Map;

public class PackageUniter {
    private final Map<String, Runnable> listOfMenuCommands = new HashMap<>();

    public PackageUniter() {
        initializeCommands();
    }

    private void initializeCommands() {
        EnglishCommands.registerCommands(listOfMenuCommands);
        CzechCommands.registerCommands(listOfMenuCommands);
        RussianCommands.registerCommands(listOfMenuCommands);
        GermanCommands.registerCommands(listOfMenuCommands);
    }

    public boolean executeCommand(String command) {
        Runnable function = listOfMenuCommands.get(command);
        if (function != null) {
            function.run();
            return true;
        }
        return false;
    }
}
