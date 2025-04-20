package core.commands;

import core.ui.essential.pages.*;
import core.logic.CommandManager;
import core.ui.extensions.ai.AiPage;
import core.ui.extensions.asciiartify.AsciiArtifyPage;
import core.ui.extensions.qr.QrPage;
import core.ui.extensions.cryptography.CryptographyPage;
import core.ui.extensions.network.NetworkPage;
import core.ui.extensions.security.PasswordGenerator;
import core.ui.extensions.terminal_emulation.TerminalPage;
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
                            rejectionColor, getDefaultTextAlignment(), 0, out::println);
                }
            };
            case 2 -> CommandManager::mainMenuRestart;
            case 3 -> CommandManager::mainMenuRestartWithClearing;
            case 4 -> new ConfigPage()::displayMenu;
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
                    + getColor(rejectionColor) + "Invalid command index");
        };
    }
}