import java.util.Random;

public class AdditionalOperations {
    static String border = "-------------------";

    static void changeBorder() {
        String[] tableStyleArray = {"+++++++++++++++++++", "===================",
                "<><><><><@><><><><>", "<.><.><.>^<.><.><.>", "+-+-+-+-+-+-+-+-+-+",
                "~~~~~~~~~~~~~~~~~~~", "...................",};
        Random rand = new Random();
        for (int i = 0; i < tableStyleArray.length; i++) {
            tableStyleArray[i] = tableStyleArray[rand.nextInt(tableStyleArray.length)];
            border = String.valueOf(tableStyleArray[i]);
        }
    }
}
