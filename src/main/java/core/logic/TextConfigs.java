package core.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static core.logic.AppearanceConfigs.*;
import static core.logic.DisplayManager.displayErrorAscii;
import static java.lang.System.out;

public class TextConfigs {

    public static int alignment;

    public static void wrapText(@NotNull String text, int width) {
        for (int i = 0; i < text.length(); i += width) {
            int end = Math.min(i + width, text.length());
            if (i == 0) {
                border();
            }
            modifyMessage('n',1);
            out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "You entered: ["
                    + getAnsi256Color(systemMainColor) + text.substring(i, end) + RESET
                    + getAnsi256Color(systemLayoutColor) + "]" + RESET);
            if (end < text.length()) {
                modifyMessage('n',1);
            }
        }
        modifyMessage('n',1);
    }

    public static void slowMotionText(int delay, int alignment, boolean isUnderlineActive,
                                      boolean isItalicActive, String mainText, String additionalText) {
        TextConfigs.alignment = alignment;
        String formattedText = alignment(alignment) +
                (isUnderlineActive ? UNDERLINE : "") +
                (isItalicActive ? ITALIC : "") + mainText + RESET + additionalText;

        for (char ch : formattedText.toCharArray()) {
            out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                message("Error, try again", systemRejectionColor,58,0, out::println);
            }
        }
        out.print("");
    }

    @Contract(pure = true)
    public static @NotNull String alignment(int widthOfElement) {
        int fullWidth = borderWidth + 2;
        int oneSide = (fullWidth - widthOfElement) / 2;
        return " ".repeat(Math.max(0, oneSide));
    }

    public static String capitalizeMessage(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /*Modified method System.out.println(). Added text color,
    alignment, delay and opportunity to move to the next line*/
    public static void message(String text, int color, int alignment, int delay, Consumer<String> printMethod) {

        String coloredText = getColoredText(text, color);
        String alignedText = alignment(alignment) + coloredText;

        StringBuilder output = new StringBuilder();
        for (char ch : alignedText.toCharArray()) {
            output.append(ch);
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    displayErrorAscii();
                    return;
                }
            }
        }
        printMethod.accept(output.toString());
        modifyMessage('n', 1);
    }

    public static void displayConfirmation(String preText, String midText, String postText,
                                           int acceptanceColor, int rejectionColor, int layoutColor) {
        message(preText
                + " '" + getAnsi256Color(acceptanceColor) + "y" + getAnsi256Color(layoutColor)
                + "/" + getAnsi256Color(acceptanceColor) + "+" + getAnsi256Color(layoutColor)
                + "' " + midText +" '" + getAnsi256Color(rejectionColor) + "n" + getAnsi256Color(layoutColor)
                + "/" + getAnsi256Color(rejectionColor) + "-" + getAnsi256Color(layoutColor)
                + "' " + postText, systemLayoutColor,58,0,out::print);
    }

    //make working with text easier(tabulation, next line moving and e.t.c automation)
    public static void modifyMessage(char modifier, int amount) {
        if(amount <= 0){
            displayErrorAscii();
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

    /*Show a message with [x], where x is a special character.
    Can be used as tip([i]) or a clarification([?]) or another alert message*/
    public static void alert(String modification ,String text, int alignment, int themeColor_1 ,int layoutColor) {
        out.println(alignment(alignment) + getAnsi256Color(layoutColor) + "["
                + getAnsi256Color(themeColor_1) + modification + getAnsi256Color(layoutColor) + "] "
                + getAnsi256Color(layoutColor) + text + RESET);
    }
}
