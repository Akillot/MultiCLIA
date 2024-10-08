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
        //En-Cz-De-Ru-Fr-Es-tok
        String[] calculatorCommands = {"calculator", "kalkulačka",
                "rechner", "калькулятор", "calculatrice", "calculadora", "kule laso"};
        String[] basicFunctionsCommands = {"commands", "příkazy", "prikazy",
                "befehle", "commandes", "команды", "commandes", "comandos", "pali lawa"};
        String[] timeCommands = {"time", "čas", "cas", "zeit", "время", "temps", "tiempo", "tenpo"};
        String[] browserCommands = {"browser", "browse", "prohlížeč", "prohlizec",
                "браузер", "поисковик", "поиск", "navigateur", "navegador", "ilo lukin"};
        String[] infoCommands = {"info", "informace", "informationen",
                "инфо", "информация", "les informations", "l'information", "informations",
                "información", "informacion", "sona"};

        String[] langsCommands = {"languages", "jazyky", "Sprachen", "sprachen", "языки", "langues",
                "les langues", "idiomas", "lenguas", "toki"};
        String[] exitCommands = {"exit", "konec", "ausgang", "выход", "sortie", "sortir", "salida", "poka"};

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
        registerMultipleCommands(commandMap,langsCommands,BasicFunctions::displaySupportedLanguages);
        registerMultipleCommands(commandMap, exitCommands, BasicFunctions::exitMultiClia);
    }

    private static void registerMultipleCommands(Map<String, Runnable> commandMap, String[] commands, Runnable action) {
        for (String command : commands) {
            commandMap.put(command, action);
        }
    }
}
