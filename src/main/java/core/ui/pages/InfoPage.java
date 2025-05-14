package core.ui.pages;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static core.CommandManager.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class InfoPage {

    @Contract(pure = true)
    public static @NotNull String getVersion() {
        loadConfig();
        String appVersion = "1" + getColor(getLayoutColor()) + "." + getColor(getMainColor()) + "5 ";
        return getColorText(appVersion, getMainColor());
    }

    public static void displayInfoPage() throws InterruptedException {
        marginBorder(1,2);
        message("Application info:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        message("Current version: " + getVersion(), getLayoutColor(),getDefaultTextAlignment(),getDefaultDelay(),out::print);

        message("Platform: Classic [" + getColor(75) + "Mac" + getColor(getLayoutColor()) + "OS, " + getColor(74)
                + getColor(207) +"Linux" + getColor(getLayoutColor()) + "]", getLayoutColor(),
                getDefaultTextAlignment(),getDefaultDelay(),out::print);

        message("Author: Nick Zozulia", getLayoutColor(), getDefaultTextAlignment(),getDefaultDelay(),out::println);

        displayApplicationDirectory();
        insertControlChars('n', 1);

        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                getAcceptanceColor(), getRejectionColor(), getLayoutColor(),getDefaultTextAlignment());

        choice("Important links", InfoPage::displayImportantLinks,
                getMainColor(), getLayoutColor(), getRejectionColor());
        marginBorder(2,1);
    }

    private static void displayApplicationDirectory() {
        try {
            String appPath = new File(
                    StartPage.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            ).getParent();

            message("Application Directory: " + getColor(getMainColor()) + appPath,
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        } catch (Exception e) {
            message("Could not determine application directory.",
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

    private static void displayImportantLinks(){
        insertControlChars('n', 1);

        choice(getColor(27) + "G" + getColor(160) + "m" + getColor(220)
                + "a" + getColor(27) + "i"
                + getColor(47) + "l",
                openUri("mailto:" + "nickzozulia@gmail.com?subject=Hello&body=I%20have%20a%20question."),
                getMainColor(), getLayoutColor(), getRejectionColor());

        insertControlChars('n', 1);

        choice(getColor(getLayoutColor()) + "Github", openUri("https://github.com/Akillot/MultiCLIA"),
                getMainColor(), getLayoutColor(), getRejectionColor());
    }
}