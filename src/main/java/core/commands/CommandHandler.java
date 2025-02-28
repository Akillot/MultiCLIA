package core.commands;

import core.ui.essential.configs.DisplayManager;
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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

import static core.ui.essential.configs.essential.AppearanceConfigs.*;
import static core.ui.essential.configs.essential.TextConfigs.alignment;
import static core.ui.essential.configs.essential.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    // System commands
    public static final String[] fullSysCmds = {
            "list", "config", "restart", "help", "info",
            "clear", "support", "quit"
    };

    public static final String[] shortSysCmds = {
            "/ls", "/cfg", "/rs", "/h", "/i",
            "/cl", "/sup", "/q"
    };

    // Extension commands
    public static final String[] fullExtCmds = {
            "date", "calendar", "ifconfig", "security", "crypt",
            "terminal", "ai", "connection"
    };

    public static final String[] shortExtCmds = {
            "/dt", "/cld", "/ifc", "/sec", "/cr",
            "/term", "/ai", "/cn"
    };

    public static void registerCommands(@NotNull Map<String, Runnable> commandMap) {
        registerCommandGroup(commandMap, fullSysCmds, shortSysCmds, 0);
        registerCommandGroup(commandMap, fullExtCmds, shortExtCmds, fullSysCmds.length);
    }

    private static void registerCommandGroup(@NotNull Map<String, Runnable> commandMap,
                                             String @NotNull [] fullCmds, String @NotNull [] shortCmds, int offset) {
        String[] sortedFullCmds = fullCmds.clone();
        String[] sortedShortCmds = shortCmds.clone();
        Arrays.sort(sortedFullCmds);
        Arrays.sort(sortedShortCmds);

        for (int i = 0; i < sortedFullCmds.length; i++) {
            commandMap.put(sortedFullCmds[i], getCommandAction(i + offset));
            commandMap.put(sortedShortCmds[i], getCommandAction(i + offset));
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
                            sysRejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                }
            };
            case 5 -> DisplayManager::clearTerminal;
            case 6 -> SupportPage::displaySupportPage;
            case 7 -> ExitPage::displayExitPage;
            case 8 -> TimePage::displayTimePage;
            case 9 -> CalendarPage::displayCalendarPage;
            case 10 -> NetworkPage::displayNetworkPage;
            case 11 -> SecurityPage::displaySecurityPage;
            case 12 -> CryptographyPage::displayCryptographyPage;
            case 13 -> TerminalPage::displayTerminalPage;
            case 14 -> AiPage::displayAiPage;
            case 15 -> ConnectionPage::displayPage;

            default -> throw new IllegalArgumentException(alignment(getDefaultTextAlignment())
                    + getColor(sysRejectionColor) + "Invalid command index");
        };
    }
}
