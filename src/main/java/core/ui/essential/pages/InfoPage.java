package core.ui.essential.pages;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static core.logic.CommandManager.*;
import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.appearance.TextConfigs.*;
import static java.lang.System.out;

public class InfoPage {

    @Contract(pure = true)
    public static @NotNull String getVersion() {
        String appVersion = "1.3 - Release";
        return getColorText(appVersion, mainColor);
    }

    public static void displayInfoPage() throws InterruptedException {
        marginBorder(1,2);
        message("Application info", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        message("Current version: " + getVersion(), layoutColor,getDefaultTextAlignment(),getDefaultDelay(),out::print);
        message("Author: Nick Zozulia", layoutColor,getDefaultTextAlignment(),getDefaultDelay(),out::println);

        displayApplicationDirectory();
        insertControlChars('n', 1);

        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                acceptanceColor, rejectionColor, layoutColor,getDefaultTextAlignment());

        choice("Important links", InfoPage::displayImportantLinks,
                mainColor, layoutColor, rejectionColor);
        marginBorder(2,1);
    }

    //app directory
    private static void displayApplicationDirectory() {
        try {
            String appPath = new File(
                    StartPage.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            ).getParent();

            message("Application Directory: " + getColor(mainColor) + appPath,
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        } catch (Exception e) {
            message("Could not determine application directory.",
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

    //important links
    private static void displayImportantLinks(){
        insertControlChars('n', 1);

        choice(getColor(27) + "G" + getColor(160) + "m" + getColor(220)
                + "a" + getColor(27) + "i"
                + getColor(47) + "l",
                openUri("mailto:" + "nickzozulia@gmail.com?subject=Hello&body=I%20have%20a%20question."),
                mainColor, layoutColor, rejectionColor);

        insertControlChars('n', 2);

        choice(getColor(layoutColor) + "Github", openUri("https://github.com/Akillot/MultiCLIA"),
                mainColor, layoutColor, rejectionColor);
    }
}