package core.pages;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.searchCommands;
import static core.logic.CommandManager.switchLogoRandomly;
import static core.logic.TextConfigs.*;
import static core.pages.InfoPage.getVersion;
import static java.lang.System.out;

public class StartPage {

    public static void displayStartPage() {
        dateChecking();
        displayMenu();
        while (true) {
            try {
                searchCommands();
            } catch (Exception ex) {
                marginBorder(1,1);
                String errorMessage = (ex.getMessage() != null) ? ex.getMessage() : "Unknown error occurred";
                message(errorMessage, systemRejectionColor, 58, 0, out::print);
            }
        }
    }

    private static void displayMenu() {
        modifyMessage('n',2);
        switchLogoRandomly(mainLogoAscii, 48);
        modifyMessage('n',1);
        getRandomMotto();
        getCurrentDate();

        marginBorder(1,1);

        message("━━━━━━━━━━━━━━━━━━━━━━", systemLayoutColor,58,0, out::print);
        message("Version: " + getVersion() , systemLayoutColor,56,0, out::print);
        message("━━━━━━━━━━━━━━━━━━━━━━", systemLayoutColor,58,0, out::print);

        alert("i", "Enter " + "'" + getAnsi256Color(systemMainColor) + "cmds" + RESET
                + getAnsi256Color(systemLayoutColor) + "'\n"
                + alignment(56) + "to show list of\n"
                + alignment(56) + "commands", 56, systemMainColor, systemLayoutColor);

        message("━━━━━━━━━━━━━━━━━━━━━━", systemLayoutColor,58,0,out::print);
        modifyMessage('n',1);
    }

    @Getter
    public static String[] mainLogoAscii = {
            "ooo        ooooo             oooo      .    o8o      .oooooo.   ooooo        ooooo         .o.       ",
            "`88.       .888'             `888    .o8    `\"'     d8P'  `Y8b  `888'        `888'        .888.      ",
            " 888b     d'888  oooo  oooo   888  .o888oo oooo    888           888          888        .8\"888.     ",
            " 8 Y88. .P  888  `888  `888   888    888   `888    888           888          888       .8  `888.    ",
            " 8  `888'   888   888   888   888    888    888    888           888          888      .8     888.    ",
            " 8    Y     888   888   888   888    888    888    888           888          888     .888oooo8888.   ",
            " 8          888   888   888   888    888 .  888    `88b    ooo   888       o  888    .88'      `888.  ",
            "o8o        o888o  `V88V\"V8P' o888o   \"888\" o888o    `Y8bood8P'  o888ooooood8 o888o  o88o       o8888o "
    };

    private static void getRandomMotto(){
        String[] motto = {"Command-driven simplicity.",
                "Built for you.", "Just type '" + getAnsi256Color(systemMainColor) + "cmds" + getAnsi256Color(systemLayoutColor) + "'.",
                "Command-driven simplicity.","Fast. Smooth. Ready.", "Harmony in command.","It starts with a command.",
                "Optimal width of the terminal window: 117 characters and wider."};
        Random rand = new Random();

        int index = rand.nextInt(0, motto.length);
        message("Everything you need. " + motto[index],15,48,0,out::print);
    }

    private static void getCurrentDate(){
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedTime = localTime.format(myFormatter);
        message("Application start time" + ": "
                        + getAnsi256Color(systemMainColor) + formattedTime
                        + getAnsi256Color(systemLayoutColor) + ".",
                systemLayoutColor,48,0,out::print);
    }

    private static String[] newYearAscii = {
            "ooooo      ooo                                 oooooo   oooo                              ",
            "`888b.     `8'                                  `888.   .8'                               ",
            " 8 `88b.    8   .ooooo.  oooo oooo    ooo        `888. .8'    .ooooo.   .oooo.   oooo d8b ",
            " 8   `88b.  8  d88' `88b  `88. `88.  .8'          `888.8'    d88' `88b `P  )88b  `888\"\"8P ",
            " 8     `88b.8  888ooo888   `88..]88..8'            `888'     888ooo888  .oP\"888   888     ",
            " 8       `888  888    .o    `888'`888'              888      888    .o d8(  888   888     ",
            "o8o        `8  `Y8bod8P'     `8'  `8'              o888o     `Y8bod8P' `Y888\"\"8o d888b    "
    };

    private static void dateChecking(){
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM");
        String actualDate = localTime.format(myFormatter).toLowerCase();

        switch (actualDate){
            case "31-12":
            case "01-01":
                modifyMessage('n',2);
                switchLogoRandomly(newYearAscii, 36);
                modifyMessage('n',2);
                message("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", systemLayoutColor, 36,0,out::print);
                break;
        }
    }

    public static void mainMenuReload(){
        modifyMessage('n',1);
        border();
        displayMenu();
    }
}