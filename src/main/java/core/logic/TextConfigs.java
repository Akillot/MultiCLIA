package core.logic;

import static core.logic.BorderConfigs.*;
import static core.logic.ColorConfigs.*;
import static core.logic.DisplayManager.message;
import static core.logic.DisplayManager.messageModifier;
import static java.lang.System.out;

public class TextConfigs {

    public static int alignment;

    public static void wrapText(String text, int width) {
        for (int i = 0; i < text.length(); i += width) {
            int end = Math.min(i + width, text.length());
            if (i == 0) {
                border();
                out.print(BOLD + alignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    messageModifier('n',1);
                }
            } else {
                out.print(BOLD + alignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    messageModifier('n',1);
                }
            }
        }
        messageModifier('n',1);
    }

    public static void slowMotionText(int delay, int alignment, boolean isUnderlineActive,
                                      boolean isItalicActive, String mainText, String additionalText) {
        TextConfigs.alignment = alignment;
        String formattedText = alignment(alignment) +
                (isUnderlineActive ? UNDERLINE : "") +
                (isItalicActive ? ITALIC : "") +
                BOLD + mainText + RESET + additionalText;

        for (char ch : formattedText.toCharArray()) {
            out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                message("Error, try again", "red", 58,0, out::println);
            }
        }
        out.print("");
    }

    public static String alignment(int widthOfElement) {
        int fullWidth = borderWidth + 2;
        int oneSide = (fullWidth - widthOfElement) / 2;
        return " ".repeat(Math.max(0, oneSide));
    }

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
