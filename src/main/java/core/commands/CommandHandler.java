package core.commands;

import core.CommandManager;
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
            "help", "plugins", "info" , "restart", "restart clear",
            "config", "clear", "support", "quit"};

    public static final String[] shortCmds = {
            "h", "plg", "i", "rst","rcl", "cfg",
            "cl", "sup", "q"};

    public static final String[] fullPluginCmds = {
            "time", "network", "genpass", "crypt",
            "terminal", "ai", "qrcode", "weather",
            "asciiartify", "translate"};

    public static final String[] shortPluginCmds = {
            "t", "n", "gp", "cr", "term", "a", "qr", "w", "art", "tran"};

    public static void registerCommands(@NotNull Map<String, Runnable> commandMap) {
        for (int i = 0; i < fullCmds.length; i++) {
            commandMap.put(fullCmds[i], ExecuteCommand(i));
            commandMap.put(shortCmds[i], ExecuteCommand(i));
        }

        int offset = fullCmds.length;
        for (int i = 0; i < fullPluginCmds.length; i++) {
            commandMap.put(fullPluginCmds[i], ExecuteCommand(i + offset));
            commandMap.put(shortPluginCmds[i], ExecuteCommand(i + offset));
        }
    }

    @Contract(pure = true)
    static @NotNull Runnable ExecuteCommand(int index) {
        return switch (index) {
            case 0 -> DisplayManager::displayCommandList;
            case 1 -> DisplayManager::displayPluginCommandList;
            case 2 -> () -> {
                try {
                    InfoPage.displayInfoPage();
                } catch (InterruptedException e) {
                    message("Error displaying this page: " + e.getMessage(),
                            getRejectionColor(), getDefaultTextAlignment(), 0, out::println);
                }
            };
            case 3 -> CommandManager::mainMenuRestart;
            case 4 -> CommandManager::mainMenuRestartWithClearing;
            case 5 -> new SettingsPage()::displayMenu;
            case 6 -> DisplayManager::clearTerminal;
            case 7 -> SupportPage::displaySupportPage;
            case 8 -> ExitPage::displayExitPage;

            case 9  -> new TimePage()::displayMenu;
            case 10 -> new NetworkPage()::displayMenu;
            case 11 -> new PasswordGenerator()::displayMenu;
            case 12 -> new CryptographyPage()::displayMenu;
            case 13 -> new TerminalPage()::displayMenu;
            case 14 -> new AiPage()::displayMenu;
            case 15 -> new QrPage()::displayMenu;
            case 16 -> new WeatherPage()::displayMenu;
            case 17 -> new AsciiArtifyPage()::displayMenu;
            case 18 -> TranslatePage::displayTranslatePage;

            default -> throw new IllegalArgumentException(
                    alignment(getDefaultTextAlignment()) + getColor(getRejectionColor()) + "Invalid command index");
        };
    }
}