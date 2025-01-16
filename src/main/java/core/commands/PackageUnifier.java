package core.commands;

import java.util.HashMap;
import java.util.Map;

public class PackageUnifier {
    private final Map<String, Runnable> listOfMenuCommands = new HashMap<>();

    public PackageUnifier() {
        initializeCommands();
    }

    private void initializeCommands() {
        CommandHandler.registerCommands(listOfMenuCommands);
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
