package core.command_handling_system;

import core.pages.SettingsPage;
import extensions.notes.NotesPage;
import core.logic.CommandManager;
import core.logic.DisplayManager;
import extensions.internet.browser.BrowserPage;
import core.pages.InfoPage;
import core.pages.StartPage;
import core.pages.TimePage;
import extensions.time.Reminder;

import java.util.Map;

import static core.logic.DisplayManager.*;
import static java.lang.System.out;

public class CommandHandler {

    public static String[] systemCmds = {
            "sys.cmds" ,"sys.setts", "sys.rerun", "sys.time",
            "sys.ip", "sys.info", "sys.exit", "sys.exitq",
            "sys.help", "sys.weather"};//Add command for sys.weather
    public static String[] extensionCmds = {
            "notepad", "browser", "reminder"};

    public static void registerCommands(Map<String, Runnable> commandMap) {
        commandMap.put(systemCmds[0], DisplayManager::displayCommandList);
        commandMap.put(systemCmds[1], SettingsPage::displaySettings);
        commandMap.put(systemCmds[2], StartPage::start);
        commandMap.put(systemCmds[3], TimePage::displayCurrentTime);
        commandMap.put(systemCmds[4], DisplayManager::displayUserIp);
        commandMap.put(systemCmds[5], () -> {
            try {
                InfoPage.displayInfo();
            } catch (InterruptedException e) {
                errorAscii();
                message("Error displaying info: " + e.getMessage(), "red",
                        58,0, out::println);
            }
        });
        commandMap.put(systemCmds[6], CommandManager::terminateProgramDefault);
        commandMap.put(systemCmds[7], CommandManager::terminateProgramQuick);
        commandMap.put(systemCmds[8], DisplayManager::displayCommandsDescription);

        commandMap.put(extensionCmds[0], NotesPage::displayNotepad);
        commandMap.put(extensionCmds[1], BrowserPage::browserStarter);
        commandMap.put(extensionCmds[2], Reminder::reminderStarted);
    }
}
