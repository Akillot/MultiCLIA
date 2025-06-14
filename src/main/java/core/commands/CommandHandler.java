package core.commands;

import core.CommandManager;
import core.InvalidCommandIndexException;
import core.ui.pages.SettingsPage;
import core.ui.pages.ExitPage;
import core.ui.pages.InfoPage;
import core.ui.pages.SupportPage;
import tools.ai.AiPage;
import tools.generate_art.AsciiArtGenPage;
import tools.qr.QrPage;
import tools.cryptography.CryptographyPage;
import tools.network.NetworkPage;
import tools.terminal_emulation.TerminalPage;
import tools.time.TimePage;
import core.ui.configs.DisplayManager;
import tools.translate.TranslatePage;
import tools.weather.WeatherPage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.TextConfigs.message;
import static java.lang.System.out;

public class CommandHandler {

    public static final String[] fullCmds = {
            "help", "tools", "info", "restart",
            "settings", "clear", "support", "quit"};

    public static final String[] shortCmds = {
            "h", "t", "i", "r", "s",
            "c", "sp", "q"};

    public static final String[] fullToolCmds = {
            "time", "network", "crypt", "terminal",
            "ai", "genqr", "weather", "genart",
            "translate"};

    public static final String[] shortToolCmds = {
            "tm", "n", "cr", "term", "a", "qr", "w", "art", "tran"};

    public static void registerCommands(@NotNull Map<String, Runnable> commandMap) {
        for (int i = 0; i < fullCmds.length; i++) {
            commandMap.put(fullCmds[i], ExecuteCommand(i));
            commandMap.put(shortCmds[i], ExecuteCommand(i));
        }

        int offset = fullCmds.length;
        for (int i = 0; i < fullToolCmds.length; i++) {
            commandMap.put(fullToolCmds[i], ExecuteCommand(i + offset));
            commandMap.put(shortToolCmds[i], ExecuteCommand(i + offset));
        }
    }

    @Contract(pure = true)
    static @NotNull Runnable ExecuteCommand(int index) {
        return switch (index) {
            case 0 -> DisplayManager::displayCommandList;
            case 1 -> DisplayManager::displayToolsCommandList;
            case 2 -> () -> {
                try {
                    InfoPage.displayInfoPage();
                } catch (InterruptedException e) {
                    message("Error " + getColor(getLayoutColor()) + "displaying this page." ,
                            getRejectionColor(), getDefaultTextAlignment(), 0, out::println);
                }
            };
            case 3 -> CommandManager::clearAndRestartApp;
            case 4 -> new SettingsPage()::displayMenu;
            case 5 -> DisplayManager::clearTerminal;
            case 6 -> SupportPage::displaySupportPage;
            case 7 -> ExitPage::displayExitPage;

            case 8  -> new TimePage()::displayMenu;
            case 9 -> new NetworkPage()::displayMenu;
            case 10 -> new CryptographyPage()::displayMenu;
            case 11 -> new TerminalPage()::displayMenu;
            case 12 -> new AiPage()::displayMenu;
            case 13 -> new QrPage()::displayMenu;
            case 14 -> new WeatherPage()::displayMenu;
            case 15 -> new AsciiArtGenPage()::displayMenu;
            case 16 -> TranslatePage::displayTranslatePage;

            default -> throw new InvalidCommandIndexException();
        };
    }
}