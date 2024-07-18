package layout;

import calculator.Calculator;
import settings.AppearanceSettings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    public static int borderWidth = 21;
    public static int delay;
    public static String nameOfFunction = "";
    public static String[] symbolsOfBorder = new String[]{"+", "-", "|"};

    static Scanner scanner = new Scanner(System.in);

    //Show a logo
    public static void displayLogo() {
        System.out.print(centeringFunction(11) + "+---------+\n" + centeringFunction(11) + "|" + AppearanceSettings.RED + "M" + AppearanceSettings.RESET +
                AppearanceSettings.GREEN + "u" + AppearanceSettings.RESET +
                AppearanceSettings.YELLOW + "l" + AppearanceSettings.RESET +
                AppearanceSettings.BLUE + "t" + AppearanceSettings.RESET +
                AppearanceSettings.PURPLE + "i" + AppearanceSettings.RESET + "CLIA|\n" + centeringFunction(11) + "+---------+\n");
    }

    public static String centeringFunction(int widthOfElement) {
        int fullWidth = borderWidth + 2; //Where 2 is "+"
        int oneSide = (fullWidth - widthOfElement) / 2;

        return " ".repeat(Math.max(0, oneSide));
    }

    //Show version
    public static void displayVersionInfo() {
        System.out.println("Current version:\n" + AppearanceSettings.YELLOW + "0.3.6");
    }

    //Show current time
    public static void displayCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("\ndd-MM-yyyy \nHH:mm");
        String formattedTime = localTime.format(myFormatter);
        System.out.println("\nTime is: " + formattedTime);
    }

    //Show horizontal border
    public static void drawHorizontalBorder() {
        System.out.print(symbolsOfBorder[0]);
        for (int i = 0; i < borderWidth; i++) {
            System.out.print(symbolsOfBorder[1]);
        }
        System.out.println(symbolsOfBorder[0]);
    }

    //Show main menu of the app
    public static void displayMenu() {
        HashMap<String, Runnable> listOfNoParamFunctions = new HashMap<>();
        listOfNoParamFunctions.put("calculator", Calculator::calculator);
        listOfNoParamFunctions.put("settings", UserInterface::displaySettings);
        listOfNoParamFunctions.put("all commands", UserInterface::displayListOfMenuCommands);
        listOfNoParamFunctions.put("time", UserInterface::displayCurrentTime);
        listOfNoParamFunctions.put("version", UserInterface::displayVersionInfo);
        listOfNoParamFunctions.put("exit", UserInterface::exitProgram);

        drawHorizontalBorder();
        displayLogo();
        drawHorizontalBorder();
        delay = 250;
        String searchText = centeringFunction(18) + "Search: ";
        for (char ch : searchText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                System.out.println(AppearanceSettings.RED + "Error, try again" + AppearanceSettings.RESET);
            }
        }

        nameOfFunction = scanner.nextLine().toLowerCase();
        drawHorizontalBorder();

    }


    //Show settings
    public static void displaySettings() {

    }

    public static void displayListOfMenuCommands() {
        System.out.println(centeringFunction(12) + "All commands:\n" + centeringFunction(12) + "· calculator\n"
                + centeringFunction(12) + "· settings\n"
                + centeringFunction(12) + "· show list\n" + centeringFunction(12) + "· exit");
    }

    //Exit program
    public static void exitProgram() {
        delay = 250;
        String exitText = AppearanceSettings.RED + "Exiting program" + AppearanceSettings.RESET;
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                System.out.println(AppearanceSettings.RED + "Error, try again" + AppearanceSettings.RESET);
            }
        }
        delay = 400;
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

    //Show list of operations in calculator
    public static void displayCalculatorOperationsList() {
        System.out.println(centeringFunction(10) + "\nOperations:\n1. sum[+]" + centeringFunction(10) + "2. sub[-]" +
                "\n3. multi[*]" + centeringFunction(10) + " 4. div[/]\n5. pow[^]" + centeringFunction(10) + "6. exit[x]\n");
    }

    //IN PROCESS
    //Method to wrap the text
    public static void wrapText(String text, int width) {
        for (int i = 0; i < text.length(); i += width) {
            if (i + width > text.length()) {
                System.out.println(text.substring(i));
            } else {
                System.out.println(text.substring(i, i + width));
            }
        }
    }
}
