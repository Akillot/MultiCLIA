package layout;

import calculator.Calculator;
import settings.AppearanceSettings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    public static int borderWidth = 21;
    public static int numberOfSymbols = 1;
    public static int delay;

    public static String searchText;
    public static String nameOfFunction = "";
    public static String[] symbolsOfBorder = new String[]{"+", "-", "|", "*", "_", "~", "·"};

    static Scanner scanner = new Scanner(System.in);

    //LOGO//
    //Show a logo
    public static void displayDefaultLogo() {
        System.out.print(centeringFunction(11) + "+---------+\n" +
                centeringFunction(11) + "|" +
                AppearanceSettings.RED + "M" + AppearanceSettings.RESET +
                AppearanceSettings.GREEN + "u" + AppearanceSettings.RESET +
                AppearanceSettings.YELLOW + "l" + AppearanceSettings.RESET +
                AppearanceSettings.BLUE + "t" + AppearanceSettings.RESET +
                AppearanceSettings.PURPLE + "i" + AppearanceSettings.RESET + "CLIA|\n" +
                centeringFunction(11) + "+---------+\n");
    }

    //Show a Google reference logo
    public static void displayGoogleReferenseLogo() {
        System.out.print(centeringFunction(11) + "+---------+\n" +
                centeringFunction(11) + "|" +
                AppearanceSettings.BLUE + "M" + AppearanceSettings.RESET +
                AppearanceSettings.RED + "u" + AppearanceSettings.RESET +
                AppearanceSettings.YELLOW + "l" + AppearanceSettings.RESET +
                AppearanceSettings.BLUE + "ti" + AppearanceSettings.RESET +
                AppearanceSettings.GREEN + "CL" + AppearanceSettings.RESET +
                AppearanceSettings.RED + "IA" + AppearanceSettings.RESET + "|\n" +
                centeringFunction(11) + "+---------+\n");
    }

    //Switching the logo
    public static void logoSwitcher() {
        String nameOfLogo = scanner.nextLine().toLowerCase();

        //IN PROCESS
        if (nameOfLogo.equalsIgnoreCase("default")) {
            displayDefaultLogo();
        } else if (nameOfLogo.equalsIgnoreCase("google")) {
            displayGoogleReferenseLogo();
        }
    }


    //MENU//SETTINGS//
    //Show main menu of the app
    public static void displayMenu() {
        HashMap<String, Runnable> listOfMenuFunctions = new HashMap<>();

        listOfMenuFunctions.put("calculator", Calculator::calculator);
        listOfMenuFunctions.put("settings", UserInterface::displaySettings);
        listOfMenuFunctions.put("all commands", UserInterface::displayListOfMenuCommands);
        listOfMenuFunctions.put("time", UserInterface::displayCurrentTime);
        listOfMenuFunctions.put("info", UserInterface::displayInfo);
        listOfMenuFunctions.put("exit", UserInterface::exitProgram);

        drawHorizontalBorder(numberOfSymbols);
        displayDefaultLogo();
        drawHorizontalBorder(numberOfSymbols);
        delay = 250;
        searchText = centeringFunction(18) + "Search: ";
        for (char ch : searchText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayRedCommands("Error, try again");
            }
        }

        nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2);
        drawHorizontalBorder(numberOfSymbols);

        if (listOfMenuFunctions.containsKey(nameOfFunction)) {
            listOfMenuFunctions.get(nameOfFunction).run();
        } else {
            transitionBorder();
            drawHorizontalBorder(numberOfSymbols);
            displayRedCommands("Command not found");
            drawHorizontalBorder(numberOfSymbols);
            transitionBorder();
        }
    }

    //Show settings
    public static void displaySettings() {
        HashMap<String, Runnable> listOfSettings = new HashMap<>();

        listOfSettings.put("all commands", UserInterface::displayListOfSettings);
        listOfSettings.put("settings value", UserInterface::displayListOfMenuCommands);
        listOfSettings.put("logo", UserInterface::logoSwitcher);
        listOfSettings.put("border", UserInterface::logoSwitcher); //In progress
        listOfSettings.put("delay", UserInterface::displayVersion); //In progress
        listOfSettings.put("exit", UserInterface::exitBlock);

        UserInterface.transitionBorder();
        UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);

        UserInterface.delay = 200;
        String searchText = UserInterface.centeringFunction(10) + "Settings\n";

        for (char ch : searchText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(UserInterface.delay);
            } catch (InterruptedException ex) {
                UserInterface.displayRedCommands("Error, try again");
            }
        }

        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        delay = 250;
        searchText = centeringFunction(18) + "Search: ";
        for (char ch : searchText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayRedCommands("Error, try again");
            }
        }

        nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2); //Wrapping the command from user
        drawHorizontalBorder(numberOfSymbols);

        if (listOfSettings.containsKey(nameOfFunction)) {
            listOfSettings.get(nameOfFunction).run();
        } else {
            transitionBorder();
            drawHorizontalBorder(numberOfSymbols);
            displayRedCommands("Command not found");
            drawHorizontalBorder(numberOfSymbols);
        }
    }


    //WRAPPING//CENTERING//
    //Method to wrap the content
    public static void wrapText(String text, int width) {
        System.out.print(centeringFunction(18)); // Align the first line
        for (int i = 0; i < text.length(); i += width) {
            if (i == 0) {
                int end = Math.min(i + width, text.length());
                System.out.print(text.substring(i, end));
                if (end < text.length()) {
                    System.out.println(); // Add newline after first segment
                }
            } else {
                int end = Math.min(i + width, text.length());
                System.out.print(centeringFunction(18) + text.substring(i, end));
                if (end < text.length()) {
                    System.out.println(); // Add newline after each wrapped line
                }
            }
        }
        System.out.println();
    }

    //Centering the content
    public static String centeringFunction(int widthOfElement) {
        int fullWidth = borderWidth + 2; //Where 2 is "+"
        int oneSide = (fullWidth - widthOfElement) / 2;
        return " ".repeat(Math.max(0, oneSide));
    }


    //BORDERS//
    //Show horizontal border
    public static void drawHorizontalBorder(int numberOfSymbol) {
        System.out.print(symbolsOfBorder[0]);
        for (int i = 0; i < borderWidth; i++) {
            System.out.print(symbolsOfBorder[numberOfSymbol]);
        }
        System.out.println(symbolsOfBorder[0]);
    }

    //Show transition border
    public static void transitionBorder() {
        System.out.println("|" + centeringFunction(-19) + "|");
    }


    //TIME//VERSION//INFO//
    //Show current time
    public static void displayCurrentTime() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern(centeringFunction(10) + "\ndd-MM-yyyy"
                + centeringFunction(5) + "\nHH:mm");
        String formattedTime = localTime.format(myFormatter);
        System.out.println("\nTime is: " + formattedTime);
        drawHorizontalBorder(numberOfSymbols);
    }

    //Show info about MultiCLIA
    public static void displayInfo() {
        displayVersion();
    }

    //Show version
    public static void displayVersion() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(centeringFunction(18) + "Current version:\n"
                + centeringFunction(18) + AppearanceSettings.YELLOW + "0.4.0" + AppearanceSettings.RESET);
        drawHorizontalBorder(numberOfSymbols);
        transitionBorder();
    }


    //LISTS OF COMMANDS//
    //Show list of commands in menu
    public static void displayListOfMenuCommands() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(centeringFunction(8) + "Commands\n"
                + centeringFunction(12) + "· calculator\n"
                + centeringFunction(12) + "· settings\n"
                + centeringFunction(12) + "· all commands\n"
                + centeringFunction(12) + "· info\n"
                + centeringFunction(12) + "· exit");
        drawHorizontalBorder(1);
        transitionBorder();
    }

    //Show list of commands in settings
    public static void displayListOfSettings() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(centeringFunction(12) + "All commands\n" + centeringFunction(12) + "· settings value\n"
                + centeringFunction(12) + "· logo\n"
                + centeringFunction(12) + "· border\n"
                + centeringFunction(12) + "· delay\n"
                + centeringFunction(12) + "· exit");
        drawHorizontalBorder(1);
        transitionBorder();
    }


    //EXIT//RED-COMMANDS//
    //Show errors
    public static void displayRedCommands(String text) {
        System.out.println(centeringFunction(text.length()) + AppearanceSettings.RED + text + AppearanceSettings.RESET);
    }

    //Exit block(apps)
    public static void exitBlock() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        delay = 150;
        String exitText = centeringFunction(18) + AppearanceSettings.RED + "Application exit" + AppearanceSettings.RESET;
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayRedCommands("Error, try again");
            }
        }
        delay = 250;
        String exitTextAdditional = AppearanceSettings.RED + "..." + AppearanceSettings.RESET;
        for (char ch : exitTextAdditional.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayRedCommands("Error, try again");
            }
        }
        drawHorizontalBorder(1);
        transitionBorder();
    }

    //Exit program
    public static void exitProgram() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        delay = 150;
        String exitText = centeringFunction(18) + AppearanceSettings.RED + "Program exit" + AppearanceSettings.RESET;
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayRedCommands("Error, try again");
            }
        }
        delay = 250;
        String exitTextAdditional = AppearanceSettings.RED + "..." + AppearanceSettings.RESET;
        for (char ch : exitTextAdditional.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayRedCommands("Error, try again");
            }
        }
        System.exit(0);
    }
}
