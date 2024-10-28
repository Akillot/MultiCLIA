package core.command_handling_system;

import extansions.notepad.NotepadPage;
import extansions.math.CalculatorPage;
import core.layout.CommandManager;
import core.layout.DisplayManager;
import extansions.browser.BrowserPage;
import core.pages.InfoPage;
import core.pages.StartPage;
import core.pages.TimePage;

import java.util.Map;

import static core.layout.DisplayManager.*;

public class CommandHandler {

    public static String[] systemCmds = {
            "sys.rerun", "sys.cmds", "sys.time", "sys.info", "sys.color", "sys.exit", "sys.exitq"};
    public static String[] extensionCmds = {
            "calculator", "notepad", "browser"};

    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put(systemCmds[0], StartPage::start);
        commandMap.put(systemCmds[1], DisplayManager::commandsSideBySide);
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
        commandMap.put(systemCmds[5], CommandManager::exitProgramDefault);
        commandMap.put(systemCmds[6], CommandManager::exitProgramQuick);

        commandMap.put(extensionCmds[0], CalculatorPage::calculator);
        commandMap.put(extensionCmds[1], NotepadPage::displayNotepad);
        commandMap.put(extensionCmds[2], BrowserPage::browserStarter);
    }
}
