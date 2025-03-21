package core.ui.essential.pages;

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.TextConfigs.message;
import static java.lang.System.out;

public abstract class Page {
    public void displayMenu(){
        message("Basic menu",layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }
    abstract void displayListOfCommands();
}
