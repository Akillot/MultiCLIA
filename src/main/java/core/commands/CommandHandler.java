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

    public static final String listFullCmnd = "list";
    public static final String settingsFullCmnd = "settings";
    public static final String rerunFullCmnd = "rerun";
    public static final String helpFullCmnd = "help";
    public static final String infoFullCmnd = "info";
    public static final String clearFullCmnd = "clear";
    public static final String timeFullCmnd = "time";
    public static final String calendarFullCmnd = "calendar";
    public static final String networkFullCmnd = "network";
    public static final String securityFullCmnd = "security";
    public static final String cryptographyFullCmnd = "cryptography";
    public static final String terminalFullCmnd = "terminal";
    public static final String aiFullCmnd = "ai-assist";
    public static final String connectionFullCmnd = "connection";
    public static final String quitFullCmnd = "quit";

    public static String specChar = "/";

    public static final String listShortCmnd = "ls";
    public static final String settingsShortCmnd = "s";
    public static final String rerunShortCmnd = "rr";
    public static final String helpShortCmnd = "h";
    public static final String infoShortCmnd = "i";
    public static final String clearShortCmnd = "cl";
    public static final String timeShortCmnd = "t";
    public static final String calendarShortCmnd = "ca";
    public static final String networkShortCmnd = "n";
    public static final String securityShortCmnd = "sc";
    public static final String cryptographyShortCmnd = "cr";
    public static final String terminalShortCmnd = "tr";
    public static final String aiShortCmnd = "ai";
    public static final String connectionShortCmnd = "cn";
    public static final String quitShortCmnd = "q";

    public static String command = specChar + listShortCmnd;

    public static final String[] fullSystemCmds = {
            "list" ,"settings", "rerun", "help", "info",
            "clear", "time", "calendar", "network", "security", "cryptography",
            "terminal", "ai-assist", "connection", "support", "quit"};

    public static final String[] shortSystemCmds = {
            "/ls" ,"/s", "/rr", "/h", "/i",
            "/cl", "/t", "/ca", "/n", "/sc", "/cr",
            "/ter", "/ai", "/cn", "/su", "/q"};

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
            case 12 -> AiPage::displayAiPage;
            case 13 -> ConnectionPage::displayPage;
            case 14 -> SupportPage::displaySupportPage;
            case 15 -> ExitPage::displayExitPage;
            default -> throw new IllegalArgumentException(alignment(getDefaultTextAlignment())
                    + getColor(sysRejectionColor) + "Invalid command index");
        };
    }
}