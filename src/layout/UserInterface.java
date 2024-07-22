package layout;

import Commands_LanguagePackages.PackageUniter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

import static layout.Stylization.*;

public class UserInterface {

    public static Scanner scanner = new Scanner(System.in);

    //MENU//SETTINGS//
    //Show main menu of the app
    public static String nameOfFunction = "";
    private static int delay;

    public static void displayMenu() {
        PackageUniter registry = new PackageUniter();

        //Display tip for the user
        displayTip("Enter 'commands'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "commands");

        displaySlowMotionText(200, 18, true, "Search", ": ");

        String nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2);
        drawFullTripleBorder();

        if (registry.executeCommand(nameOfFunction)) {
            // Command executed successfully
        } else {
            displayColorCommand("Command not found", "red", (byte) 0);
            drawFullTripleBorder();
        }
    }

    //Show settings
    public static void displaySettings() {
        HashMap<String, Runnable> listOfSettings = new HashMap<>();

        listOfSettings.put("commands", UserInterface::displayListOfSettings);
        listOfSettings.put("settings value", UserInterface::displayListOfMenuCommands);
        listOfSettings.put("logo", Stylization::logoSwitcher);
        listOfSettings.put("border", Stylization::logoSwitcher); //In progress
        listOfSettings.put("delay", Stylization::logoSwitcher); //In progress
        listOfSettings.put("exit", UserInterface::exitBlock);

        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        displaySlowMotionText(200, 8, false, "Settings\n", "");

        drawHorizontalBorder(numberOfSymbols);
        //Display tip for the user
        displayTip("Enter 'commands'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "commands");

        displaySlowMotionText(200, 18, true, "Search", ": ");

        nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2); //Wrapping the command from user

        if (listOfSettings.containsKey(nameOfFunction)) {
            listOfSettings.get(nameOfFunction).run();
        } else {
            transitionBorder();
            drawHorizontalBorder(numberOfSymbols);
            displayColorCommand("Command not found", "red", (byte) 0);
        }
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
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(contentAlignment(18) + "Current version:");
        displayColorCommand("0.4.7", "randomly", (byte) 0);
        drawFullTripleBorder();
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
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
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
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(contentAlignment(8) + "Commands\n" + contentAlignment(18) + "· settings value\n"
                + contentAlignment(18) + "· logo\n"
                + contentAlignment(18) + "· border\n"
                + contentAlignment(18) + "· delay\n"
                + contentAlignment(18) + "· exit");
        drawFullTripleBorder();
    }
}