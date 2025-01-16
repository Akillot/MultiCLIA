package core.commands;

import core.pages.*;
import extensions.finance.CryptoUI;
import core.logic.CommandManager;
import core.ui.DisplayManager;
import extensions.internet.SearcherUI;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static core.configs.AppearanceConfigs.getAnsi256Color;
import static core.configs.AppearanceConfigs.systemRejectionColor;
import static core.configs.TextConfigs.alignment;
import static core.configs.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static String[] fullSystemCmds = {
            "cmds" ,"setts", "rerun", "ip",
            "info", "help", "clear", "time",
            "ports", "appinfo", "cryptography", "exit"};

    public static String[] shortSystemCmds = {
            "/c" ,"/s", "/rr", "/ip",
            "/i", "/h", "/cl", "/t",
            "/p", "/ai", "/cr", "/e"};

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
            case 5 -> DisplayManager::displayCommandsDescription;
            case 6 -> DisplayManager::clearTerminal;
            case 7 -> TimePage::displayTimePage;
            case 8 -> DisplayManager::multiThreadedPortScanner;
            case 9 -> DisplayManager::displayAppInfo;
            case 10 -> CryptographyPage::displayEncryptionPage;
            case 11 -> CommandManager::terminateProgram;
            default -> throw new IllegalArgumentException(alignment(58)
                    + getAnsi256Color(systemRejectionColor) + "Invalid command index");
        };
    }
}