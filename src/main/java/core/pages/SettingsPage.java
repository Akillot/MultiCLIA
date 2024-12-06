package core.pages;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.choice;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;
import static core.logic.TextConfigs.message;
import static java.lang.System.out;

public class SettingsPage {
    public static void displaySettings() {
        modifyMessage('n', 2);
        out.print(alignment(60) + BOLD + "-" + BLUE + ITALIC + "Memory\n" + RESET);
        displayUsingMemory();

        modifyMessage('n', 2);
        out.print(alignment(60) + BOLD + "-" + BLUE + ITALIC + "Text\n" + RESET);
        choice("Text", textModification());
        marginBorder();
    }

    public static void displayUsingMemory(){
        message("Memory used: " +
                ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                        / (1000 * 1000) + "M"), "white", 58,0, out::print);
    }

    @Contract(pure = true)
    public static @NotNull Runnable textModification() {
        return () -> {
            modifyMessage('n', 1);
            message("All colors and text modifiers", "white", 58,0, out::print);
            modifyMessage('n', 1);
            for (String color : colors) {
                message(color, color, 58,0, out::print);
            }
            modifyMessage('n', 1);
            message("Bold", "white", 58,0, out::print);
            message(ITALIC + "Italic", "white", 58,0, out::print);
            message(UNDERLINE + "Underline", "white", 58,0, out::print);
        };
    }
}
