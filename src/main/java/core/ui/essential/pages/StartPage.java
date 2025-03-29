package core.ui.essential.pages;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static core.commands.CommandHandler.shortCmds;
import static core.logic.CommandManager.*;
import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.TextConfigs.*;
import static java.lang.System.out;

public class StartPage {

    @Getter
    private static String dateAndTimeOfProgramLaunching;

    private static String[] mainLogoAscii = {
            "ooo        ooooo             oooo      .    o8o      .oooooo.   ooooo        ooooo         .o.       ",
            "`88.       .888'             `888    .o8    `\"'     d8P'  `Y8b  `888'        `888'        .888.      ",
            " 888b     d'888  oooo  oooo   888  .o888oo oooo    888           888          888        .8\"888.     ",
            " 8 Y88. .P  888  `888  `888   888    888   `888    888           888          888       .8  `888.    ",
            " 8  `888'   888   888   888   888    888    888    888           888          888      .8     888.    ",
            " 8    Y     888   888   888   888    888    888    888           888          888     .888oooo8888.   ",
            " 8          888   888   888   888    888 .  888    `88b    ooo   888       o  888    .88'      `888.  ",
            "o8o        o888o  `V88V\"V8P' o888o   \"888\" o888o    `Y8bood8P'  o888ooooood8 o888o  o88o       o8888o "
    };

    private static String[] newYearAscii = {
            "ooooo      ooo                                 oooooo   oooo                              ",
            "`888b.     `8'                                  `888.   .8'                               ",
            " 8 `88b.    8   .ooooo.  oooo oooo    ooo        `888. .8'    .ooooo.   .oooo.   oooo d8b ",
            " 8   `88b.  8  d88' `88b  `88. `88.  .8'          `888.8'    d88' `88b `P  )88b  `888\"\"8P ",
            " 8     `88b.8  888ooo888   `88..]88..8'            `888'     888ooo888  .oP\"888   888     ",
            " 8       `888  888    .o    `888'`888'              888      888    .o d8(  888   888     ",
            "o8o        `8  `Y8bod8P'     `8'  `8'              o888o     `Y8bod8P' `Y888\"\"8o d888b    "
    };

    public static void displayMenu() {
        dateChecking();
        insertControlChars('n',2);
        displayLogo(mainLogoAscii, getDefaultLogoAlignment());
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
                message(errorMessage, mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            }
        }
    }

    private static void getRandomMotto(){
        String userName = System.getProperty("user.name");
        String[] motto = {
                "Built for you.", "Command-driven simplicity.","Fast. Smooth. Ready.", "Harmony in command.",
                "It starts with a command.", "Optimal width of the terminal window: 117 characters and wider.",
                "Hi " + getColorText(capitalizeMessage(userName), mainColor)
                        + getColor(layoutColor) + " and welcome to MultiCLIA!", "Everything you need.",
                "What you think about when you think about love?",
                "Find a bug or have an idea? Go to" + getColor(mainColor) + " nickzozulia@gmail.com" + getColor(layoutColor) + "."};

        Random rand = new Random();
        int index = rand.nextInt(0, motto.length);
        message("Just type '" + getColor(mainColor)
                + shortCmds[0] + getColor(layoutColor) + "'. " + motto[index],15,getDefaultLogoAlignment(),
                getDefaultDelay(),out::print);
    }

    private static void getCurrentDate(){
        LocalDateTime localTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = localTime.getDayOfWeek();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        dateAndTimeOfProgramLaunching = localTime.format(myFormatter).toLowerCase();
        message("Application start time" + ": "
                        + getColor(mainColor) + capitalizeMessage(String.valueOf(dayOfWeek)) + " " + dateAndTimeOfProgramLaunching
                        + getColor(layoutColor) + ".",
                layoutColor,getDefaultLogoAlignment(),getDefaultDelay(),out::print);
    }

    private static void dateChecking(){
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM");
        String actualDate = localTime.format(myFormatter).toLowerCase();

        switch (actualDate){
            case "31-12":
            case "01-01":
                insertControlChars('n',2);
                displayLogo(newYearAscii, 36);
                insertControlChars('n',2);
                message("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", layoutColor, 36,getDefaultDelay(),out::print);
                break;
        }
    }

    private static void displayLogo(String @NotNull [] logo, int alignment) {
        String[] colors = getColorsForLogo();

        for (int i = 0; i < logo.length; i++) {
            String coloredText = colors[i % colors.length] + logo[i] + RESET;
            message(coloredText, layoutColor, alignment, getDefaultDelay(), System.out::print);
        }
    }

    private static String @NotNull [] getColorsForLogo() {
        return new String[]{
                getColor(color1), getColor(color2),
                getColor(color3), getColor(color4),
                getColor(color5), getColor(color6)
        };
    }
}