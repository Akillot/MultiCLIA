package core.ui.essential.pages;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static core.commands.CommandHandler.fullCmds;
import static core.logic.CommandManager.*;
import static core.ui.essential.configs.DisplayManager.apiKeyChecking;
import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.appearance.TextConfigs.*;
import static core.ui.essential.pages.InfoPage.getVersion;
import static java.lang.System.out;

public class StartPage {

    @Getter
    private static String dateAndTimeOfProgramLaunching;

    private final static String[] MAIN_LOGO_ASCII = {
            "╔═════════════════════════════════════════════════════════════════════════════════════════╗",
            "║                                                                                         ║",
            "║   ███╗   ███╗██╗   ██╗██╗  ████████╗██╗ ██████╗██╗     ██╗ █████╗                       ║",
            "║   ████╗ ████║██║   ██║██║  ╚══██╔══╝██║██╔════╝██║     ██║██╔══██╗                      ║",
            "║   ██╔████╔██║██║   ██║██║     ██║   ██║██║     ██║     ██║███████║                      ║",
            "║   ██║╚██╔╝██║██║   ██║██║     ██║   ██║██║     ██║     ██║██╔══██║                      ║",
            "║   ██║ ╚═╝ ██║██║   ██║██║     ██║   ██║██║     ██║     ██║██║  ██║                      ║",
            "║   ██║     ██║╚██████╔╝███████╗██║   ██║╚██████╗███████╗██║██║  ██║                      ║",
            "║   ╚═╝     ╚═╝ ╚═════╝ ╚══════╝╚═╝   ╚═╝ ╚═════╝╚══════╝╚═╝╚═╝  ╚═╝    v." + getVersion() + getColor(getLayoutColor()) +"   ║",
            "║                                                                                         ║",
            "╚═════════════════════════════════════════════════════════════════════════════════════════╝"
    };

    public static void displayMenu() {
        loadConfig();
        insertControlChars('n',1);
        apiKeyChecking("OPENAI_API_KEY");
        apiKeyChecking("DEEPL_API_KEY");
        apiKeyChecking("OPEN_WEATHER_API_KEY");
        insertControlChars('n',1);
        displayLogo(MAIN_LOGO_ASCII, getDefaultLogoAlignment());
        insertControlChars('n',1);
        getRandomMotto();
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

    private static void getRandomMotto(){
        String userName = System.getProperty("user.name");
        Random rand = new Random();
        String[] motto = {
                "Built for you.", "Command-driven simplicity.","Fast. Smooth. Ready.", "Harmony in command.",
                "It starts with a command.", "Optimal width of the terminal window: 108 characters and wider.",
                "Hi " + getColorText(capitalizeMessage(userName), getMainColor())
                        + getColor(getLayoutColor()) + " and welcome to MultiCLIA!", "Everything you need.",
                "What you think about when you think about MultiCLIA?",
                "Find a bug or have an idea? Go to" + getColor(getMainColor()) + " nickzozulia@gmail.com" + getColor(getLayoutColor()) + "."};

        int index = rand.nextInt(0, motto.length);
        message("For start type '" + getColor(getMainColor())
                + fullCmds[0] + getColor(getLayoutColor()) + "'. " + motto[index],15,getDefaultLogoAlignment(),
                getDefaultDelay(),out::print);
    }

    private static void getCurrentDate(){
        LocalDateTime localTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = localTime.getDayOfWeek();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        dateAndTimeOfProgramLaunching = localTime.format(myFormatter).toLowerCase();
        message("Application start time" + ": "
                        + getColor(getMainColor()) + capitalizeMessage(String.valueOf(dayOfWeek)) + " " + getColor(getLayoutColor()) + dateAndTimeOfProgramLaunching
                        + getColor(getLayoutColor()) + ".",
                getLayoutColor(),getDefaultLogoAlignment(),getDefaultDelay(),out::print);
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