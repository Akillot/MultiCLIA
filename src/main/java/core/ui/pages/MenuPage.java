package core.ui.pages;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static core.CommandManager.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.TextConfigs.*;
import static core.ui.pages.InfoPage.getVersion;
import static java.lang.System.out;

public class MenuPage {

    @Getter
    private static String dateAndTimeOfProgramLaunching;
    private final static String[] MAIN_LOGO_ASCII = {
            "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓",
            "┃                                                                                                        ┃",
            "┃                                                                                                        ┃",
            "┃                                                                                                        ┃",
            "┃                    ███╗   ███╗██╗   ██╗██╗  ████████╗██╗ ██████╗██╗     ██╗ █████╗                     ┃",
            "┃                    ████╗ ████║██║   ██║██║  ╚══██╔══╝██║██╔════╝██║     ██║██╔══██╗                    ┃",
            "┃                    ██╔████╔██║██║   ██║██║     ██║   ██║██║     ██║     ██║███████║                    ┃",
            "┃                    ██║╚██╔╝██║██║   ██║██║     ██║   ██║██║     ██║     ██║██╔══██║                    ┃",
            "┃                    ██║ ╚═╝ ██║██║   ██║██║     ██║   ██║██║     ██║     ██║██║  ██║                    ┃",
            "┃                    ██║     ██║╚██████╔╝███████╗██║   ██║╚██████╗███████╗██║██║  ██║                    ┃",
            "┃                    ╚═╝     ╚═╝ ╚═════╝ ╚══════╝╚═╝   ╚═╝ ╚═════╝╚══════╝╚═╝╚═╝  ╚═╝   v." + getVersion() + getColor(getLayoutColor()) + "           ┃",
            "┃                                                                                                        ┃",
            "┃                           Terminal Plugin Framework | Open-source | Modular.                           ┃",
            "┃                                                                                                        ┃",
            "┃                                                                                                        ┃",
            "┃                                                                                                        ┃",
            "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"
    };

    public static void displayMenu() {
        loadConfig();

        insertControlChars('n',1);
        displayLogo(getDefaultTextAlignment() + 4);
        insertControlChars('n',1);

        message("Hi, "
                        + getColor(getMainColor())
                        + capitalizeMessage(System.getProperty("user.name"))
                        + getColor(getLayoutColor()) + "!",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        getCurrentDate();

        message("Platform: "
                        + getColor(getMainColor())
                        + capitalizeMessage(System.getProperty("os.name"))
                        + getColor(getLayoutColor())
                        + "   |   Theme: Default (type " + getColor(getMainColor())
                        + "theme" + getColor(getLayoutColor()) + " to switch)",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        marginBorder(1,1);
        checkApiKeys(apiKeyNames.toArray(new String[0]));
        marginBorder(1,1);

        message("Tips:",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        message(" - Type '"
                        + getColor(getMainColor()) + "help"
                        + getColor(getLayoutColor()) + "' to explore basic commands",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message(" - Type '"
                        + getColor(getMainColor()) + "plugins"
                        + getColor(getLayoutColor()) + "' to explore or install mini apps",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        message(" - Type '"
                        + getColor(getMainColor()) + "info"
                        + getColor(getLayoutColor()) + "' to see information about MultiCLIA",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);
        insertControlChars('n',1);

        message("[" + getColor(getMainColor()) + "t" + getColor(getLayoutColor()) + "] Theme     ["
                        + getColor(getMainColor()) + "acf" + getColor(getLayoutColor()) + "] APIs     ["
                        + getColor(getMainColor()) + "p" + getColor(getLayoutColor()) + "] Plugins     ["
                        + getColor(getMainColor()) + "h" + getColor(getLayoutColor()) + "] Help     ["
                        + getColor(getMainColor()) + "i" + getColor(getLayoutColor()) + "] Info     ["
                        + getColor(getMainColor()) + "r" + getColor(getLayoutColor()) + "] Restart    ["
                        + getColor(getMainColor()) + "q" + getColor(getLayoutColor()) + "] Quit",
                getLayoutColor(),
                getDefaultLogoAlignment(),
                getDefaultDelay(),
                out::println);

        marginBorder(0,1);

        while (true) {
            try {
                searchCommands();
            } catch (Exception ex) {
                marginBorder(1,1);
                String errorMessage = (ex.getMessage() != null) ? ex.getMessage() : "Unknown error occurred";
                message(errorMessage,
                        getMainColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);
            }
        }
    }

    private static void getCurrentDate(){
        LocalDateTime localTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = localTime.getDayOfWeek();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        dateAndTimeOfProgramLaunching = localTime.format(myFormatter).toLowerCase();
        message("Current session started: "
                        + getColor(getMainColor()) + capitalizeMessage(String.valueOf(dayOfWeek)) + " "
                        + getColor(getLayoutColor()) + dateAndTimeOfProgramLaunching
                        + getColor(getLayoutColor()) + ".",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);
    }

    private static void displayLogo(int alignment) {
        String[] colors = getColorsForLogo();

        for (int i = 0; i < MenuPage.MAIN_LOGO_ASCII.length; i++) {
            String coloredText = colors[i % colors.length] + MenuPage.MAIN_LOGO_ASCII[i] + RESET;
            message(coloredText,
                    getLayoutColor(),
                    alignment,
                    getDefaultDelay(),
                    out::print);
        }
    }

    private static String @NotNull [] getColorsForLogo() {
        return new String[]{
                getColor(getLayoutColor()), getColor(getLayoutColor()),
                getColor(getLayoutColor()), getColor(getLayoutColor()),
                getColor(getLayoutColor()), getColor(getLayoutColor()),
        };
    }
}