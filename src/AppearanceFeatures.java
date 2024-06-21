import java.util.Random;
import java.util.Scanner;

public class AppearanceFeatures {
    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String GRAY = "\033[0;37m";

    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
    public static final String WHITE_BACKGROUND = "\033[47m";

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
                    System.out.println(AppearanceFeatures.BLACK);
                    break;
                case 1:
                    System.out.println(AppearanceFeatures.RED);
                    break;
                case 2:
                    System.out.println(AppearanceFeatures.GREEN);
                    break;
                case 3:
                    System.out.println(AppearanceFeatures.YELLOW);
                    break;
                case 4:
                    System.out.println(AppearanceFeatures.BLUE);
                    break;
                case 5:
                    System.out.println(AppearanceFeatures.PURPLE);
                    break;
                case 6:
                    System.out.println(AppearanceFeatures.CYAN);
                    break;
                case 7:
                    System.out.println(AppearanceFeatures.GRAY);
                    break;
            }
        }
    }

    static void changeColorRainbow() {
        while (true) {

            System.out.println();
        }
    }

    static void changeColor() {
        System.out.print("To which color do you want to change: " +
                "\nColors:" + AppearanceFeatures.BLACK + "\n1. Black" + AppearanceFeatures.RESET +
                AppearanceFeatures.RED + "\n2. Red" + AppearanceFeatures.RESET + AppearanceFeatures.GREEN + "\n3. Green" + AppearanceFeatures.RESET +
                AppearanceFeatures.YELLOW + "\n4. Yellow" + AppearanceFeatures.RESET + AppearanceFeatures.BLUE + "\n5. Blue" + AppearanceFeatures.RESET +
                AppearanceFeatures.PURPLE + "\n6. Purple" + AppearanceFeatures.RESET + AppearanceFeatures.CYAN + "\n7. Cyan" + AppearanceFeatures.RESET +
                AppearanceFeatures.GRAY + "\n8. grey" + AppearanceFeatures.RESET + "\n9. Exit[x]" + "\n" + border + " \nYour choice is: ");

        Scanner scanner = new Scanner(System.in);
        String colorChoice = scanner.nextLine().toLowerCase();
        System.out.println(border);

        switch (colorChoice) {
            case "black":
            case "1":
                System.out.println(AppearanceFeatures.BLACK);
                break;
            case "red":
            case "2":
                System.out.println(AppearanceFeatures.RED);
                break;
            case "green":
            case "3":
                System.out.println(AppearanceFeatures.GREEN);
                break;
            case "yellow":
            case "4":
                System.out.println(AppearanceFeatures.YELLOW);
                break;
            case "blue":
            case "5":
                System.out.println(AppearanceFeatures.BLUE);
                break;
            case "purple":
            case "6":
                System.out.println(AppearanceFeatures.PURPLE);
                break;
            case "cyan":
            case "7":
                System.out.println(AppearanceFeatures.CYAN);
                break;
            case "grey":
            case "8":
                System.out.println(AppearanceFeatures.GRAY);
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
