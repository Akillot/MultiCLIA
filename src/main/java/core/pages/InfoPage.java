package core.pages;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.configs.TextConfigs.*;
import static java.lang.System.out;

public  class InfoPage {

    @Contract(pure = true)
    public static @NotNull String getVersion() {
        String appVersion = "A-0.8.6.1.3";
        return getColorText(appVersion, sysMainColor);
    }

    public static void displayInfoPage() throws InterruptedException {
        marginBorder(1,2);
        message("Application info", sysLayoutColor, 58, 0, out::println);
        message("Current version: " + getVersion(), sysLayoutColor,58,0,out::print);
        message("Author: Nick Zozulia", sysLayoutColor,58,0,out::println);

        displayApplicationDirectory();
        modifyMessage('n', 1);

        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                sysAcceptanceColor, sysRejectionColor, sysLayoutColor,58);

        choice("Important links", InfoPage::displayImportantLinks,
                sysMainColor, sysLayoutColor, sysRejectionColor);
        marginBorder(2,1);
    }

    //app directory
    private static void displayApplicationDirectory() {
        try {
            String appPath = new File(
                    StartPage.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            ).getParent();

            message("Application Directory: " + getColor(sysMainColor) + appPath,
                    sysLayoutColor, 58, 0, out::print);
        } catch (Exception e) {
            message("Could not determine application directory.",
                    sysLayoutColor, 58, 0, out::print);
        }
    }

    //important links
    private static void displayImportantLinks(){
        modifyMessage('n', 1);

        choice(getColor(27) + "G" + getColor(160) + "m" + getColor(220)
                + "a" + getColor(27) + "i"
                + getColor(47) + "l",
                openUri("mailto:" + "nickzozulia@gmail.com?subject=Hello&body=I%20have%20a%20question."),
                sysMainColor, sysLayoutColor, sysRejectionColor);

        modifyMessage('n', 2);

        choice(getColor(sysLayoutColor) + "Github", openUri("https://github.com/Akillot/MultiCLIA"),
                sysMainColor, sysLayoutColor, sysRejectionColor);
    }
}