package core.command_handling_system;

import core.pages.SettingsPage;
import extensions.finance.CurrencyExchangerPage;
import extensions.notes.NotesPage;
import core.logic.CommandManager;
import core.logic.DisplayManager;
import extensions.internet.browser.BrowserPage;
import core.pages.InfoPage;
import core.pages.StartPage;
import extensions.time.clock.ClockPage;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static String[] systemCmds = {
            "sys.cmds" ,"sys.setts", "sys.rerun",
            "sys.ip", "sys.info", "sys.exit", "sys.exitq",
            "sys.help"};
    public static String[] extensionCmds = {
            "notes", "browser", "crypto", "clock"};

    public static void registerCommands(@NotNull Map<String, Runnable> commandMap) {
        commandMap.put(systemCmds[0], DisplayManager::displayCommandList);
        commandMap.put(systemCmds[1], SettingsPage::displaySettings);
        commandMap.put(systemCmds[2], StartPage::displayStart);
        commandMap.put(systemCmds[3], DisplayManager::displayUserIp);
        commandMap.put(systemCmds[4], () -> {
            try {
                InfoPage.displayInfo();
            } catch (InterruptedException e) {
                errorAscii();
                message("Error displaying info: " + e.getMessage(),196,58,0,out::println);
            }
        });
        commandMap.put(systemCmds[5], CommandManager::terminateProgramDefault);
        commandMap.put(systemCmds[6], CommandManager::terminateProgramQuick);
        commandMap.put(systemCmds[7], DisplayManager::displayCommandsDescription);

        commandMap.put(extensionCmds[0], NotesPage::displayNotepad);
        commandMap.put(extensionCmds[1], BrowserPage::browserPage);
        commandMap.put(extensionCmds[2], CurrencyExchangerPage::exchangerPage);
        commandMap.put(extensionCmds[3], ClockPage::clockPage);
    }
}
