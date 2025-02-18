package core.commands;

import core.ui.pages.*;
import core.logic.CommandManager;
import core.ui.pages.time.CalendarPage;
import core.ui.pages.time.TimePage;
import core.ui.DisplayManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.alignment;
import static core.configs.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static String[] fullSystemCmds = {
            "cmds" ,"settings", "rerun", "help", "info",
            "clear", "time", "calendar", "network", "security", "cryptography",
            "terminal", "ai-assistance", "connection", "support", "exit"};

    public static String[] shortSystemCmds = {
            "/c" ,"/s", "/rr", "/h", "/i",
            "/cl", "/t", "/ca", "/n", "/sc", "/cr",
            "/ter", "/ai", "/cn", "/su", "/e"};

    public static String[] extensionCmds = {};

    public static void registerCommands(@NotNull Map<String, Runnable> commandMap) {
        for (int i = 0; i < fullSystemCmds.length; i++) {
            commandMap.put(fullSystemCmds[i], getCommandAction(i));
            commandMap.put(shortSystemCmds[i], getCommandAction(i));
        }
    }

    @Contract(pure = true)
    private static @NotNull Runnable getCommandAction(int index) {
        return switch (index) {
            case 0 -> DisplayManager::displayCommandList;
            case 1 -> SettingsPage::displaySettingsPage;
            case 2 -> CommandManager::mainMenuRerun;
            case 3 -> DisplayManager::displayCommandsDescription;
            case 4 -> () -> {
                try {
                    InfoPage.displayInfoPage();
                } catch (InterruptedException e) {
                    message("Error displaying this page: " + e.getMessage(),
                            sysRejectionColor, getDefaultTextAlignment(), 0, out::println);
                }
            };
            case 5 -> DisplayManager::clearTerminal;
            case 6 -> TimePage::displayTimePage;
            case 7 -> CalendarPage::displayCalendarPage;
            case 8 -> NetworkPage::displayNetworkPage;
            case 9 -> SecurityPage::displaySecurityPage;
            case 10 -> CryptographyPage::displayCryptographyPage;
            case 11 -> TerminalPage::displayTerminalPage;
            case 12 -> AiPage::displayPage;
            case 13 -> ConnectionPage::displayPage;
            case 14 -> SupportPage::displaySupportPage;
            case 15 -> ExitPage::displayExitPage;
            default -> throw new IllegalArgumentException(alignment(getDefaultTextAlignment())
                    + getColor(sysRejectionColor) + "Invalid command index");
        };
    }
}