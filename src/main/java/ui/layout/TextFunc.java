package ui.layout;

import static ui.layout.BasicFunc.displayContent;
import static ui.layout.BorderFunc.*;
import static ui.layout.ColorFunc.*;

public class TextFunc {

    public static int alignment;

    public static void wrapText(String text, int width) {
        for (int i = 0; i < text.length(); i += width) {
            int end = Math.min(i + width, text.length());
            if (i == 0) {
                displayBigBorder();
                System.out.print(BOLD + alignmentLogic(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    System.out.println();
                }
            } else {
                System.out.print(BOLD + alignmentLogic(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    System.out.println();
                }
            }
        }
        System.out.println();
    }

    public static void displaySlowMotionText(int delay, int alignment, boolean isUnderlineActive, String mainText, String additionalText) {
        TextFunc.alignment = alignment;
        String formattedText = alignmentLogic(alignment) +
                (isUnderlineActive ? UNDERLINE : "") +
                BOLD + mainText + RESET + additionalText;

        for (char ch : formattedText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayContent("Error, try again", "red", 0);
            }
        }
        System.out.print("");
    }

    public static String alignmentLogic(int widthOfElement) {
        int fullWidth = borderWidth + 2;
        int oneSide = (fullWidth - widthOfElement) / 2;
        return " ".repeat(Math.max(0, oneSide));
    }
}
