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

    public static String[] systemCmds = {
            "sys.reload", "sys.cmds", "sys.time", "sys.info", "sys.color", "sys.exit"};
    public static String[] extensionCmds = {
            "calculator", "notepad", "browser"};

    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put(systemCmds[0], StartPage::start);
        commandMap.put(systemCmds[1], DisplayManager::displayCommandsSideBySide);
        commandMap.put(systemCmds[2], TimePage::displayCurrentTime);
        commandMap.put(systemCmds[3], () -> {
            try {
                InfoPage.displayInfo();
            } catch (InterruptedException e) {
                errorAscii();
                message("Error displaying info: " + e.getMessage(), "red", 58);
            }
        });
        commandMap.put(systemCmds[4], DisplayManager::colors);
        commandMap.put(systemCmds[5], CommandManager::exitProgram);

        commandMap.put(extensionCmds[0], CalculatorPage::calculator);
        commandMap.put(extensionCmds[1], NotepadPage::displayNotepad);
        commandMap.put(extensionCmds[2], BrowserPage::browserStarter);
    }
}
