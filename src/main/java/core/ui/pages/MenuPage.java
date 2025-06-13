package core.ui.pages;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static core.CommandManager.*;
import static core.commands.CommandHandler.fullCmds;
import static core.commands.SearchingManager.search;
import static core.ui.configs.ApiConfigs.checkApiKeys;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.displayLogo;
import static core.ui.configs.TextConfigs.*;
import static core.ui.pages.InfoPage.getVersion;
import static java.lang.System.getProperty;
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
            "┃                                    Terminal Tool App | Open-source.                                    ┃",
            "┃                                                                                                        ┃",
            "┃                                                                                                        ┃",
            "┃                                                                                                        ┃",
            "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"
    };

    public static void displayMenu() {
        loadConfig();
        insertControlChars('n',1);
        displayLogo(getDefaultTextAlignment() + 4, MAIN_LOGO_ASCII);
        insertControlChars('n',1);

        message("Hi, "
                        + capitalizeMessage(getProperty("user.name"))
                        + getColor(getLayoutColor()) + "!",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        getCurrentDate();

        insertControlChars('n',1);
        message("Enter [" + getColor(getMainColor())
                        + fullCmds[0] + getColor(getLayoutColor()) + "] to see basic commands | sections.",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),out::println);

        marginBorder(0,1);

        // Fix
        checkApiKeys(apiKeyNames.toArray(new String[0]));
        marginBorder(1,1);

        while (true) {
            try {
                search();
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
                        + getColor(getMainColor()) + capitalizeMessage(String.valueOf(dayOfWeek))
                        + " " + getColor(getLayoutColor()) + dateAndTimeOfProgramLaunching
                        + getColor(getLayoutColor()) + ".",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);
    }
}