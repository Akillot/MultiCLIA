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

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.TextConfigs.alignment;
import static core.ui.essential.configs.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static final String[] fullCmds = {
            "list" ,"config", "restart", "help", "info",
            "clear", "time", "network", "security", "crypt",
            "terminal", "ai", "connection", "weather",
            "translate","support", "quit"};

    public static final String[] shortCmds = {
            "/ls" ,"/cfg", "/rs", "/h", "/i",
            "/cl", "/t", "/n", "/sec", "/cr",
            "/term", "/a", "/cn", "/w", "/tr",
            "/sup", "/q"};

    // Here is an array for commands from massive user extensions
    public static String[] extensionCmds = {};

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
            case 3 -> DisplayManager::displayCommandsDescription;
            case 4 -> () -> {
                try {
                    InfoPage.displayInfoPage();
                } catch (InterruptedException e) {
                    message("Error displaying this page: " + e.getMessage(),
                            rejectionColor, getDefaultTextAlignment(), 0, out::println);
                }
            };
            case 5 -> DisplayManager::clearTerminal;
            case 6 -> new TimePage()::displayMenu;
            case 7 -> new NetworkPage()::displayMenu;
            case 8 -> new SecurityPage()::displayMenu;
            case 9 -> new CryptographyPage()::displayMenu;
            case 10 -> new TerminalPage()::displayMenu;
            case 11 -> new AiPage()::displayMenu;
            case 12 -> new ConnectionPage()::displayMenu;
            case 13 -> new WeatherPage()::displayMenu;
            case 14 -> TranslatePage::displayTranslatePage;
            case 15 -> SupportPage::displaySupportPage;
            case 16 -> ExitPage::displayExitPage;
            default -> throw new IllegalArgumentException(alignment(getDefaultTextAlignment())
                    + getColor(rejectionColor) + "Invalid command index");
        };
    }
}