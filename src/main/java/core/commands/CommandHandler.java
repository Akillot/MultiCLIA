package core.commands;

import core.ui.essential.essential.pages.ExitPage;
import core.ui.essential.essential.pages.InfoPage;
import core.ui.essential.essential.pages.SettingsPage;
import core.ui.essential.essential.pages.SupportPage;
import core.logic.CommandManager;
import core.ui.extensions.ai.AiPage;
import core.ui.extensions.connection.ConnectionPage;
import core.ui.extensions.cryptography.CryptographyPage;
import core.ui.extensions.network.NetworkPage;
import core.ui.extensions.security.SecurityPage;
import core.ui.extensions.terminal.emulation.TerminalPage;
import core.ui.extensions.time.CalendarPage;
import core.ui.extensions.time.TimePage;
import core.ui.essential.configs.essential.DisplayManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static core.ui.essential.configs.essential.AppearanceConfigs.*;
import static core.ui.essential.configs.essential.TextConfigs.alignment;
import static core.ui.essential.configs.essential.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static final String[] fullCmds = {
            "list" ,"config", "restart", "help", "info",
            "clear", "date", "calendar", "ifconfig", "security", "crypt",
            "terminal", "ai", "connection", "support", "quit"};

    public static final String[] shortCmds = {
            "/ls" ,"/сfg", "/rs", "/h", "/i",
            "/cl", "/dt", "/cld", "/ifc", "/sec", "/cr",
            "/term", "/a", "/cn", "/sup", "/q"};

    public static String[] extensionCmds = {};

    public static void registerCommands(@NotNull Map<String, Runnable> commandMap) {
        for (int i = 0; i < fullCmds.length; i++) {
            commandMap.put(fullCmds[i], getCommandAction(i));
            commandMap.put(shortCmds[i], getCommandAction(i));
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
            case 12 -> AiPage::displayAiPage;
            case 13 -> ConnectionPage::displayPage;
            case 14 -> SupportPage::displaySupportPage;
            case 15 -> ExitPage::displayExitPage;
            default -> throw new IllegalArgumentException(alignment(getDefaultTextAlignment())
                    + getColor(sysRejectionColor) + "Invalid command index");
        };
    }
}