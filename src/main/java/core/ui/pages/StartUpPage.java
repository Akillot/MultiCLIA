package core.ui.pages;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.clearTerminal;
import static core.ui.configs.TextConfigs.message;
import static java.lang.System.out;

public class StartUpPage {

    public static void displayStartUpPage() {
        clearTerminal();
        message("Good " ,
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        marginBorder(0,1);
    }
}
