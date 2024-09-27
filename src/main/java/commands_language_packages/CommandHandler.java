package commands_language_packages;

import additional_packages.math.CalculatorLayout;
import ui.layout.BasicFunctions;
import ui.pages.BrowserPage;
import ui.pages.InfoPage;
import ui.pages.TimePage;

import java.util.Map;

import static ui.layout.ThemesWork.displayErrorAscii;

public class CommandHandler {
    public static void registerCommands(Map<String, Runnable> commandMap) {
        String[] calculatorCommands = {"calculator", "kalkulačka",
                "rechner", "калькулятор", "calculatrice"};
        String[] basicFunctionsCommands = {"commands", "příkazy", "prikazy",
                "befehle", "commandes", "команды", "commandes"};
        String[] timeCommands = {"time", "čas", "cas", "zeit", "время", "temps"};
        String[] browserCommands = {"browser", "browse", "prohlížeč", "prohlizec",
                "браузер", "поисковик", "поиск", "navigateur"};
        String[] infoCommands = {"info", "informace", "informationen",
                "инфо", "информация", "les informations", "l'information", "informations"};
        String[] exitCommands = {"exit", "konec", "ausgang", "выход", "sortie", "sortir"};

        registerMultipleCommands(commandMap, calculatorCommands, CalculatorLayout::calculator);
        registerMultipleCommands(commandMap, basicFunctionsCommands, BasicFunctions::displayListOfMenuCommands);
        registerMultipleCommands(commandMap, timeCommands, TimePage::displayCurrentTime);
        registerMultipleCommands(commandMap, browserCommands, BrowserPage::browser);

        registerMultipleCommands(commandMap, infoCommands, () -> {
            try {
                InfoPage.displayInfo();
            } catch (InterruptedException e) {
                displayErrorAscii();
                System.err.println("Error displaying info: " + e.getMessage());
            }
        });
        registerMultipleCommands(commandMap, exitCommands, BasicFunctions::exitMultiClia);
    }

    private static void registerMultipleCommands(Map<String, Runnable> commandMap, String[] commands, Runnable action) {
        for (String command : commands) {
            commandMap.put(command, action);
        }
    }
}
