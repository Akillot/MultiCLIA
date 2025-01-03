package core.command_handling_system;

import core.pages.SettingsPage;
import extensions.finance.CryptoUI;
import core.logic.CommandManager;
import core.logic.DisplayManager;
import extensions.internet.SearcherUI;
import core.pages.InfoPage;
import core.pages.StartPage;
import extensions.notes.NotesUI;
import extensions.time.clock.ClockUI;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static core.logic.AppearanceConfigs.systemRejectionColor;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static String[] fullSystemCmds = {
            "cmds" ,"setts", "rerun",
            "ip", "info", "exit", "exitq",
            "help", "version", "clear"};

    public static String[] shortSystemCmds = {
            "/c" ,"/s", "/rr",
            "/ip", "/i", "/e", "/eq",
            "/h", "/v", "/cl"};

    public static String[] extensionCmds = {
            "searcher", "crypto", "clock"};//Add notes in first place

    public static void registerCommands(@NotNull Map<String, Runnable> commandMap) {
        for (int i = 0; i < fullSystemCmds.length; i++) {
            commandMap.put(fullSystemCmds[i], getCommandAction(i));
            commandMap.put(shortSystemCmds[i], getCommandAction(i));
        }

        //commandMap.put("notes", NotesUI::displayNotesMenu);
        commandMap.put("searcher", SearcherUI::displaySearcherMenu);
        commandMap.put("crypto", CryptoUI::displayCryptoMenu);
        commandMap.put("clock", ClockUI::displayClockMenu);
    }

    @Contract(pure = true)
    private static @NotNull Runnable getCommandAction(int index) {
        return switch (index) {
            case 0 -> DisplayManager::displayCommandList;
            case 1 -> SettingsPage::displaySettings;
            case 2 -> StartPage::displayStartPage;
            case 3 -> DisplayManager::displayUserIp;
            case 4 -> () -> {
                try {
                    InfoPage.displayInfo();
                } catch (InterruptedException e) {
                    displayErrorAscii();
                    message("Error displaying this page: " + e.getMessage(), systemRejectionColor, 58, 0, out::println);
                }
            };
            case 5 -> CommandManager::terminateProgramDefault;
            case 6 -> CommandManager::terminateProgramQuick;
            case 7 -> DisplayManager::displayCommandsDescription;
            case 8 -> DisplayManager::displayCurrentVersion;
            case 9 -> CommandManager::clearTerminal;
            default -> throw new IllegalArgumentException("Invalid command index");
        };
    }
}
