import java.util.Random;
import java.util.Scanner;

public class AppearanceFeatures {

    public static final String WHITE = "\033[0;38m";

    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String GRAY = "\033[0;37m";
    static Scanner scanner = new Scanner(System.in);

    static String border = "-------------------";

    static void changeBorder() {
        System.out.println(" ");
        String[] tableStyleArray = {"+++++++++++++++++++", "===================",
                "~+~+~+~+~+~+~+~+~+~", "~-~-~-~-~-~-~-~-~-~", "+-+-+-+-+-+-+-+-+-+",
                "~~~~~~~~~~~~~~~~~~~", "...................", "*******************",
                "I-I-I-I-I-I-I-I-I-I", "I0I0I0I0I0I0I0I0I0I"};
        Random rand = new Random();
        border = tableStyleArray[rand.nextInt(tableStyleArray.length)];
    }

    static void changeColorRandom() {
        Random rand = new Random();
        int colorNum;
        for (int i = 0; i < 200; i++) {
            colorNum = rand.nextInt(0, 10);
            switch (colorNum) {
                case 0:
                    System.out.println(BLACK);
                    break;
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
    }

    /*static void changeColorRainbow() {
        while (true) {

            System.out.println();
        }
    }*/

    static void changeColor() {
        System.out.print("To which color\ndo you want to change: " +
                RED + "\n1. Red" + RESET + GREEN + "\n2. Green" + RESET +
                YELLOW + "\n3. Yellow" + RESET + BLUE + "\n4. Blue" + RESET +
                PURPLE + "\n5. Purple" + RESET + CYAN + "\n6. Cyan" + RESET +
                GRAY + "\n7. grey" + RESET + "\n8. grey" + WHITE + "\n9. Exit[x]" + "\n" + border + " \nYour choice is: ");

        String colorChoice = scanner.nextLine().toLowerCase();
        System.out.println(border);

        switch (colorChoice) {
            case "red":
            case "1":
                System.out.println(RED);
                break;
            case "green":
            case "2":
                System.out.println(GREEN);
                break;
            case "yellow":
            case "3":
                System.out.println(YELLOW);
                break;
            case "blue":
            case "4":
                System.out.println(BLUE);
                break;
            case "purple":
            case "5":
                System.out.println(PURPLE);
                break;
            case "cyan":
            case "6":
                System.out.println(CYAN);
                break;
            case "grey":
            case "7":
                System.out.println(GRAY);
                break;
            case "8":
                System.out.println(WHITE);
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
}
