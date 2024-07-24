package layout;

import commands_language_packages.PackageUniter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

import static layout.Logos.logoInitializer;
import static layout.Stylization.*;

public class UserInterface {

    public static Scanner scanner = new Scanner(System.in);

    //MENU//SETTINGS//
    public static String nameOfFunction = "";
    private static int delay;

    //Show main menu of the app (menu ui)
    public static void displayMainMenuUi() {
        PackageUniter registry = new PackageUniter();

        displayTip("Enter 'commands'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "commands");

        displaySlowMotionText(100, 18, true, "Search", ": ");

        String nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2);
        drawFullTripleBorder();

        if (!registry.executeCommand(nameOfFunction)) {
            displayColorCommand("Command not found", "red", (byte) 0);
            drawFullTripleBorder();
        }
    }

    //Show settings ui
    public static void displaySettingsUi() {
        HashMap<String, Runnable> listOfSettings = new HashMap<>();

        listOfSettings.put("commands", UserInterface::displayListOfSettings);
        listOfSettings.put("settings value", UserInterface::displayListOfMenuCommands);
        listOfSettings.put("logo", UserInterface::logoSwitcherUi);
        listOfSettings.put("border", UserInterface::logoSwitcherUi); //In progress
        listOfSettings.put("delay", UserInterface::logoSwitcherUi); //In progress
        listOfSettings.put("exit", UserInterface::exitBlock);

        displaySlowMotionText(100, 8, false, "Settings\n", "");

        drawFullTripleBorder();
        //Display tip for the user
        displayTip("Enter 'commands'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "commands");

        displaySlowMotionText(100, 18, true, "Search", ": ");

        nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2); //Wrapping the command from user
        drawFullTripleBorder();

        if (listOfSettings.containsKey(nameOfFunction)) {
            listOfSettings.get(nameOfFunction).run();
        } else {
            drawFullTripleBorder();
            displayColorCommand("Command not found", "red", (byte) 0);
            drawFullTripleBorder();
        }
    }

    //Show a logo switcher user interface
    public static void logoSwitcherUi() {
        displayTip("Enter 'logos'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "all logos");

        displaySlowMotionText(200, 18, true, "Search", ": ");

        String nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2);

        logoInitializer(nameOfFunction);
        drawFullTripleBorder();
    }



    //TIME//VERSION//INFO//TIP//
    //Show current time
    public static void displayCurrentTime() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern(contentAlignment(10) + "\ndd-MM-yyyy"
                + contentAlignment(5) + "\nHH:mm");
        String formattedTime = localTime.format(myFormatter);
        System.out.println(contentAlignment(9) + "Time is: " + formattedTime);
        drawHorizontalBorder(numberOfSymbols);
    }

    //Show info about MultiCLIA
    public static void displayInfo() {
        //IN PROGRESS
        displayVersion();
    }

    //Show version
    public static void displayVersion() {
        System.out.println(contentAlignment(18) + "Current version:");
        displayColorCommand("0.4.8", "randomly", (byte) 0);
    }

    //Show tip for the user
    public static void displayTip(String text) {
        System.out.println(contentAlignment(18) + BOLD
                + "[" + YELLOW + BOLD + "i" + RESET
                + BOLD + "] " + text + RESET);
        drawFullTripleBorder();
    }


    //EXIT//
    //Exit block(apps)
    public static void exitBlock() {
        drawFullTripleBorder();
        delay = 150;
        String exitText = contentAlignment(18) + RED + BOLD + "Application exit" + RESET;
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorCommand("Error, try again", "red", (byte) 0);
            }
        }
        delay = 250;
        String exitTextAdditional = RED + "...\n" + RESET;
        for (char ch : exitTextAdditional.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorCommand("Error, try again", "red", (byte) 0);
            }
        }
        drawFullTripleBorder();
    }

    //Exit program
    public static void exitProgram() {
        delay = 150;
        String exitText = contentAlignment(18) + RED + "Program exit" + RESET;
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorCommand("Error, try again", "red", (byte) 0);
            }
        }
        delay = 250;
        String exitTextAdditional = RED + "..." + RESET;
        for (char ch : exitTextAdditional.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorCommand("Error, try again", "red", (byte) 0);
            }
        }
        System.exit(0);
    }


    //LISTS OF COMMANDS//
    //Show list of commands in menu
    public static void displayListOfMenuCommands() {
        System.out.println(contentAlignment(8) + "Commands\n"
                + contentAlignment(18) + "· calculator\n"
                + contentAlignment(18) + "· settings\n"
                + contentAlignment(18) + "· commands\n"
                + contentAlignment(18) + "· info\n"
                + contentAlignment(18) + "· exit");
        drawFullTripleBorder();
    }

    //Show list of commands in settings
    public static void displayListOfSettings() {
        System.out.println(contentAlignment(8) + "Commands\n" + contentAlignment(18) + "· settings value\n"
                + contentAlignment(18) + "· logo\n"
                + contentAlignment(18) + "· border\n"
                + contentAlignment(18) + "· delay\n"
                + contentAlignment(18) + "· exit");
        drawFullTripleBorder();
    }
}