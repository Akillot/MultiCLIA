package core.ui.essential.pages;

import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.appearance.TextConfigs.insertControlChars;
import static core.ui.essential.configs.appearance.TextConfigs.message;
import static java.lang.System.out;

public abstract class Page {
    public void displayMenu(){
    }

    protected String[][] commands;

    protected void displayListOfCommands(String[][] commands) {
        insertControlChars('n', 1);
        for (String[] command : commands) {
            message("·  " + command[0] + " [" + getColor(getMainColor()) + command[1] + getColor(getLayoutColor()) + "]",
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
        insertControlChars('n', 1);
    }
}
