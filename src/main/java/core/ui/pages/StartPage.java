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

public class StartPage {

    @Getter
    private static String dateAndTimeOfProgramLaunching;

    private final static String[] MAIN_LOGO_ASCII = {
            "╔════════════════════════════════════════════════════════════════════════════════════════════════════════╗",
            "║                                                                                                        ║",
            "║                                                                                                        ║",
            "║                                                                                                        ║",
            "║                    ███╗   ███╗██╗   ██╗██╗  ████████╗██╗ ██████╗██╗     ██╗ █████╗                     ║",
            "║                    ████╗ ████║██║   ██║██║  ╚══██╔══╝██║██╔════╝██║     ██║██╔══██╗                    ║",
            "║                    ██╔████╔██║██║   ██║██║     ██║   ██║██║     ██║     ██║███████║                    ║",
            "║                    ██║╚██╔╝██║██║   ██║██║     ██║   ██║██║     ██║     ██║██╔══██║                    ║",
            "║                    ██║ ╚═╝ ██║██║   ██║██║     ██║   ██║██║     ██║     ██║██║  ██║                    ║",
            "║                    ██║     ██║╚██████╔╝███████╗██║   ██║╚██████╗███████╗██║██║  ██║                    ║",
            "║                    ╚═╝     ╚═╝ ╚═════╝ ╚══════╝╚═╝   ╚═╝ ╚═════╝╚══════╝╚═╝╚═╝  ╚═╝   v." + getVersion() + getColor(getLayoutColor()) + "           ║",
            "║                                                                                                        ║",
            "║                           Terminal Plugin Framework | Open-source | Modular.                           ║",
            "║                                                                                                        ║",
            "║                                                                                                        ║",
            "║                                                                                                        ║",
            "╚════════════════════════════════════════════════════════════════════════════════════════════════════════╝"
    };

    public static void displayMenu() {
        loadConfig();
        //apiKeyChecking(apiKeyNames);
        insertControlChars('n',1);
        displayLogo(MAIN_LOGO_ASCII, getDefaultTextAlignment() + 4);
        insertControlChars('n',1);
        getCurrentDate();
        marginBorder(1,1);
        while (true) {
            try {
                searchCommands();
            } catch (Exception ex) {
                marginBorder(1,1);
                String errorMessage = (ex.getMessage() != null) ? ex.getMessage() : "Unknown error occurred";
                message(errorMessage, getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
            }
        }
    }

    private static void getCurrentDate(){
        LocalDateTime localTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = localTime.getDayOfWeek();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        dateAndTimeOfProgramLaunching = localTime.format(myFormatter).toLowerCase();
        message("Current session started: "
                        + getColor(getMainColor()) + capitalizeMessage(String.valueOf(dayOfWeek)) + " " + getColor(getLayoutColor()) + dateAndTimeOfProgramLaunching
                        + getColor(getLayoutColor()) + ".",
                getLayoutColor(),getDefaultTextAlignment(),getDefaultDelay(),out::print);
    }

    private static void displayLogo(String @NotNull [] logo, int alignment) {
        String[] colors = getColorsForLogo();

        for (int i = 0; i < logo.length; i++) {
            String coloredText = colors[i % colors.length] + logo[i] + RESET;
            message(coloredText, getLayoutColor(), alignment, getDefaultDelay(), System.out::print);
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