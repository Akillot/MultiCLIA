package core.commands;

import core.logic.CommandManager;
import core.ui.pages.SettingsPage;
import core.ui.pages.ExitPage;
import core.ui.pages.InfoPage;
import core.ui.pages.SupportPage;
import plugins.ai.AiPage;
import plugins.asciiartify.AsciiArtifyPage;
import plugins.qr.QrPage;
import plugins.cryptography.CryptographyPage;
import plugins.network.NetworkPage;
import plugins.security.PasswordGenerator;
import plugins.terminal_emulation.TerminalPage;
import plugins.time.TimePage;
import core.ui.configs.DisplayManager;
import plugins.translate.TranslatePage;
import plugins.weather.WeatherPage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.TextConfigs.alignment;
import static core.ui.configs.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static final String[] fullCmds = {
            "help", "info" , "restart", "restart clear", "config",
            "clear", "time", "network", "genpass", "crypt",
            "terminal", "ai", "qrcode", "weather", "asciiartify",
            "translate","support", "quit"};

    public static final String[] shortCmds = {
            "h", "i", "rst","rcl", "cfg",
            "cl", "t", "n", "gp", "cr",
            "term", "a", "qr", "w", "art",
            "tran", "sup", "q"};

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
            case 1 -> () -> {
                try {
                    InfoPage.displayInfoPage();
                } catch (InterruptedException e) {
                    message("Error displaying this page: " + e.getMessage(),
                            getRejectionColor(), getDefaultTextAlignment(), 0, out::println);
                }
            };
            case 2 -> CommandManager::mainMenuRestart;
            case 3 -> CommandManager::mainMenuRestartWithClearing;
            case 4 -> new SettingsPage()::displayMenu;
            case 5 -> DisplayManager::clearTerminal;
            case 6 -> new TimePage()::displayMenu;
            case 7 -> new NetworkPage()::displayMenu;
            case 8 -> new PasswordGenerator()::displayMenu;
            case 9 -> new CryptographyPage()::displayMenu;
            case 10 -> new TerminalPage()::displayMenu;
            case 11 -> new AiPage()::displayMenu;
            case 12 -> new QrPage()::displayMenu;
            case 13 -> new WeatherPage()::displayMenu;
            case 14 -> new AsciiArtifyPage()::displayMenu;
            case 15 -> TranslatePage::displayTranslatePage;
            case 16 -> SupportPage::displaySupportPage;
            case 17 -> ExitPage::displayExitPage;
            default -> throw new IllegalArgumentException(alignment(getDefaultTextAlignment())
                    + getColor(getRejectionColor()) + "Invalid command index");
        };
    }
}