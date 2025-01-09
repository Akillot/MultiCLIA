package core.command_handling_system;

import core.pages.SettingsPage;
import core.pages.TimePage;
import extensions.finance.CryptoUI;
import core.logic.CommandManager;
import core.logic.DisplayManager;
import extensions.internet.SearcherUI;
import core.pages.InfoPage;
import core.pages.StartPage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static core.logic.AppearanceConfigs.getAnsi256Color;
import static core.logic.AppearanceConfigs.systemRejectionColor;
import static core.logic.TextConfigs.alignment;
import static core.logic.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static String[] fullSystemCmds = {
            "cmds" ,"setts", "rerun", "ip",
            "info", "exit", "help", "version",
            "clear", "time", "ports", "sysinfo"};

    public static String[] shortSystemCmds = {
            "/c" ,"/s", "/rr", "/ip",
            "/i", "/e", "/h", "/v",
            "/cl", "/t", "/p", "/si"};

    public static String[] extensionCmds = {
            "searcher", "crypto"};//Add notes in first place

    public static void registerCommands(@NotNull Map<String, Runnable> commandMap) {
        for (int i = 0; i < fullSystemCmds.length; i++) {
            commandMap.put(fullSystemCmds[i], getCommandAction(i));
            commandMap.put(shortSystemCmds[i], getCommandAction(i));
        }

        //commandMap.put("notes", NotesUI::displayNotesMenu);
        commandMap.put("searcher", SearcherUI::displaySearcherMenu);
        commandMap.put("crypto", CryptoUI::displayCryptoMenu);
    }

    @Contract(pure = true)
    private static @NotNull Runnable getCommandAction(int index) {
        return switch (index) {
            case 0 -> DisplayManager::displayCommandList;
            case 1 -> SettingsPage::displaySettingsPage;
            case 2 -> StartPage::mainMenuReload;
            case 3 -> DisplayManager::displayUserIp;
            case 4 -> () -> {
                try {
                    InfoPage.displayInfo();
                } catch (InterruptedException e) {
                    message("Error displaying this page: " + e.getMessage(),
                            systemRejectionColor, 58, 0, out::println);
                }
            };
            case 5 -> CommandManager::terminateProgram;
            case 6 -> DisplayManager::displayCommandsDescription;
            case 7 -> DisplayManager::displayCurrentVersion;
            case 8 -> DisplayManager::clearTerminal;
            case 9 -> TimePage::displayTimePage;
            case 10 -> DisplayManager::multiThreadedPortScanner;
            case 11 -> DisplayManager::displaySystemInfo;
            default -> throw new IllegalArgumentException(alignment(58)
                    + getAnsi256Color(systemRejectionColor) + "Invalid command index");
        };
    }
}
