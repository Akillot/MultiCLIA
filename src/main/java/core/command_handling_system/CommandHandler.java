package core.command_handling_system;

import core.pages.SettingsPage;
import extensions.finance.CryptoPage;
import extensions.notes.NotesPage;
import core.logic.CommandManager;
import core.logic.DisplayManager;
import extensions.internet.SearcherPage;
import core.pages.InfoPage;
import core.pages.StartPage;
import extensions.time.clock.ClockPage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static core.logic.ColorConfigs.systemDefaultRed;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static String[] fullSystemCmds = {
            "cmds" ,"setts", "rerun",
            "ip", "info", "exit", "exitq",
            "help", "version"};

    public static String[] shortSystemCmds = {
            "c" ,"s", "rr",
            "ip", "i", "e", "eq",
            "h", "v"};

    public static String[] extensionCmds = {
            "notes", "searcher", "crypto", "clock"};

    public static void registerCommands(@NotNull Map<String, Runnable> commandMap) {
        for (int i = 0; i < fullSystemCmds.length; i++) {
            commandMap.put(fullSystemCmds[i], getCommandAction(i));
            commandMap.put(shortSystemCmds[i], getCommandAction(i));
        }

        commandMap.put("notes", NotesPage::displayNotepad);
        commandMap.put("searcher", SearcherPage::browserPage);
        commandMap.put("crypto", CryptoPage::exchangerPage);
        commandMap.put("clock", ClockPage::clockPage);
    }

    @Contract(pure = true)
    private static @NotNull Runnable getCommandAction(int index) {
        return switch (index) {
            case 0 -> DisplayManager::displayCommandList;
            case 1 -> SettingsPage::displaySettings;
            case 2 -> StartPage::displayStart;
            case 3 -> DisplayManager::displayUserIp;
            case 4 -> () -> {
                try {
                    InfoPage.displayInfo();
                } catch (InterruptedException e) {
                    errorAscii();
                    message("Error displaying info: " + e.getMessage(), systemDefaultRed, 58, 0, out::println);
                }
            };
            case 5 -> CommandManager::terminateProgramDefault;
            case 6 -> CommandManager::terminateProgramQuick;
            case 7 -> DisplayManager::displayCommandsDescription;
            case 8 -> DisplayManager::displayCurrentVersion;
            default -> throw new IllegalArgumentException("Invalid command index");
        };
    }

}
