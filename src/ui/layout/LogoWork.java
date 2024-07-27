package ui.layout;

import java.util.HashMap;

import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.contentAlignment;

public class LogoWork {

    public static String nameOfLogo = "neon";

    public static void logoInitializer(String requiredCommand) {
        HashMap<String, Runnable> listOfCommands = new HashMap<>();

        listOfCommands.put("default", LogoWork::displayDefaultLogo);
        listOfCommands.put("google", LogoWork::displayGoogleReferenceLogo);
        listOfCommands.put("neon", LogoWork::displayNeonDefaultLogo);

        listOfCommands.put("exit", AdditionalFunctions::exitBlock);

        Runnable command = listOfCommands.get(requiredCommand.toLowerCase());
        if (command != null) {
            command.run();
        } else {
            displayColorCommand("Command not found", "red", (byte) 0);
        }
    }


    //Default logo
    public static void displayDefaultLogo() {
        System.out.print(contentAlignment(11) +
                BOLD + "+---------+\n" +
                contentAlignment(11) + "|" +
                RED + BOLD + "M" + RESET + GREEN + BOLD + "u" + RESET +
                YELLOW + BOLD + "l" + RESET + BLUE + BOLD + "t" + RESET +
                PURPLE + BOLD + "i" + RESET + BOLD + "CLIA|\n" +
                contentAlignment(11) + "+---------+\n" + RESET);
    }

    //Google-like logo
    public static void displayGoogleReferenceLogo() {
        System.out.print(contentAlignment(11) + "+---------+\n" +
                contentAlignment(11) + "|" +
                BLUE + "M" + RESET + RED + "u" + RESET + YELLOW + "l" + RESET +
                BLUE + "ti" + RESET + GREEN + "CL" + RESET + RED + "IA" + RESET + "|\n" +
                contentAlignment(11) + "+---------+\n");
    }

    //Neon logo
    public static void displayNeonDefaultLogo() {
        System.out.print(contentAlignment(11) +
                BOLD + "+---------+\n" +
                contentAlignment(11) + "|" +
                RED + BOLD + "M" + RESET + PURPLE + BOLD + "u" + RESET +
                RED + BOLD + "l" + RESET + PURPLE + BOLD + "t" + RESET +
                RED + BOLD + "i" + RESET + BOLD + "CLIA|\n" +
                contentAlignment(11) + "+---------+\n" + RESET);
    }
}