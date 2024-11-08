package core.command_handling_system;

import core.pages.SettingsPage;
import extensions.notepad.NotepadPage;
import extensions.math.CalculatorPage;
import core.layout.CommandManager;
import core.layout.DisplayManager;
import extensions.browser.BrowserPage;
import core.pages.InfoPage;
import core.pages.StartPage;
import core.pages.TimePage;

import java.util.Map;

import static core.layout.DisplayManager.*;

public class CommandHandler {

    public static String[] systemCmds = {
            "sys.cmds" ,"sys.setts", "sys.rerun", "sys.time",
            "sys.ip", "sys.info", "sys.exit", "sys.exitq",
            "sys.help"};
    public static String[] extensionCmds = {
            "calculator", "notepad", "browser"};

    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put(systemCmds[0], DisplayManager::commandList);
        commandMap.put(systemCmds[1], SettingsPage::displaySettings);
        commandMap.put(systemCmds[2], StartPage::start);
        commandMap.put(systemCmds[3], TimePage::displayCurrentTime);
        commandMap.put(systemCmds[4], CommandManager::getUserIp);
        commandMap.put(systemCmds[5], () -> {
            try {
                InfoPage.displayInfo();
            } catch (InterruptedException e) {
                errorAscii();
                message("Error displaying info: " + e.getMessage(), "red", 58,0);
            }
        });
        commandMap.put(systemCmds[6], CommandManager::exitProgramDefault);
        commandMap.put(systemCmds[7], CommandManager::exitProgramQuick);
        commandMap.put(systemCmds[8], DisplayManager::commandsDescription);

        commandMap.put(extensionCmds[0], CalculatorPage::calculator);
        commandMap.put(extensionCmds[1], NotepadPage::displayNotepad);
        commandMap.put(extensionCmds[2], BrowserPage::browserStarter);
    }
}
