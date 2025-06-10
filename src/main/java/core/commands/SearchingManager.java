package core.commands;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.AppearanceConfigs.getLayoutColor;
import static core.ui.configs.AppearanceConfigs.getSearchingArrow;
import static core.ui.configs.DisplayManager.scanner;
import static core.ui.configs.TextConfigs.slowMotionText;
import static java.lang.System.out;

public class SearchingManager {
    public static void search() {
        PackageUnifier registry = new PackageUnifier();
        try {
            slowMotionText(getDefaultDelay(),
                    getSearchingLineAlignment(),
                    false,
                    getColor(getLayoutColor()) + getSearchingArrow(),
                    "");

            String nameOfFunction = scanner.nextLine().toLowerCase();
            if (!registry.executeCommand(nameOfFunction)) search();
        }
        catch(Exception e){
            out.print("");
        }
    }
}
