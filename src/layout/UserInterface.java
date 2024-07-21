package layout;

import calculator.Calculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static settings.AppearanceSettings.*;

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
        System.out.print(contentAlignment(11) +
                BOLD + "+---------+\n" +
                contentAlignment(11) + "|" +
                RED + BOLD + "M" + RESET + GREEN + BOLD + "u" + RESET +
                YELLOW + BOLD + "l" + RESET + BLUE + BOLD + "t" + RESET +
                PURPLE + BOLD + "i" + RESET + BOLD + "CLIA|\n" +
                contentAlignment(11) + "+---------+\n" + RESET);
    }

    //Show a Google reference logo
    public static void displayGoogleReferenseLogo() {
        System.out.print(contentAlignment(11) + "+---------+\n" +
                contentAlignment(11) + "|" +
                BLUE + "M" + RESET + RED + "u" + RESET + YELLOW + "l" + RESET +
                BLUE + "ti" + RESET + GREEN + "CL" + RESET + RED + "IA" + RESET + "|\n" +
                contentAlignment(11) + "+---------+\n");
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
        listOfMenuFunctions.put("commands", UserInterface::displayListOfMenuCommands);
        listOfMenuFunctions.put("time", UserInterface::displayCurrentTime);
        listOfMenuFunctions.put("info", UserInterface::displayInfo);
        listOfMenuFunctions.put("exit", UserInterface::exitProgram);

        System.out.println(contentAlignment(18) + BOLD
                + "[" + YELLOW + BOLD + "i" + RESET
                + BOLD + "] " + "Enter 'commands'\n"
                + contentAlignment(18) + "to show list of\n"
                + contentAlignment(18) + "commands" + RESET);
        drawFullTripleBorder();

        delay = 250;
        searchText = contentAlignment(18) + UNDERLINE + BOLD + "Search" + RESET + ": ";
        for (char ch : searchText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorfulCommands("Error, try again", "red");
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
            displayColorfulCommands("Command not found", "red");

            drawFullTripleBorder();
        }
    }

    //Show settings
    public static void displaySettings() {
        HashMap<String, Runnable> listOfSettings = new HashMap<>();

        listOfSettings.put("commands", UserInterface::displayListOfSettings);
        listOfSettings.put("settings value", UserInterface::displayListOfMenuCommands);
        listOfSettings.put("logo", UserInterface::logoSwitcher);
        listOfSettings.put("border", UserInterface::logoSwitcher); //In progress
        listOfSettings.put("delay", UserInterface::logoSwitcher); //In progress
        listOfSettings.put("exit", UserInterface::exitBlock);

        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);

        delay = 200;
        String searchText = contentAlignment(10) + "Settings\n";

        for (char ch : searchText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorfulCommands("Error, try again", "red");
            }
        }

        drawHorizontalBorder(numberOfSymbols);
        delay = 250;
        searchText = contentAlignment(18) + UNDERLINE + BOLD + "Search" + RESET + ": ";
        for (char ch : searchText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorfulCommands("Error, try again", "red");
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
            displayColorfulCommands("Command not found", "red");
            drawHorizontalBorder(numberOfSymbols);
        }
    }


    //WRAPPING//ALIGNMENT//
    //Method to wrap the content
    public static void wrapText(String text, int width) {
        //System.out.print(centeringFunction(18)); // Align the first line
        for (int i = 0; i < text.length(); i += width) {
            if (i == 0) {
                int end = Math.min(i + width, text.length());
                drawFullTripleBorder();
                System.out.print(BOLD + contentAlignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    System.out.println(); // Add newline after first segment
                }
            } else {
                int end = Math.min(i + width, text.length());
                System.out.print(BOLD + contentAlignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    System.out.println(); // Add newline after each wrapped line
                }
            }
        }
        System.out.println();
    }

    //Aligns the content
    public static String contentAlignment(int widthOfElement) {
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
        System.out.println("|" + contentAlignment(-19) + "|");
    }

    //Show triple border
    public static void drawFullTripleBorder() {
        drawHorizontalBorder(numberOfSymbols);
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
    }


    //TIME//VERSION//INFO//
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
        displayColorfulCommands("0.4.2", "randomly");
        drawFullTripleBorder();
    }


    //LISTS OF COMMANDS//
    //Show list of commands in menu
    public static void displayListOfMenuCommands() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(contentAlignment(8) + "Commands\n"
                + contentAlignment(12) + "· calculator\n"
                + contentAlignment(12) + "· settings\n"
                + contentAlignment(12) + "· commands\n"
                + contentAlignment(12) + "· info\n"
                + contentAlignment(12) + "· exit");
        drawFullTripleBorder();
    }

    //Show list of commands in settings
    public static void displayListOfSettings() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(contentAlignment(12) + "Commands\n" + contentAlignment(12) + "· settings value\n"
                + contentAlignment(12) + "· logo\n"
                + contentAlignment(12) + "· border\n"
                + contentAlignment(12) + "· delay\n"
                + contentAlignment(12) + "· exit");
        drawHorizontalBorder(1);
        transitionBorder();
    }


    //EXIT//COLORFUL-COMMANDS//
    //Show colorful content//exit and other red text
    public static void displayColorfulCommands(String text, String color) {
        switch (color) {
            case "red":
                System.out.println(contentAlignment(text.length()) + RED + BOLD + text + RESET);
                break;
            case "yellow":
                System.out.println(contentAlignment(text.length()) + YELLOW + BOLD + text + RESET);
                break;
            case "green":
                System.out.println(contentAlignment(text.length()) + GREEN + BOLD + text + RESET);
                break;
            case "blue":
                System.out.println(contentAlignment(text.length()) + BLUE + BOLD + text + RESET);
                break;
            case "purple":
                System.out.println(contentAlignment(text.length()) + PURPLE + BOLD + text + RESET);
                break;
            case "cyan":
                System.out.println(contentAlignment(text.length()) + CYAN + BOLD + text + RESET);
                break;
            case "randomly":
                Random rand = new Random();
                int randomColor = rand.nextInt(1, 7);

                switch (randomColor) {
                    case 1:
                        System.out.println(contentAlignment(text.length()) + RED + BOLD + text + RESET);
                        break;
                    case 2:
                        System.out.println(contentAlignment(text.length()) + YELLOW + BOLD + text + RESET);
                        break;
                    case 3:
                        System.out.println(contentAlignment(text.length()) + GREEN + BOLD + text + RESET);
                        break;
                    case 4:
                        System.out.println(contentAlignment(text.length()) + BLUE + BOLD + text + RESET);
                        break;
                    case 5:
                        System.out.println(contentAlignment(text.length()) + PURPLE + BOLD + text + RESET);
                        break;
                    case 6:
                        System.out.println(contentAlignment(text.length()) + CYAN + BOLD + text + RESET);
                        break;
                }
        }
    }

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
                displayColorfulCommands("Error, try again", "red");
            }
        }
        delay = 250;
        String exitTextAdditional = RED + "...\n" + RESET;
        for (char ch : exitTextAdditional.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorfulCommands("Error, try again", "red");
            }
        }
        drawFullTripleBorder();
    }

    //Exit program
    public static void exitProgram() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        delay = 150;
        String exitText = contentAlignment(18) + RED + "Program exit" + RESET;
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorfulCommands("Error, try again", "red");
            }
        }
        delay = 250;
        String exitTextAdditional = RED + "..." + RESET;
        for (char ch : exitTextAdditional.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorfulCommands("Error, try again", "red");
            }
        }
        System.exit(0);
    }
}