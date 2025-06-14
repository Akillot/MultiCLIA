package core.ui.pages;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static core.CommandManager.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.clearTerminal;
import static core.ui.configs.DisplayManager.displayLogo;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class InfoPage {

    private static final String[] INFO_ASCII_LOGO = {
            "╔══════════════════════════════════╗",
            "║                                  ║",
            "║  ██╗███╗   ██╗███████╗ ██████╗   ║",
            "║  ██║████╗  ██║██╔════╝██╔═══██╗  ║",
            "║  ██║██╔██╗ ██║█████╗  ██║   ██║  ║",
            "║  ██║██║╚██╗██║██╔══╝  ██║   ██║  ║",
            "║  ██║██║ ╚████║██║     ╚██████╔╝  ║",
            "║  ╚═╝╚═╝  ╚═══╝╚═╝      ╚═════╝   ║",
            "║                                  ║",
            "╚══════════════════════════════════╝"
    };

    @Contract(pure = true)
    public static @NotNull String getVersion() {
        loadConfig();
        String appVersion = "3.0 ";
        return getColorText(appVersion, getLayoutColor());
    }

    public static void displayInfoPage() throws InterruptedException {
        marginBorder(1,2);
        clearTerminal();
        displayLogo(getDefaultTextAlignment(),INFO_ASCII_LOGO);
        insertControlChars('n',2);

        message("Version name: " + getColor(getMainColor()) + "Highland",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("Version number: " + getVersion(),
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        message("Platform: " + getColor(75) + "Mac"
                        + getColor(getLayoutColor()) + "OS, " + getColor(74)
                        + getColor(207) +"Linux",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message("Author: Nick Zozulia",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        displayApplicationDirectory();

        insertControlChars('n',1);
        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                getAcceptanceColor(),
                getRejectionColor(),
                getLayoutColor(),
                getDefaultTextAlignment());

        choice(getColor(getMainColor()) + "Github",
                openUri("https://github.com/Akillot/MultiCLIA"),
                getMainColor(),
                getLayoutColor(),
                getRejectionColor());

        exitPageFormatting();
        clearAndRestartApp();
    }

    private static void displayApplicationDirectory() {
        try {
            String appPath = new File(
                    MenuPage.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            ).getParent();

            message("Application Directory: " + getColor(getMainColor()) + appPath,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

        } catch (Exception e) {
            message("Could not determine application directory.",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);
        }
    }
}