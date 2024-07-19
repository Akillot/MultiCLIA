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
    public static String nameOfFunction = "";
    public static String[] symbolsOfBorder = new String[]{"+", "-", "|", "*", "_", "~", "·"};

    static Scanner scanner = new Scanner(System.in);


    //GRAPHIC
    //Show a logo
    public static void displayLogo() {
        System.out.print(centeringFunction(11) + "+---------+\n" + centeringFunction(11) + "|" + AppearanceSettings.RED + "M" + AppearanceSettings.RESET +
                AppearanceSettings.GREEN + "u" + AppearanceSettings.RESET +
                AppearanceSettings.YELLOW + "l" + AppearanceSettings.RESET +
                AppearanceSettings.BLUE + "t" + AppearanceSettings.RESET +
                AppearanceSettings.PURPLE + "i" + AppearanceSettings.RESET + "CLIA|\n" + centeringFunction(11) + "+---------+\n");
    }


    //GRAPHIC
    //Centering the content
    public static String centeringFunction(int widthOfElement) {
        int fullWidth = borderWidth + 2; //Where 2 is "+"
        int oneSide = (fullWidth - widthOfElement) / 2;

        return " ".repeat(Math.max(0, oneSide));
    }


    //GRAPHIC
    //Show version
    public static void displayVersionInfo() {
        System.out.println("Current version:\n" + AppearanceSettings.YELLOW + "0.3.9");
    }


    //GRAPHIC
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
        transitionBorder();
    }


    //GRAPHIC
    //Show horizontal border
    public static void drawHorizontalBorder(int numberOfSymbol) {
        System.out.print(symbolsOfBorder[0]);
        for (int i = 0; i < borderWidth; i++) {
            System.out.print(symbolsOfBorder[numberOfSymbol]);
        }
        System.out.println(symbolsOfBorder[0]);
    }


    //GRAPHIC
    //Show main menu of the app
    public static void displayMenu() {
        HashMap<String, Runnable> listOfNoParamFunctions = new HashMap<>();
        listOfNoParamFunctions.put("calculator", Calculator::calculator);
        listOfNoParamFunctions.put("settings", UserInterface::displaySettings);
        listOfNoParamFunctions.put("all commands", UserInterface::displayListOfMenuCommands);
        listOfNoParamFunctions.put("time", UserInterface::displayCurrentTime);
        listOfNoParamFunctions.put("version", UserInterface::displayVersionInfo);
        listOfNoParamFunctions.put("exit", UserInterface::exitProgram);

        drawHorizontalBorder(1);
        displayLogo();
        drawHorizontalBorder(1);
        delay = 250;
        String searchText = centeringFunction(18) + "Search: ";
        for (char ch : searchText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayError("Error, try again");
            }
        }

        nameOfFunction = scanner.nextLine().toLowerCase();
        wrapText(nameOfFunction, borderWidth - 2);
        drawHorizontalBorder(numberOfSymbols);

        if (listOfNoParamFunctions.containsKey(nameOfFunction)) {
            listOfNoParamFunctions.get(nameOfFunction).run();
        } else {
            transitionBorder();
            drawHorizontalBorder(numberOfSymbols);
            displayError("Command not found");
            drawHorizontalBorder(numberOfSymbols);
            transitionBorder();
        }
    }


    //GRAPHIC
    //Show settings
    public static void displaySettings() {

    }


    //GRAPHIC
    //Show list of commands in menu
    public static void displayListOfMenuCommands() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(centeringFunction(12) + "All commands\n" + centeringFunction(12) + "· calculator\n"
                + centeringFunction(12) + "· settings\n"
                + centeringFunction(12) + "· show list\n" + centeringFunction(12) + "· exit");
        drawHorizontalBorder(1);
        System.out.println("|" + centeringFunction(-19) + "|");
    }


    //GRAPHIC
    //Show errors
    public static void displayError(String text) {
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(centeringFunction(text.length()) + AppearanceSettings.RED + text + AppearanceSettings.RESET);
    }


    //GRAPHIC
    //Exit program
    public static void exitProgram() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        delay = 150;
        String exitText = centeringFunction(18) + AppearanceSettings.RED + "Exiting program" + AppearanceSettings.RESET;
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                System.out.println(AppearanceSettings.RED + "Error, try again" + AppearanceSettings.RESET);
            }
        }
        delay = 250;
        String exitTextAdditional = AppearanceSettings.RED + "..." + AppearanceSettings.RESET;
        for (char ch : exitTextAdditional.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                System.out.println(AppearanceSettings.RED + "Error, try again" + AppearanceSettings.RESET);
            }
        }
        System.exit(0);
    }


    //GRAPHIC
    //Show transition border
    public static void transitionBorder() {
        System.out.println("|" + centeringFunction(-19) + "|");
    }


    //GRAPHIC
    //Show list of operations in calculator
    public static void displayCalculatorOperationsList() {
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
        System.out.println(centeringFunction(10) + "Operations:");
        drawHorizontalBorder(numberOfSymbols);
        System.out.println("1. sum[+]" + centeringFunction(10) + "2. sub[-]" +
                "\n3. multi[*]" + centeringFunction(14) + "4. div[/]\n5. pow[^]" + centeringFunction(10) + "6. exit[x]");
        drawHorizontalBorder(numberOfSymbols);
    }


    //LOGIC
    //Method to wrap the text
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
}
