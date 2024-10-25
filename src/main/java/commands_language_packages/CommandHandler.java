package commands_language_packages;

import extansions.notepad.NotepadPage;
import extansions.math.CalculatorPage;
import ui.layout.CommandManager;
import ui.layout.DisplayManager;
import extansions.browser.BrowserPage;
import ui.pages.InfoPage;
import ui.pages.StartPage;
import ui.pages.TimePage;

import java.util.Map;

import static ui.layout.DisplayManager.*;

public class CommandHandler {

    public static String[] systemCommands = {
            "sys.reload", "sys.commands", "sys.time", "sys.info", "sys.color", "sys.exit"};
    public static String[] extensionCommands = {
            "calculator", "notepad", "browser"};

    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put("sys.reload", StartPage::start);
        commandMap.put("sys.commands", DisplayManager::menuCommands);
        commandMap.put("sys.time", TimePage::displayCurrentTime);
        commandMap.put("sys.info", () -> {
            try {
                InfoPage.displayInfo();
            } catch (InterruptedException e) {
                errorAscii();
                message("Error displaying info: " + e.getMessage(), "red", 58, false);
            }
        });
        commandMap.put("sys.color", DisplayManager::colors);
        commandMap.put("sys.exit", CommandManager::exitProgram);

        commandMap.put("calculator", CalculatorPage::calculator);
        commandMap.put("notepad", NotepadPage::displayNotepad);
        commandMap.put("browser", BrowserPage::browserStarter);
    }
}
