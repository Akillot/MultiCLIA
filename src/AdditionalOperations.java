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
}
