package commands_language_packages;

import additional_packages.notepad.NotepadLayout;
import additional_packages.math.CalculatorLayout;
import ui.layout.BasicFunc;
import ui.pages.BrowserPage;
import ui.pages.InfoPage;
import ui.pages.TimePage;

import java.util.Map;

import static ui.layout.BasicFunc.displayContent;
import static ui.layout.ThemesFunc.displayErrorAscii;

public class CommandHandler {
    //En-Cz-De-Ru-Fr-Es-tok
    public static String[] calculatorCommands = {
            "calculator", "kalkulačka", "rechner",
            "калькулятор", "calculatrice", "calculadora",
            "kule laso"};

    public static String[] basicFunctionsCommands = {
            "commands", "příkazy", "prikazy",
            "befehle", "commandes", "команды",
            "commandes", "comandos", "pali lawa"};

    public static String[] timeCommands = {
            "time", "čas", "cas", "zeit",
            "время", "temps", "tiempo", "tenpo"};

    public static String[] browserCommands = {
            "browser", "browse", "prohlížeč", "prohlizec",
            "браузер", "поисковик", "поиск", "navigateur",
            "navegador", "ilo lukin"};

    public static String[] infoCommands = {
            "info", "informace", "informationen", "инфо",
            "информация", "les informations", "l'information",
            "informations", "información", "informacion", "sona"};

    public static String[] notepadCommands = {
            "notepad", "zápisník", "zapisnik", "notizblock", "notizbuch",
            "merkzettel", "блокнот", "bloc-notes", "calepin", "bloc note",
            "cuaderno", "bloc de notas", "cuaderno de notas", "lipu toki"};

    public static String[] langsCommands = {
            "languages", "jazyky", "sprachen", "языки",
            "langues", "les langues", "idiomas", "lenguas",
            "toki"};

    public static String[] exitCommands = {
            "exit", "konec", "ausgang",
            "выход", "sortie", "sortir",
            "salida", "poka"};

    public static String[] allLangCommands = {
            "commands:languages", "commands:langs", "commands:lang"};

    public static void registerCommands(Map<String, Runnable> commandMap) {
        registerMultipleCommands(commandMap, allLangCommands, BasicFunc::displayAllLangCommands);
        registerMultipleCommands(commandMap, calculatorCommands, CalculatorLayout::calculator);
        registerMultipleCommands(commandMap, basicFunctionsCommands, BasicFunc::displayListOfMenuCommands);
        registerMultipleCommands(commandMap, timeCommands, TimePage::displayCurrentTime);
        registerMultipleCommands(commandMap, browserCommands, BrowserPage::browser);
        registerMultipleCommands(commandMap, infoCommands, () -> {
            try {
                InfoPage.displayInfo();
            } catch (InterruptedException e) {
                displayErrorAscii();
                displayContent("Error displaying info: " + e.getMessage(), "red", 0);
            }
        });
        registerMultipleCommands(commandMap, notepadCommands, NotepadLayout::displayNotepad);
        registerMultipleCommands(commandMap,langsCommands, BasicFunc::displayLangs);
        registerMultipleCommands(commandMap, exitCommands, BasicFunc::exitMultiClia);
    }

    private static void registerMultipleCommands(Map<String, Runnable> commandMap, String[] commands, Runnable action) {
        for (String command : commands) {
            commandMap.put(command, action);
        }
    }
}
