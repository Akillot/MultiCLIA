package core.pages;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.logic.CommandManager.*;
import static java.lang.System.out;

public class StartPage {

    @Getter
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

    public static void displayStartPage() {
        dateChecking();
        displayMenu();
        while (true) {
            try {
                searchCommands();
            } catch (Exception ex) {
                marginBorder(1,1);
                String errorMessage = (ex.getMessage() != null) ? ex.getMessage() : "Unknown error occurred";
                message(errorMessage, sysMainColor, 58, 0, out::print);
            }
        }
    }

    private static void displayMenu() {
        modifyMessage('n',2);
        switchLogo(mainLogoAscii, 48);
        modifyMessage('n',1);
        getRandomMotto();
        getCurrentDate();
        marginBorder(1,1);
    }

    private static void getRandomMotto(){
        String userName = System.getProperty("user.name");
        String[] motto = {"Command-driven simplicity.",
                "Built for you.", "Command-driven simplicity.","Fast. Smooth. Ready.", "Harmony in command.",
                "It starts with a command.", "Optimal width of the terminal window: 117 characters and wider.",
                "Hi " + getColorText(capitalizeMessage(userName),sysMainColor)
                        + getColor(sysLayoutColor) + " and welcome to MultiCLIA!", "Everything you need.", "Try secret command '"
                + getColor(sysMainColor) + "/xq" + getColor(sysLayoutColor) + "' in sections like "
                + getColor(sysMainColor) + "time" + getColor(sysLayoutColor) + "/"
                + getColor(sysMainColor) + "security" + getColor(sysLayoutColor) + "/"
                + getColor(sysMainColor) + "etc" + getColor(sysLayoutColor) + "."};
        Random rand = new Random();

        int index = rand.nextInt(0, motto.length);
        message("Just type '" + getColor(sysMainColor)
                + "cmds" + getColor(sysLayoutColor) + "'. " + motto[index],15,48,0,out::print);
    }

    private static void getCurrentDate(){
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedTime = localTime.format(myFormatter);
        message("Application start time" + ": "
                        + getColor(sysMainColor) + formattedTime
                        + getColor(sysLayoutColor) + ".",
                sysLayoutColor,48,0,out::print);
    }

    private static void dateChecking(){
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM");
        String actualDate = localTime.format(myFormatter).toLowerCase();

        switch (actualDate){
            case "31-12":
            case "01-01":
                modifyMessage('n',2);
                switchLogo(newYearAscii, 36);
                modifyMessage('n',2);
                message("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", sysLayoutColor, 36,0,out::print);
                break;
        }
    }

    private static void switchLogo(String @NotNull [] logo, int alignment) {
        String[] colors = getColorsForLogo();

        for (int i = 0; i < logo.length; i++) {
            String coloredText = colors[i % colors.length] + logo[i] + RESET;
            message(coloredText, sysLayoutColor, alignment, 0, System.out::print);
        }
    }
}