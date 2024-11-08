package core.layout;

import static core.layout.BorderFunc.*;
import static core.layout.ColorFunc.*;
import static core.layout.DisplayManager.message;
import static java.lang.System.out;

public class TextFunc {

    public static int alignment;

    public static void wrapText(String text, int width) {
        for (int i = 0; i < text.length(); i += width) {
            int end = Math.min(i + width, text.length());
            if (i == 0) {
                bigBorder();
                out.print(BOLD + alignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    out.println();
                }
            } else {
                out.print(BOLD + alignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    out.println();
                }
            }
        }
        out.println();
    }

    public static void slowMotionText(int delay, int alignment, boolean isUnderlineActive,
                                      boolean isItalicActive, String mainText, String additionalText) {
        TextFunc.alignment = alignment;
        String formattedText = alignment(alignment) +
                (isUnderlineActive ? UNDERLINE : "") +
                (isItalicActive ? ITALIC : "") +
                BOLD + mainText + RESET + additionalText;

        for (char ch : formattedText.toCharArray()) {
            out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                message("Error, try again", "red", 58,0);
            }
        }
        out.print("");
    }

    public static String alignment(int widthOfElement) {
        int fullWidth = borderWidth + 2;
        int oneSide = (fullWidth - widthOfElement) / 2;
        return " ".repeat(Math.max(0, oneSide));
    }
}
