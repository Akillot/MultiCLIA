package ui.layout;

import static ui.layout.BorderWork.borderWidth;
import static ui.layout.BorderWork.drawTripleBorder;
import static ui.layout.ColorWork.*;

public class TextWork {

    public static void wrapText(String text, int width) {
        for (int i = 0; i < text.length(); i += width) {
            int end = Math.min(i + width, text.length());
            if (i == 0) {
                drawTripleBorder();
                System.out.print(BOLD + contentAlignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    System.out.println();
                }
            } else {
                System.out.print(BOLD + contentAlignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    System.out.println();
                }
            }
        }
        System.out.println();
    }

    //Show slow motion text
    public static void displaySlowMotionText(int delay, int alignment, boolean isUnderlineActive, String mainText, String additionalText) {
        String formattedText = contentAlignment(alignment) +
                (isUnderlineActive ? UNDERLINE : "") +
                BOLD + mainText + RESET + additionalText;

        for (char ch : formattedText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorCommand("Error, try again", "red", (byte) 0);
            }
        }
        System.out.print("");
    }

    //Aligns the content
    public static String contentAlignment(int widthOfElement) {
        int fullWidth = borderWidth + 2; //Where 2 is "+"
        int oneSide = (fullWidth - widthOfElement) / 2;
        return " ".repeat(Math.max(0, oneSide));
    }
}
