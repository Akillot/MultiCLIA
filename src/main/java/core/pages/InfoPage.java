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
        String appVersion = "A-0.8.5.1";
        return getAnsi256Color(sysMainColor) + appVersion;
    }

    public static void displayInfo() throws InterruptedException {
        marginBorder(1,2);
        message("Application info", sysLayoutColor, 58, 0, out::print);
        modifyMessage('n',1);
        message("Current version: " + getVersion(), sysLayoutColor,58,0,out::print);
        message("Author: Nick Zozulia", sysLayoutColor,58,0,out::print);
        modifyMessage('n', 1);

        displayOs();
        displayApplicationDirectory();

        modifyMessage('n', 1);
        displayJavaInfo();
        modifyMessage('n', 1);

        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                sysAcceptanceColor, sysRejectionColor, sysLayoutColor,58);

        choice("Important links", InfoPage::displayImportantLinks,
                sysMainColor, sysLayoutColor, sysRejectionColor);
        marginBorder(2,1);
    }

    private static void displayOs(){
        String operatingSystem = System.getProperty("os.name");
        message("OS: " + getAnsi256Color(sysMainColor) + operatingSystem, sysLayoutColor,58,0,out::print);
    }

    private static void displayJavaInfo() {
        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        message("Java Version: " + getAnsi256Color(sysMainColor) + javaVersion,
                sysLayoutColor, 58, 0, out::print);

        message("Java Vendor: " + getAnsi256Color(sysMainColor) + javaVendor,
                sysLayoutColor, 58, 0, out::print);
    }

    private static void displayApplicationDirectory() {
        try {
            String appPath = new File(
                    StartPage.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            ).getParent();

            message("Application Directory: " + getAnsi256Color(sysMainColor) + appPath,
                    sysLayoutColor, 58, 0, out::print);
        } catch (Exception e) {
            message("Could not determine application directory.",
                    sysLayoutColor, 58, 0, out::print);
        }
    }

    private static void displayImportantLinks(){
        modifyMessage('n', 1);

        choice(getAnsi256Color(27) + "G" + getAnsi256Color(160) + "m" + getAnsi256Color(220)
                + "a" + getAnsi256Color(27) + "i"
                + getAnsi256Color(47) + "l",
                openUri("mailto:" + "nickzozulia@gmail.com?subject=Hello&body=I%20have%20a%20question."),
                sysMainColor, sysLayoutColor, sysRejectionColor);

        modifyMessage('n', 2);

        choice(getAnsi256Color(sysLayoutColor) + "Github", openUri("https://github.com/Akillot/MultiCLIA"),
                sysMainColor, sysLayoutColor, sysRejectionColor);
    }
}