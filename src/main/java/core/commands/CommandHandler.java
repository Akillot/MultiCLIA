package core.commands;

import core.ui.essential.pages.*;
import core.logic.CommandManager;
import core.ui.extensions.ai.AiPage;
import core.ui.extensions.connection.ConnectionPage;
import core.ui.extensions.cryptography.CryptographyPage;
import core.ui.extensions.network.NetworkPage;
import core.ui.extensions.security.SecurityPage;
import core.ui.extensions.terminal.emulation.TerminalPage;
import core.ui.extensions.time.TimePage;
import core.ui.essential.configs.DisplayManager;
import core.ui.extensions.translate.TranslatePage;
import core.ui.extensions.weather.WeatherPage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.appearance.TextConfigs.alignment;
import static core.ui.essential.configs.appearance.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static final String[] fullCmds = {
            "help" ,"config", "restart", "info",
            "clear", "time", "network", "security", "crypt",
            "terminal", "ai", "connection", "weather",
            "translate","support", "quit", "exit"};

    public static final String[] shortCmds = {
            "h" ,"cfg", "rst", "i",
            "cl", "t", "n", "sec", "cr",
            "term", "a", "cn", "w", "tr",
            "sup", "q", "e"};

    public static void registerCommands(@NotNull Map<String, Runnable> commandMap) {
        for (int i = 0; i < fullCmds.length; i++) {
            commandMap.put(fullCmds[i], getCommandAction(i));
            commandMap.put(shortCmds[i], getCommandAction(i));
        }
    }

    @Contract(pure = true)
    static @NotNull Runnable getCommandAction(int index) {
        return switch (index) {
            case 0 -> DisplayManager::displayCommandList;
            case 1 -> new SettingsPage()::displayMenu;
            case 2 -> CommandManager::mainMenuRerun;
            case 3 -> () -> {
                try {
                    InfoPage.displayInfoPage();
                } catch (InterruptedException e) {
                    message("Error displaying this page: " + e.getMessage(),
                            rejectionColor, getDefaultTextAlignment(), 0, out::println);
                }
            };
            case 4 -> DisplayManager::clearTerminal;
            case 5 -> new TimePage()::displayMenu;
            case 6 -> new NetworkPage()::displayMenu;
            case 7 -> new SecurityPage()::displayMenu;
            case 8 -> new CryptographyPage()::displayMenu;
            case 9 -> new TerminalPage()::displayMenu;
            case 10 -> new AiPage()::displayMenu;
            case 11 -> new ConnectionPage()::displayMenu;
            case 12 -> new WeatherPage()::displayMenu;
            case 13 -> TranslatePage::displayTranslatePage;
            case 14 -> SupportPage::displaySupportPage;
            case 15, 16 -> ExitPage::displayExitPage;
            default -> throw new IllegalArgumentException(alignment(getDefaultTextAlignment())
                    + getColor(rejectionColor) + "Invalid command index");
        };
    }
}