import java.util.Random;
import java.util.Scanner;

public class AppearanceFeatures {

    public static final String WHITE = "\033[0;38m";
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String GRAY = "\033[0;37m";

    static Scanner scanner = new Scanner(System.in);
    static String border = WHITE + "-------------------" + RESET;

    //Method which contains styles of border
    static void changeBorder() {
        System.out.println(" ");
        String[] tableStyleArray = {"+++++++++++++++++++", "===================",
                "~+~+~+~+~+~+~+~+~+~", "~-~-~-~-~-~-~-~-~-~", "+-+-+-+-+-+-+-+-+-+",
                "~~~~~~~~~~~~~~~~~~~", "...................", "*******************",
                "I-I-I-I-I-I-I-I-I-I", "_-_-_-_-_-_-_-_-_-_"};
        Random rand = new Random();
        border = tableStyleArray[rand.nextInt(tableStyleArray.length)];
    }

    //Method for changing color of text randomly
    static void changeColorRandom() {
        Random rand = new Random();
        int colorNum;
            colorNum = rand.nextInt(0, 10);
            switch (colorNum) {
                case 1:
                    System.out.println(RED);
                    break;
                case 2:
                    System.out.println(GREEN);
                    break;
                case 3:
                    System.out.println(YELLOW);
                    break;
                case 4:
                    System.out.println(BLUE);
                    break;
                case 5:
                    System.out.println(PURPLE);
                    break;
                case 6:
                    System.out.println(CYAN);
                    break;
                case 7:
                    System.out.println(GRAY);
                    break;
                case 8:
                    System.out.println(WHITE);
                    break;
            }
    }

    // 4. Method for changing color of border
    static void changeBorderColor(String choiceColor) {
        switch (choiceColor) {
            case "red":
            case "1":
                border = RED + border + RESET;
                break;
            case "green":
            case "2":
                border = GREEN + border + RESET;
                break;
            case "yellow":
            case "3":
                border = YELLOW + border + RESET;
                break;
            case "blue":
            case "4":
                border = BLUE + border + RESET;
                break;
            case "purple":
            case "5":
                border = PURPLE + border + RESET;
                break;
            case "cyan":
            case "6":
                border = CYAN + border + RESET;
                break;
            case "gray":
            case "7":
                border = GRAY + border + RESET;
                break;
            case "white":
            case "8":
                border = WHITE + border + RESET;
                break;
            case "x":
            case "exit":
            case "9":
                System.out.println("Exiting...");
                break;
            case "text-random":
                changeColorRandom();
                break;
        }
    }

    // 3. Method for changing text color
    static void changeTextColor(String choiceColor) {
        switch (choiceColor) {
            case "red":
                System.out.print(RED);
                break;
            case "green":
                System.out.print(GREEN);
                break;
            case "yellow":
                System.out.print(YELLOW);
                break;
            case "blue":
                System.out.print(BLUE);
                break;
            case "purple":
                System.out.print(PURPLE);
                break;
            case "cyan":
                System.out.print(CYAN);
                break;
            case "gray":
                System.out.print(GRAY);
                break;
            case "white":
                System.out.print(WHITE);
                break;
            case "x":
            case "exit":
                System.out.println("Exiting...");
                break;
            case "text-random":
                changeColorRandom();
                break;
        }
    }

    // 5. Method to show a list of colors
    static void listOfColors() {
        System.out.print("To which color\ndo you want to change: " +
                RED + "\n1. Red" + RESET + GREEN + "\n2. Green" + RESET +
                YELLOW + "\n3. Yellow" + RESET + BLUE + "\n4. Blue" + RESET +
                PURPLE + "\n5. Purple" + RESET + CYAN + "\n6. Cyan" + RESET +
                GRAY + "\n7. Gray" + RESET + WHITE + "\n8. White" + "\n9. Exit[x]" + RESET + "\n" + border + " \nYour choice is: ");
    }

    // 2. Method for changing color of border or text
    static void changeColor() {
        System.out.println("Pick the color of \nwhat you want to change.\n" + border);
        System.out.println("1. Text[abc]\n2. Border[---]\n3. Exit[x]\n" + border + " \nYour choice is: ");
        String partColorChoice = scanner.nextLine().toLowerCase();
        System.out.println(border);
        String choiceColor;

        if (partColorChoice.equals("1") || partColorChoice.equalsIgnoreCase("abc")
                || partColorChoice.equalsIgnoreCase("text")) {
            listOfColors();
            choiceColor = scanner.nextLine().toLowerCase();
            System.out.println(border);
            changeTextColor(choiceColor);

        } else if (partColorChoice.equals("2") || partColorChoice.equalsIgnoreCase("---")
                || partColorChoice.equalsIgnoreCase("border")) {
            listOfColors();
            choiceColor = scanner.nextLine().toLowerCase();
            System.out.println(border);
            changeBorderColor(choiceColor);

        } else if (partColorChoice.equals("3") || partColorChoice.equalsIgnoreCase("x")
                || partColorChoice.equalsIgnoreCase("Exit")) {
            System.out.println("Exiting...\n");
        }
    }

    // 1. Method for choosing basic changes of color or border
    static void basicChanges() {
        System.out.println("Choose what you\nwant to change.\n" + border);
        System.out.println("1. Color[~]\n2. Border[-]\n3. Exit[x]\n" + border + " \nYour choice is: ");
        String basicChangesChoice = scanner.nextLine().toLowerCase();
        System.out.println(border);
        if (basicChangesChoice.equals("1") || basicChangesChoice.equals("~") ||
                basicChangesChoice.equalsIgnoreCase("color")) {
            changeColor();
        } else if (basicChangesChoice.equals("2") || basicChangesChoice.equals("-") ||
                basicChangesChoice.equalsIgnoreCase("border")) {
            changeBorder();
        } else if (basicChangesChoice.equals("3") || basicChangesChoice.equalsIgnoreCase("x") ||
                basicChangesChoice.equalsIgnoreCase("exit")) {
            System.out.println("Exiting...\n");
        }
    }
}
