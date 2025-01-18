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
        String appVersion = "A-0.8.4";
        return getAnsi256Color(systemMainColor) + appVersion;
    }

    public static void displayInfo() throws InterruptedException {
        marginBorder(1,2);
        message("Application info", systemLayoutColor, 58, 0, out::print);
        modifyMessage('n',1);
        message("Current version: " + getVersion(), systemLayoutColor,58,0,out::print);
        message("Author: Nick Zozulia", systemLayoutColor,58,0,out::print);
        modifyMessage('n', 1);

        displayOs();
        displayApplicationDirectory();

        modifyMessage('n', 1);
        displayJavaInfo();
        modifyMessage('n', 1);

        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                systemAcceptanceColor, systemRejectionColor, systemLayoutColor);

        choice("Important links", InfoPage::displayImportantLinks,
                systemMainColor, systemLayoutColor,systemRejectionColor);
        marginBorder(2,1);
    }

    private static void displayOs(){
        String operatingSystem = System.getProperty("os.name");
        message("OS: " + getAnsi256Color(systemMainColor) + operatingSystem, systemLayoutColor,58,0,out::print);
    }

    private static void displayJavaInfo() {
        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        message("Java Version: " + getAnsi256Color(systemMainColor) + javaVersion,
                systemLayoutColor, 58, 0, out::print);

        message("Java Vendor: " + getAnsi256Color(systemMainColor) + javaVendor,
                systemLayoutColor, 58, 0, out::print);
    }

    private static void displayApplicationDirectory() {
        try {
            String appPath = new File(
                    StartPage.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            ).getParent();

            message("Application Directory: " + getAnsi256Color(systemMainColor) + appPath,
                    systemLayoutColor, 58, 0, out::print);
        } catch (Exception e) {
            message("Could not determine application directory.",
                    systemLayoutColor, 58, 0, out::print);
        }
    }

    private static void displayImportantLinks(){
        modifyMessage('n', 1);

        choice(getAnsi256Color(27) + "G" + getAnsi256Color(160) + "m" + getAnsi256Color(220)
                + "a" + getAnsi256Color(27) + "i"
                + getAnsi256Color(47) + "l",
                openUri("mailto:" + "nickzozulia@gmail.com?subject=Hello&body=I%20have%20a%20question."),
                systemMainColor, systemLayoutColor, systemRejectionColor);

        modifyMessage('n', 2);

        choice(getAnsi256Color(systemLayoutColor) + "Github", openUri("https://github.com/Akillot/MultiCLIA"),
                systemMainColor, systemLayoutColor, systemRejectionColor);
    }
}