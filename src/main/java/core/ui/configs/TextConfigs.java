package core.ui.configs;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static core.ui.configs.AppearanceConfigs.*;
import static java.lang.System.out;

public class TextConfigs {

    public static int alignment;
    public static void slowMotionText(int delay, int alignment, boolean isUnderlineActive,
                                      String mainText, String additionalText) {
        TextConfigs.alignment = alignment;
        String formattedText = alignment(alignment) +
                (isUnderlineActive ? UNDERLINE : "") + mainText + RESET + additionalText;

        for (char ch : formattedText.toCharArray()) {
            out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                message("Error, try again",
                        getRejectionColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);
            }
        }
        out.print("");
    }

    @Contract(pure = true)
    public static @NotNull String alignment(int widthOfElement) {
        int fullWidth = DEFAULT_BORDER_WIDTH + 2;
        int oneSide = (fullWidth - widthOfElement) / 2;
        return " ".repeat(Math.max(0, oneSide));
    }

    public static String capitalizeMessage(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static void message(String text, int color,
                               int alignment, int delay, Consumer<String> printMethod) {

        String coloredText = getColorText(text, color);
        String alignedText = alignment(alignment) + coloredText;

        StringBuilder output = new StringBuilder();
        for (char ch : alignedText.toCharArray()) {
            output.append(ch);
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    return;
                }
            }
        }
        printMethod.accept(output.toString());
        insertControlChars('n', 1);
    }

    public static void displayConfirmation(String preText, String confirmation_1, String confirmation_2,
                                           String midText, String rejection_1, String rejection_2, String postText,
                                           int acceptanceColor, int rejectionColor, int layoutColor, int alignment) {
        message(preText
                + " '" + getColor(acceptanceColor) + confirmation_1 + getColor(layoutColor)
                + "/" + getColor(acceptanceColor) + confirmation_2 + getColor(layoutColor)
                + "' " + midText +" '" + getColor(rejectionColor) + rejection_1 + getColor(layoutColor)
                + "/" + getColor(rejectionColor) + rejection_2 + getColor(layoutColor)
                + "' " + postText, layoutColor,alignment,getDefaultDelay(),out::print);
    }

    public static void insertControlChars(char modifier, int amount) {
        if(amount < 0){
            message("Error, number of modifiers is less than 0.",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        }
        String output = switch(modifier){
            case 'n' -> "\n";
            case 't' -> "\t";
            case 'b' -> "\b";
            case 'r' -> "\r";
            case '\\' -> "\\";
            default -> "\\" + modifier;
        };

        for (int i = 0; i < amount; i++) {
            out.print(output);
        }
    }

    public static @NotNull String formatResponse(@NotNull String text,
                                                 int maxLength,
                                                 @NotNull String prefix) {

        StringBuilder formatted = new StringBuilder(prefix);
        int lineLength = prefix.length();

        for (String word : text.split(" ")) {
            if (lineLength + word.length() + 1 > maxLength) {
                formatted.append("\n").append(alignment(getDefaultTextAlignment())).append("   ");
                lineLength = 3;
            } else {
                formatted.append(" ");
                lineLength++;
            }

            formatted.append(word);
            lineLength += word.length();
        }

        return formatted.toString();
    }
}
