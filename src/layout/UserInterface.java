package layout;

import calculator.Calculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class UserInterface {

    //STYLE
    //Colors
    public static final String WHITE = "\033[0;38m";
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";

    //Text styles
    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";

    //Show colorful content with alignment
    public static void displayColorCommand(String text, String colorName, byte alignment) {
        Color color;
        try {
            color = Color.valueOf(colorName.toUpperCase());
        } catch (IllegalArgumentException e) {
            if (colorName.equalsIgnoreCase("randomly")) {
                color = getRandomColor();
            } else {
                displayColorCommand("Invalid input", "red", (byte) 0);
                return;
            }
        }

        String coloredText = getColoredText(text, color);
        int alignLength = alignment == 0 ? text.length() : alignment;

        if (alignment < 0) {
            displayColorCommand("Invalid input", "red", (byte) 0);
        } else {
            System.out.println(contentAlignment(alignLength) + coloredText);
        }
    }

    static String getColoredText(String text, Color color) {
        String colorCode;
        switch (color) {
            case RED:
                colorCode = RED;
                break;
            case YELLOW:
                colorCode = YELLOW;
                break;
            case GREEN:
                colorCode = GREEN;
                break;
            case BLUE:
                colorCode = BLUE;
                break;
            case PURPLE:
                colorCode = PURPLE;
                break;
            case CYAN:
                colorCode = CYAN;
                break;
            default:
                colorCode = RESET;
                break;
        }
        return colorCode + BOLD + text + RESET;
    }

    static Color getRandomColor() {
        Random rand = new Random();
        int randomColorIndex = rand.nextInt(Color.values().length);
        return Color.values()[randomColorIndex];
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
                displayColorCommand("Error, try again", "red", (byte) 0);
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
            displayColorCommand("Command not found", "red", (byte) 0);

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
                displayColorCommand("Error, try again", "red", (byte) 0);
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
                displayColorCommand("Error, try again", "red", (byte) 0);
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
            displayColorCommand("Command not found", "red", (byte) 0);
            drawHorizontalBorder(numberOfSymbols);
        }
    }

    //Show version
    public static void displayVersion() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(contentAlignment(18) + "Current version:");
        displayColorCommand("0.4.5", "randomly", (byte) 0);
        drawFullTripleBorder();
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

    //EXIT//COLORFUL-COMMANDS//
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

    enum Color {
        RED, YELLOW, GREEN, BLUE, PURPLE, CYAN
    }
}