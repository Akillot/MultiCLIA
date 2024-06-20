import java.util.Random;

public class AdditionalOperations {
    static String border = "-------------------";
    static void changeBorder() {
        System.out.println(" ");
        String[] tableStyleArray = {"+++++++++++++++++++", "===================",
                "~+~+~+~+~+~+~+~+~+~", "~-~-~-~-~-~-~-~-~-~", "+-+-+-+-+-+-+-+-+-+",
                "~~~~~~~~~~~~~~~~~~~", "...................", "*******************"};
        Random rand = new Random();
        border = tableStyleArray[rand.nextInt(tableStyleArray.length)];
    }

   /* static void changeColor() {
        //Random rand = new Random();
        System.out.println("To which color do you want to change: " +
                "\nColors:" + ConsoleColors.BLACK + "\n1. Black" + ConsoleColors.RESET +
                ConsoleColors.RED + "\n2. Red" + ConsoleColors.RESET + ConsoleColors.GREEN + "\n3. Green" + ConsoleColors.RESET +
                ConsoleColors.YELLOW + "\n4. Yellow" + ConsoleColors.RESET + ConsoleColors.BLUE + "\n5. Blue" + ConsoleColors.RESET +
                ConsoleColors.PURPLE +"\n6. Purple" + ConsoleColors.RESET + ConsoleColors.CYAN + "\n7. Cyan" + ConsoleColors.RESET +
                ConsoleColors.WHITE + "\n8. White" + ConsoleColors.RESET + "\n9. Close\n");
    }*/
}
