package core.ui.essential.pages;

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.TextConfigs.insertControlChars;
import static core.ui.essential.configs.TextConfigs.message;
import static java.lang.System.out;

public abstract class Page {
    public void displayMenu(){
        message("Basic menu",layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    protected String[][] commands;

    protected void displayListOfCommands(String[][] commands) {
        insertControlChars('n', 1);
        for (String[] command : commands) {
            message("·  " + command[0] + " [" + getColor(mainColor) + command[1] + getColor(layoutColor) + "]",
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
        insertControlChars('n', 1);
    }
}
