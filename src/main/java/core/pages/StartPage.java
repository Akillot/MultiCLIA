package core.pages;

import java.util.Random;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.searchCommands;
import static core.logic.CommandManager.switchLogo;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;
import static core.pages.InfoPage.getVersion;
import static java.lang.System.out;

public class StartPage {

    public static void displayStartPage() {
        progressbarAnimation("Processing");
        displayMenu();
        while (true) {
            try {
                searchCommands();
            } catch (Exception ex) {
                marginBorder();
                errorAscii();
                String errorMessage = (ex.getMessage() != null) ? ex.getMessage() : "Unknown error occurred";
                message(errorMessage, systemRejectionColor, 58, 0, out::print);
            }
        }
    }

    private static void displayMenu() {
        modifyMessage('n',2);
        switchLogo(mainLogoAscii, 48);
        modifyMessage('n',1);
        getRandomMotto();
        marginBorder();

        message("━━━━━━━━━━━━━━━━━━━━━━", systemLayoutColor,58,0, out::print);
        message("Version: " + getVersion() , systemLayoutColor,56,0, out::print);
        message("━━━━━━━━━━━━━━━━━━━━━━", systemLayoutColor,58,0, out::print);

        alert("i", "Enter " + "'" + getAnsi256Color(systemFirstColor) + "cmds" + RESET
                + getAnsi256Color(systemLayoutColor) + "'\n"
                + alignment(56) + "to show list of\n"
                + alignment(56) + "commands" + RESET, 56);

        message("━━━━━━━━━━━━━━━━━━━━━━", systemLayoutColor,58,0,out::print);
        modifyMessage('n', 1);
    }

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

    private static void getRandomMotto(){
        String[] motto = {"Command-driven simplicity.",
                "Version: " + getVersion() + getAnsi256Color(systemLayoutColor) + " — Stable.",
                "Built for you.", "Just type ‘" + getAnsi256Color(systemFirstColor) + "cmds" + getAnsi256Color(systemLayoutColor) + "’.",
                "Command-driven simplicity.","Fast. Smooth. Ready.", "Harmony in command.","It starts with a command.",
                "Optimal width of the terminal window: 117 characters and wider."};
        Random rand = new Random();

        int index = rand.nextInt(0, motto.length);
        message("Everything you need. " + motto[index],15,48,0,out::print);
    }
}