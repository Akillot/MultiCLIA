package commands_language_packages;

import default_extansions.notepad.NotepadLayout;
import default_extansions.math.CalculatorLayout;
import ui.layout.CommandManager;
import ui.layout.DisplayManager;
import ui.pages.BrowserPage;
import ui.pages.InfoPage;
import ui.pages.TimePage;

import java.util.Map;

import static ui.layout.DisplayManager.errorAscii;
import static ui.layout.DisplayManager.message;

public class CommandHandler {
    //En-Cz-De-Ru-Uk-Fr-Es-tok-Pl
    public static String[] calculatorCommands = {
            "calculator", "kalkulačka", "rechner", "калькулятор",
            "calculatrice", "calculadora", "kule laso", "kalkulator"};

    public static String[] basicFunctionsCommands = {
            "commands", "příkazy", "prikazy", "befehle", "commandes",
            "команды", "команди", "commandes", "comandos", "pali lawa",
            "komendy"};

    public static String[] timeCommands = {
            "time", "čas", "cas", "zeit", "время",
            "час", "temps", "tiempo", "tenpo", "czas"};

    public static String[] browserCommands = {
            "browser", "browse", "prohlížeč", "prohlizec", "браузер",
            "поисковик", "поиск","пошуковик", "пошук", "navigateur",
            "navegador", "ilo lukin", "przeglądarka", "przegladarka"};

    public static String[] infoCommands = {
            "info", "informace", "informationen", "инфо", "информация",
            "інфо", "інформація", "les informations", "l'information", "informations",
            "información", "informacion", "sona", "informacja", "informacje"};

    public static String[] notepadCommands = {
            "notepad", "zápisník", "zapisnik", "notizblock", "notizbuch",
            "merkzettel", "блокнот", "bloc-notes", "calepin", "bloc note",
            "cuaderno", "bloc de notas", "cuaderno de notas", "lipu toki"};

    public static String[] langsCommands = {
            "langs", "languages", "jazyky", "sprachen", "языки",
            "мови", "langues", "les langues", "idiomas", "lenguas",
            "toki", "języki", "jezyki"};

    public static String[] exitCommands = {
            "exit", "konec", "ausgang", "выход", "вихід",
            "sortie", "sortir", "salida", "poka", "wyjście",
            "wyjscie"};

    public static String[] combinedCommands(String[] prefixCommands, String[] suffixCommands) {
        String[] result = new String[prefixCommands.length * suffixCommands.length];
        int index = 0;
        for (String prefix : prefixCommands) {
            for (String suffix : suffixCommands) {
                result[index++] = prefix + "." + suffix;
            }
        }
        return result;
    }

    public static void registerCommands(Map<String, Runnable> commandMap) {
        registerMultipleCommands(commandMap, calculatorCommands, CalculatorLayout::calculator);
        registerMultipleCommands(commandMap, basicFunctionsCommands, DisplayManager::menuCommands);
        registerMultipleCommands(commandMap, timeCommands, TimePage::displayCurrentTime);
        registerMultipleCommands(commandMap, browserCommands, BrowserPage::browser);
        registerMultipleCommands(commandMap, infoCommands, () -> {
            try {
                InfoPage.displayInfo();
            } catch (InterruptedException e) {
                errorAscii();
                message("Error displaying info: " + e.getMessage(), "red", 58);
            }
        });
        registerMultipleCommands(commandMap, notepadCommands, NotepadLayout::displayNotepad);
        registerMultipleCommands(commandMap, langsCommands, DisplayManager::langs);
        registerMultipleCommands(commandMap, exitCommands, CommandManager::exitProgram);

        String[] combinedLangCommands = combinedCommands(basicFunctionsCommands, langsCommands);
        registerMultipleCommands(commandMap, combinedLangCommands, DisplayManager::langCommands);
    }

    private static void registerMultipleCommands(Map<String, Runnable> commandMap, String[] commands, Runnable action) {
        for (String command : commands) {
            commandMap.put(command, action);
        }
    }
}
