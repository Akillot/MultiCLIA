package ui.settings;

public class AppearanceSettings {

    //IN PROGRESS

    //public static final String GRAY = "\033[0;37m";
}
   /* static Scanner scanner = new Scanner(System.in);

    //Method which contains styles of border
    static void changeBorder() {
        System.out.println(" ");
        String[] tableStyleArray = {"+++++++++++++++++", "=================",
                "~+~+~+~+~+~+~+~+~", "~-~-~-~-~-~-~-~-~", "+-+-+-+-+-+-+-+-+",
                "~~~~~~~~~~~~~~~~~", ".................", "*****************",
                "_-_-_-_-_-_-_-_-_", "·················"};
        Random rand = new Random();
        UserInterface.fullBorder = tableStyleArray[rand.nextInt(tableStyleArray.length)];
    }

    // 4. Method for changing color of border
    static void changeBorderColor(String choiceColor) {
        switch (choiceColor) {
            case "red":
            case "1":
                UserInterface.fullBorder = RED + UserInterface.fullBorder + RESET;
                break;
            case "green":
            case "2":
                UserInterface.fullBorder = GREEN + UserInterface.fullBorder + RESET;
                break;
            case "yellow":
            case "3":
                UserInterface.fullBorder = YELLOW + UserInterface.fullBorder + RESET;
                break;
            case "blue":
            case "4":
                UserInterface.fullBorder = BLUE + UserInterface.fullBorder + RESET;
                break;
            case "purple":
            case "5":
                UserInterface.fullBorder = PURPLE + UserInterface.fullBorder + RESET;
                break;
            case "cyan":
            case "6":
                UserInterface.fullBorder = CYAN + UserInterface.fullBorder + RESET;
                break;
            case "gray":
            case "7":
                UserInterface.fullBorder = GRAY + UserInterface.fullBorder + RESET;
                break;
            case "white":
            case "8":
                UserInterface.fullBorder = WHITE + UserInterface.fullBorder + RESET;
                break;
            case "x":
            case "exit":
            case "9":
                System.out.println("Exiting...");
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
        }
    }

    // 5. Method to show a list of colors
    static void listOfColors() {
        System.out.print("To which color\ndo you want to change: " +
                RED + "\n1. Red" + RESET + GREEN + "\n2. Green" + RESET +
                YELLOW + "\n3. Yellow" + RESET + BLUE + "\n4. Blue" + RESET +
                PURPLE + "\n5. Purple" + RESET + CYAN + "\n6. Cyan" + RESET +
                GRAY + "\n7. Gray" + RESET + WHITE + "\n8. White" + "\n9. Exit[x]" + RESET + "\n" + UserInterface.border + " \nYour choice is: ");
    }

    // 2. Method for changing color of border or text
    static void changeColor() {
        System.out.println("Pick the color of \nwhat you want to change.\n" + UserInterface.border);
        System.out.println("1. Text[_]\n2. Border[-]\n3. Exit[x]\n" + UserInterface.border + " \nYour choice is: ");

        String partColorChoice = scanner.nextLine().toLowerCase();
        System.out.println(UserInterface.border);
        String choiceColor;

        switch (partColorChoice) {
            case "1":
            case "_":
            case "text":
            listOfColors();
            choiceColor = scanner.nextLine().toLowerCase();
                System.out.println(UserInterface.border);
            changeTextColor(choiceColor);
                break;

            case "2":
            case "-":
            case "border":
            listOfColors();
            choiceColor = scanner.nextLine().toLowerCase();
                System.out.println(UserInterface.border);
            changeBorderColor(choiceColor);
                break;

            case "3":
            case "exit":
            case "x":
            System.out.println("Exiting...\n");
                break;
        }
    }

    // 1. Method for choosing basic changes of color or border
    public static void basicChanges() {
        System.out.println("Choose what you\nwant to change.\n" + UserInterface.fullBorder);
        System.out.println("1. Color[~]\n2. Border[-]\n3. Exit[x]\n" + UserInterface.fullBorder + " \nYour choice is: ");
        String basicChangesChoice = scanner.nextLine().toLowerCase();
        System.out.println(UserInterface.fullBorder);
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
*/

